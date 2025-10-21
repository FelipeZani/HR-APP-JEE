package org.employee.dea;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.employee.model.Post;
import org.hibernate.Session;
import org.util.HibernateUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/addPost")
public class PostDAO extends HttpServlet {



    public void addPost(Post etu){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(etu);
        session.getTransaction().commit();

        session.close();
        

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Post myPost = new Post();

        addPost(myPost);

        PrintWriter out = resp.getWriter();
        out.write("Added Post to DB");

    }
    
}
