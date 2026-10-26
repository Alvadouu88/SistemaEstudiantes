package com.marcos.sistemaestudiantes.repository;

import com.marcos.sistemaestudiantes.model.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {
    List<Asistencia> findBySesion_IdOrderByDetalleMatricula_Matricula_EstudiantePlan_Estudiante_ApellidosAsc(Long idSesion);
}