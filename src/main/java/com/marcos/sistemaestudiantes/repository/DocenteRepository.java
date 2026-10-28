package com.marcos.sistemaestudiantes.repository;

import com.marcos.sistemaestudiantes.dto.DocenteEstadoDto;
import com.marcos.sistemaestudiantes.model.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocenteRepository extends JpaRepository<Docente, Long> {
    Optional<Docente> findByCodigo(String codigo);

    long countByActivo(Boolean activo);

    boolean existsByCodigoIgnoreCase(String codigo);

    boolean existsByCodigoIgnoreCaseAndIdNot(String codigo, Long id);

    boolean existsByTipoDocumentoAndNumeroDocumento(String tipoDocumento, String numeroDocumento);

    boolean existsByTipoDocumentoAndNumeroDocumentoAndIdNot(String tipoDocumento, String numeroDocumento, Long id);

    List<Docente> findByActivoTrue();



    @Query(value = "SELECT id_docente, codigo, nombres, apellidos, correo, telefono FROM docente WHERE activo = false", nativeQuery = true)
    List<Object[]> listarDocentesInactivosBruto();
}
