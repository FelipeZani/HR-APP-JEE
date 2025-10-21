package org.project.model;

import java.util.HashSet;
import java.util.Set;

import org.employee.model.Employee;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Table(name = "Projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int projectId;
    @Column(updatable = true)
    private String label;
    @Column(updatable = true)
    private String status;
    @Column(updatable = true)
    private String deadLine;

    @ManyToMany(mappedBy = "Project")
    private Set<Employee> employees = new HashSet<Employee>();
}
