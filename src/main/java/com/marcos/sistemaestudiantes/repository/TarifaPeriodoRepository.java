package com.marcos.sistemaestudiantes.repository;

import com.marcos.sistemaestudiantes.model.TarifaPeriodo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TarifaPeriodoRepository extends JpaRepository<TarifaPeriodo, Long> {
    Optional<TarifaPeriodo> findByPeriodoId(Long periodoId);
}