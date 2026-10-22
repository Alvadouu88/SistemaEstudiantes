package com.marcos.sistemaestudiantes.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tarifa_periodo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TarifaPeriodo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarifa")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_periodo", nullable = false, unique = true)
    private PeriodoAcademico periodo;

    @Column(name = "costo_matricula", nullable = false, precision = 10, scale = 2)
    private BigDecimal costoMatricula;

    @Column(name = "costo_credito_mensual", nullable = false, precision = 10, scale = 2)
    private BigDecimal costoCreditoMensual;

    @Column(name = "cantidad_mensualidades", nullable = false)
    private Short cantidadMensualidades;
}