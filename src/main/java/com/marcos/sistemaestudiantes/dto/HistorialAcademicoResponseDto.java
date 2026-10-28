package com.marcos.sistemaestudiantes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistorialAcademicoResponseDto {
    private List<DetalleHistorialDto> detalles;
    private BigDecimal notaFinalAcumulada;
}