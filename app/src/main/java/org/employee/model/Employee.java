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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.department.model.Department;
import org.project.model.Project;

@Entity
public class Employee {

    @Column(name="name", updatable = true)
    private String name;
    @Column(name="lastname", updatable = true)
    private String lastName;
    @Column(name="rank", updatable = true)
    private String rank;
    @Column(name = "employmentDate" , updatable=false)
    private String employementDate;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int employeeId; 
    
    @ManyToOne
    @JoinColumn(name = "departementId")
    private Department departement;


    @OneToMany
    @JoinColumn(name = "postId")
    private List<Post> postsList;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "accountId", referencedColumnName = "accountId")
    private Account account;  

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="payStubId")
    private PayStub payStub;



    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        joinColumns ={@JoinColumn(name = "employeeId")},
        inverseJoinColumns = {@JoinColumn(name = "projectId")}
    )
    private List<Project> projects = new ArrayList<Project>();
    
    // @ManyToMany(cascade =  CascadeType.ALL )
    // @JoinTable(
    //     joinColumns = { @JoinColumn(name = "employeeId") }, 
    //     inverseJoinColumns = { @JoinColumn(name = "roleEmployeeId") }
    // )
    // private List<RoleEmployee> rolesEmployeeList;
    


    public int getemployeeId(){ return employeeId; }

}
