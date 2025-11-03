package org.employee.dea;

import java.util.List;

import org.employee.model.Employee;
import org.hibernate.Session;
import org.util.HibernateUtil;

public class EmployeeDAO {


    public void addEmployee(Employee emp){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(emp);
        session.getTransaction().commit();

        session.close();
    }

    public List<Employee> getAllEmployees(){
        try(Session session =  HibernateUtil.getSessionFactory().openSession()){
            
            List<Employee> employeesList = session.createQuery("from Employee", Employee.class)
            .list();
            
            if(employeesList.size()<= 0){
                throw new Exception("Empty employee list at getAllEmployees() function.");
            }
            
            return employeesList;

        }catch(Exception exception){
            exception.printStackTrace();
            return null;
        }

    }

    public boolean isEmployeeTabEmpty() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Employee> employees = session.createQuery("from Employee", Employee.class)
                                            .setMaxResults(1)
                                            .list();
            return employees.isEmpty();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
