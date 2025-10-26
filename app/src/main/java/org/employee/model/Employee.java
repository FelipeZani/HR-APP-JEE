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
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.department.model.Department;
import org.project.model.EmployeeProject;
import org.project.model.Project;

@Entity
public class Employee{




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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "departmentId", referencedColumnName = "departmentId")
    private Department department;   

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="payStubId")
    private PayStub payStub;


    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private Set<EmployeeProject> employeeProject = new HashSet<EmployeeProject>();
    
    // @ManyToMany(cascade =  CascadeType.ALL )
    // @JoinTable(
    //     joinColumns = { @JoinColumn(name = "employeeId") }, 
    //     inverseJoinColumns = { @JoinColumn(name = "roleEmployeeId") }
    // )
    // private List<RoleEmployee> rolesEmployeeList;
    


    public int getemployeeId(){ return employeeId; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getEmployementDate() {
        return employementDate;
    }

    public void setEmployementDate(String employementDate) {
        this.employementDate = employementDate;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Department getDepartement() {
        return departement;
    }

    public void setDepartement(Department departement) {
        this.departement = departement;
    }

    public List<Post> getPostsList() {
        return postsList;
    }

    public void setPostsList(List<Post> postsList) {
        this.postsList = postsList;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public PayStub getPayStub() {
        return payStub;
    }

    public void setPayStub(PayStub payStub) {
        this.payStub = payStub;
    }

    public Set<EmployeeProject> getProjectsSet() {
        return employeeProject;
    }

    public void setProjectsSet(Set<EmployeeProject> employeeProject) {
        this.employeeProject = employeeProject;
    }

}
