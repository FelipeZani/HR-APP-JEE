package org.employee.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class RoleEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int roleEmployeeId;

    private String label;
    
    private Set<String> permissions;

    // @ManyToMany(mappedBy = "RoleEmployee")
    // private Set<Employee> employees = new HashSet<>();
}
