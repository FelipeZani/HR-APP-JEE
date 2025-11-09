package org.employee.dea;

import org.employee.model.Account;
import org.hibernate.Session;
import org.util.HibernateUtil;

public class AccountDAO {
  public void addAccount(Account acc) {
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {

      session.beginTransaction();
      session.persist(acc);
      session.getTransaction().commit();

    } catch (Exception e) {
      e.printStackTrace();
    }

  }
   public void updateAccount(Account acc) {
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {

      session.beginTransaction();
      session.merge(acc);
      session.getTransaction().commit();

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
