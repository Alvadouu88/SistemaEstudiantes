package com.marcos.sistemaestudiantes.repository;

import com.marcos.sistemaestudiantes.model.Seccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeccionRepository extends JpaRepository<Seccion, Long> {

    // Método necesario para el LoginController
    List<Seccion> findByDocenteId(Long docenteId);
}