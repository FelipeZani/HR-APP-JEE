package org.employee.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.util.HashSet;
import java.util.Set;

import org.department.model.Department;
import org.project.model.EmployeeProject;
import org.project.model.Project;

@Entity
public class Employee{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int employeeId;


    @Column(name="name", updatable = true, nullable = false)
    private String name;
    @Column(name="lastname", updatable = true, nullable = false)
    private String lastname;
    @Column(name="rank", updatable = true, nullable = false)
    private String rank;
    @Column(name = "employmentdate" , updatable=false, nullable = false)
    private String employementdate;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    private Set<PayStub> payStubSet = new HashSet<PayStub>();


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "postId", referencedColumnName = "postId")
    private Post post;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "departmentId", referencedColumnName="departmentId")
    private Department department;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private Set<EmployeeProject> employeeProjects = new HashSet<>();


    @OneToOne(mappedBy = "employee",cascade = CascadeType.ALL)
    private Account userAccount;

    @OneToOne(mappedBy = "managerEmployee",cascade = CascadeType.ALL)
    private Department departmentManagered;

    @OneToOne(mappedBy = "projectManagerEmployee",cascade = CascadeType.ALL)
    private Project projectManagered;



    public int getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastName() {
        return lastname;
    }
    public void setLastName(String lastName) {
        this.lastname = lastName;
    }
    public String getRank() {
        return rank;
    }
    public void setRank(String rank) {
        this.rank = rank;
    }
    public String getEmployementDate() {
        return employementdate;
    }
    public void setEmployementDate(String employementDate) {
        this.employementdate = employementDate;
    }
    public Set<PayStub> getPayStubSet() {
        return payStubSet;
    }
    public void setPayStubSet(Set<PayStub> payStubSet) {
        this.payStubSet = payStubSet;
    }

    public Account getUserAccount() {
        return userAccount;
    }
    public void setUserAccount(Account userAccount) {
        this.userAccount = userAccount;
    }
    public Post getPost() {
        return post;
    }
    public void setPost(Post post) {
        this.post = post;
    }
    public Department getDepartment() {
        return department;
    }
    public void setDepartment(Department department) {
        this.department = department;
    }
    public Department getDepartmentManagered() {
        return departmentManagered;
    }
    public void setDepartmentManagered(Department departmentManagered) {
        this.departmentManagered = departmentManagered;
    }
    public Set<EmployeeProject> getEmployeeProjects() {
        return employeeProjects;
    }
    public void setEmployeeProjects(Set<EmployeeProject> employeeProjects) {
        this.employeeProjects = employeeProjects;
    }
    public Project getProjectManagered() {
        return projectManagered;
    }
    public void setProjectManagered(Project projectManagered) {
        this.projectManagered = projectManagered;
    }
    
    

}
