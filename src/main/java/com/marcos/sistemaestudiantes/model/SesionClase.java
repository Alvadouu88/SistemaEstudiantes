package com.marcos.sistemaestudiantes.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(
        name = "sesion_clase",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_seccion", "id_horario", "fecha"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SesionClase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sesion")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_seccion", nullable = false)
    private Seccion seccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_horario", nullable = false)
    private SeccionHorario horario;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "tema", length = 250)
    private String tema;

    @Column(name = "estado", nullable = false, length = 20)
    @Builder.Default
    private String estado = "ABIERTA";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "abierta_por", nullable = false)
    private Usuario abiertaPor;

    @Column(name = "abierta_en", nullable = false, insertable = false, updatable = false)
    private OffsetDateTime abiertaEn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cerrada_por")
    private Usuario cerradaPor;

    @Column(name = "cerrada_en")
    private OffsetDateTime cerradaEn;
}