package com.marcos.sistemaestudiantes.dto;

public record CursoResponseDto(
        Long id,
        String codigo,
        String nombre,
        String tipo,
        Boolean activo
) {}
