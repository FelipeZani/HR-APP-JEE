package org.employee.action;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

            String completeName = request.getParameter("name");
            String departmentName = request.getParameter("department");
            String postLabel = request.getParameter("post");
            String rankName = request.getParameter("rank");

            if ( completeName== null || completeName.isEmpty()) {
                response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "CompleteName format is not accepted");
                return;
            }
            if ( departmentName == null || departmentName.isBlank() || DepartmentAction.getDao().getDepartementByName(departmentName) == null) {
                response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Department doesnt exist");
                return;
            }
            if (Employee.Rank.getRank(rankName) == null) {
                response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Rank format is not correct");
                return;
            }
            if (postLabel == null || postLabel.isBlank() || PostsAction.getDao().getPostByLabel(postLabel) == null) {
                response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Post label format is not correct");
                return;
            }

            String[] nameLastName = completeName.split("\\s");
            int maxSizeNameLastName = 2;

            if (nameLastName == null || nameLastName.length > maxSizeNameLastName) {
                response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,
                        "Name or Last Name table are in a incorrect format");
                return;
            }
            if (!nameLastName[0].matches("[A-Z][a-z]+") || !nameLastName[1].matches("[A-Z][a-z]+")) {
                response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,
                        "Name or Last Name are in a incorrect format");
                return;

            }

            if (EmployeesAction.getDao().getEmployeeByParameters(null,
                nameLastName[0],
                nameLastName[1],
                departmentName,
                postLabel,
                rankName

            ) != null) {
                response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Employee Already exists");
                return;

            }

            Department department = DepartmentAction.getDao().getDepartementByName(departmentName);

            if (department == null) {
                throw new NullPointerException("Department not found");
            }

            Post post = PostsAction.getDao().getPostByLabel(postLabel);

            if (post == null) {
                throw new NullPointerException("Post not found");
            }

            /*Query a list of employees with the same  name and lastname input in order to verify if there re
             other records with the same name and lastname in the DB
             It s important to notice that will impact directly the creation of usernames, which are in the format name.lastname
            */
            List<Employee> employeeList = EmployeesAction.dao.getEmployeeByParameters(
                    null,
                    nameLastName[0],
                    nameLastName[1],
                    null,
                    null,
                    null);

            Employee employee = new Employee();

            employee.setName(nameLastName[0]);
            employee.setLastName(nameLastName[1]);
            employee.setDepartment(department);

        
            employee.setRank(Employee.Rank.getRank(rankName));

            LocalDate today = LocalDate.now();
            String formatted = today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            employee.setEmployementDate(formatted);

            Account newAccount = new Account();

            newAccount.setEmployee(employee);
            newAccount.setFirstConnexion(true);
            
            // newAccount.setPassword("password123");
            
            if (!employeeList.isEmpty()) { //if there is already other users with the same name/lname the fomat of the username will be name.lastnameNbOfExistingUsers

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
                    .add("name", employee.getName() + " " + employee.getLastName())
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
