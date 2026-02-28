package com.example.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.entity.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
	boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

	boolean existsByStudentIdAndCourseIdAndIsCompletedTrue(Long studentId, Long courseId);

	@EntityGraph(attributePaths = {"student","course","scores"})
	List<Enrollment> findAll();   

	List<Enrollment> findByStudentId(Long studentId);
}
