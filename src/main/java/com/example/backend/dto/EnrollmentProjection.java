package com.example.backend.dto;

import java.time.LocalDate;

public interface EnrollmentProjection {
    Long getId();
    LocalDate getEnrollmentDate();
    Long getStudentId();
    String getStudentName();
    Long getCourseId();
    String getCourseName();
}
