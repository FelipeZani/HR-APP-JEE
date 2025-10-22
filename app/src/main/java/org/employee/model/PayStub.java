package org.employee.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class PayStub {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int payStubId;
    @Column(updatable=true)
    private float total;
    @Column(updatable=true)
    private String creationDate;
    @Column(updatable=true)
    private String date;


    @OneToMany
    @JoinColumn(name = "employeeId")
    private List<Employee> employeeList;
    
}
