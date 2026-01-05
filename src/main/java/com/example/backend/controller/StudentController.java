package com.example.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.entity.Student;
import com.example.backend.repository.StudentRepository;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping
    public Iterable<Student> returnAllStudents() {
        return studentRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
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
