package com.marcos.sistemaestudiantes.repository;

import com.marcos.sistemaestudiantes.model.PlanEstudio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanEstudioRepository extends JpaRepository<PlanEstudio, Long> {
    Optional<PlanEstudio> findByCodigo(String codigo);
    List<PlanEstudio> findByCarreraId(Long carreraId);
}