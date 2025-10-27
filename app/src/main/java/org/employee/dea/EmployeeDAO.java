package org.employee.dea;

import java.util.List;

import org.employee.model.Employee;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.util.HibernateUtil;

import jakarta.data.repository.Query;

public class EmployeeDAO {


    public void addEmployee(Employee emp){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(emp);
        session.getTransaction().commit();

        session.close();
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
