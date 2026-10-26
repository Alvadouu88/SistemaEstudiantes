package com.marcos.sistemaestudiantes.config;

import com.marcos.sistemaestudiantes.service.UsuarioSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UsuarioSecurityService usuarioSecurityService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(usuarioSecurityService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, DaoAuthenticationProvider authenticationProvider) throws Exception {
        http
                .authenticationProvider(authenticationProvider)
                .authorizeHttpRequests(auth -> auth
                        // 1. Permisos de Administrador (Gestión total)
                        .requestMatchers("/cursos/**", "/estudiantes/**", "/docentes/**").hasRole("ADMINISTRADOR")

                        // 2. Permisos Mixtos (Si quieres que el docente solo VEA el listado general de estudiantes/cursos)
                        //.requestMatchers(HttpMethod.GET, "/estudiantes/**", "/cursos/**").hasAnyRole("ADMINISTRADOR", "DOCENTE")

                        // 3. Permisos de Docente (Asistencia, Calificaciones y sus Secciones)
                        .requestMatchers("/mis-secciones/**", "/calificaciones/**", "/asistencia/**").hasRole("DOCENTE")

                        // 4. Rutas públicas
                        .requestMatchers("/login", "/css/**", "/img/**").permitAll()

                        // Cualquier otra petición requiere estar logueado
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/seleccionar-rol", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                );

        return http.build();
    }
}