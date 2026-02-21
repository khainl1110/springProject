package com.example.backend.entity;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Index;

@Entity
@Table(name = "class_score", indexes = {
    @Index(name = "idx_enrollment_id", columnList = "enrollment_id")
})
public class ClassScore {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "enrollment_id", nullable = false)
    private Enrollment enrollment;
    private String assessmentType;
    private BigDecimal score;
    private BigDecimal maxScore;

    private Instant recordedAt;

    public ClassScore() {}
    public ClassScore(Enrollment enrollment, String assessmentType, BigDecimal score, BigDecimal maxScore, Instant recordedAt) {
        this.enrollment = enrollment;
        this.assessmentType = assessmentType;
        this.score = score;
        this.maxScore = maxScore;
        this.recordedAt = recordedAt;
    }

    public Long getId() {
        return id;
    }
    public Enrollment getEnrollment() {
        return enrollment;
    }
    public String getAssessmentType() {
        return assessmentType;
    }
    public BigDecimal getScore() {
        return score;
    }
    public BigDecimal getMaxScore() {
        return maxScore;
    }

    public Instant getRecordedAt() {
        return recordedAt;
    }
}
