package com.example.backend.service;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.backend.entity.Course;
import com.example.backend.entity.Enrollment;
import com.example.backend.entity.Student;
import com.example.backend.repository.CourseRepository;
import com.example.backend.repository.EnrollmentRepository;
import com.example.backend.repository.StudentRepository;

@SpringBootTest
public class EnrollmentServiceTest {
    @Autowired
    EnrollmentService enrollmentService;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    EnrollmentRepository enrollmentRepository;

    @BeforeEach
    public void setUp() {
        // Initialize repositories and services here
        studentRepository.deleteAll();
        courseRepository.deleteAll();
        enrollmentRepository.deleteAll();
    }

    @Test
    public void testEnrollStudentInCourseFailed() {
        Student student = new Student();
        student.setEmail("jane.doe@example.com");
        student.setName("Jane Doe");
        Student savedStudent = studentRepository.save(student);
        
        Course prerequisite = new Course("Intro to Math", "Introduction to Mathematics");
        courseRepository.save(prerequisite);

        Course mainCourse = new Course("Advance Math", "Advanced Mathematics");
        mainCourse.getPrerequisites().add(prerequisite);
        courseRepository.save(mainCourse);

        List<String> missingPrerequisites = enrollmentService.getMissingPrerequisites(savedStudent.getId(), mainCourse);
        assert(missingPrerequisites.contains("Intro to Math"));
    }

    @Test
    public void testEnrollStudentInCourseSucceeded() {
        Student student = new Student();
        student.setEmail("jane.doe@example.com");
        student.setName("Jane Doe");
        Student savedStudent = studentRepository.save(student);
        
        Course prerequisite = new Course("Intro to Math", "Introduction to Mathematics");
        courseRepository.save(prerequisite);

        Course mainCourse = new Course("Advance Math", "Advanced Mathematics");
        mainCourse.getPrerequisites().add(prerequisite);
        courseRepository.save(mainCourse);

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(savedStudent);
        enrollment.setCourse(prerequisite);
        enrollment.setCompleted(true);
        enrollmentRepository.save(enrollment);

        List<String> missingPrerequisites = enrollmentService.getMissingPrerequisites(savedStudent.getId(), mainCourse);
        assert(missingPrerequisites.isEmpty());
    }

}
