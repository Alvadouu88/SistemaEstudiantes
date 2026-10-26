package com.marcos.sistemaestudiantes.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "plan_estudio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanEstudio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plan")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carrera", nullable = false)
    private Carrera carrera;

    @Column(name = "codigo", nullable = false, length = 30, unique = true)
    private String codigo;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "cantidad_ciclos", nullable = false)
    @Builder.Default
    private Short cantidadCiclos = 10;

    @Column(name = "estado", nullable = false, length = 20)
    @Builder.Default
    private String estado = "BORRADOR";
}
