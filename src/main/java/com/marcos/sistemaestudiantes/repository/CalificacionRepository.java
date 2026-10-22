package com.marcos.sistemaestudiantes.repository;

import com.marcos.sistemaestudiantes.model.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
    List<Calificacion> findByDetalleMatriculaId(Long detalleId);
    Optional<Calificacion> findByDetalleMatriculaIdAndEvaluacionId(Long detalleId, Long evaluacionId);
}