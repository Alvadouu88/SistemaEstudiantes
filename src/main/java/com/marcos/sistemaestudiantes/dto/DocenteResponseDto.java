package com.marcos.sistemaestudiantes.dto;

public record DocenteResponseDto(
        Long id,
        String codigo,
        String tipoDocumento,
        String numeroDocumento,
        String nombres,
        String apellidos,
        String correo,
        String telefono,
        Boolean activo
) {
    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }
}