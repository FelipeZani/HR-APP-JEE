package org.project.action;

import java.util.List;

import org.project.model.Project;

import jakarta.servlet.ServletException;
import java.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GetProjectsFullList extends ProjectAction {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            StringBuilder sb = new StringBuilder();

            List<Project> list = ProjectAction.getDao().getProjectList();
            if (list == null || list.isEmpty()) {
                sb.append("<div>No projects found</div>");
                resp.getWriter().write(sb.toString());
                return;
            }

            for (Project p : list) {
                String managerName = "--";
                if (p.getProjectManagerEmployee() != null) {
                    managerName = p.getProjectManagerEmployee().getName() + " " + p.getProjectManagerEmployee().getLastName();
                }

                sb.append("<div class='project-row' data-id='" + p.getProjectId() + "'>");
                sb.append("<div><strong>" + p.getLabel() + "</strong> (id:" + p.getProjectId() + ")</div>");
                sb.append("<div>Manager: " + managerName + "</div>");
                sb.append("<div>Status: <span class='project-status-" + p.getStatus().toLowerCase() + "'>" + p.getStatus() + "</span></div>");
                sb.append("<div>Deadline: " + p.getDeadLine() + "</div>");

                // Action buttons
                sb.append("<div style='display: flex; gap: 10px; margin: 10px 0;'>");
                sb.append("<button type='button' class='project-members-btn' onclick=\"viewProjectMembers('" + p.getProjectId() + "')\">Members</button>");
                sb.append("<button type='button' class='project-modify-btn' onclick=\"modifyProject('" + p.getProjectId() + "')\">Modify</button>");
                sb.append("<button type='button' class='project-delete-btn' onclick=\"removeProject('" + p.getProjectId() + "')\">Delete</button>");
                sb.append("</div>");

                // form to assign employee
                sb.append("<form id='assign-form-" + p.getProjectId() + "' style='margin-top: 10px;'>");
                sb.append("<input type='hidden' name='action' value='assignemployee'>");
                sb.append("<input type='hidden' name='projectId' value='" + p.getProjectId() + "'>");
                sb.append("<div style='display: flex; gap: 10px; align-items: center;'>");
                sb.append("<label style='margin: 0;'>Assign employee: ");
                sb.append("<select name='employeeId' required " +
                    "hx-get='/app/projectServlet?action=getemployeesforselect' hx-trigger='revealed' hx-swap='innerHTML'>");
                sb.append("<option value=''>-- Loading employees --</option>");
                sb.append("</select>");
                sb.append("</label>");
                sb.append("<button type='button' class='project-assign-btn' hx-post='/app/projectServlet' hx-include='#assign-form-" + p.getProjectId() + "' hx-swap='none' onclick=\"assignEmployeeToProject(this)\">Assign</button>");
                sb.append("</div>");
                sb.append("</form>");

                sb.append("</div>");
            }

            resp.getWriter().write(sb.toString());

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to retrieve projects.");
        }
    }

}
