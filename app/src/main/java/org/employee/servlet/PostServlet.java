package org.employee.servlet;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Enumeration;

import org.employee.action.AddPostAction;
import org.employee.action.GetPostsListForDialog;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/postServlet")
public class PostServlet extends HttpServlet {

    private boolean checkParametersIntegrity(HttpServletRequest req, HttpServletResponse resp)
            throws NullPointerException, IOException {

        Enumeration<String> parameterKeyNames = req.getParameterNames();

        if (parameterKeyNames == null) {
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Unknown passed method");

            throw new NullPointerException("Parameters String enumeration is null");
        }

        while (parameterKeyNames.hasMoreElements()) {

            String parameterKey = parameterKeyNames.nextElement();

            if (req.getParameter(parameterKey) == null) {
                throw new NullPointerException("Parameters String enumeration is null");
            }
        }
        return true;

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            Enumeration<String> attributeNames = req.getParameterNames();

            if (attributeNames == null || attributeNames.nextElement() == null) {
                resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Unknown passed method");
                resp.getWriter().write("Error : Unknown passed method");

                return;

            }

            String actionKey = (String) req.getParameter("action");

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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            checkParametersIntegrity(req, resp);

            String actionKey = (String) req.getParameter("action");

            switch (actionKey) {
                case "addnewpost":
                    AddPostAction addPost = new AddPostAction();
                    addPost.execute(req, resp);
                    break;

                default:
                    resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Unknown passed method");

                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
