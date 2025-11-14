package org.employee.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RemoveEmployeeAction extends EmployeesAction {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
         
            String userId = (String) request.getParameter("employeeid");

            if(userId == null){
                throw new NullPointerException("userId is null");
            }else if(userId == ""){
                throw new Exception("userId is an empty string: \" \" ");
            }

            EmployeesAction.getDao().removeEmployee(userId);

            


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
}
