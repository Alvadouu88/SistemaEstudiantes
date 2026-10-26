package com.marcos.sistemaestudiantes.repository;

import com.marcos.sistemaestudiantes.model.Evaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {
    List<Evaluacion> findBySeccionId(Long seccionId);
}