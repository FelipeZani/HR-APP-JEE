package org.project.model;

import java.io.Serializable;

import org.employee.model.Employee;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Embeddable
public class EmployeeProject implements Serializable {
    @ManyToOne
    @MapsId("employeeId")
    private Employee employee;

    @ManyToOne
    @MapsId("projectId")
    private Project project;
    

    private String affectationDate;
}
