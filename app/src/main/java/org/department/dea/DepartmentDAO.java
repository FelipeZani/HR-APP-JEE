package org.department.dea;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.department.model.Department;
import org.employee.model.Employee;
import org.employee.model.Post;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.util.HibernateUtil;


import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;


@WebServlet("/addDepartment")
public class DepartmentDAO{
    private static SessionFactory sessionFactory;

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
        Query<Department> query = session.createQuery("SELECT * FROM department",Department.class);

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