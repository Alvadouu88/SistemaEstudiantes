package com.marcos.sistemaestudiantes.repository;

import com.marcos.sistemaestudiantes.model.SesionClase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SesionClaseRepository extends JpaRepository<SesionClase, Long> {

    List<SesionClase> findBySeccion_IdOrderByFechaDesc(Long idSeccion);
    @Modifying
    @Transactional
    @Query(value = "select * from (SELECT fn_abrir_asistencia(:idSeccion, :tema)) faa", nativeQuery = true)
    void abrirAsistencia(@Param("idSeccion") Long idSeccion, @Param("tema") String tema);
}