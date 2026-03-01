package com.example.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.dto.EnrollmentDto;
import com.example.backend.dto.Mapper;
import com.example.backend.entity.Course;
import com.example.backend.entity.Enrollment;
import com.example.backend.entity.Student;
import com.example.backend.exception.AlreadyEnrolledException;
import com.example.backend.exception.InvalidEnrollmentRequestException;
import com.example.backend.exception.MissingPrerequisitesException;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.repository.CourseRepository;
import com.example.backend.repository.EnrollmentRepository;
import com.example.backend.repository.StudentRepository;

@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;

    @Autowired CourseRepository courseRepository;
    @Autowired StudentRepository studentRepository;
    
    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public EnrollmentDto createEnrollment(Long studentId, Long courseId) {
        // if (!canEnroll(studentId, courseId)) {
        //     throw new IllegalStateException("Student does not meet prerequisites for this course.");
        // }

        if (studentId == null || courseId == null) {
            throw new InvalidEnrollmentRequestException("studentId and courseId are required");
        }

        Student student = studentRepository.findById(studentId)
            .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + studentId));  
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + courseId));

        List<String> missingPrereqs = getMissingPrerequisites(studentId, course);
        if (!missingPrereqs.isEmpty()) {
            throw new MissingPrerequisitesException(missingPrereqs);
        }

        if (enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            throw new AlreadyEnrolledException(studentId, courseId);
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollmentDate(new java.util.Date());
        Enrollment saved = enrollmentRepository.save(enrollment);
        return Mapper.toDto(saved);
    }

    public boolean canEnroll(Long studentId, Long courseId) {
        var course = courseRepository.findById(courseId);
        if (course.isEmpty()) return false;
        var prereqs = course.get().getPrerequisites();
        if (prereqs == null || prereqs.isEmpty()) return true;
        return prereqs.stream()
            .allMatch(p -> enrollmentRepository.existsByStudentIdAndCourseIdAndIsCompletedTrue(studentId, p.getId()));
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
