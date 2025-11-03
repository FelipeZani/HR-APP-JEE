package org.employee.servlet;

import java.io.IOException;
import java.util.Enumeration;

import org.employee.action.GetPostsListForDialog;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/postServlet")
public class PostServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        try {

            Enumeration<String> attributeNames = req.getParameterNames();
            
            if(attributeNames == null || attributeNames.nextElement() == null){
                resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Unknown passed method");
                resp.getWriter().write("Error : Unknown passed method");

                return;

            }

            String actionKey = (String)req.getParameter("action");

            switch (actionKey) {
                case "getpostlist":
                    GetPostsListForDialog getallemp = new GetPostsListForDialog();
                    getallemp.execute(req, resp);
                        
                    break;
            
                default:
                    resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Unknown passed method");

                    break;
            }

        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Post data unavailable");
            resp.getWriter().write("Error : Unknown passed method");


            e.printStackTrace();

        }


    }
    
}
