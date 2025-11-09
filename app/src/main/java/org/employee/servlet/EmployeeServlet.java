package org.employee.servlet;

import java.io.IOException;
import java.util.Enumeration;

import org.employee.action.AddEmployee;
import org.employee.action.GetAllEmployees;
import org.employee.action.GetEmployeeByParameters;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/employeeServlet")
public class EmployeeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            Enumeration<String> attributeNames = req.getParameterNames();

            if (attributeNames == null || attributeNames.nextElement() == null) {
                resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Unknown passed method");
                return;

            }

            String actionKey = (String) req.getParameter("action");

            switch (actionKey) {
                case "getallemployees":
                    GetAllEmployees getallemp = new GetAllEmployees();
                    getallemp.execute(req, resp);

                    break;
                default:
                    resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Unknown passed method");

                    break;
            }

        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Employee data unavailable");

            e.printStackTrace();

        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            Enumeration<String> attributeNames = req.getParameterNames(); //I Have to find a way to get the action 

            if (attributeNames == null || attributeNames.nextElement() == null) {
                resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Unknown passed method");
                return;

            }

            String actionKey = (String) req.getParameter("action");

            
            switch (actionKey) {
               
                case "searchemployee":
                    GetEmployeeByParameters searchEmployee = new GetEmployeeByParameters();
                    searchEmployee.execute(req, resp);
                    break;
                    
                case "addemployee":
                    AddEmployee addEmployee = new AddEmployee();
                    addEmployee.execute(req, resp);
                    break;
                    
                default:
                    resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Unknown passed method");
                    break;
            }

        }catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Request failed");

            e.printStackTrace();

        }

    }

}
