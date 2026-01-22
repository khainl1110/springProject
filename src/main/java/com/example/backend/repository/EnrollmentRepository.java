package com.example.backend.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend.entity.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
	boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

	boolean existsByStudentIdAndCourseIdAndCompletedTrue(Long studentId, Long courseId);

	List<Enrollment> findByStudentId(Long studentId);
}
