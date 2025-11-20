package org.project.action;

import java.util.List;

import org.employee.model.Employee;
import org.project.dea.EmployeeProjectDAO;

import jakarta.servlet.ServletException;
import java.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GetProjectMembers extends ProjectAction {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String projectIdStr = req.getParameter("projectId");
            
            if (projectIdStr == null || projectIdStr.isEmpty()) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Project ID is required");
                return;
            }

            int projectId = Integer.parseInt(projectIdStr);
            EmployeeProjectDAO empProjDao = new EmployeeProjectDAO();
            List<Employee> employees = empProjDao.getEmployeesByProject(projectId);

            StringBuilder sb = new StringBuilder();
            sb.append("<h3>Project Members</h3>");
            
            if (employees == null || employees.isEmpty()) {
                sb.append("<p>No employees assigned to this project</p>");
                resp.getWriter().write(sb.toString());
                return;
            }

            sb.append("<table style='width: 100%; border-collapse: collapse;'>");
            sb.append("<tr style='background: #f0f0f0;'>");
            sb.append("<th style='border: 1px solid #ddd; padding: 8px;'>Employee ID</th>");
            sb.append("<th style='border: 1px solid #ddd; padding: 8px;'>Name</th>");
            sb.append("<th style='border: 1px solid #ddd; padding: 8px;'>Last Name</th>");
            sb.append("<th style='border: 1px solid #ddd; padding: 8px;'>Rank</th>");
            sb.append("<th style='border: 1px solid #ddd; padding: 8px;'>Employment Date</th>");
            sb.append("</tr>");

            for (Employee e : employees) {
                sb.append("<tr>");
                sb.append("<td style='border: 1px solid #ddd; padding: 8px;'>" + e.getEmployeeId() + "</td>");
                sb.append("<td style='border: 1px solid #ddd; padding: 8px;'>" + e.getName() + "</td>");
                sb.append("<td style='border: 1px solid #ddd; padding: 8px;'>" + e.getLastName() + "</td>");
                sb.append("<td style='border: 1px solid #ddd; padding: 8px;'>" + e.getRank() + "</td>");
                sb.append("<td style='border: 1px solid #ddd; padding: 8px;'>" + e.getEmployementDate() + "</td>");
                sb.append("</tr>");
            }
            sb.append("</table>");

            resp.getWriter().write(sb.toString());

        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid project ID");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to retrieve project members.");
        }
    }
}
