package org.project.dea;

import org.hibernate.Session;
import org.project.model.Project;
import org.util.HibernateUtil;

public class ProjectDAO {

    public void addProject(Project project) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(project);
            session.getTransaction().commit();

            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

     public void updateProject(Project project) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.merge(project);
            session.getTransaction().commit();

            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
