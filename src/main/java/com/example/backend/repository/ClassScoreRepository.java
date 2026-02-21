package com.example.backend.repository;

import java.util.List;
import java.time.Instant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend.entity.ClassScore;
import com.example.backend.entity.StudentClassGradesProjection;

public interface ClassScoreRepository extends JpaRepository<ClassScore, Long> {
    @Query(value = """
            SELECT s.id as studentId,
                   e.course_id as classId,
                   cs.score,
                   cs.assessment_type as assessmentType,
                   cs.recorded_at as recordedAt
            FROM class_score cs
            JOIN enrollment e ON cs.enrollment_id = e.id
            JOIN student s ON e.student_id = s.id
            WHERE s.id = :studentId AND e.course_id = :classId
            """, nativeQuery = true)
    List<StudentClassGradesProjection> findByStudentIdAndClassId(
            @Param("studentId") Long studentId, 
            @Param("classId") Long classId);
    
    // Queries using idx_enrollment_id index
    /**
     * Find all class scores by enrollment ID
     * Uses idx_enrollment_id index for fast lookup
     */
    List<ClassScore> findByEnrollmentId(@Param("enrollmentId") Long enrollmentId);
    
    /**
     * Find class scores by enrollment ID and assessment type
     * Uses idx_enrollment_id index as the primary filter
     */
    @Query("SELECT cs FROM ClassScore cs WHERE cs.enrollment.id = :enrollmentId AND cs.assessmentType = :assessmentType ORDER BY cs.recordedAt DESC")
    List<ClassScore> findByEnrollmentIdAndAssessmentType(
            @Param("enrollmentId") Long enrollmentId,
            @Param("assessmentType") String assessmentType);
    
    /**
     * Find the latest score for an enrollment
     * Uses idx_enrollment_id index to efficiently filter by enrollment
     */
    @Query("SELECT cs FROM ClassScore cs WHERE cs.enrollment.id = :enrollmentId ORDER BY cs.recordedAt DESC LIMIT 5")
    List<ClassScore> findLatestScoresByEnrollmentId(@Param("enrollmentId") Long enrollmentId);
    
    /**
     * Count scores for an enrollment
     * Uses idx_enrollment_id index for fast counting
     */
    long countByEnrollmentId(@Param("enrollmentId") Long enrollmentId);

}
