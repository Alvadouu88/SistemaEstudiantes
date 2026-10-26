package com.marcos.sistemaestudiantes.controller;


import com.marcos.sistemaestudiantes.dto.DocenteRequestDto;
import com.marcos.sistemaestudiantes.dto.DocenteResponseDto;
import com.marcos.sistemaestudiantes.service.DocenteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/docentes")
public class DocenteController {

    private final DocenteService docenteService;

    public DocenteController(DocenteService docenteService) {
        this.docenteService = docenteService;
    }

    @GetMapping
    public String listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        return cargarVistaPaginada(model, new DocenteRequestDto("", "DNI", "", "", "", "", "", true), null, false, page, size);
    }

    @PostMapping("/guardar")
    public String guardar(
            @RequestParam(name = "id", required = false) Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @Valid @ModelAttribute("docente") DocenteRequestDto dto,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return cargarVistaPaginada(model, dto, id, true, page, size);
        }

        try {
            if (id == null) {
                docenteService.crear(dto);
                redirectAttributes.addFlashAttribute("exito", "Docente registrado correctamente.");
            } else {
                docenteService.actualizar(id, dto);
                redirectAttributes.addFlashAttribute("exito", "Docente actualizado correctamente.");
            }
            return "redirect:/docentes?page=" + page + "&size=" + size;
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
            docenteService.cambiarEstado(id, false);
            redirectAttributes.addFlashAttribute("exito", "El docente fue desactivado correctamente.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/docentes?page=" + page + "&size=" + size;
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            RedirectAttributes redirectAttributes) {
        try {
            docenteService.eliminar(id);
            redirectAttributes.addFlashAttribute("exito", "El docente fue eliminado permanentemente.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/docentes?page=" + page + "&size=" + size;
    }

    private String cargarVistaPaginada(Model model, DocenteRequestDto docenteDto, Long id, boolean abrirModal, int page, int size) {
        Page<DocenteResponseDto> paginaDocentes = docenteService.listarTodosPaginado(page, size);

        model.addAttribute("pagina", paginaDocentes);
        model.addAttribute("docentes", paginaDocentes.getContent());
        model.addAttribute("docente", docenteDto);
        model.addAttribute("docenteId", id);
        model.addAttribute("abrirModal", abrirModal);

        return "docentes/docentes"; // O "docentes/docentes" según tu estructura de carpetas
    }
}