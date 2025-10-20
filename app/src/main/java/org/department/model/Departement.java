package org.department.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Deparement")
public class Departement {
    
    @Id
    @Column(name="departmentId")
    private int departementId;

    @Column(name = "name")
    private String name;

}
