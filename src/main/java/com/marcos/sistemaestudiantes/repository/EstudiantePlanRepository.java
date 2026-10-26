package com.marcos.sistemaestudiantes.repository;

import com.marcos.sistemaestudiantes.model.EstudiantePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstudiantePlanRepository extends JpaRepository<EstudiantePlan, Long> {
    List<EstudiantePlan> findByEstudianteId(Long estudianteId);
    Optional<EstudiantePlan> findByEstudianteIdAndPlanEstudioId(Long estudianteId, Long planId);
}
