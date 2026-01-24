package com.example.backend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.backend.entity.Course;
import com.example.backend.entity.Enrollment;
import com.example.backend.entity.Student;

// use ./gradlew test --tests EnrollmentRepositoryTest.testEnrollmentWithPrerequisite --info for log
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

    @Test
    void testOneEnrollmentDetails() {
        Miscell.addOneStudent("john.doe@example.com", "John Doe", studentRepository);
        Student student = studentRepository.findAll().get(0);

        Miscell.addOneCourse("Math 101", "Basic Mathematics", courseRepository);
        Course course = courseRepository.findAll().get(0);

        Miscell.addOneEnrollment(student, course, enrollmentRepository);
        Enrollment enrollment = enrollmentRepository.findAll().get(0);
        assert(enrollmentRepository.count() == 1);
        assert(enrollment.getStudent().getName().equals("John Doe"));
        assert(enrollment.getCourse().getName().equals("Math 101"));
    }

    @Test
    void testEnrollmentWithPrerequisite() {
        Miscell.addOneStudent("jane.doe@example.com", "Jane Doe", studentRepository);
        Student student = studentRepository.findAll().get(0);

        Course prerequisite = new Course("Intro to Math", "Introduction to Mathematics");
        courseRepository.save(prerequisite);

        Course mainCourse = new Course("Advance Math", "Advanced Mathematics");
        mainCourse.getPrerequisites().add(prerequisite);
        courseRepository.save(mainCourse);

        System.out.println("Student count: " + studentRepository.count());
        System.out.println("Main course ID: " + mainCourse.getId());
        System.out.println("Prerequisites: " + mainCourse.getPrerequisites().size());
    
        try {
            Miscell.addOneEnrollment(student, mainCourse, enrollmentRepository);
            System.out.println("Enrollment SUCCEEDED");
        } catch (Exception e) {
            System.out.println("Enrollment FAILED: " + e.getMessage());
            e.printStackTrace();
        }
        Enrollment enrollment = enrollmentRepository.findAll().get(0);
        System.out.println("Enrollment count: " + enrollmentRepository.count());
        assert(enrollmentRepository.count() == 1);
        assertEquals(1, enrollmentRepository.count());
        assert(mainCourse.getPrerequisites().contains(prerequisite));
    }

}
