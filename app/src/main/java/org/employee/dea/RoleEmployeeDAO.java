package org.employee.dea;

import java.io.IOException;
import java.io.PrintWriter;

import org.employee.model.RoleEmployee;
import org.util.HibernateUtil;
import org.hibernate.Session;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/addRoleEmployee")
public class RoleEmployeeDAO extends HttpServlet{

    public void addRoleEmployee(RoleEmployee re){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(re);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RoleEmployee myRe = new RoleEmployee();
        myRe.setLabel("toto");
        addRoleEmployee(myRe);
        PrintWriter out = resp.getWriter();
        out.write("Added RoleEmployee to DB");

    }

 
    
}