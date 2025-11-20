package org.employee.servlet;

import java.io.IOException;
import java.util.Enumeration;
import java.util.NoSuchElementException;

import org.employee.action.AddEmployee;
import org.employee.action.GetAllEmployees;
import org.employee.action.GetEmployeeByParameters;
import org.employee.action.LoginAction;
import org.employee.action.ModifyEmployeeAction;
import org.employee.action.RemoveEmployeeAction;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/employeeServlet")
public class EmployeeServlet extends HttpServlet {

    private boolean checkParametersIntegrity(HttpServletRequest req) throws NullPointerException {

        Enumeration<String> parameterKeyNames = req.getParameterNames();

        if (parameterKeyNames == null) {
            throw new NullPointerException("Parameters String enumeration is null");
        }

        if(req.getParameter("action") == null){
            throw new NoSuchElementException("Action Method not found");
        }
        while (parameterKeyNames.hasMoreElements()) {

            String parameterKey = parameterKeyNames.nextElement();

            if (req.getParameter(parameterKey) == null) {
                throw new NullPointerException("Parameters String enumeration is null");
            }
        }
        return true;

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

           checkParametersIntegrity(req);

            String actionKey = (String) req.getParameter("action");

            switch (actionKey) {
                case "getallemployees":
                    GetAllEmployees getallemp = new GetAllEmployees();
                    getallemp.execute(req, resp);

                    break;
                case "login":
                    LoginAction loginAction = new LoginAction();
                    loginAction.execute(req, resp);
                    break;
                default:
                    resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Unknown passed method");

                    break;
            }

        }catch(NoSuchElementException e){
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Method not found");

        } 
        catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Employee data unavailable");

            e.printStackTrace();

        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            Enumeration<String> attributeNames = req.getParameterNames(); // I Have to find a way to get the action

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

        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Request failed");

            e.printStackTrace();

        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            Enumeration<String> attributeNames = req.getParameterNames();
            if (attributeNames == null || attributeNames.nextElement() == null) {
                resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Unknown passed method");
                return;

            }

            String actionKey = (String) req.getParameter("action");

            switch (actionKey) {

                case "removeemployee":
                    RemoveEmployeeAction removeEmployee = new RemoveEmployeeAction();
                    removeEmployee.execute(req, resp);

                    break;
                default:
                    resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Unknown passed method");
                    break;
            }

        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Request failed");

            e.printStackTrace();

        }
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (!checkParametersIntegrity(req)) {
                resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Unknown passed method");
                throw new NullPointerException("Null parameter found");
            }
            String actionKey = (String) req.getParameter("action");

            switch (actionKey) {

                case "modifyemployee":

                    ModifyEmployeeAction modifyEmployeeAction = new ModifyEmployeeAction();
                    modifyEmployeeAction.execute(req, resp);

                    break;
                default:
                    resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Unknown passed method");
                    break;
            }

        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Request failed");

            e.printStackTrace();
        }

    }

}
