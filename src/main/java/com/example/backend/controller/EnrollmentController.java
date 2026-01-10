package com.example.backend.controller;

import com.example.backend.entity.Enrollment;
import com.example.backend.entity.Student;
import com.example.backend.entity.Course;
import com.example.backend.repository.EnrollmentRepository;
import com.example.backend.repository.StudentRepository;
import com.example.backend.repository.CourseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public EnrollmentController(EnrollmentRepository enrollmentRepository,
                                StudentRepository studentRepository,
                                CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public static class EnrollmentRequest {
        private Long studentId;
        private Long courseId;

        public Long getStudentId() { return studentId; }
        public void setStudentId(Long studentId) { this.studentId = studentId; }
        public Long getCourseId() { return courseId; }
        public void setCourseId(Long courseId) { this.courseId = courseId; }
    }

    @GetMapping
    public Iterable<Enrollment> getMethodName() {
        return enrollmentRepository.findAll();
    }
    
    @PostMapping
    public ResponseEntity<?> create(@RequestBody EnrollmentRequest request) {
        Long studentId = request.getStudentId();
        Long courseId = request.getCourseId();

        if (studentId == null || courseId == null) {
            return ResponseEntity.badRequest().body("studentId and courseId are required");
        }

        Optional<Student> studentOpt = studentRepository.findById(studentId);
        Optional<Course> courseOpt = courseRepository.findById(courseId);
        if (studentOpt.isEmpty() || courseOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student or Course not found");
        }

        // check prerequisites: student must have completed all prerequisite courses
        Set<Course> prereqs = courseOpt.get().getPrerequisites();
        if (prereqs != null && !prereqs.isEmpty()) {
            List<String> missing = prereqs.stream()
                .filter(p -> !enrollmentRepository.existsByStudentIdAndCourseIdAndCompletedTrue(studentId, p.getId()))
                .map(Course::getName)
                .collect(Collectors.toList());
            if (!missing.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Missing completed prerequisites: " + String.join(", ", missing));
            }
        }

        if (enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Student already enrolled in this course");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(studentOpt.get());
        enrollment.setCourse(courseOpt.get());
        Enrollment saved = enrollmentRepository.save(enrollment);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<?> completeEnrollment(@PathVariable Long id) {
        Optional<Enrollment> opt = enrollmentRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        Enrollment e = opt.get();
        e.setCompleted(true);
        enrollmentRepository.save(e);
        return ResponseEntity.ok(e);
    }
}
