package com.marcos.sistemaestudiantes.repository;

import com.marcos.sistemaestudiantes.model.PeriodoAcademico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeriodoAcademicoRepository extends JpaRepository<PeriodoAcademico, Long> {
    Optional<PeriodoAcademico> findByCodigo(String codigo);
    Optional<PeriodoAcademico> findByEstado(String estado);
}
