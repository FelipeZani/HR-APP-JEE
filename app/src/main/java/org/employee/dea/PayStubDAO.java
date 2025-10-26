package org.employee.dea;

import org.employee.model.Employee;
import org.employee.model.PayStub;
import org.hibernate.Session;
import org.util.HibernateUtil;

public class PayStubDAO {

      public void addPayStub(PayStub payStub){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(payStub);
        session.getTransaction().commit();

        session.close();
    }
    
}
