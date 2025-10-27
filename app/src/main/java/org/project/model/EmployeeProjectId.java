package org.project.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class EmployeeProjectId implements Serializable {
    public EmployeeProjectId(){}
    @Column(name = "employeeId")
    private int employeeId;

    @Column(name = "projectId")
    private int projectId;

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeProjectId)) return false;
        EmployeeProjectId that = (EmployeeProjectId) o;
        return employeeId == that.employeeId && projectId == that.projectId;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(employeeId, projectId);
    }
}