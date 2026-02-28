package com.example.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.backend.entity.Course;
import com.example.backend.repository.EnrollmentRepository;

@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    
    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public List<String> getMissingPrerequisites(Long studentId, Course course) {
        var prereqs = course.getPrerequisites();
        if (prereqs == null || prereqs.isEmpty()) return List.of();
        return prereqs.stream()
            .filter(p -> !enrollmentRepository.existsByStudentIdAndCourseIdAndIsCompletedTrue(studentId, p.getId()))
            .map(Course::getName)
            .collect(Collectors.toList());
    }
}
