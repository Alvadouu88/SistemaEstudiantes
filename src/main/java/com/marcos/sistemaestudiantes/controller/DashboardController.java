package com.marcos.sistemaestudiantes.controller;

import com.marcos.sistemaestudiantes.repository.CursoRepository;
import com.marcos.sistemaestudiantes.repository.DocenteRepository;
import com.marcos.sistemaestudiantes.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/dashboard")
@Controller
public class DashboardController {
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

