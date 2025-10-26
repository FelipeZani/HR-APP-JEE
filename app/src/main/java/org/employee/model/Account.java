package org.employee.model;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Account implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int accountId;

    private String username;
    @Column(updatable = true)
    private String password;

    @Column(updatable =  true)
    private boolean firstConnexion;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employeeId", referencedColumnName = "employeeId")
    private Employee employee;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isFirstConnexion() {
        return firstConnexion;
    }

    public void setFirstConnexion(boolean firstConnexion) {
        this.firstConnexion = firstConnexion;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }  
    
}
