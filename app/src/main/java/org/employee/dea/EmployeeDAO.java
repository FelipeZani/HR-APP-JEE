package org.employee.dea;

import org.employee.model.Employee;
import org.hibernate.Session;
import org.util.HibernateUtil;

public class EmployeeDAO {


    void addEmployee(Employee emp){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(emp);
        session.getTransaction().commit();

        session.close();
    }
    
}
