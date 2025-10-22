package org.department.model;

import java.util.List;

import org.employee.model.Employee;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Departement")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int departementId;

    @Column(name = "name")
    private String name;



    @OneToMany(mappedBy =  "department")
    private List<Employee> employeesList;
    
    

    public int getDepartementId() {
        return departementId;
    }

    public void setDepartementId(int departementId) {
        this.departementId = departementId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


   

}
