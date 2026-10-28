package com.marcos.sistemaestudiantes.controller;

import com.marcos.sistemaestudiantes.dto.EstudianteResponseDto;
import com.marcos.sistemaestudiantes.dto.HistorialAcademicoResponseDto;
import com.marcos.sistemaestudiantes.service.EstudianteService;
import com.marcos.sistemaestudiantes.service.HistorialAcademicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/historial")
@RequiredArgsConstructor
public class historialAcademicoController {

    private final HistorialAcademicoService historialService;
    private final EstudianteService estudianteService;

    @GetMapping("/{estudianteId}")
    public String verHistorial(@PathVariable("estudianteId") Long estudianteId, Model model) {
        // Obtenemos el objeto que contiene la lista calculada y la nota final
        HistorialAcademicoResponseDto historial = historialService.obtenerHistorialCompleto(estudianteId);
        EstudianteResponseDto estudiante = estudianteService.buscarPorId(estudianteId);

        model.addAttribute("historial", historial);
        model.addAttribute("estudiante", estudiante);

        return "seccion/historial-academico";
    }
}