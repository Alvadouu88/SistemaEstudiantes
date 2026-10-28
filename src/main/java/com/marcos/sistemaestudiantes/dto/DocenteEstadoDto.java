package com.marcos.sistemaestudiantes.dto;

public class DocenteEstadoDto {
    private Long id;
    private String codigo;
    private String nombres;
    private String apellidos;
    private String correo;
    private String telefono;

    public DocenteEstadoDto(Long id, String codigo, String nombres, String apellidos, String correo, String telefono) {
        this.id = id;
        this.codigo = codigo;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.telefono = telefono;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public String getCodigo() { return codigo; }
    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public String getCorreo() { return correo; }
    public String getTelefono() { return telefono; }
}