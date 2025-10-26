package org.employee.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int postId;
    String label;
    float wage;

    @ManyToOne
    @JoinColumn(name="employeeId")
    private Employee employee;

    public int getPostId() {
        return postId;
    }
    public void setPostId(int postId) {
        this.postId = postId;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public float getWage() {
        return wage;
    }
    public void setWage(float money) {
        this.wage = money;
    }
    public Employee getEmployee() {
        return employee;
    }
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    

    
}
