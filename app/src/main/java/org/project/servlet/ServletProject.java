package org.project.servlet;

import java.io.IOException;

import org.employee.model.Employee;
import org.project.action.GetProjectsFullList;
import org.project.action.GetProjectMembers;
import org.project.action.GetEmployeesForSelect;
import org.project.dea.EmployeeProjectDAO;
import org.project.dea.ProjectDAO;
import org.project.model.EmployeeProject;
import org.project.model.EmployeeProjectId;
import org.project.model.Project;
import org.employee.dea.EmployeeDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/projectServlet")
public class ServletProject extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action == null) {
            action = "";
        }

        switch (action) {
            case "getprojectlist":
                new GetProjectsFullList().execute(req, resp);
                break;
            case "getprojectmembers":
                new GetProjectMembers().execute(req, resp);
                break;
            case "getemployeesforselect":
                new GetEmployeesForSelect().execute(req, resp);
                break;
            case "getprojectinfo":
                handleGetProjectInfo(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action == null) {
            action = "";
        }

        switch (action) {
            case "addproject":
                handleAddProject(req, resp);
                break;
            case "deleteproject":
                handleDeleteProject(req, resp);
                break;
            case "modifyproject":
                handleModifyProject(req, resp);
                break;
            case "assignemployee":
                handleAssignEmployee(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action");
                break;
        }
    }

    private void handleAddProject(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String label = req.getParameter("label");
            String status = req.getParameter("status");
            String deadLine = req.getParameter("deadLine");
            String managerIdStr = req.getParameter("managerId");

            if (label == null || label.trim().isEmpty() || status == null || status.trim().isEmpty()) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Label and Status are required");
                return;
            }

            Project project = new Project();
            project.setLabel(label);
            project.setStatus(status);
            project.setDeadLine(deadLine != null ? deadLine : "");

            if (managerIdStr != null && !managerIdStr.trim().isEmpty()) {
                try {
                    int managerId = Integer.parseInt(managerIdStr);
                    EmployeeDAO empDao = new EmployeeDAO();
                    Employee manager = empDao.getEmployeeById(String.valueOf(managerId));
                    if (manager != null) {
                        project.setProjectManagerEmployee(manager);
                    }
                } catch (NumberFormatException e) {
                    // Invalid manager ID, continue without setting manager
                }
            }

            ProjectDAO projectDao = new ProjectDAO();
            projectDao.addProject(project);
            resp.getWriter().write("OK");

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to create project");
        }
    }

    private void handleDeleteProject(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String projectIdStr = req.getParameter("projectId");

            if (projectIdStr == null || projectIdStr.trim().isEmpty()) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Project ID is required");
                return;
            }

            int projectId = Integer.parseInt(projectIdStr);
            ProjectDAO projectDao = new ProjectDAO();
            projectDao.deleteProject(projectId);
            resp.getWriter().write("OK");

        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid project ID");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete project");
        }
    }

    private void handleModifyProject(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String projectIdStr = req.getParameter("projectId");
            String label = req.getParameter("label");
            String status = req.getParameter("status");
            String deadLine = req.getParameter("deadLine");
            String managerIdStr = req.getParameter("managerId");

            if (projectIdStr == null || projectIdStr.trim().isEmpty()) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Project ID is required");
                return;
            }

            int projectId = Integer.parseInt(projectIdStr);
            ProjectDAO projectDao = new ProjectDAO();
            Project project = projectDao.getProjectById(projectId);

            if (project == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Project not found");
                return;
            }

            if (label != null && !label.trim().isEmpty()) {
                project.setLabel(label);
            }
            if (status != null && !status.trim().isEmpty()) {
                project.setStatus(status);
            }
            if (deadLine != null && !deadLine.trim().isEmpty()) {
                project.setDeadLine(deadLine);
            }

            if (managerIdStr != null && !managerIdStr.trim().isEmpty()) {
                try {
                    int managerId = Integer.parseInt(managerIdStr);
                    EmployeeDAO empDao = new EmployeeDAO();
                    Employee manager = empDao.getEmployeeById(String.valueOf(managerId));
                    if (manager != null) {
                        project.setProjectManagerEmployee(manager);
                    }
                } catch (NumberFormatException e) {
                    // Invalid manager ID, continue without changing manager
                }
            }

            projectDao.updateProject(project);
            resp.getWriter().write("OK");

        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid project ID");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to modify project");
        }
    }

    private void handleAssignEmployee(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String projectIdStr = req.getParameter("projectId");
            String employeeIdStr = req.getParameter("employeeId");

            if (projectIdStr == null || projectIdStr.trim().isEmpty() || employeeIdStr == null || employeeIdStr.trim().isEmpty()) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Project ID and Employee ID are required");
                return;
            }

            int projectId = Integer.parseInt(projectIdStr);
            int employeeId = Integer.parseInt(employeeIdStr);

            EmployeeProjectDAO epDao = new EmployeeProjectDAO();
            
            // Check if employee is already assigned to this project
            if (epDao.isEmployeeAssignedToProject(projectId, employeeId)) {
                resp.getWriter().write("ALREADY_ASSIGNED");
                return;
            }

            ProjectDAO projectDao = new ProjectDAO();
            Project project = projectDao.getProjectById(projectId);

            if (project == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Project not found");
                return;
            }

            EmployeeDAO empDao = new EmployeeDAO();
            Employee employee = empDao.getEmployeeById(String.valueOf(employeeId));

            if (employee == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Employee not found");
                return;
            }

            // Create EmployeeProject entry with current date
            EmployeeProject employeeProject = new EmployeeProject();
            employeeProject.setEmployee(employee);
            employeeProject.setProject(project);
            
            EmployeeProjectId epId = new EmployeeProjectId();
            epId.setProjectId(projectId);
            epId.setEmployeeId(employeeId);
            employeeProject.setId(epId);
            employeeProject.setAssignmentDate(new java.util.Date().toString());

            // Persist the EmployeeProject
            epDao.addEmployeeProject(employeeProject);

            resp.getWriter().write("OK");

        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid IDs");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to assign employee");
        }
    }

    private void handleGetProjectInfo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String projectIdStr = req.getParameter("projectId");

            if (projectIdStr == null || projectIdStr.trim().isEmpty()) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Project ID is required");
                return;
            }

            int projectId = Integer.parseInt(projectIdStr);
            ProjectDAO projectDao = new ProjectDAO();
            Project project = projectDao.getProjectById(projectId);

            if (project == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Project not found");
                return;
            }

            // Return JSON representation of the project
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            sb.append("\"id\":").append(project.getProjectId()).append(",");
            sb.append("\"label\":\"").append(project.getLabel()).append("\",");
            sb.append("\"status\":\"").append(project.getStatus()).append("\",");
            sb.append("\"deadLine\":\"").append(project.getDeadLine()).append("\",");
            sb.append("\"managerId\":").append(project.getProjectManagerEmployee() != null ? project.getProjectManagerEmployee().getEmployeeId() : "null");
            sb.append("}");

            resp.setContentType("application/json");
            resp.getWriter().write(sb.toString());

        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid project ID");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to retrieve project info");
        }
    }
}
