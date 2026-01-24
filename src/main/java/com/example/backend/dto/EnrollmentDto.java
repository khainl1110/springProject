package com.example.backend.dto;

import java.util.Set;

public class EnrollmentDto {
    private Long id;
    private boolean completed;
    private StudentDto student;
    private CourseDto course;
    private Set<ClassScoreDto> scores;

    public EnrollmentDto() {}

    public EnrollmentDto(Long id, boolean completed, StudentDto student, CourseDto course) {
        this.id = id;
        this.completed = completed;
        this.student = student;
        this.course = course;
    }

    public EnrollmentDto(Long id, boolean completed, StudentDto student, CourseDto course, Set<ClassScoreDto> scores) {
        this.id = id;
        this.completed = completed;
        this.student = student;
        this.course = course;
        this.scores = scores;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public StudentDto getStudent() { return student; }
    public void setStudent(StudentDto student) { this.student = student; }

    public CourseDto getCourse() { return course; }
    public void setCourse(CourseDto course) { this.course = course; }

    public Set<ClassScoreDto> getScores() { return scores; }
    public void setScores(Set<ClassScoreDto> scores) { this.scores = scores; }
}
