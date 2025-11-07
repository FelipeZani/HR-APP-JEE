package org.employee.action;

import java.io.BufferedReader;
import java.util.List;
import java.util.stream.Collectors;

import org.employee.model.Employee;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

class EmployeePartial {

    private String name;
    private String post;
    private String department;
    private String string;

    public EmployeePartial(String name, String lastname, String post, String department, String string) {
        this.name = name;
        this.post = post;
        this.department = department;
        this.string = string;
    }

    public String getName() {
        return name;
    }

    public String getPost() {
        return post;
    }

    public String getDepartment() {
        return department;
    }

    public String getString() {
        return string;
    }



}

public class AddEmployee extends EmployeesAction {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        try {
            
            
            String completeName = request.getParameter("name");
            String [] nameLastName = completeName.split("\\s");
            System.out.println(nameLastName[0]+" "+nameLastName[1]);
            List<Employee> employee = EmployeesAction.dao.getEmployeeByParameters(null,
                    nameLastName[0], nameLastName[1],null);

            System.out.println("is it empty: "+employee.isEmpty());
            

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
