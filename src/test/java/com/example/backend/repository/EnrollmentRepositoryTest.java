package com.example.backend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.backend.entity.Course;
import com.example.backend.entity.Enrollment;
import com.example.backend.entity.Student;

@SpringBootTest
public class EnrollmentRepositoryTest {
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        enrollmentRepository.deleteAll();
        courseRepository.deleteAll();
        studentRepository.deleteAll();
    }

    void addOneStudent() {
        Student student = new Student();
        student.setEmail("john.doe@example.com");
        student.setName("John Doe");
        studentRepository.save(student);
    }

    void addOneCourse() {
        Course course = new Course("Math 101", "Basic Mathematics");
        courseRepository.save(course);
    }

    void addOneEnrollment(Student student, Course course) {
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollmentRepository.save(enrollment);
    }

    void addCoursePrerequisite(Course course, Course prerequisite) {
        course.getPrerequisites().add(prerequisite);
        courseRepository.save(course);
    }

    @Test
    void testOneEnrollmentDetails() {
        addOneStudent();
        Student student = studentRepository.findAll().get(0);

        addOneCourse();
        Course course = courseRepository.findAll().get(0);

        addOneEnrollment(student, course);
        Enrollment enrollment = enrollmentRepository.findAll().get(0);
        assert(enrollmentRepository.count() == 1);
        assert(enrollment.getStudent().getName().equals("John Doe"));
        assert(enrollment.getCourse().getName().equals("Math 101"));
    }

    @Test
    void testEnrollmentWithPrerequisite() {
        System.out.println("=== DEBUG START ===");
        addOneStudent();
        Student student = studentRepository.findAll().get(0);

        Course prerequisite = new Course("Intro to Math", "Introduction to Mathematics");
        courseRepository.save(prerequisite);

        Course mainCourse = new Course("Advance Math", "Advanced Mathematics");
        mainCourse.getPrerequisites().add(prerequisite);
        courseRepository.save(mainCourse);

        // ✅ ADD THESE DEBUG LINES
        System.out.println("=== DEBUG START ===");
        System.out.println("Student count: " + studentRepository.count());
        System.out.println("Main course ID: " + mainCourse.getId());
        System.out.println("Prerequisites: " + mainCourse.getPrerequisites().size());
    
        try {
            addOneEnrollment(student, mainCourse);
            System.out.println("✅ Enrollment SUCCEEDED");
        } catch (Exception e) {
            System.out.println("❌ Enrollment FAILED: " + e.getMessage());
            e.printStackTrace();
        }
        Enrollment enrollment = enrollmentRepository.findAll().get(0);
        System.out.println("Enrollment count: " + enrollmentRepository.count());
        assert(enrollmentRepository.count() == 1);
        assertEquals(1, enrollmentRepository.count());
        assert(mainCourse.getPrerequisites().contains(prerequisite));
    }
}
