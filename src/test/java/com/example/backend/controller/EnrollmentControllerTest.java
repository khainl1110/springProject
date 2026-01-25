package com.example.backend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.example.backend.entity.Course;
import com.example.backend.entity.Student;
import com.example.backend.repository.CourseRepository;
import com.example.backend.repository.StudentRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

// use ./gradlew test --tests EnrollmentRepositoryTest.testEnrollmentWithPrerequisite --info for log

@SpringBootTest
@AutoConfigureMockMvc
public class EnrollmentControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired StudentRepository studentRepository;
    @Autowired CourseRepository courseRepository;
    
    @BeforeEach
    void setUp() {
        studentRepository.deleteAll();
        courseRepository.deleteAll();

        Student testStudent = studentRepository.save(new Student("jane.doe@gmail.com", "Jane Doe"));
        Course mainCourse = courseRepository.save(new Course("MA101", "Math 101", "Mathematics"));
        Course prerequisiteCourse = courseRepository.save(new Course("INTRO101", "Intro to Math", "Introduction to Mathematics"));

        studentRepository.save(testStudent);
        mainCourse.getPrerequisites().add(prerequisiteCourse);
        courseRepository.save(mainCourse);
        courseRepository.save(prerequisiteCourse);

    }

    @Test
    void studentEnrollingInCourseShouldSucceed() throws Exception {
        // Implementation for testing successful enrollment
        mockMvc.perform(post("/enrollment")
            .contentType("application/json")
            .content("{\"studentId\": 1, \"courseId\": 2}"))
            .andExpect(status().isCreated());
    }

    @Test
    void studentEnrollingInCourseWithoutPrerequisiteShouldFail() throws Exception {
        // Implementation for testing enrollment failure due to missing prerequisite
        mockMvc.perform(post("/enrollment")
            .contentType("application/json")
            .content("{\"studentId\": 1, \"courseId\": 1}"))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    void studentEnrolledingInCourseWithPrerequisiteShouldSucceed() throws Exception {
        // Implementation for testing successful enrollment after completing prerequisite
        mockMvc.perform(post("/enrollment")
            .contentType("application/json")
            .content("{\"studentId\": 1, \"courseId\": 2}"))
            .andExpect(status().isCreated());

        mockMvc.perform(put("/enrollment/1/complete")) // 1 here is the enrollment ID for prerequisite
            .andExpect(status().isOk());

        mockMvc.perform(post("/enrollment")
            .contentType("application/json")
            .content("{\"studentId\": 1, \"courseId\": 1}"))
            .andExpect(status().isCreated());
    }
}
