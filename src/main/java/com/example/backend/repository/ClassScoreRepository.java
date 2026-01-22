package com.example.backend.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backend.entity.ClassScore;

public interface ClassScoreRepository extends JpaRepository<ClassScore, Long> {
}
