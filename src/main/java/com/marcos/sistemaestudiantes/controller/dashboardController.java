package com.marcos.sistemaestudiantes.controller;

import com.marcos.sistemaestudiantes.dto.EstudianteReporteDto;
import com.marcos.sistemaestudiantes.repository.CursoRepository;
import com.marcos.sistemaestudiantes.repository.DocenteRepository;
import com.marcos.sistemaestudiantes.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/dashboard")
@Controller
public class dashboardController {
    @Autowired
    private EstudianteRepository estudiante;

    @Autowired
    private DocenteRepository docente;

    @Autowired
    private CursoRepository cursos;

    @GetMapping("/principal")
    public String reporte(Model model) {
        long totalEstudiantes = estudiante.count();
        long totalDocentes = docente.count();
        long totalCursos = cursos.count();
        model.addAttribute("totalEstudiantes", totalEstudiantes);
        model.addAttribute("totalDocentes", totalDocentes);
        model.addAttribute("totalCursos", totalCursos);
        return "dashboards/principal";
    }
}

