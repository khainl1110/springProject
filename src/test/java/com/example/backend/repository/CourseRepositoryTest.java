package com.example.backend.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.backend.entity.Course;


@SpringBootTest
public class CourseRepositoryTest {
    @Autowired
    private CourseRepository courseRepository;

    @BeforeEach
    void setUp() {
        courseRepository.deleteAll();
    }

    @Test
    void testCourseRepositoryIsEmpty() {
        assert(courseRepository.count() == 0);
    }

    @Test
    void addCourse() {
        // Implementation for adding a course and testing can be added here
        Course course = new Course("Intro to Art", "Learn the basics of art.");

        courseRepository.save(course);
        assert(courseRepository.count() == 1);
    }

    @Test
    void addAnotherCourse() {
        Course course = new Course("Advanced Art", "Learn advanced art techniques.");

        courseRepository.save(course);
        assert(courseRepository.count() == 1);
    }

    @Test
    void addPrerequisiteCourse() {
        Course prerequisite = new Course("Basic Drawing", "Learn basic drawing skills.");
        courseRepository.save(prerequisite);

        Course mainCourse = new Course("Sketching", "Learn sketching techniques.");
        mainCourse.getPrerequisites().add(prerequisite);
        courseRepository.save(mainCourse);

        assert(courseRepository.count() == 2);
        assert(mainCourse.getPrerequisites().contains(prerequisite));
    }
}
