package com.marcos.sistemaestudiantes.config;

import com.marcos.sistemaestudiantes.model.Rol;
import com.marcos.sistemaestudiantes.model.Usuario;
// import com.marcos.sistemaestudiantes.model.Docente; // Descomentar cuando tengas la entidad
import com.marcos.sistemaestudiantes.repository.RolRepository;
import com.marcos.sistemaestudiantes.repository.UsuarioRepository;
// import com.marcos.sistemaestudiantes.repository.DocenteRepository; // Descomentar cuando lo necesites
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.OffsetDateTime;

@Configuration
@RequiredArgsConstructor
public class InitialDataConfig {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    // private final DocenteRepository docenteRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.bootstrap.admin.username}")
    private String usernameAdmin;

    @Value("${app.bootstrap.admin.password}")
    private String passwordAdmin;


    @Bean
    public CommandLineRunner crearUsuariosIniciales() {

        return args -> {
            // 1. Crear Administrador
            if (usuarioRepository.findByNombreUsuario(usernameAdmin).isEmpty()) {
                Rol rolAdministrador = rolRepository.findByCodigo("ADMINISTRADOR")
                        .orElseThrow(() -> new IllegalStateException("No existe el rol ADMINISTRADOR"));

                Usuario admin = new Usuario();
                admin.setNombreUsuario(usernameAdmin);
                admin.setNombreMostrar("Administrador del sistema");
                admin.setPasswordHash(passwordEncoder.encode(passwordAdmin));
                admin.setActivo(true);
                admin.setCreadoEn(OffsetDateTime.now());
                admin.getRoles().add(rolAdministrador);

                usuarioRepository.save(admin);
            }

            // 2. Crear Docente


        };
    }
}