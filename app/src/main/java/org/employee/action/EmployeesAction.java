package org.employee.action;


import org.employee.dea.EmployeeDAO;

public abstract class EmployeesAction implements Action {

    protected static EmployeeDAO dao = new EmployeeDAO();

    public static EmployeeDAO getDao() {
        return dao;
    }

}
