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
import org.employee.model.Post;
import org.hibernate.SessionFactory;
import org.util.HibernateUtil;


import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;


@WebServlet("/addDepartement")
public class DepartementDAO extends HttpServlet{
    private static SessionFactory sessionFactory;

    public void addDepartment(Department d){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(d);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");

        // try(PrintWriter out = resp.getWriter()){
        //     Connection connection = DriverManager.getConnection(
        //     "jdbc:mariadb://localhost:3306/jeeDB",
        //     "jee",
        //     "jee"
        // );
        // }
        // catch(SQLException e){
        //     throw new ServletException("Erreur SQL:"+e.getMessage(),e);
        // }



        Department departement = new Department();
        departement.setName("DRE");
        addDepartment(departement);



        

    }
    
    
}