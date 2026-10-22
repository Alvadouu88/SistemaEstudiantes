package com.marcos.sistemaestudiantes.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(
        name = "matricula",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_estudiante_plan", "id_periodo"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_matricula")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estudiante_plan", nullable = false)
    private EstudiantePlan estudiantePlan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_periodo", nullable = false)
    private PeriodoAcademico periodo;

    @Column(name = "fecha_matricula", nullable = false)
    @Builder.Default
    private LocalDate fechaMatricula = LocalDate.now();

    @Column(name = "estado", nullable = false, length = 20)
    @Builder.Default
    private String estado = "REGISTRADA";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registrado_por", nullable = false)
    private Usuario registradoPor;
}