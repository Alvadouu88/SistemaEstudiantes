package com.marcos.sistemaestudiantes.controller;

import com.marcos.sistemaestudiantes.dto.EstudiantePromedioDto;
import com.marcos.sistemaestudiantes.dto.EstudianteRequestDto;
import com.marcos.sistemaestudiantes.dto.EstudianteResponseDto;
import com.marcos.sistemaestudiantes.service.EstudianteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;

    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @GetMapping
    public String listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        return cargarVistaPaginada(model, new EstudianteRequestDto("", "DNI", "", "", "", null, "", "", "ACTIVO"), null, false, page, size);
    }

    @PostMapping("/guardar")
    public String guardar(
            @RequestParam(name = "id", required = false) Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @Valid @ModelAttribute("estudiante") EstudianteRequestDto dto,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return cargarVistaPaginada(model, dto, id, true, page, size);
        }

        try {
            if (id == null) {
                estudianteService.crear(dto);
                redirectAttributes.addFlashAttribute("exito", "Estudiante registrado correctamente.");
            } else {
                estudianteService.actualizar(id, dto);
                redirectAttributes.addFlashAttribute("exito", "Estudiante actualizado correctamente.");
            }
            return "redirect:/estudiantes?page=" + page + "&size=" + size;
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return cargarVistaPaginada(model, dto, id, true, page, size);
        }
    }

    @PostMapping("/{id}/desactivar")
    public String desactivar(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            RedirectAttributes redirectAttributes) {
        try {
            estudianteService.cambiarEstado(id, "INACTIVO");
            redirectAttributes.addFlashAttribute("exito", "El estudiante fue marcado como inactivo correctamente.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/estudiantes?page=" + page + "&size=" + size;
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            RedirectAttributes redirectAttributes) {
        try {
            estudianteService.eliminar(id);
            redirectAttributes.addFlashAttribute("exito", "El estudiante fue eliminado permanentemente.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/estudiantes?page=" + page + "&size=" + size;
    }

    private String cargarVistaPaginada(Model model, EstudianteRequestDto estudianteDto, Long id, boolean abrirModal, int page, int size) {
        Page<EstudianteResponseDto> paginaEstudiantes = estudianteService.listarTodosPaginado(page, size);

        model.addAttribute("pagina", paginaEstudiantes);
        model.addAttribute("estudiantes", paginaEstudiantes.getContent());
        model.addAttribute("estudiante", estudianteDto);
        model.addAttribute("estudianteId", id);
        model.addAttribute("abrirModal", abrirModal);

        return "estudiantes/estudiantes"; // O "estudiantes/estudiantes" dependiendo de tu estructura de carpetas
    }

    @GetMapping("/promedios")
    public String verTablaPromedios(Model model) {
        List<EstudiantePromedioDto> promedios = estudianteService.listarPromediosEstudiantes();
        model.addAttribute("promedios", promedios);
        return "dashboards/reporteEstudiantes";
    }
}