package com.marcos.sistemaestudiantes.service;

import com.marcos.sistemaestudiantes.model.Usuario;
import com.marcos.sistemaestudiantes.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioSecurityService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public @NonNull UserDetails loadUserByUsername(
            @NonNull String nombreUsuario
    ) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Usuario no encontrado"
                ));

        List<SimpleGrantedAuthority> permisos = usuario.getRoles().stream()
                .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.getCodigo()))
                .toList();

        return User.withUsername(usuario.getNombreUsuario())
                .password(usuario.getPasswordHash())
                .authorities(permisos)
                .disabled(!usuario.getActivo())
                .build();
    }
}