package org.project.model;


import org.employee.model.Employee;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;


@Entity
@Table(name = "EmployeeProject")
public class EmployeeProject {

    @EmbeddedId
    private EmployeeProjectId id;

    @ManyToOne
    @MapsId("employeeId") 
    @JoinColumn(name = "employeeId")
    private Employee employee;

    @ManyToOne
    @MapsId("projectId")
    @JoinColumn(name = "projectId")
    private Project project;

    @Column(name = "assignmentDate")
    private String assignmentDate;

    public EmployeeProjectId getId() {
        return id;
    }

    public void setId(EmployeeProjectId id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getAssignmentDate() {
        return assignmentDate;
    }

    public void setAssignmentDate(String assignmentDate) {
        this.assignmentDate = assignmentDate;
    }
}