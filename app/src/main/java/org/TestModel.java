package org;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.department.dea.DepartmentDAO;
import org.department.model.Department;
import org.employee.dea.AccountDAO;
import org.employee.dea.EmployeeDAO;
import org.employee.dea.PayStubDAO;
import org.employee.dea.PostDAO;
import org.employee.model.Account;
import org.employee.model.Employee;
import org.employee.model.PayStub;
import org.employee.model.Post;
import org.project.dea.ProjectDAO;
import org.project.model.EmployeeProject;
import org.project.model.Project;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class TestModel  implements ServletContextListener  {
    
    @Override 
    public void contextInitialized(ServletContextEvent sce){
        try {
            
            Employee employee = new Employee();
            EmployeeDAO testEmployeeDAO = new EmployeeDAO();
        
            employee.setName("Test");
            employee.setLastName("TestLastname");

            PayStub payStub = new PayStub();
            PayStubDAO payStubDAO = new PayStubDAO();
            

            payStub.setPayStubId(1);
            payStub.setTotal(2500.75f);
            payStub.setCreationDate("2025-10-24");
            payStub.setDate("2025-10-31");
            payStubDAO.addPayStub(payStub);

            Post post = new Post();
            PostDAO postDAO = new PostDAO();

            post.setPostId(1);
            post.setLabel("Software Engineer");
            post.setWage(3200.50f);
            post.setEmployee(employee);

            Account account = new Account();
            AccountDAO accountDAO = new AccountDAO();


            account.setAccountId(1);
            account.setUsername("felipe.zani");
            account.setPassword("securePassword123");
            account.setFirstConnexion(true);
            account.setEmployee(employee);

            accountDAO.addAccount(account);
        
            Project project = new Project();
            ProjectDAO projectDAO = new ProjectDAO();
            project.setProjectId(1);
            project.setLabel("AI-Based Health Monitoring System");
            project.setStatus("In Progress");
            project.setDeadLine("2025-12-15");
            project.setManager(employee);


            EmployeeProject employeeProject = new EmployeeProject();
            employeeProject.setEmployee(employee); 
            employeeProject.setProject(project);  
            employeeProject.setAffectationDate("2025-10-24");

            Department department = new Department();
            DepartmentDAO departmentDAO = new DepartmentDAO();

            department.setDepartementId(1);
            department.setName("Research and Development");

            department.setEmployee(employee);
            List<Employee> departmentTeam = new ArrayList<Employee>();
            departmentTeam.add(employee);

            departmentDAO.addDepartment(department);        
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
