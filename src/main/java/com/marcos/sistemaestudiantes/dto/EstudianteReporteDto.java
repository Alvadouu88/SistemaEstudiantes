package com.marcos.sistemaestudiantes.dto;

public class EstudianteReporteDto {
    private String codigoEstudiante;
    private String nombresCompletos;
    private String cursoMatriculado;
    private String semestre;

    public EstudianteReporteDto(String codigoEstudiante, String nombresCompletos, String cursoMatriculado, String semestre) {
        this.codigoEstudiante = codigoEstudiante;
        this.nombresCompletos = nombresCompletos;
        this.cursoMatriculado = cursoMatriculado;
        this.semestre = semestre;
    }

    public String getCodigoEstudiante() { return codigoEstudiante; }
    public String getNombresCompletos() { return nombresCompletos; }
    public String getCursoMatriculado() { return cursoMatriculado; }
    public String getSemestre() { return semestre; }
}
