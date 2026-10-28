package com.marcos.sistemaestudiantes.service;

import com.marcos.sistemaestudiantes.dto.DetalleHistorialDto;
import com.marcos.sistemaestudiantes.dto.HistorialAcademicoResponseDto;
import com.marcos.sistemaestudiantes.model.Calificacion;
import com.marcos.sistemaestudiantes.model.Seccion; // <-- ¡Agrega esta línea!
import com.marcos.sistemaestudiantes.repository.CalificacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistorialAcademicoService {

    private final CalificacionRepository calificacionRepository;

    public HistorialAcademicoResponseDto obtenerHistorialCompleto(Long estudianteId) {
        // Obtenemos las calificaciones usando el repositorio JPQL que ya creamos
        List<Calificacion> calificaciones = calificacionRepository.obtenerCalificacionesPorEstudiante(estudianteId);

        List<DetalleHistorialDto> detalles = new ArrayList<>();
        BigDecimal sumaTotal = BigDecimal.ZERO;

        for (Calificacion c : calificaciones) {
            BigDecimal nota = c.getNota();
            BigDecimal peso = c.getEvaluacion().getPesoPorcentaje();

            BigDecimal puntajeParcial = nota.multiply(peso)
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

            sumaTotal = sumaTotal.add(puntajeParcial);

            // Navegamos correctamente a través de planCurso (ajusta los métodos getNombreCurso() o getCurso() según tu entidad PlanCurso)
            Seccion seccion = c.getDetalleMatricula().getSeccion();

            // Dependiendo de cómo esté estructurado tu PlanCurso, por lo general será algo así:
            String nombreCurso = seccion.getPlanCurso().getCurso().getNombre(); // O getNombre() directo si planCurso es el curso
            String ciclo = String.valueOf(seccion.getPlanCurso().getCiclo());   // O el campo ciclo que tenga PlanCurso

            detalles.add(new DetalleHistorialDto(
                    nombreCurso,
                    ciclo,
                    c.getEvaluacion().getCodigo(),
                    c.getEvaluacion().getNombre(),
                    nota,
                    peso,
                    puntajeParcial
            ));
        }

        return new HistorialAcademicoResponseDto(detalles, sumaTotal);
    }
}