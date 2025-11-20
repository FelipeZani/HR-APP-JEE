
package org.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            sessionFactory = configuration.buildSessionFactory();
            System.out.println("[HibernateUtil] SessionFactory initialized successfully");
        } catch (Exception e) {
            System.err.println("[HibernateUtil] FATAL: Failed to initialize SessionFactory");
            e.printStackTrace();
            sessionFactory = null;
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            System.err.println("[HibernateUtil] ERROR: SessionFactory is null. Check hibernate.cfg.xml and database connection.");
        }
        return sessionFactory;
    }
}

