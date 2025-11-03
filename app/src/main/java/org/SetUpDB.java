

package org;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.department.model.Department;
import org.employee.dea.EmployeeDAO;
import org.employee.dea.PayStubDAO;
import org.employee.model.Account;
import org.employee.model.Employee;
import org.employee.model.PayStub;
import org.employee.model.Post;
import org.project.dea.EmployeeProjectDAO;
import org.project.dea.ProjectDAO;
import org.project.model.EmployeeProject;
import org.project.model.EmployeeProjectId;
import org.project.model.Project;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;


@WebListener
public class SetUpDB implements ServletContextListener  {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Employee employee = new Employee();
        EmployeeDAO empDao = new EmployeeDAO();


        if(empDao.isEmployeeTabEmpty()){

            employee.setName("Felipe");
            employee.setLastName("ZANI");
            employee.setRank("JUNIOR");
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
            
            Set<Employee> postTeam = new HashSet<>();
            postTeam.add(employee);

            post.setEmployeeSet(postTeam);

            employee.setPost(post);




            Department department = new Department();
            department.setName("Research and Stuff");
            Set<Employee> departmentTeam = new HashSet<>();
            departmentTeam.add(employee);
            department.setDepartmentEmployees(departmentTeam);
            department.setManagerEmployee(employee);

            employee.setDepartmentManagered(department);

            employee.setDepartment(department);

            Project project = new Project();
            project.setLabel("AI Health Detection System");
            project.setDeadLine("05-10-2013");
            project.setStatus("ONGOING");


            employee.setProjectManagered(project);

            
            
            EmployeeProjectId epId = new EmployeeProjectId();
            
            epId.setEmployeeId(employee.getEmployeeId());
            epId.setProjectId(project.getProjectId());
            

            EmployeeProject employeeProject = new EmployeeProject();
            employeeProject.setId(epId);
            employeeProject.setEmployee(employee);
            employeeProject.setProject(project);
            employeeProject.setAssignmentDate("01-02-2002");


            Set<EmployeeProject> noCreativityForAnNewName = new HashSet<EmployeeProject>();
            noCreativityForAnNewName.add(employeeProject);
            
            employee.setEmployeeProjects(noCreativityForAnNewName);
            project.setEmployeeProjects(noCreativityForAnNewName);
            empDao.addEmployee(employee);
        }
    } 
}