package org.project.action;

import org.employee.dea.EmployeeDAO;
import org.employee.model.Employee;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GetEmployeesForSelect extends ProjectAction {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            EmployeeDAO empDao = new EmployeeDAO();
            List<Employee> employees = empDao.getAllEmployees();

            StringBuilder sb = new StringBuilder();
            sb.append("<option value=''>-- Select an employee --</option>");

            if (employees != null && !employees.isEmpty()) {
                for (Employee e : employees) {
                    sb.append("<option value='" + e.getEmployeeId() + "'>");
                    sb.append(e.getName() + " " + e.getLastName());
                    sb.append("</option>");
                }
            }

            resp.getWriter().write(sb.toString());

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to retrieve employees.");
        }
    }
}
