package com.marcos.sistemaestudiantes.repository;

import com.marcos.sistemaestudiantes.model.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
    /**
     * Obtiene todas las calificaciones ya registradas de todos los alumnos
     * en una sección específica.
     * Navega: Calificacion -> DetalleMatricula -> Seccion -> id
     */
    List<Calificacion> findByDetalleMatricula_Seccion_Id(Long idSeccion);

    /**
     * Busca si un alumno (DetalleMatricula) ya tiene una nota registrada
     * para una evaluación en particular (PC1, Parcial, etc.).
     * Retorna Optional porque la nota podría ser nueva (no existir en BD aún).
     */
    Optional<Calificacion> findByDetalleMatricula_IdAndEvaluacion_Id(Long idDetalle, Long idEvaluacion);
}
