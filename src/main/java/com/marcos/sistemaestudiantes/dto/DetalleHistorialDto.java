package com.marcos.sistemaestudiantes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleHistorialDto {
    private String nombreCurso;
    private String ciclo;
    private String codigoEvaluacion;
    private String nombreEvaluacion;
    private BigDecimal nota;
    private BigDecimal pesoPorcentaje;
    private BigDecimal puntajeParcial;
}