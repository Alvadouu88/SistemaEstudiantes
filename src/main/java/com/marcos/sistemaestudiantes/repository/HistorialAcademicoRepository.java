package com.marcos.sistemaestudiantes.repository;

import com.marcos.sistemaestudiantes.model.HistorialAcademico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HistorialAcademicoRepository extends JpaRepository<HistorialAcademico, Long> {
    List<HistorialAcademico> findByEstudianteId(Long estudianteId);
}
