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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
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

    @OneToOne
    @JoinColumn(name = "projectManagerEmployeeId", referencedColumnName = "employeeId")
    private Employee projectManagerEmployee;

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


    public Set<EmployeeProject> getEmployeeProjects() {
        return employeeProjects;
    }
    
    public void addEmployeeProjects(EmployeeProject emp){
        this.employeeProjects.add(emp);
    }

    public void setEmployeeProjects(Set<EmployeeProject> employeeProjects) {
        this.employeeProjects = employeeProjects;
    }


    public Employee getProjectManagerEmployee() {
        return projectManagerEmployee;
    }


    public void setProjectManagerEmployee(Employee projectManagerEmployee) {
        this.projectManagerEmployee = projectManagerEmployee;
    }





}
