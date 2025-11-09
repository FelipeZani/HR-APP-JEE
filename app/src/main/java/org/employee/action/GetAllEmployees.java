package org.employee.action;

import java.util.List;

import org.employee.model.Employee;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GetAllEmployees extends EmployeesAction {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        try {

            StringBuilder responseHTML = new StringBuilder("");
            List<Employee> empList = EmployeesAction.dao.getAllEmployees();

            if (empList.isEmpty() || empList == null) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Employee data unavailable");
                return;
            }

            for (Employee employee : empList) {
                responseHTML.append( "<div class='row' data-id='" + employee.getEmployeeId() + "'>" +
                        "<div class=\'name\'>" + employee.getName() + " " + employee.getLastName() + "</div>" +
                        "<div class=\'post\'>" + employee.getPost().getLabel() + "</div>" +
                        "<div class=\'department\'>" + employee.getDepartment().getName() + "</div>" +
                        "<div class=\'rank\'>" + employee.getRank() + "</div>" +
                        "<div><button onclick=\"modifyEmployee(\'" + employee.getEmployeeId() + "\')\">modify</button>"+
                        "<button onclick=\"removeEmployee(\'" + employee.getEmployeeId() + "\')\">remove</button>" +
                        "</div>" +

                        "</div>");
            }
            
            resp.getWriter().write(responseHTML.toString());

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to retrieve employee data.");

        }
    }

}
