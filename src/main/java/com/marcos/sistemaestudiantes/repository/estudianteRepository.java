package com.marcos.sistemaestudiantes.repository;
import com.marcos.sistemaestudiantes.model.estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

public interface estudianteRepository extends JpaRepository<estudiante,Integer> {
    @Query(value = "CALL public.sp_insertar_estudiante(:tipoDoc, :numDoc, :nombres, :apellidos, :fechaNac, :correo, :telefono, :estado, NULL)", nativeQuery = true)
    Long ejecutarInsertarEstudiante(
            @Param("tipoDoc") String tipoDocumento,
            @Param("numDoc") String numeroDocumento,
            @Param("nombres") String nombres,
            @Param("apellidos") String apellidos,
            @Param("fechaNac") LocalDate fechaNacimiento,
            @Param("correo") String correo,
            @Param("telefono") String telefono,
            @Param("estado") String estado
    );
    @Modifying
    @Transactional
    @Query(value = "update public.estudiante set tipo_documento = :tipoDoc, numero_documento = :numDoc, nombres = :nombres, apellidos = :apellidos, fecha_nacimiento = :fechaNac, correo = :correo, telefono = :telefono, estado = :estado where id_estudiante = :ID", nativeQuery = true)
    Long actualizarEstudiante(
            @Param("tipoDoc") String tipoDocumento,
            @Param("numDoc") String numeroDocumento,
            @Param("nombres") String nombres,
            @Param("apellidos") String apellidos,
            @Param("fechaNac") LocalDate fechaNacimiento,
            @Param("correo") String correo,
            @Param("telefono") String telefono,
            @Param("estado") String estado,
            @Param("ID") Integer idEstudiante
    );
}

