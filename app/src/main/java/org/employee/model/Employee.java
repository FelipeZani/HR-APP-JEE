package org.employee.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.department.model.Department;
import org.project.model.Project;

@Entity
public class Employee implements Serializable {

    @Column(name="name", updatable = true)
    private String name;
    @Column(name="lastname", updatable = true)
    private String lastName;
    @Column(name="rank", updatable = true)
    private String rank;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int employeeId; 
    
    // @ManyToOne
    // @JoinColumn(name = "departementId")
    // private Department departement;


    // @OneToMany
    // @JoinColumn(name = "postId")
    // private Post post;

    // @ManyToMany(cascade =  CascadeType.ALL )
    // @JoinTable(
    //     joinColumns = { @JoinColumn(name = "employeeId") }, 
    //     inverseJoinColumns = { @JoinColumn(name = "roleEmployeeId") }
    // )
    // private Set<RoleEmployee> rolesEmployee = new HashSet<>();
    
    // @ManyToMany(cascade = CascadeType.ALL)
    // @JoinTable(
    //     joinColumns ={@JoinColumn(name = "employeeId")},
    //     inverseJoinColumns = {@JoinColumn(name = "projectId")}
    // )
    // private Set<Project> projects = new HashSet<Project>();
    

    // @OneToOne(cascade = CascadeType.ALL)
    // @JoinColumn(name = "accountId", referencedColumnName = "accountId")
    // private Account account;    
}
