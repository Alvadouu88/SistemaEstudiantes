package com.marcos.sistemaestudiantes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public record CursoRequestDto(
        @NotBlank(message = "El código es obligatorio.")
        @Size(max = 30, message = "El código no debe superar los 30 caracteres.")
        String codigo,

        @NotBlank(message = "El nombre es obligatorio.")
        @Size(max = 150, message = "El nombre no debe superar los 150 caracteres.")
        String nombre,

        @NotBlank(message = "El tipo es obligatorio.")
        @Pattern
                (regexp = "OBLIGATORIO|ELECTIVO", message = "El tipo debe ser OBLIGATORIO o ELECTIVO.")
        String tipo,

        Boolean activo
) {}