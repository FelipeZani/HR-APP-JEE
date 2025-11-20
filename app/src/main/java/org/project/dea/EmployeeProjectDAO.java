package org.project.dea;

import java.util.List;

import org.employee.model.Employee;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.project.model.EmployeeProject;
import org.project.model.EmployeeProjectId;
import org.util.HibernateUtil;

public class EmployeeProjectDAO {

    public EmployeeProjectDAO(){}
    
    public void addEmployeeProject(EmployeeProject emp){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.merge(emp);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Employee> getEmployeesByProject(int projectId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query<Employee> query = session.createQuery(
                "SELECT ep.employee FROM EmployeeProject ep WHERE ep.project.projectId = :projectId",
                Employee.class
            );
            query.setParameter("projectId", projectId);
            List<Employee> employees = query.getResultList();
            session.getTransaction().commit();
            return employees;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isEmployeeAssignedToProject(int projectId, int employeeId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query<EmployeeProject> query = session.createQuery(
                "FROM EmployeeProject ep WHERE ep.project.projectId = :projectId AND ep.employee.employeeId = :employeeId",
                EmployeeProject.class
            );
            query.setParameter("projectId", projectId);
            query.setParameter("employeeId", employeeId);
            EmployeeProject result = query.uniqueResult();
            session.getTransaction().commit();
            return result != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
}

