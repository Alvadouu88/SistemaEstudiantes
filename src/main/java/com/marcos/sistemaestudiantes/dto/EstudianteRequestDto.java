package com.marcos.sistemaestudiantes.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record EstudianteRequestDto(
        @NotBlank(message = "El código es obligatorio.")
        @Size(max = 20, message = "El código no debe superar los 20 caracteres.")
        String codigo,

        @NotBlank(message = "El tipo de documento es obligatorio.")
        @Pattern(regexp = "DNI|CE|PASAPORTE|OTRO", message = "El tipo de documento no es válido.")
        String tipoDocumento,

        @NotBlank(message = "El número de documento es obligatorio.")
        @Size(max = 20, message = "El número de documento no debe superar los 20 caracteres.")
        String numeroDocumento,

        @NotBlank(message = "Los nombres son obligatorios.")
        @Size(max = 100, message = "Los nombres no deben superar los 100 caracteres.")
        String nombres,

        @NotBlank(message = "Los apellidos son obligatorios.")
        @Size(max = 100, message = "Los apellidos no deben superar los 100 caracteres.")
        String apellidos,

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate fechaNacimiento,

        @Email(message = "El formato del correo electrónico no es válido.")
        @Size(max = 150, message = "El correo no debe superar los 150 caracteres.")
        String correo,

        @Size(max = 20, message = "El teléfono no debe superar los 20 caracteres.")
        String telefono,

        @NotBlank(message = "El estado es obligatorio.")
        @Pattern(regexp = "ACTIVO|INACTIVO|EGRESADO|RETIRADO", message = "Estado no válido.")
        String estado
) {}
