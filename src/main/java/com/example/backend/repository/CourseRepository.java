package com.example.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
    
}
