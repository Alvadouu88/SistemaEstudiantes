package com.marcos.sistemaestudiantes.controller;

import com.marcos.sistemaestudiantes.model.Curso;
import com.marcos.sistemaestudiantes.service.CursoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cursos")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public String listar(Model model) {
        return cargarVista(model, nuevoCurso(), false);
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("curso") Curso curso, Model model,
                          RedirectAttributes redirectAttributes) {
        try {
            boolean esNuevo = curso.getIdCurso() == null;
            cursoService.guardar(curso);

            redirectAttributes.addFlashAttribute(
                    "exito",
                    esNuevo ? "Curso creado correctamente." : "Curso actualizado correctamente."
            );

            return "redirect:/cursos";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return cargarVista(model, curso, true);
        }
    }

    @PostMapping("/{idCurso}/eliminar")
    public String eliminar(@PathVariable Long idCurso, RedirectAttributes redirectAttributes) {
        try {
            cursoService.desactivar(idCurso);
            redirectAttributes.addFlashAttribute(
                    "exito",
                    "El curso fue desactivado. Ya no estará disponible para nuevas configuraciones académicas."
            );
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/cursos";
    }

    private String cargarVista(Model model, Curso curso, boolean abrirModal) {
        model.addAttribute("cursos", cursoService.listar());
        model.addAttribute("curso", curso);
        model.addAttribute("abrirModal", abrirModal);
        return "cursos/cursos";
    }

    private Curso nuevoCurso() {
        Curso curso = new Curso();
        curso.setActivo(true);
        return curso;
    }
}
