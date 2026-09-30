package com.marcos.sistemaestudiantes.controller;

import com.marcos.sistemaestudiantes.model.Docente;
import com.marcos.sistemaestudiantes.repository.DocenteRepository;
import com.marcos.sistemaestudiantes.service.DocenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/Docentes")

public class DocenteController {
    @Autowired
    private DocenteService DocenteService;
    private final DocenteRepository DocenteRepository;

    public DocenteController(DocenteRepository docenteRepository) {
        DocenteRepository = docenteRepository;
    }
    @GetMapping("/tabla")
    public String tabla(Model model) {
        List<Docente> Docentes = DocenteRepository.findAll();
        model.addAttribute("Docentes", Docentes);
        model.addAttribute("docente", new Docente());
        return "TablaDocentes";

    }

    @PostMapping("/guardar")
            public String guardarDocente(@ModelAttribute("Docente") Docente Docente){
            DocenteService.RegistrarDocente(Docente);
            return"redirect:/Docentes/tabla";

    }
    @PostMapping("/eliminar/{id}")
    public String eliminarDocente(@PathVariable("id") Integer id) {
        DocenteRepository.deleteById(id);
        return "redirect:/Docentes/tabla";
    }
    @GetMapping("/editar/{id}")
    public String cargarDatosEdicion(@PathVariable("id") Integer id, Model model) {
        Docente docente = DocenteRepository.findById(id).orElse(null);

        if (docente == null) {
            return "redirect:/Docentes/tabla";
        }

        model.addAttribute("Docentes", DocenteRepository.findAll());
        model.addAttribute("docente", docente);

        model.addAttribute("modoEdicion", true);

        return "TablaDocentes";
    }
    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute("docente") Docente Docente){
        DocenteService.actualizarDocente(Docente);
        return "redirect:/Docentes/tabla";
    }
}



