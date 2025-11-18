package org.employee.action;

import org.employee.model.Account;
import org.employee.model.Employee;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginAction extends EmployeesAction {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        try {

            String login = (String) request.getParameter("username");

            String pwd = (String) request.getParameter("password");

            if(login !=null &&login.matches("[a-z]+.[a-z]+[0-9]*")){
                response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,"Format of username not acceptable");
                return;
            }
            if(pwd!= null && !pwd.isEmpty()){
                response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,"Password canot be empty");
            }

            Account userAcc = EmployeesAction.getDao().getUserAccount(login, pwd);

            if (userAcc == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Acces denied");
            }

            Employee user = userAcc.getEmployee();

            String dest = "";
            if(user.getPermissions().contains(Employee.Permissions.HR))
                dest="/app/employeeScreen.html";
            
            response.setHeader("HX-Redirect", dest);
            response.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}
