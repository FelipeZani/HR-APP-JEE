
package org;

import java.util.HashSet;
import java.util.Set;

import org.department.dea.DepartmentDAO;
import org.department.model.Department;
import org.employee.dea.EmployeeDAO;
import org.employee.dea.PostDAO;
import org.employee.model.Account;
import org.employee.model.Employee;
import org.employee.model.PayStub;
import org.employee.model.Post;
import org.project.dea.ProjectDAO;
import org.project.model.EmployeeProject;
import org.project.model.EmployeeProjectId;
import org.project.model.Project;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class SetUpDB implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Employee employee = new Employee();
        EmployeeDAO empDao = new EmployeeDAO();

        if (empDao.isEmployeeTabEmpty()) {

            employee.setName("Felipe");
            employee.setLastName("ZANI");
            employee.setRank(Employee.Rank.JUNIOR);
            employee.setEmployementDate("03-05-2025");

            PayStub payStub = new PayStub();
            payStub.setDate("08-09-2005");
            payStub.setCreationDate("08-09-2005");
            payStub.setTotal(255f);
            payStub.setBonus(0);

            payStub.setEmployee(employee);

            Set<PayStub> paystubs = new HashSet<PayStub>();

            paystubs.add(payStub);
            employee.setPayStubSet(paystubs);

            Account userAccount = new Account();
            userAccount.setUsername("felipe.zani");
            userAccount.setPassword("superSecurePWD123");
            userAccount.setFirstConnexion(true);
            userAccount.setEmployee(employee);
            employee.setUserAccount(userAccount);


            Post post = new Post();
            post.setLabel("Dev");
            post.setWage(35f);
            post.addEmployee(employee);
            employee.setPost(post);

            PostDAO pdao = new PostDAO();
            pdao.addPost(post);

            Department department = new Department();
            department.setName("Research and Stuff");
            department.addEmployeeToDepartment(employee);
            department.setManagerEmployee(employee);
            employee.setDepartment(department);

            employee.setDepartmentManagered(department);

            DepartmentDAO derpdao = new DepartmentDAO();
            derpdao.addDepartment(department);

            Project project = new Project();
            project.setLabel("AI Vibe Coded System");
            project.setDeadLine("05-10-2013");
            project.setStatus("ONGOING");
            project.setProjectManagerEmployee(employee);


            employee.setProjectManagered(project);

            ProjectDAO projectDAO = new ProjectDAO();
            projectDAO.addProject(project);
            
            EmployeeProjectId epId = new EmployeeProjectId();

            epId.setEmployeeId(employee.getEmployeeId());
            epId.setProjectId(project.getProjectId());

            EmployeeProject employeeProject = new EmployeeProject();
            employeeProject.setId(epId);
            employeeProject.setEmployee(employee);
            employeeProject.setProject(project);
            employeeProject.setAssignmentDate("01-02-2002");

            employee.addPermission(Employee.Permissions.PROJECTMANAGER);
            employee.addPermission(Employee.Permissions.DEPARTMENTMANAGER);
            employee.addPermission(Employee.Permissions.HR);

            employee.addEmployeeProjects(employeeProject);
            project.addEmployeeProjects(employeeProject);

            empDao.updateEmployee(employee);

        }
    }
}