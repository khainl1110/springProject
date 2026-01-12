package com.example.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.entity.Student;
import com.example.backend.repository.StudentRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    
    public Student getStudentWithEnrollments(String email) {
        Student student = studentRepository
            .findByEmailWithFullDetails(email)
            .orElseThrow(() -> new EntityNotFoundException("Student not found: " + email));

        System.out.println("Student: " + student.getName() + " has " + student.getEnrollments().size() + " enrollments");
        
        student.getEnrollments().forEach(e -> {
            System.out.println("Course: " + e.getCourse().getName());
            System.out.println("Prereqs: " + e.getCourse().getPrerequisites().size());
        });
        
        return student;
    }
}
