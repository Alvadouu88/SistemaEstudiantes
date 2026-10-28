package com.marcos.sistemaestudiantes.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "historial_academico")
public class HistorialAcademico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    private Double notaFinal;
    private String periodo; // Ej: "2026-1"
    private String estado;  // Ej: "Aprobado", "Desaprobado"

    // Constructores, Getters y Setters
}
