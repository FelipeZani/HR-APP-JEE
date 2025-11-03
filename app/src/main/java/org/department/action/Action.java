package org.department.action;

import java.io.IOException;

import org.department.dea.DepartmentDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class Action {
    
    protected static DepartmentDAO dao = new DepartmentDAO();

    public abstract void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    
}
