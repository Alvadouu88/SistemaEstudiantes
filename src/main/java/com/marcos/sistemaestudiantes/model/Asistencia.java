package com.marcos.sistemaestudiantes.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "asistencia",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_sesion", "id_detalle"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asistencia")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sesion", nullable = false)
    private SesionClase sesion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_detalle", nullable = false)
    private DetalleMatricula detalleMatricula;

    @Column(name = "asistio", nullable = false)
    @Builder.Default
    private Boolean asistio = true;
}