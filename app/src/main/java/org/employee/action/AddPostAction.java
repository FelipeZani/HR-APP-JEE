package org.employee.action;

import org.employee.model.Post;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AddPostAction extends PostsAction {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        try {

            String label = (String) request.getParameter("postlabel");
            float wage = Float.parseFloat(request.getParameter("postwage")) ;
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
