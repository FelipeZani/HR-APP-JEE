package org.employee.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RoleEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int roleEmployeeId;

    private String label;
    
    private Set<String> permissions;

    public int getRoleEmployeeId() {
        return roleEmployeeId;
    }

    public void setRoleEmployeeId(int roleEmployeeId) {
        this.roleEmployeeId = roleEmployeeId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    // @ManyToMany(mappedBy = "RoleEmployee")
    // private Set<Employee> employees = new HashSet<>();
}
