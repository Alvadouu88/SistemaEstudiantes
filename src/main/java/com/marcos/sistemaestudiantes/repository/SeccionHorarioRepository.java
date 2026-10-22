package com.marcos.sistemaestudiantes.repository;

import com.marcos.sistemaestudiantes.model.SeccionHorario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeccionHorarioRepository extends JpaRepository<SeccionHorario, Long> {
    List<SeccionHorario> findBySeccionId(Long seccionId);
}