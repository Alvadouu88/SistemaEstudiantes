package com.marcos.sistemaestudiantes.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "plan_curso",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_plan", "id_curso"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanCurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plan_curso")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_plan", nullable = false)
    private PlanEstudio planEstudio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_curso", nullable = false)
    private Curso curso;

    @Column(name = "ciclo", nullable = false)
    private Short ciclo;

    @Column(name = "creditos", precision = 4, scale = 1)
    private BigDecimal creditos;

    @Column(name = "horas_semanales", precision = 4, scale = 1)
    private BigDecimal horasSemanales;

    @Column(name = "cantidad_sesiones")
    private Short cantidadSesiones;
}
