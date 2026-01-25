package com.example.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 10, nullable = false)
    private String code;

    @Column(name = "name")
    private String name;

    private String description;

    @ManyToMany
    @JoinTable(
        name = "course_prerequisite",
        joinColumns = @JoinColumn(name = "course_id"),
        inverseJoinColumns = @JoinColumn(name = "prerequisite_id")
    )
    private Set<Course> prerequisites = new HashSet<>();

    public Course() {}
    
    public Course(String code, String name, String description) {
        this.name = name;
        this.description = description;
        this.code = code;
    }
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public Set<Course> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(Set<Course> prerequisites) {
        this.prerequisites = prerequisites;
    }

    public void addPrerequisite(Course course) {
        this.prerequisites.add(course);
    }

    public void removePrerequisite(Course course) {
        this.prerequisites.remove(course);
    }
}
