package com.marcos.sistemaestudiantes.config;

import com.marcos.sistemaestudiantes.model.Rol;
import com.marcos.sistemaestudiantes.model.Usuario;
import com.marcos.sistemaestudiantes.repository.RolRepository;
import com.marcos.sistemaestudiantes.repository.UsuarioRepository;
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
    private final PasswordEncoder passwordEncoder;

    @Value("${app.bootstrap.admin.username}")
    private String usernameAdmin;

    @Value("${app.bootstrap.admin.password}")
    private String passwordAdmin;

    @Bean
    public CommandLineRunner crearUsuarioAdministrador() {
        return args -> {
            if (usuarioRepository.findByNombreUsuario(usernameAdmin).isPresent()) {
                return;
            }

            Rol rolAdministrador = rolRepository.findByCodigo("ADMINISTRADOR")
                    .orElseThrow(() -> new IllegalStateException(
                            "No existe el rol ADMINISTRADOR en la base de datos."
                    ));

            Usuario usuario = new Usuario();
            usuario.setNombreUsuario(usernameAdmin);
            usuario.setNombreMostrar("Administrador del sistema");
            usuario.setPasswordHash(passwordEncoder.encode(passwordAdmin));
            usuario.setActivo(true);
            usuario.setCreadoEn(OffsetDateTime.now());
            usuario.getRoles().add(rolAdministrador);

            usuarioRepository.save(usuario);
        };
    }
}