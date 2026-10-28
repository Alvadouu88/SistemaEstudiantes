package com.marcos.sistemaestudiantes.repository;

import com.marcos.sistemaestudiantes.model.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
    List<Calificacion> findByDetalleMatricula_Seccion_Id(Long idSeccion);

    Optional<Calificacion> findByDetalleMatricula_IdAndEvaluacion_Id(Long idDetalle, Long idEvaluacion);

    @Query("SELECT c FROM Calificacion c " +
            "JOIN c.detalleMatricula dm " +
            "JOIN dm.seccion s " +
            "JOIN s.planCurso pc " +
            "JOIN pc.curso cur " +
            "JOIN dm.matricula m " +
            "JOIN m.estudiantePlan ep " +
            "JOIN ep.estudiante e " +
            "WHERE e.id = :estudianteId")
    List<Calificacion> obtenerCalificacionesPorEstudiante(@Param("estudianteId") Long estudianteId);
}
