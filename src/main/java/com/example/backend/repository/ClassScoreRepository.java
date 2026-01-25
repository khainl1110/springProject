package com.example.backend.repository;

import java.util.List;

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
    

}
