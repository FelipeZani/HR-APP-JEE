package org.department.action;

import java.util.List;

import org.department.model.Department;

import jakarta.servlet.ServletException;
import java.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GetDepartmentsFullList extends DepartmentAction {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            StringBuilder sb = new StringBuilder();

            List<Department> list = DepartmentAction.getDao().getDepartementList();
            if (list == null || list.isEmpty()) {
                sb.append("<div>No departments found</div>");
                resp.getWriter().write(sb.toString());
                return;
            }

            for (Department d : list) {
                String managerName = "--";
                if (d.getManagerEmployee() != null) {
                    managerName = d.getManagerEmployee().getName() + " " + d.getManagerEmployee().getLastName();
                }

                sb.append("<div class='department-row' data-id='" + d.getDepartmentId() + "'>");
                sb.append("<div><strong>" + d.getName() + "</strong> (id:" + d.getDepartmentId() + ")</div>");
                sb.append("<div>Manager: " + managerName + "</div>");

                // Action buttons
                sb.append("<div style='display: flex; gap: 10px; margin: 10px 0;'>");
                sb.append("<button type='button' class='dept-members-btn' onclick=\"viewDepartmentMembers('" + d.getDepartmentId() + "')\">Members</button>");
                sb.append("<button type='button' class='dept-delete-btn' onclick=\"removeDepartment('" + d.getDepartmentId() + "')\">Delete</button>");
                sb.append("</div>");

                // form to assign employee
                sb.append("<form id='assign-form-" + d.getDepartmentId() + "' style='margin-top: 10px;'>");
                sb.append("<input type='hidden' name='action' value='assignemployee'>");
                sb.append("<input type='hidden' name='departmentId' value='" + d.getDepartmentId() + "'>");
                sb.append("<div style='display: flex; gap: 10px; align-items: center;'>");
                sb.append("<label style='margin: 0;'>Assign employee: ");
        sb.append("<select name='employeeId' required " +
            "hx-get='/app/departmentServlet?action=getemployeesforselect' hx-trigger='revealed' hx-swap='innerHTML'>");
                sb.append("<option value=''>-- Loading employees --</option>");
                sb.append("</select>");
                sb.append("</label>");
                sb.append("<button type='button' class='dept-assign-btn' hx-post='/app/departmentServlet' hx-include='#assign-form-" + d.getDepartmentId() + "' hx-swap='none' onclick=\"assignEmployee(this)\">Assign</button>");
                sb.append("</div>");
                sb.append("</form>");

                sb.append("</div>");
            }

            resp.getWriter().write(sb.toString());

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to retrieve departments.");
        }
    }

}
