package com.example.backend.entity;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "enrollment", uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "course_id"}))
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "enrollment", cascade = CascadeType.ALL) 
    private Set<ClassScore> scores;

    @Column(name = "iscompleted")
    private boolean isCompleted = false;

    @Column(name = "enrollment_date")
    private Date enrollmentDate;

    // Getters and setters

    public Long getId() {
        return id;
    }
    
    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(Date enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public Student getStudent() {
        return student;
    }
    
    public Course getCourse() {
        return course;
    }

    public Set<ClassScore> getScores() {
        // when no scores, return empty set instead of null
        return scores != null ? scores : Set.of();
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setCompleted(boolean completed) {
        this.isCompleted = completed;
    }
}