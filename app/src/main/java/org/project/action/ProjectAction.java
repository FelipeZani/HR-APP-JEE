package org.project.action;

import java.io.IOException;

import org.project.dea.ProjectDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class ProjectAction {
    
    protected static ProjectDAO dao = new ProjectDAO();

    public abstract void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    public static ProjectDAO getDao() {
        return dao;
    }
    
}
