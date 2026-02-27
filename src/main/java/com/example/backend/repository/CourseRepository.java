package com.example.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
    
    @EntityGraph(attributePaths = {"prerequisites"})
    List<Course> findAll();
}
