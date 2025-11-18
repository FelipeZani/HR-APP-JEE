package org.employee.action;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.List;

import org.department.action.DepartmentAction;
import org.department.model.Department;
import org.employee.model.Account;
import org.employee.model.Employee;
import org.employee.model.Post;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AddEmployee extends EmployeesAction {
 

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            Enumeration<String> requestParametersNames = request.getParameterNames();

            while (requestParametersNames.hasMoreElements()) {
                String parameter = requestParametersNames.nextElement();
                if (request.getParameter(parameter) == null) {
                    response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,
                            "request parameter in addEmployee not found");
                    throw new NullPointerException(parameter + " parameter not found");
                }
            }

            String completeName = request.getParameter("name");
            String departmentName = request.getParameter("department");
            String postLabel = request.getParameter("post");
            String rankName = request.getParameter("rank");

            String[] nameLastName = completeName.split("\\s");

            Department department = DepartmentAction.getDao().getDepartementByName(departmentName);

            if (department == null) {
                throw new NullPointerException("Department not found");
            }

            Post post = PostsAction.getDao().getPostByLabel(postLabel);

            if (post == null) {
                throw new NullPointerException("Post not found");
            }

            List<Employee> employeeList = EmployeesAction.dao.getEmployeeByParameters(
                    null,
                    nameLastName[0],
                    nameLastName[1],
                    null);

            Employee employee = new Employee();

            employee.setName(nameLastName[0]);
            employee.setLastName(nameLastName[1]);
            employee.setDepartment(department);

            if(Employee.Rank.getRank(rankName) == null){
                response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,"Chosen Rank not allowed");
                return;
            }
            employee.setRank(Employee.Rank.getRank(rankName));
            

            LocalDate today = LocalDate.now();
            String formatted = today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            employee.setEmployementDate(formatted);

            Account newAccount = new Account();

            newAccount.setEmployee(employee);
            newAccount.setFirstConnexion(true);

            if (!employeeList.isEmpty()) {

                newAccount.setUsername(
                        nameLastName[0].toLowerCase() + "." + nameLastName[1].toLowerCase() + employeeList.size());

            } else {

                newAccount.setUsername(nameLastName[0].toLowerCase() + "." + nameLastName[1].toLowerCase());

            }

            employee.setUserAccount(newAccount);

            department.addEmployeeToDepartment(employee);
            employee.setDepartment(department);
            post.addEmployee(employee);
            employee.setPost(post);
            employee.addPermission(Employee.Permissions.NORMALEMPLOYEE);
           
           

            EmployeesAction.getDao().addEmployee(employee);


            JsonObject jsonResponse = Json.createObjectBuilder()
                    .add("id", employee.getEmployeeId())
                    .add("name", employee.getName()+" "+employee.getLastName())
                    .add("post", employee.getPost().getLabel())
                    .add("department", employee.getDepartment().getName())
                    .add("rank", employee.getRank().toString())
                    .add("username", employee.getUserAccount().getUsername())
                    .add("password", employee.getUserAccount().getPassword())
                    .build();
            response.getWriter().write(jsonResponse.toString());

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failled to save user");
        }

    }

}
