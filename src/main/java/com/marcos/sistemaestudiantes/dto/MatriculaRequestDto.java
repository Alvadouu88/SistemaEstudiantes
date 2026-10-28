package com.marcos.sistemaestudiantes.dto;

import java.time.LocalDate;

public class MatriculaRequestDto {
    private Long idMatricula;
    private LocalDate fechaMatricula;
    private String estadoMatricula;
    private String periodoNombre;
    private String nombreUsuarioRegistro;

    // Si necesitas datos del estudiante a través del EstudiantePlan:
    // private String codigoEstudiante;
    // private String nombreCompletoEstudiante;

    public MatriculaRequestDto(Long idMatricula, LocalDate fechaMatricula, String estadoMatricula, String periodoNombre, String nombreUsuarioRegistro) {
        this.idMatricula = idMatricula;
        this.fechaMatricula = fechaMatricula;
        this.estadoMatricula = estadoMatricula;
        this.periodoNombre = periodoNombre;
        this.nombreUsuarioRegistro = nombreUsuarioRegistro;
    }

    // Getters y Setters
    public Long getIdMatricula() { return idMatricula; }
    public LocalDate getFechaMatricula() { return fechaMatricula; }
    public String getEstadoMatricula() { return estadoMatricula; }
    public String getPeriodoNombre() { return periodoNombre; }
    public String getNombreUsuarioRegistro() { return nombreUsuarioRegistro; }
}
