package com.example.backend.dto;

public class ClassScoreRequest {
    private String assessmentType; // e.g., "Midterm", "Final", "Quiz"
    private Long enrollmentId;
    private String score;
    private String maxScore;

    public ClassScoreRequest() {}

    public ClassScoreRequest(String assessmentType, Long enrollmentId, String score, String maxScore) {
        this.assessmentType = assessmentType;
        this.enrollmentId = enrollmentId;
        this.score = score;
        this.maxScore = maxScore;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public Long getEnrollmentId() {
        return enrollmentId;
    }

    public String getScore() {
        return score;
    }

    public String getMaxScore() {
        return maxScore;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public void setEnrollmentId(Long enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public void setScore(String score) {
        this.score = score;
    }
    public void setMaxScore(String maxScore) {
        this.maxScore = maxScore;
    }
}