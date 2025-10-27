package org.project.dea;

import org.hibernate.Session;
import org.project.model.Project;
import org.util.HibernateUtil;

public class ProjectDAO {

     public void addProject(Project project){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(project);
        session.getTransaction().commit();

        session.close();
        

    }
    
}
