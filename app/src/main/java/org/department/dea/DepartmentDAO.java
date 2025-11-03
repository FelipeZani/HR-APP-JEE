package org.department.dea;

import java.util.List;

import org.department.model.Department;

import org.hibernate.query.Query;
import org.util.HibernateUtil;


import org.hibernate.Session;


public class DepartmentDAO{
    
    public void addDepartment(Department d){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(d);
        session.getTransaction().commit();
        session.close();
    }

    public List<Department> getDepartementList(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query<Department> query = session.createQuery("FROM Department",Department.class);

        List<Department> d = query.getResultList();

        session.getTransaction().commit();
        session.close();
        return d;

    }

    public void managerAffectation(int employeeId, int departementId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query<Department> query = session.createQuery(
            "UPDATE department d SET d.managerId= :employeeId WHERE d.departmentId= :departementId"
            );
            query.setParameter("employeeId",employeeId);
            query.setParameter("departementId",departementId);

            int rowsaffected = query.executeUpdate();

            session.getTransaction().commit();
            session.close();
    }

    public void deleteDepartment(int departementId){
        
    }
}