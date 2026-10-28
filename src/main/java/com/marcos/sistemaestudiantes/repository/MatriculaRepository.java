package com.marcos.sistemaestudiantes.repository;

import com.marcos.sistemaestudiantes.dto.MatriculaRequestDto;
import com.marcos.sistemaestudiantes.model.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
    Optional<Matricula> findByEstudiantePlanIdAndPeriodoId(Long estudiantePlanId, Long periodoId);
    List<Matricula> findByPeriodoId(Long periodoId);

    @Query("SELECT new com.marcos.sistemaestudiantes.dto.MatriculaRequestDto(" +
            "m.id, m.fechaMatricula, m.estado, p.codigo, u.nombreUsuario) " + // <- Cambiado a u.nombreUsuario
            "FROM Matricula m " +
            "JOIN m.periodo p " +
            "JOIN m.registradoPor u " +
            "JOIN m.estudiantePlan ep")
    List<MatriculaRequestDto> obtenerReporteMultitabla();
}