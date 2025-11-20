package org.department.servlet;

import java.io.IOException;
import java.util.Enumeration;

import org.department.action.GetDepartmentsListForDialog;
import org.project.action.GetEmployeesForSelect;
import org.department.action.GetDepartmentEmployees;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.department.dea.DepartmentDAO;
import org.department.model.Department;
import org.department.action.GetDepartmentsFullList;
import org.employee.model.Employee;
import org.employee.dea.EmployeeDAO;
import org.util.HibernateUtil;
import org.hibernate.Session;

@WebServlet("/departmentServlet")
public class DepartmentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


            try {

            Enumeration<String> attributeNames = req.getParameterNames();
            
            if(attributeNames == null || attributeNames.nextElement() == null){
                resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Unknown passed method");
                resp.getWriter().write("Error : Unknown passed method");
                return;

            }

            String actionKey = (String)req.getParameter("action");

            switch (actionKey) {
                case "getdepartmentlist":
                    GetDepartmentsListForDialog getDepartmentsListForDialog = new GetDepartmentsListForDialog();
                    getDepartmentsListForDialog.execute(req, resp);
                    break;
                case "getdepartmentfulllist":
                    GetDepartmentsFullList fullList = new GetDepartmentsFullList();
                    fullList.execute(req, resp);
                    break;
                case "getemployeesforselect":
                    GetEmployeesForSelect getEmployeesForSelect = new GetEmployeesForSelect();
                    getEmployeesForSelect.execute(req, resp);
                    break;
                case "getdepartmentemployees":
                    GetDepartmentEmployees getDepartmentEmployees = new GetDepartmentEmployees();
                    getDepartmentEmployees.execute(req, resp);
                    break;
            
                default:
                    resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Unknown passed method");
                    resp.getWriter().write("Error : Unknown passed method");

                    break;
            }

        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Department data unavailable");
            resp.getWriter().write("Error : Unknown passed method");
            e.printStackTrace();

        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String actionKey = req.getParameter("action");
            DepartmentDAO dao = new DepartmentDAO();

            if (actionKey == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing action parameter");
                return;
            }

            switch (actionKey) {
                case "adddepartment": {
                    String name = req.getParameter("name");
                    if (name == null || name.isEmpty()) {
                        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing department name");
                        return;
                    }
                    Department d = new Department();
                    d.setName(name);
                    dao.addDepartment(d);
                    // if a manager was selected, assign after department is persisted
                    String managerIdStr = req.getParameter("managerId");
                    if (managerIdStr != null && !managerIdStr.isEmpty()) {
                        try {
                            int managerId = Integer.parseInt(managerIdStr);
                            Department persisted = dao.getDepartementByName(name);
                            if (persisted != null) {
                                dao.managerAffectation(managerId, persisted.getDepartmentId());
                            }
                        } catch (NumberFormatException nfe) {
                            // ignore invalid manager id, department is still created
                        }
                    }
                    resp.getWriter().write("OK");
                }
                    break;

                case "deletdepartment": {
                    String idStr = req.getParameter("departmentId");
                    if (idStr == null || idStr.isEmpty()) {
                        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing departmentId");
                        return;
                    }
                    try {
                        int id = Integer.parseInt(idStr);
                        dao.deleteDepartment(id);
                        resp.getWriter().write("OK");
                    } catch (NumberFormatException nfe) {
                        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid departmentId");
                        return;
                    }
                }
                    break;

                case "affectmanager": {
                    String depIdStr = req.getParameter("departmentId");
                    String empIdStr = req.getParameter("employeeId");
                    if (depIdStr == null || empIdStr == null) {
                        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameters");
                        return;
                    }
                    try {
                        int depId = Integer.parseInt(depIdStr);
                        int empId = Integer.parseInt(empIdStr);
                        dao.managerAffectation(empId, depId);
                        resp.getWriter().write("OK");
                    } catch (NumberFormatException nfe) {
                        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid numeric parameter");
                        return;
                    }
                }
                    break;

                case "assignemployee": {
                    String depIdStr = req.getParameter("departmentId");
                    String empIdStr = req.getParameter("employeeId");
                    if (depIdStr == null || empIdStr == null) {
                        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameters");
                        return;
                    }
                    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                        session.beginTransaction();
                        try {
                            int depId = Integer.parseInt(depIdStr);
                            int empId = Integer.parseInt(empIdStr);

                            Department department = session.get(Department.class, depId);
                            Employee employee = session.get(Employee.class, empId);

                            if (department != null && employee != null) {
                                employee.setDepartment(department);
                                department.addEmployeeToDepartment(employee);

                                session.merge(employee);
                                session.merge(department);
                            }

                            session.getTransaction().commit();
                            resp.getWriter().write("OK");
                        } catch (NumberFormatException nfe) {
                            session.getTransaction().rollback();
                            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid numeric parameter");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Assignment failed");
                    }
                }
                    break;

                default:
                    resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Unknown passed method");
                    break;
            }

        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Department action failed");
            e.printStackTrace();
        }
    }
    
}
