package com.marcos.sistemaestudiantes.repository;

import com.marcos.sistemaestudiantes.model.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface DocenteRepository extends JpaRepository<Docente, Integer> {
    @Modifying // <-- Indica que es una operación que modifica la base de datos (INSERT/UPDATE/DELETE)
    @Transactional // <-- Maneja la transacción de la base de datos
    @Query(value = "CALL public.sp_insertar_docente(:tipoDoc, :numDoc, :nombres, :apellidos, :correo, :telefono)", nativeQuery = true)
    void ejecutarInsertarDocente(
            @Param("tipoDoc") String tipoDocumento,
            @Param("numDoc") String numeroDocumento,
            @Param("nombres") String nombres,
            @Param("apellidos") String apellidos,
            @Param("correo") String correo,
            @Param("telefono") String telefono
    );
    @Modifying
    @Transactional
    @Query(value = "update public.docente set tipo_documento = :tipoDoc, numero_documento = :numDoc, nombres = :nombres, apellidos = :apellidos, correo = :correo, telefono = :telefono, activo = :activo where id_docente = :ID", nativeQuery = true)
    Long actualizarDocente(
            @Param("tipoDoc") String tipoDocumento,
            @Param("numDoc") String numeroDocumento,
            @Param("nombres") String nombres,
            @Param("apellidos") String apellidos,
            @Param("correo") String correo,
            @Param("telefono") String telefono,
            @Param("activo") boolean activo,
            @Param("ID") Integer idEstudiante
    );
}
