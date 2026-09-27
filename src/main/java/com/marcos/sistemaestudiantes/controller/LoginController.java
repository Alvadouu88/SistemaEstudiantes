package com.marcos.sistemaestudiantes.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @GetMapping("/seleccionar-rol")
    public String mostrarSelectorRol(
            Authentication authentication,
            HttpSession session,
            Model model
    ) {
        session.removeAttribute("rolActivo");

        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(Objects::nonNull)
                .filter(rol -> rol.startsWith("ROLE_"))
                .map(rol -> rol.substring(5))
                .toList();

        model.addAttribute("roles", roles);
        model.addAttribute("nombreUsuario", authentication.getName());

        return "seleccionar-rol";
    }

    @PostMapping("/seleccionar-rol")
    public String seleccionarRol(
            @RequestParam String rol,
            Authentication authentication,
            HttpSession session
    ) {
        boolean tieneRol = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(("ROLE_" + rol)::equals);

        if (!tieneRol) {
            return "redirect:/seleccionar-rol?error=true";
        }

        session.setAttribute("rolActivo", rol);

        return "redirect:/inicio";
    }

    @GetMapping("/inicio")
    public String mostrarInicio(
            Authentication authentication,
            HttpSession session,
            Model model
    ) {
        String rolActivo = (String) session.getAttribute("rolActivo");

        if (rolActivo == null) {
            return "redirect:/seleccionar-rol";
        }

        model.addAttribute("nombreUsuario", authentication.getName());
        model.addAttribute("rolActivo", rolActivo);

        return "inicio";
    }
}