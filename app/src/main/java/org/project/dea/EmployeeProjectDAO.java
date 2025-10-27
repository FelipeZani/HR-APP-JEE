package org.project.dea;

import org.hibernate.Session;
import org.project.model.EmployeeProject;
import org.project.model.Project;
import org.util.HibernateUtil;

public class EmployeeProjectDAO {

    public EmployeeProjectDAO(){}
    public void addEmployeeProject(EmployeeProject emp){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(emp);
        session.getTransaction().commit();

        session.close();
        

    }
    
}
