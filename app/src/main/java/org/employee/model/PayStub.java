package org.employee.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

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
    @ManyToOne
    private int employeeId ;
    
}
