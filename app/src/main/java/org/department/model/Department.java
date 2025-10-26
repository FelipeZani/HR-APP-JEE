package org.department.model;

import java.util.List;

import org.employee.model.Account;
import org.employee.model.Employee;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Departement")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private int departmentId;

    @Column(name = "name")
    private String name;



    @OneToMany(mappedBy =  "department")
    private List<Employee> employeesList;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employeeId", referencedColumnName = "employeeId")
    private Employee employee;     
     

    public int getDepartementId() {
        return departmentId;
    }

    public void setDepartementId(int departementId) {
        this.departmentId = departementId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployeesList() {
        return employeesList;
    }

    public void setEmployeesList(List<Employee> employeesList) {
        this.employeesList = employeesList;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }


   

}
