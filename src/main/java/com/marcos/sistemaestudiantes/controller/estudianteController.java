package com.marcos.sistemaestudiantes.controller;

import com.marcos.sistemaestudiantes.service.estudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import com.marcos.sistemaestudiantes.model.estudiante;
import com.marcos.sistemaestudiantes.repository.estudianteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/estudiantes")
public class estudianteController {
    @Autowired
    private estudianteService estudianteService;
    private final estudianteRepository estudianteRepository;

    public estudianteController(estudianteRepository estudianterepository) {
        this.estudianteRepository = estudianterepository;
    }

    @GetMapping("/tabla")
    public String tabla(Model model){
        List<estudiante> estudiantes = estudianteRepository.findAll();

        model.addAttribute("estudiantes", estudiantes);
        model.addAttribute("estudiante", new estudiante());
        return "tablaEstudiantes";
    }

    @GetMapping("/editar/{id}")
    public String cargarDatosEdicion(@PathVariable("id") Integer id, Model model) {
        estudiante est = estudianteRepository.findById(id).orElse(null);

        if (est == null) {
            return "redirect:/estudiantes/tabla";
        }

        model.addAttribute("estudiantes", estudianteRepository.findAll());
        model.addAttribute("estudiante", est);

        model.addAttribute("modoEdicion", true);

        return "tablaEstudiantes";
    }
    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute("estudiante") estudiante estudiante){
        estudianteService.actualizarEstudiante(estudiante);
        return "redirect:/estudiantes/tabla";
    }

    @PostMapping("/guardar")
    public String guardarEstudiante(@ModelAttribute("estudiante") estudiante estudiante) {
        estudianteService.registrarEstudiante(estudiante);
        return "redirect:/estudiantes/tabla";
    }
    @PostMapping("/eliminar/{id}")
    public String eliminarEstudiante(@PathVariable("id") Integer id) {
        estudianteRepository.deleteById(id);
        return "redirect:/estudiantes/tabla";
    }
}
