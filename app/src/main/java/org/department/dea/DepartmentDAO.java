package org.department.dea;

import java.util.List;

import org.department.model.Department;

import org.hibernate.query.Query;
import org.util.HibernateUtil;

import org.hibernate.Session;

public class DepartmentDAO {

    public void addDepartment(Department d) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(d);
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void updateDepartment(Department department) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {

            session.beginTransaction();
            session.merge(department);
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Department getDepartementByName(String name) throws Exception {

        if (name == null || name.isEmpty()) {
            throw new Exception("Department name is null or empty");
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            session.beginTransaction();

            String hib = "from Department d where d.name = :name";

            Query<Department> query = session.createQuery(hib, Department.class);
            query.setParameter("name", name);

            Department department = query.uniqueResult();

            return department;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<Department> getDepartementList() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query<Department> query = session.createQuery("FROM Department", Department.class);

        List<Department> d = query.getResultList();

        session.getTransaction().commit();
        session.close();
        return d;

    }

    public void managerAffectation(int employeeId, int departementId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query<Department> query = session.createQuery(
                "UPDATE department d SET d.managerId= :employeeId WHERE d.departmentId= :departementId");
        query.setParameter("employeeId", employeeId);
        query.setParameter("departementId", departementId);

        int rowsaffected = query.executeUpdate();

        session.getTransaction().commit();
        session.close();
    }

    public void deleteDepartment(int departementId) {

    }
}