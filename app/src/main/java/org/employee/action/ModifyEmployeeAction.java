package org.employee.action;

import org.employee.model.Employee;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ModifyEmployeeAction extends EmployeesAction {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {

            String employeeId = request.getParameter("id");
            String name = request.getParameter("name");
            String lastname = request.getParameter("lastname");

            String rank = request.getParameter("rank");
            String password = request.getParameter("newPassword");

            String newName = "";
            String newLastName = "";
            String newRank = "";
            String newPassword = "";
            System.out.println(employeeId);

            Employee oldEmp = EmployeesAction.dao.getEmployeeById(employeeId);

            if (oldEmp == null) {

                System.out.println("EmployeeId not found");
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
                return;

            }

            if (name != null && !name.isEmpty()) {
                newName += name;
            } else {
                newName += oldEmp.getName();
            }

            if (lastname != null && !lastname.isEmpty()) {
                newLastName += lastname;
            } else {
                newLastName += oldEmp.getLastName();
            }

            if (Rank.getRank(rank) != null) {
                newRank = Rank.getRank(rank).toString();
            } else {
                newRank = oldEmp.getRank();
            }

            if (password != null && !password.isEmpty()) {
                newPassword = EmployeesAction.hashString(password);
            } else {
                newPassword = oldEmp.getUserAccount().getPassword();
            }

            oldEmp.setName(newName);
            oldEmp.setLastName(newLastName);
            oldEmp.setRank(newRank);
            oldEmp.getUserAccount().setPassword(newPassword);
            oldEmp.getUserAccount().setUsername(newLastName.toLowerCase()+"."+newLastName.toLowerCase());

            EmployeesAction.getDao().updateEmployee(oldEmp);

            JsonObject jsonResponse = Json.createObjectBuilder()
                    .add("id", oldEmp.getEmployeeId())
                    .add("name", oldEmp.getName() + " " + oldEmp.getLastName())
                    .add("rank", oldEmp.getRank())
                    .build();
            response.getWriter().write(jsonResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();

            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
