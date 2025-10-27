package org.employee.dea;

import org.employee.model.Account;
import org.employee.model.Post;
import org.hibernate.Session;
import org.util.HibernateUtil;

public class AccountDAO {
      public void addAccount(Account acc){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(acc);
        session.getTransaction().commit();

        session.close();
        

    }
    
}
