package com.marcos.sistemaestudiantes.repository;

import com.marcos.sistemaestudiantes.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    Optional<Usuario> findByDocenteId(Long docenteId);
    boolean existsByNombreUsuario(String nombreUsuario);
}