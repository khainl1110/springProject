package com.example.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.entity.Course;
import com.example.backend.entity.Student;
import com.example.backend.repository.StudentRepository;
import com.example.backend.service.StudentService;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired 
    private StudentService studentService;

    @GetMapping
    public Iterable<Student> returnAllStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/{email}")
    public Student getEachStudentDetails(@PathVariable String email) {
        return studentService.getStudentWithEnrollments(email);

    }

    @GetMapping("/{email}/details")
    public Map<String, Object> getStudentDetails(@PathVariable String email) {
        Student student = studentService.getStudentWithEnrollments(email);
        
        Map<String, Object> response = new HashMap<>();
        response.put("name", student.getName());
        response.put("enrollments", student.getEnrollments().stream()
            .map(e -> Map.of(
                "course", e.getCourse().getName(),
                "prerequisites", e.getCourse().getPrerequisites().stream()
                    .map(Course::getName).collect(Collectors.toList())
            ))
            .collect(Collectors.toList()));
        
        return response;
    }
    
    

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody Student student) {
        if (student.getEmail() == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email is required"));
        }
        Optional<Student> existing = studentRepository.findByEmail(student.getEmail());
        if (existing.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "Email already exists"));
        }
        Student createdStudent = studentRepository.save(student);
        return ResponseEntity.ok(createdStudent);
    }

    @PutMapping
    public ResponseEntity<Student> modifyStudent(@RequestBody Student student) {
        if (student.getEmail() == null) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Student> existing = studentRepository.findByEmail(student.getEmail());
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Student s = existing.get();
        s.setName(student.getName());
        Student updated = studentRepository.save(s);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllStudents() {
        studentRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
