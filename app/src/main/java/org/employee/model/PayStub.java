package org.employee.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Set<Employee> employeeList = new HashSet<Employee>();


    public int getPayStubId() {
        return payStubId;
    }


    public void setPayStubId(int payStubId) {
        this.payStubId = payStubId;
    }


    public float getTotal() {
        return total;
    }


    public void setTotal(float total) {
        this.total = total;
    }


    public String getCreationDate() {
        return creationDate;
    }


    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }


    public String getDate() {
        return date;
    }


    public void setDate(String date) {
        this.date = date;
    }


    public Set<Employee> getEmployeeList() {
        return employeeList;
    }


    public void setEmployeeList(Set<Employee> employeeList) {
        this.employeeList = employeeList;
    }
    
}
