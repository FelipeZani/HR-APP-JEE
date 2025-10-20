package org.employee.model;

import jakarta.persistence.Table;

@Table(name="Post")
public class Post {
    String label;
    float money;
}
