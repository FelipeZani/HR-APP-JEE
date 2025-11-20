package org.department.action;

import java.util.List;
import java.util.Set;

import org.department.model.Department;
import org.employee.model.Employee;

import jakarta.servlet.ServletException;
import java.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GetDepartmentEmployees extends DepartmentAction {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String departmentIdStr = req.getParameter("departmentId");
            
            if (departmentIdStr == null || departmentIdStr.isEmpty()) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing departmentId");
                return;
            }

            int departmentId = Integer.parseInt(departmentIdStr);
            List<Department> departments = DepartmentAction.getDao().getDepartementList();
            
            Department targetDepartment = null;
            for (Department d : departments) {
                if (d.getDepartmentId() == departmentId) {
                    targetDepartment = d;
                    break;
                }
            }

            StringBuilder sb = new StringBuilder();
            
            if (targetDepartment == null) {
                sb.append("<div>Department not found</div>");
                resp.getWriter().write(sb.toString());
                return;
            }

            Set<Employee> employees = targetDepartment.getDepartmentEmployees();
            
            sb.append("<h3>Department: " + targetDepartment.getName() + "</h3>");
            sb.append("<div style='margin: 15px 0;'>");
            
            if (employees == null || employees.isEmpty()) {
                sb.append("<p><em>No employees in this department</em></p>");
            } else {
                sb.append("<table style='width: 100%; border-collapse: collapse;'>");
                sb.append("<thead>");
                sb.append("<tr style='background: #f0f0f0; border-bottom: 2px solid #ccc;'>");
                sb.append("<th style='padding: 10px; text-align: left; border: 1px solid #ddd;'>ID</th>");
                sb.append("<th style='padding: 10px; text-align: left; border: 1px solid #ddd;'>Name</th>");
                sb.append("<th style='padding: 10px; text-align: left; border: 1px solid #ddd;'>Last Name</th>");
                sb.append("<th style='padding: 10px; text-align: left; border: 1px solid #ddd;'>Rank</th>");
                sb.append("<th style='padding: 10px; text-align: left; border: 1px solid #ddd;'>Employment Date</th>");
                sb.append("</tr>");
                sb.append("</thead>");
                sb.append("<tbody>");
                
                for (Employee emp : employees) {
                    sb.append("<tr style='border-bottom: 1px solid #ddd;'>");
                    sb.append("<td style='padding: 10px; border: 1px solid #ddd;'>" + emp.getEmployeeId() + "</td>");
                    sb.append("<td style='padding: 10px; border: 1px solid #ddd;'>" + emp.getName() + "</td>");
                    sb.append("<td style='padding: 10px; border: 1px solid #ddd;'>" + emp.getLastName() + "</td>");
                    sb.append("<td style='padding: 10px; border: 1px solid #ddd;'>" + emp.getRank() + "</td>");
                    sb.append("<td style='padding: 10px; border: 1px solid #ddd;'>" + emp.getEmployementDate() + "</td>");
                    sb.append("</tr>");
                }
                
                sb.append("</tbody>");
                sb.append("</table>");
            }
            
            sb.append("</div>");
            resp.getWriter().write(sb.toString());

        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid departmentId");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to retrieve department employees.");
        }
    }

}
