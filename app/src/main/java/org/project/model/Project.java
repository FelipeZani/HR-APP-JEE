package org.project.model;

import java.util.HashSet;
import java.util.Set;

import org.employee.model.Employee;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int projectId;
    @Column(updatable = true)
    private String label;
    @Column(updatable = true)
    private String status;
    @Column(updatable = true)
    private String deadLine;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "managerId", referencedColumnName = "employeeId")
    private Employee manager;

    
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private Set<EmployeeProject> employeeProjects = new HashSet<>();


    public int getProjectId() {
        return projectId;
    }


    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }


    public String getLabel() {
        return label;
    }


    public void setLabel(String label) {
        this.label = label;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public String getDeadLine() {
        return deadLine;
    }


    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }


    public Employee getManager() {
        return manager;
    }


    public void setManager(Employee manager) {
        this.manager = manager;
    }


    public Set<EmployeeProject> getEmployees() {
        return employeeProjects;
    }


    public void setEmployees(Set<EmployeeProject> employees) {
        this.employeeProjects = employees;
    }

}
