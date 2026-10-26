package com.marcos.sistemaestudiantes.repository;

import com.marcos.sistemaestudiantes.model.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
    Optional<Matricula> findByEstudiantePlanIdAndPeriodoId(Long estudiantePlanId, Long periodoId);
    List<Matricula> findByPeriodoId(Long periodoId);
}