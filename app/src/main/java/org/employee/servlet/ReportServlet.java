package org.employee.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Collections;
import org.employee.model.ChartData;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.employee.dea.EmployeeDAO;
import org.employee.model.ChartData;
import org.employee.model.Employee;

@WebServlet("/report/employees")
public class ReportServlet extends HttpServlet {

@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            EmployeeDAO employeeDAO = new EmployeeDAO();
            
            // --- 1. DÉCLARATION DES VARIABLES EN HAUT (Correction ici) ---
            String idParam = request.getParameter("id");
            String typeParam = request.getParameter("type");
            
            String templateFile;
            String outputFilename;
            
            // On prépare ces variables maintenant pour pouvoir les utiliser dans les if/else
            Map<String, Object> parameters = new HashMap<>();
            JRBeanCollectionDataSource dataSource; 

            if ("dashboard".equals(typeParam)) {
                // === CAS 0 : DASHBOARD ===
                List<Employee> allEmployees = employeeDAO.getAllEmployees();

                // Calculs des stats (Rank, Post, Wage)
                Map<String, Long> rankCounts = allEmployees.stream()
                    .collect(Collectors.groupingBy(e -> e.getRank().toString(), Collectors.counting()));
                
                List<ChartData> rankList = new ArrayList<>();
                rankCounts.forEach((k, v) -> rankList.add(new ChartData(k, v)));

                Map<String, Long> postCounts = allEmployees.stream()
                    .collect(Collectors.groupingBy(e -> e.getPost().getLabel(), Collectors.counting()));

                List<ChartData> postList = new ArrayList<>();
                postCounts.forEach((k, v) -> postList.add(new ChartData(k, v)));

                Map<String, Double> wageSums = allEmployees.stream()
                    .collect(Collectors.groupingBy(
                        e -> e.getPost().getLabel(), 
                        Collectors.summingDouble(e -> e.getPost().getWage())
                    ));

                List<ChartData> wageList = new ArrayList<>();
                wageSums.forEach((k, v) -> wageList.add(new ChartData(k, v)));
                System.out.println("--- DEBUG DASHBOARD ---");
                System.out.println("Nombre total d'employés trouvés : " + allEmployees.size());
                System.out.println("Données Rangs (RankData) : " + rankList.size());
                System.out.println("Données Postes (PostData) : " + postList.size());

                // Vérifie le contenu du premier élément si possible
                if (!rankList.isEmpty()) {
                    System.out.println("Exemple Rank: " + rankList.get(0).getLabel() + " = " + rankList.get(0).getValue());
                }
                // Remplissage des paramètres
                parameters.put("RankData", rankList);
                parameters.put("PostData", postList);
                parameters.put("WageData", wageList);

                // Pour le dashboard, la source de données principale est vide (tout passe par les paramètres)
                dataSource = new JRBeanCollectionDataSource(new ArrayList<>());
                
                templateFile = "/dashboard.jrxml";
                outputFilename = "Dashboard_RH.pdf";

            } else {
                // === CAS 1 & 2 : LISTE OU FICHE EMPLOYÉ ===
                List<Employee> data;
                
                if (idParam != null && !idParam.isEmpty()) {
                    Employee emp = employeeDAO.getEmployeeById(idParam);
                    data = Collections.singletonList(emp); 
                    templateFile = "/test.jrxml";
                    outputFilename = "fiche_employe_" + emp.getLastName() + ".pdf";
                } else {
                    data = employeeDAO.getAllEmployees();
                    templateFile = "/test.jrxml";
                    outputFilename = "liste_employes.pdf";
                }
                
                // Ici, on remplit la dataSource classique avec la liste des employés
                dataSource = new JRBeanCollectionDataSource(data);
            }

            // --- 2. GÉNÉRATION DU PDF ---
            InputStream reportStream = this.getClass().getResourceAsStream(templateFile);
            if (reportStream == null) {
                response.getWriter().println("Erreur : Template introuvable " + templateFile);
                return;
            }
            
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            
            // On utilise les variables 'parameters' et 'dataSource' qu'on a remplies plus haut
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=\"" + outputFilename + "\"");
            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Erreur : " + e.getMessage());
        }
    }
}