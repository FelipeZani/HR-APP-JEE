package org.department.action;

import java.io.IOException;
import java.util.List;

import org.department.model.Department;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GetDepartmentsListForDialog extends DepartmentAction{


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try {
            
            String responseHTML ="<option value=\"\" selected disabled>Select department</option>";
            List<Department> departmentList =  DepartmentAction.dao.getDepartementList();

            if( departmentList == null || departmentList.isEmpty()){
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Department data unavailable");
                return;
            }


            for (Department department : departmentList) {
                responseHTML+="<option value='"+department.getName()+"'>"+department.getName()+"</option>";
                
            }
            response.getWriter().write(responseHTML);


        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to retrieve department data.");

        } 
    }
}
