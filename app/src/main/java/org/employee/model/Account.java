package org.employee.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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
    
}
