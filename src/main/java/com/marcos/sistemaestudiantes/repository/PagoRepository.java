package com.marcos.sistemaestudiantes.repository;

import com.marcos.sistemaestudiantes.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findByMatriculaId(Long matriculaId);
}