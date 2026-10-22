package com.marcos.sistemaestudiantes.repository;

import com.marcos.sistemaestudiantes.model.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {
    List<Asistencia> findBySesionId(Long sesionId);
    List<Asistencia> findByDetalleMatriculaId(Long detalleId);
}