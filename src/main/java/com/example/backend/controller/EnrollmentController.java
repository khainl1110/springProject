package com.example.backend.controller;

import com.example.backend.entity.Enrollment;
import com.example.backend.dto.EnrollmentDto;
import com.example.backend.dto.Mapper;
import com.example.backend.repository.EnrollmentRepository;
import com.example.backend.service.EnrollmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {
    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentRepository enrollmentRepository,
                                EnrollmentService enrollmentService) {
        this.enrollmentRepository = enrollmentRepository;
        this.enrollmentService = enrollmentService;
    }

    public static class EnrollmentRequest {
        private Long studentId;
        private Long courseId;

        public Long getStudentId() { return studentId; }
        public void setStudentId(Long studentId) { this.studentId = studentId; }
        public Long getCourseId() { return courseId; }
        public void setCourseId(Long courseId) { this.courseId = courseId; }
    }

    // This one is circular reference
    @GetMapping
    public Iterable<Enrollment> getMethodName() {
        return enrollmentRepository.findAll();
    }

    @GetMapping("/all")
    public List<EnrollmentDto> getCourseDetailsWithoutCircularReference() {
        List<Enrollment> enrollments = enrollmentRepository.findAll();
        return enrollments.stream().map(Mapper::toDto).toList();
    }

    @GetMapping("/student/{studentId}")
    public List<EnrollmentDto> getEnrollmentsByStudentId(@PathVariable Long studentId) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);
        return enrollments.stream().map(Mapper::toDto).toList();
    }
    
    @PostMapping
    public ResponseEntity<?> create(@RequestBody EnrollmentRequest request) {
        Long studentId = request.getStudentId();
        Long courseId = request.getCourseId();

        EnrollmentDto enrollmentDto = enrollmentService.createEnrollment(studentId, courseId);
        return ResponseEntity.status(HttpStatus.CREATED).body(enrollmentDto);
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<?> completeEnrollment(@PathVariable Long id) {
        Optional<Enrollment> opt = enrollmentRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        Enrollment e = opt.get();
        e.setCompleted(true);
        enrollmentRepository.save(e);
        return ResponseEntity.ok(Mapper.toDto(e));
    }
}
