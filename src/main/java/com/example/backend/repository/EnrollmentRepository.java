package com.example.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.entity.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
	boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

	boolean existsByStudentIdAndCourseIdAndCompletedTrue(Long studentId, Long courseId);

	List<Enrollment> findByStudentId(Long studentId);
}
