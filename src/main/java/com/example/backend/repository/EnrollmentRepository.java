package com.example.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.backend.dto.EnrollmentDto;
import com.example.backend.dto.EnrollmentProjection;
import com.example.backend.entity.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
	boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

	boolean existsByStudentIdAndCourseIdAndIsCompletedTrue(Long studentId, Long courseId);

	@EntityGraph(attributePaths = {"student","course","scores"})
	List<Enrollment> findAll();   

	List<Enrollment> findByStudentId(Long studentId);

	// @Query(value = "SELECT *\r\n" + //
	// 			"FROM enrollment e\r\n" + //
	// 			"WHERE e.enrollment_date >= CURRENT_DATE - INTERVAL '30 days';\r\n" + //
	// 			"", nativeQuery = true)
	// @Query(value = """
    // SELECT e.* 
    // FROM enrollment e
    // LEFT JOIN student s ON e.student_id = s.id
    // LEFT JOIN course c ON e.course_id = c.id
    // WHERE e.enrollment_date >= CURRENT_DATE - INTERVAL '30 days'
    // """, nativeQuery = true)
	@Query(value = """
    SELECT 
        e.id as id,
        e.enrollment_date as enrollmentDate,
        e.student_id as studentId,
        s.name as studentName,
        e.course_id as courseId,
        c.name as courseName
    FROM enrollment e
    LEFT JOIN student s ON e.student_id = s.id
    LEFT JOIN course c ON e.course_id = c.id
    WHERE e.enrollment_date >= CURRENT_DATE - INTERVAL '30 days'
    """, nativeQuery = true)
	//https://blog.stackademic.com/caching-projection-dto-in-spring-boot-37da1cd34369
    List<EnrollmentProjection> findRecentEnrollments();
}
