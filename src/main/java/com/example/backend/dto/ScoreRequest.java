package com.example.backend.dto;

public class ScoreRequest {
    private String assessmentType; // e.g., "Midterm", "Final", "Quiz"
    private Long enrollmentId;
    private String score;
    private String maxScore;

    public ScoreRequest() {}

    public ScoreRequest(String assessmentType, Long enrollmentId, String score, String maxScore) {
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
}