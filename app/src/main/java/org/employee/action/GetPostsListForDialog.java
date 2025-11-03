package org.employee.action;

import java.util.List;

import org.employee.model.Post;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GetPostsListForDialog extends PostsAction {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            
            String responseHTML ="<option value=\"\" selected disabled>Select post</option>";

            List<Post> postsList = PostsAction.dao.getPostList();
            for (Post post : postsList) {
                responseHTML+="<option value='"+post.getLabel()+"'>"+post.getLabel()+"</option>";
            }

            response.getWriter().write(responseHTML);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
