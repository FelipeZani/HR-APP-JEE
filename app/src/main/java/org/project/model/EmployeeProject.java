package org.project.model;

import java.io.Serializable;

import org.employee.model.Employee;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;



@Embeddable
class EmployeeProjectId implements Serializable {  
    @Column(name = "employeeId")
    private Integer employeeId;

    @Column(name = "projectId")
    private Integer projectId;

}

@Entity
public class EmployeeProject implements Serializable {
    

    @EmbeddedId
    private EmployeeProjectId employeeProjectId;

    @ManyToOne
    @MapsId("employeeId")
    private Employee employee;

    @ManyToOne
    @MapsId("projectId")
    private Project project;
    

    private String affectationDate;


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


    public String getAffectationDate() {
        return affectationDate;
    }


    public void setAffectationDate(String affectationDate) {
        this.affectationDate = affectationDate;
    }
}
