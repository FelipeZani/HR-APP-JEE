package org.employee.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="Post")
public class Post {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "postId")
    int postId;
    @Column(name = "label", unique = true, nullable = false)
    String label;
    @Column(name = "wage", nullable = false)
    double wage;
    
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<Employee>employeeSet = new HashSet<Employee>();

    public int getPostId() {
        return postId;
    }

    public void addEmployee(Employee emp){
        employeeSet.add(emp);
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

    public double getWage() {
        return wage;
    }

    public void setWage(float wage) {
        this.wage = wage;
    }

    public Set<Employee> getEmployeeSet() {
        return employeeSet;
    }

    public void setEmployeeSet(Set<Employee> employeeSet) {
        this.employeeSet = employeeSet;
    }

    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder( "Post postId=" + postId + ", label=" + label + ", wage=" + wage) ;
        
        Iterator<Employee> it = employeeSet.iterator();
        while(it.hasNext()){
            toString.append(" employee = "+it.next().getName());
        }


        return toString.toString();
    }

 
    

    
}
