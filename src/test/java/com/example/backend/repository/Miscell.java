package com.example.backend.repository;

import com.example.backend.entity.Course;
import com.example.backend.entity.Enrollment;
import com.example.backend.entity.Student;

public class Miscell {
    public static void addOneStudent(String email, String name, StudentRepository studentRepository) {
        Student student = new Student();
        student.setEmail(email);
        student.setName(name);
        studentRepository.save(student);
    }

    public static void addOneCourse(String code, String title, String description, CourseRepository courseRepository) {
        Course course = new Course(code, title, description);
        courseRepository.save(course);
    }

    public static void addOneEnrollment(Student student, Course course, EnrollmentRepository enrollmentRepository) {
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollmentRepository.save(enrollment);
    }

    public static void addCoursePrerequisite(Course course, Course prerequisite, CourseRepository courseRepository) {
        course.getPrerequisites().add(prerequisite);
        courseRepository.save(course);
    }
}
