package com.example.backend.entity;

import java.time.LocalDateTime;

public interface StudentClassGradesProjection {
    Long getStudentId();
    String getClassId();
    Integer getScore();
    String getAssessmentType();
    LocalDateTime getRecordedAt();
}
