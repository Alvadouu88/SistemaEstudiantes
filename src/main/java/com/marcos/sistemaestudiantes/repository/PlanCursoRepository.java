package com.marcos.sistemaestudiantes.repository;

import com.marcos.sistemaestudiantes.model.PlanCurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanCursoRepository extends JpaRepository<PlanCurso, Long> {
    List<PlanCurso> findByPlanEstudioId(Long planId);
    List<PlanCurso> findByPlanEstudioIdAndCiclo(Long planId, Integer ciclo);
    Optional<PlanCurso> findByPlanEstudioIdAndCursoId(Long planId, Long cursoId);
}