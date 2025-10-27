package org.employee.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
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

    @Column(updatable = true)
    private float bonus;
    
    @Column(updatable=true)
    private String creationDate;
    
    @Column(updatable=true)
    private String date;


    @ManyToOne
    @JoinColumn(name="employeeId",referencedColumnName = "employeeId")
    private Employee employee;


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


    public float getBonus() {
        return bonus;
    }


    public void setBonus(float bonus) {
        this.bonus = bonus;
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


    public Employee getEmployee() {
        return employee;
    }


    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    
}
