package com.example.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backend.entity.ClassScore;

public interface ClassScoreRepository extends JpaRepository<ClassScore, Long> {
    
}
