package com.marcos.sistemaestudiantes.repository;
import com.marcos.sistemaestudiantes.dto.EstudiantePromedioDto;
import com.marcos.sistemaestudiantes.dto.EstudianteReporteDto;
import com.marcos.sistemaestudiantes.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    Optional<Estudiante> findByCodigo(String codigo);

    boolean existsByCodigoIgnoreCase(String codigo);

    boolean existsByCodigoIgnoreCaseAndIdNot(String codigo, Long id);

    boolean existsByTipoDocumentoAndNumeroDocumento(String tipoDocumento, String numeroDocumento);

    boolean existsByTipoDocumentoAndNumeroDocumentoAndIdNot(String tipoDocumento, String numeroDocumento, Long id);

    List<Estudiante> findByEstado(String estado);

    @Query(value = "SELECT " +
            "e.id_estudiante AS idEstudiante, " +
            "e.codigo AS codigo, " +
            "e.nombres AS nombres, " +
            "e.apellidos AS apellidos, " +
            "CAST(SUM(c.nota * ev.peso_porcentaje) AS NUMERIC(10,2)) / NULLIF(SUM(ev.peso_porcentaje), 0) AS promedioCalculado " +
            "FROM estudiante e " +
            "JOIN estudiante_plan ep ON e.id_estudiante = ep.id_estudiante " +
            "JOIN matricula m ON ep.id_estudiante_plan = m.id_estudiante_plan " +
            "JOIN detalle_matricula d ON m.id_matricula = d.id_matricula " +
            "JOIN calificacion c ON d.id_detalle = c.id_detalle " +
            "JOIN evaluacion ev ON ev.id_evaluacion = c.id_evaluacion " +
            "GROUP BY e.id_estudiante, e.codigo, e.nombres, e.apellidos " +
            "HAVING (CAST(SUM(c.nota * ev.peso_porcentaje) AS NUMERIC(10,2)) / NULLIF(SUM(ev.peso_porcentaje), 0)) < 13",
            nativeQuery = true)
    List<EstudiantePromedioDto> obtenerEstudiantesPromedioBajo();
}