package com.marcos.sistemaestudiantes.dto;

import java.math.BigDecimal;

public class EstudiantePromedioDto {
    private Long idEstudiante;
    private String codigo;
    private String nombres;
    private String apellidos;
    private BigDecimal promedioCalculado;

    public EstudiantePromedioDto(Long idEstudiante, String codigo, String nombres, String apellidos, BigDecimal promedioCalculado) {
        this.idEstudiante = idEstudiante;
        this.codigo = codigo;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.promedioCalculado = promedioCalculado;
    }

    // Getters y Setters
    public Long getIdEstudiante() { return idEstudiante; }
    public String getCodigo() { return codigo; }
    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public BigDecimal getPromedioCalculado() { return promedioCalculado; }
}