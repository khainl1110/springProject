package com.example.backend.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.example.backend.entity.Course;
import com.example.backend.entity.Enrollment;
import com.example.backend.entity.Student;

public class Mapper {
    public static EnrollmentDto toDto(Enrollment enrollment) {
        Student s = enrollment.getStudent();
        StudentDto sd = null;
        if (s != null) {
            sd = new StudentDto(s.getId(), s.getName(), s.getEmail());
        }

        Course c = enrollment.getCourse();
        CourseDto cd = null;
        if (c != null) {
            cd = new CourseDto(c.getId(), c.getName(), c.getDescription());
        }

        Set<ClassScoreDto> scoreDtos = enrollment.getScores().stream()
            .map(cs -> new ClassScoreDto(
                cs.getAssessmentType(),
                enrollment.getId(),
                cs.getScore().toString(),
                cs.getMaxScore().toString()
            ))
            .collect(Collectors.toSet());
            
        return new EnrollmentDto(enrollment.getId(), enrollment.isCompleted(), sd, cd, scoreDtos);
    }
}
