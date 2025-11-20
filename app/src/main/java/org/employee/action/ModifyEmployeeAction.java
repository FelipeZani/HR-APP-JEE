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
            String postLabel = request.getParameter("post");


        
          
            String newName = "";
            String newLastName = "";
            Employee.Rank newRank = null;
            String newPassword = "";
            String newPostLabel ="";
            
            Employee oldEmp = EmployeesAction.dao.getEmployeeById(employeeId);

            if (oldEmp == null) {

                System.out.println("EmployeeId not found");
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
                return;

            }

            if (name !=null && !name.isBlank()) {
                newName = name;
                oldEmp.setName(newName);
            }

            if (lastname != null && !lastname.isBlank()) {
                newLastName = lastname;
                oldEmp.setLastName(newLastName);
            } 

            if (Employee.Rank.getRank(rank) != null) {
                newRank = Employee.Rank.getRank(rank);
                oldEmp.setRank(newRank);
            }

            if (password != null && !password.isBlank()) {
                newPassword = password;

            }

            if(postLabel!= null && !postLabel.isBlank()){
                newPostLabel = postLabel;
                oldEmp.getPost().removeEmployeeFromSet(oldEmp);            
                
                oldEmp.setPost(PostsAction.getDao().getPostByLabel(newPostLabel));
                
                oldEmp.getPost().addEmployee(oldEmp);
            }

            
            oldEmp.getUserAccount().setPassword(newPassword);
            oldEmp.getUserAccount().setUsername(newLastName.toLowerCase() + "." + newLastName.toLowerCase());

            EmployeesAction.getDao().updateEmployee(oldEmp);
            System.out.println(oldEmp.getName() + " " + oldEmp.getLastName());
            JsonObject jsonResponse = Json.createObjectBuilder()
                    .add("id", oldEmp.getEmployeeId())
                    .add("name", oldEmp.getName() + " " + oldEmp.getLastName())
                    .add("rank", oldEmp.getRank().toString())
                    .add("post",oldEmp.getPost().getLabel())
                    .build();
            response.getWriter().write(jsonResponse.toString());
        } catch (Exception e) {

            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();

        }
    }

}
