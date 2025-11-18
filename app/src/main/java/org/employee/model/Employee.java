package org.employee.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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
public class Employee {

    public static enum Permissions {
        PROJECTMANAGER,
        DEPARTMENTMANAGER,
        HR,
        NORMALEMPLOYEE;

    }

    public static enum Rank {
        JUNIOR,
        MIDLEVEL,
        SENIOR;

        @Override
        public String toString() {
            switch (this.ordinal()) {
                case 0:
                    return "JUNIOR";
                case 1:
                    return "MIDLEVEL";
                case 2:
                    return "SENIOR";
                default:
                    return null;
            }
        }

        public static Rank getRank(String rank) {
            switch (rank) {
                case "junior":
                    return JUNIOR;
                case "midlevel":
                    return MIDLEVEL;
                case "senior":
                    return SENIOR;
                default:
                    return null;
            }
        }

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int employeeId;

    @Column(name = "name", updatable = true, nullable = false)
    private String name;
    @Column(name = "lastname", updatable = true, nullable = false)
    private String lastname;
    
    @Column(name = "employee_rank", updatable = true, nullable = false) // Changed from "rank" to "employee_rank"
    @Enumerated(EnumType.STRING)    
    private Rank rank;

    @Column(name = "employmentdate", updatable = false, nullable = false)
    private String employementdate;

    @Column(name = "permissions", updatable = true, nullable = false)
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Permissions.class, fetch = FetchType.EAGER)
    private Set<Permissions> permissions = new HashSet<Permissions>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    private Set<PayStub> payStubSet = new HashSet<PayStub>();

    @ManyToOne
    @JoinColumn(name = "postId", referencedColumnName = "postId")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "departmentId", referencedColumnName = "departmentId")
    private Department department;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.MERGE, orphanRemoval = true)
    private Set<EmployeeProject> employeeProjects = new HashSet<>();

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Account userAccount;

    @OneToOne(mappedBy = "managerEmployee")
    private Department departmentManagered;

    @OneToOne(mappedBy = "projectManagerEmployee")
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

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
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

    public void removeEmployeeProject() {

        for (EmployeeProject empro : this.employeeProjects) {
            if (this.employeeId == empro.getEmployee().getEmployeeId()) {
                this.employeeProjects.remove(empro);
            }
        }

    }

    public Set<EmployeeProject> getEmployeeProjects() {
        return employeeProjects;
    }

    public void addEmployeeProjects(EmployeeProject emp) {
        this.employeeProjects.add(emp);
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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmployementdate() {
        return employementdate;
    }

    public void setEmployementdate(String employementdate) {
        this.employementdate = employementdate;
    }

    public Set<Permissions> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permissions> permissions) {
        this.permissions = permissions;
    }

    public void addPermission(Permissions permission) {
        this.permissions.add(permission);
    }

}
