package com.example.backend.repository;

import com.example.backend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
	@Query("SELECT s FROM Student s " +
           "LEFT JOIN FETCH s.enrollments e " +           // student → enrollment
           "LEFT JOIN FETCH e.course c " +                // enrollment → course  
           "LEFT JOIN FETCH c.prerequisites p " +         // course → course_prerequisite → course
           "WHERE s.email = :email")
    Optional<Student> findByEmailWithFullDetails(@Param("email") String email);
	
	Optional<Student> findByEmail(String email);

}
