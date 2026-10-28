package com.marcos.sistemaestudiantes.controller;

import com.marcos.sistemaestudiantes.dto.MatriculaRequestDto;
import com.marcos.sistemaestudiantes.repository.MatriculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Controller
@RequestMapping("/matriculas")
public class MatriculaController {
    @Autowired
    private MatriculaRepository matriculaRepository;

    @GetMapping("/reporte")
    public String verReporte(Model model) {
        List<MatriculaRequestDto> reporte = matriculaRepository.obtenerReporteMultitabla();
        model.addAttribute("reporte", reporte);
        return "reporteMatriculas";
    }
}
