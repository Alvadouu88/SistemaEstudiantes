package com.marcos.sistemaestudiantes.repository;

import com.marcos.sistemaestudiantes.model.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarreraRepository extends JpaRepository<Carrera, Long> {
    Optional<Carrera> findByCodigo(String codigo);
    boolean existsByCodigo(String codigo);
}
