package com.example.backend.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.ClassScoreDto;
import com.example.backend.dto.ScoreRequest;
import com.example.backend.entity.ClassScore;
import com.example.backend.repository.ClassScoreRepository;
import com.example.backend.service.ClassScoreService;

@RestController
@RequestMapping("/classscore")
public class ClassScoreController {
    @Autowired
    ClassScoreRepository classScoreRepository;

    @Autowired ClassScoreService classScoreService;

    // private EnrollmentDto toDto(Enrollment enrollment) {
    private ClassScoreDto toDto(ClassScore classScore) {
        return new ClassScoreDto(
            classScore.getAssessmentType(),
            classScore.getEnrollment().getId(),
            classScore.getScore().toString(),
            classScore.getMaxScore().toString()
        );
    }
    
    @GetMapping
    public Iterable<ClassScoreDto> getClassScores() {
        return classScoreRepository.findAll().stream().map(this::toDto).toList();
    }

    @PostMapping
    public String createClassScore(@RequestBody ScoreRequest scoreRequest) {
        // (Enrollment enrollment, String assessmentType, BigDecimal score, BigDecimal maxScore, Instant recordedAt) {

        ClassScore classScore = classScoreService.createClassScore(scoreRequest);
        return classScore.toString();
    }
}
