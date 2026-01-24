package com.example.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.backend.dto.ClassScoreRequest;
import com.example.backend.entity.ClassScore;
import com.example.backend.entity.Enrollment;
import com.example.backend.repository.ClassScoreRepository;
import com.example.backend.repository.EnrollmentRepository;

@Service
public class ClassScoreService {
    @Autowired
    EnrollmentRepository enrollmentRepository;

    @Autowired
    ClassScoreRepository classScoreRepository;
    // Add service methods here
    public ClassScore createClassScore(@RequestBody ClassScoreRequest scoreRequest) {
        // Implementation goes here
        Optional<Enrollment> enrollmentOpt = enrollmentRepository.findById(scoreRequest.getEnrollmentId());
        if (enrollmentOpt.isEmpty()) {
            return null; // Or throw an exception
        }
        ClassScore score = new ClassScore(
            enrollmentOpt.get(),
            scoreRequest.getAssessmentType(),
            new java.math.BigDecimal(scoreRequest.getScore()),
            scoreRequest.getMaxScore() != null ? new java.math.BigDecimal(scoreRequest.getMaxScore()) : java.math.BigDecimal.valueOf(100),
            java.time.Instant.now()
        );
        return classScoreRepository.save(score);

    }
}
