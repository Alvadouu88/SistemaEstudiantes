package com.marcos.sistemaestudiantes.repository;

import com.marcos.sistemaestudiantes.model.DetalleMatricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleMatriculaRepository extends JpaRepository<DetalleMatricula, Long> {
    List<DetalleMatricula> findByMatriculaId(Long matriculaId);
    List<DetalleMatricula> findBySeccionId(Long seccionId);
}