package org.employee.action;

import org.employee.model.Post;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AddPostAction extends PostsAction {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        try {

            String label = (String) request.getParameter("postlabel");
            String strWage = request.getParameter("postwage");

            if (label == null || label.isBlank() || !label.matches("[A-ZÀ-Ï][a-zà-ï]+(\\s[A-ZÀ-Ï][a-zà-ï]+)*")) {
                response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Post Label not allowed");
                return;
            }

            if (strWage == null || strWage.isBlank() || !strWage.matches("[0-9]+")) {
                response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Wage format is not allowed");
                return;

            }
            float wage = Float.parseFloat(strWage);

            Post newPost = new Post();

            newPost.setWage(wage);
            newPost.setLabel(label);

            PostsAction.getDao().addPost(newPost);

            response.getWriter().write("<p>Post successfully created</p>");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
