package com.marcos.sistemaestudiantes.repository;

import com.marcos.sistemaestudiantes.model.SesionClase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SesionClaseRepository extends JpaRepository<SesionClase, Long> {
    List<SesionClase> findBySeccionId(Long seccionId);
}