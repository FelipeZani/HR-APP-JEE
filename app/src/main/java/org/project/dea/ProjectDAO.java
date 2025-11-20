package org.project.dea;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
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

    public void deleteProject(int projectId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Project project = session.get(Project.class, projectId);
            if (project != null) {
                session.remove(project);
            }
            session.getTransaction().commit();

            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Project getProjectById(int projectId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Project project = session.get(Project.class, projectId);
            session.getTransaction().commit();
            return project;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Project> getProjectList() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query<Project> query = session.createQuery("FROM Project", Project.class);
            List<Project> projects = query.getResultList();
            session.getTransaction().commit();
            return projects;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Project> getProjectsByStatus(String status) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query<Project> query = session.createQuery("FROM Project WHERE status = :status", Project.class);
            query.setParameter("status", status);
            List<Project> projects = query.getResultList();
            session.getTransaction().commit();
            return projects;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
