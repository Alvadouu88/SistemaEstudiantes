package com.marcos.sistemaestudiantes.dto;

import java.time.LocalDate;

public record EstudianteResponseDto(
        Long id,
        String codigo,
        String tipoDocumento,
        String numeroDocumento,
        String nombres,
        String apellidos,
        LocalDate fechaNacimiento,
        String correo,
        String telefono,
        String estado
) {
    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }
}
