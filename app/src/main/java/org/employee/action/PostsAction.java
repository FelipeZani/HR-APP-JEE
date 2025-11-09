package org.employee.action;

import org.employee.dea.PostDAO;

public abstract class PostsAction implements Action{
    protected static PostDAO dao = new PostDAO();

    public static PostDAO getDao() {
        return dao;
    }
    
}
