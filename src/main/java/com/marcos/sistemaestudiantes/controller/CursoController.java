package com.marcos.sistemaestudiantes.controller;


import com.marcos.sistemaestudiantes.dto.CursoRequestDto;
import com.marcos.sistemaestudiantes.dto.CursoResponseDto;
import com.marcos.sistemaestudiantes.service.CursoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cursos")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public String listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        return cargarVistaPaginada(model, new CursoRequestDto("", "", "OBLIGATORIO", true), null, false, page, size);
    }

    @PostMapping("/guardar")
    public String guardar(
            @RequestParam(name = "id", required = false) Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @Valid @ModelAttribute("curso") CursoRequestDto dto,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        // 1. Validaciones declarativas del DTO (@NotBlank, @Size, etc.)
        if (bindingResult.hasErrors()) {
            return cargarVistaPaginada(model, dto, id, true, page, size);
        }

        try {
            // 2. Lógica separada en el Service
            if (id == null) {
                cursoService.crear(dto);
                redirectAttributes.addFlashAttribute("exito", "Curso creado correctamente.");
            } else {
                cursoService.actualizar(id, dto);
                redirectAttributes.addFlashAttribute("exito", "Curso actualizado correctamente.");
            }

            // Redirigir manteniendo la página actual
            return "redirect:/cursos?page=" + page + "&size=" + size;

        } catch (IllegalArgumentException e) {
            // 3. Captura de errores de regla de negocio
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
            cursoService.cambiarEstado(id, false);
            redirectAttributes.addFlashAttribute("exito", "El curso fue desactivado correctamente.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/cursos?page=" + page + "&size=" + size;
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            RedirectAttributes redirectAttributes) {

        try {
            cursoService.eliminar(id);
            redirectAttributes.addFlashAttribute("exito", "El curso fue eliminado permanentemente.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/cursos?page=" + page + "&size=" + size;
    }

    // Método auxiliar privado para cargar la vista con la paginación
    private String cargarVistaPaginada(Model model, CursoRequestDto cursoDto, Long id, boolean abrirModal, int page, int size) {

        // Llamamos al nuevo método paginado del Service
        Page<CursoResponseDto> paginaCursos = cursoService.listarTodosPaginado(page, size);

        // Pasamos el objeto Page completo (para saber total de páginas, si es primera/última, etc.)
        model.addAttribute("pagina", paginaCursos);

        // Pasamos la lista exacta de esta página
        model.addAttribute("cursos", paginaCursos.getContent());

        // Atributos del formulario
        model.addAttribute("curso", cursoDto);
        model.addAttribute("cursoId", id);
        model.addAttribute("abrirModal", abrirModal);

        /*
         * NOTA SOBRE LA RUTA:
         * Si solucionaste el error Whitelabel moviendo tu archivo a una carpeta "cursos",
         * deja esto como "cursos/cursos".
         * Si lo dejaste en la raíz de templates, cámbialo a "cursos".
         */
        return "cursos/cursos";
    }
}