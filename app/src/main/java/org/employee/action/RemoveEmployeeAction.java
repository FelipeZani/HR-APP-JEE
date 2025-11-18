package org.employee.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RemoveEmployeeAction extends EmployeesAction {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
         
            String userId = (String) request.getParameter("employeeid");

            if( userId == null || userId.isBlank()){
               response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,"User Id not incorrect");
            }

            EmployeesAction.getDao().removeEmployee(userId);

            

            


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
}
