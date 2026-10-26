package com.marcos.sistemaestudiantes.controller;

import com.marcos.sistemaestudiantes.model.*;
import com.marcos.sistemaestudiantes.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mis-secciones")
@RequiredArgsConstructor
public class SeccionDocenteController {

    private final SeccionRepository seccionRepository;
    private final UsuarioRepository usuarioRepository;
    private final DetalleMatriculaRepository detalleMatriculaRepository;
    private final SeccionHorarioRepository seccionHorarioRepository;
    private final SesionClaseRepository sesionClaseRepository;
    private final AsistenciaRepository asistenciaRepository;
    private final EvaluacionRepository evaluacionRepository;
    private final CalificacionRepository calificacionRepository;

    // ==========================================
    // 1. RESUMEN GENERAL DE LA SECCIÓN
    // ==========================================
    @GetMapping("/{id}")
    public String verResumenSeccion(@PathVariable("id") Long idSeccion, Authentication authentication, Model model) {
        Usuario usuario = usuarioRepository.findByNombreUsuario(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario.getDocente() == null) {
            return "redirect:/inicio?error=no_es_docente";
        }

        Seccion seccion = seccionRepository.findById(idSeccion)
                .orElseThrow(() -> new IllegalArgumentException("Sección no encontrada"));

        // SEGURIDAD: Validar que la sección pertenezca al docente que inició sesión
        if (!seccion.getDocente().getId().equals(usuario.getDocente().getId())) {
            return "redirect:/inicio?error=acceso_denegado";
        }

        model.addAttribute("seccion", seccion);
        model.addAttribute("nombreUsuario", usuario.getNombreMostrar());

        return "seccion/resumen";
    }

    // ==========================================
    // 2. LISTA DE ALUMNOS MATRICULADOS
    // ==========================================
    @GetMapping("/{id}/alumnos")
    public String verAlumnosMatriculados(@PathVariable("id") Long idSeccion, Authentication authentication, Model model) {
        Usuario usuario = usuarioRepository.findByNombreUsuario(authentication.getName()).orElseThrow();
        Seccion seccion = seccionRepository.findById(idSeccion).orElseThrow();

        if (!seccion.getDocente().getId().equals(usuario.getDocente().getId())) {
            return "redirect:/inicio?error=acceso_denegado";
        }

        List<DetalleMatricula> alumnos = detalleMatriculaRepository.findBySeccion_IdAndEstado(idSeccion, "INSCRITO");

        model.addAttribute("seccion", seccion);
        model.addAttribute("alumnos", alumnos);

        return "seccion/alumnos";
    }

    // ==========================================
    // 3. PANEL PRINCIPAL DE ASISTENCIA (Horarios e Historial)
    // ==========================================
    @GetMapping("/{id}/asistencia")
    public String verControlAsistencia(@PathVariable("id") Long idSeccion, Authentication authentication, Model model) {
        Usuario usuario = usuarioRepository.findByNombreUsuario(authentication.getName()).orElseThrow();
        Seccion seccion = seccionRepository.findById(idSeccion).orElseThrow();

        if (!seccion.getDocente().getId().equals(usuario.getDocente().getId())) {
            return "redirect:/inicio?error=acceso_denegado";
        }

        List<SeccionHorario> horarios = seccionHorarioRepository.findBySeccionId(idSeccion);
        List<SesionClase> historial = sesionClaseRepository.findBySeccion_IdOrderByFechaDesc(idSeccion);

        LocalDate fechaActual = LocalDate.now();
        LocalTime horaActual = LocalTime.now();
        int diaSemanaActual = fechaActual.getDayOfWeek().getValue();

        SeccionHorario horarioActivo = null;

        for (SeccionHorario h : horarios) {
            if (h.getDiaSemana() == diaSemanaActual) {
                if (!horaActual.isBefore(h.getHoraInicio()) && !horaActual.isAfter(h.getHoraFin())) {
                    horarioActivo = h;
                    break;
                }
            }
        }

        model.addAttribute("seccion", seccion);
        model.addAttribute("horarios", horarios);
        model.addAttribute("historial", historial);
        model.addAttribute("horarioActivo", horarioActivo);
        model.addAttribute("fechaHoy", fechaActual);

        return "seccion/asistencia";
    }

    // ==========================================
    // 4. ABRIR NUEVA SESIÓN DE CLASE
    // ==========================================
    @PostMapping("/{id}/asistencia/abrir")
    public String abrirAsistencia(
            @PathVariable("id") Long idSeccion,
            @RequestParam(value = "tema", required = false) String tema,
            Authentication authentication
    ) {
        Usuario usuario = usuarioRepository.findByNombreUsuario(authentication.getName()).orElseThrow();
        Seccion seccion = seccionRepository.findById(idSeccion).orElseThrow();

        if (!seccion.getDocente().getId().equals(usuario.getDocente().getId())) {
            return "redirect:/inicio?error=acceso_denegado";
        }

        try {
            sesionClaseRepository.abrirAsistencia(idSeccion, tema);
            return "redirect:/mis-secciones/" + idSeccion + "/asistencia?exito=sesion_abierta";
        } catch (Exception e) {
            return "redirect:/mis-secciones/" + idSeccion + "/asistencia?error=ya_existe";
        }
    }
    // ==========================================
    // 5. VER DETALLE DE UNA SESIÓN Y PASAR LISTA
    // ==========================================

    @GetMapping("/{id}/asistencia/sesion/{idSesion}")
    public String verDetalleSesion(
            @PathVariable("id") Long idSeccion,
            @PathVariable("idSesion") Long idSesion,
            Authentication authentication,
            Model model
    ) {
        Usuario usuario = usuarioRepository.findByNombreUsuario(authentication.getName()).orElseThrow();
        Seccion seccion = seccionRepository.findById(idSeccion).orElseThrow();

        if (!seccion.getDocente().getId().equals(usuario.getDocente().getId())) {
            return "redirect:/inicio?error=acceso_denegado";
        }

        SesionClase sesion = sesionClaseRepository.findById(idSesion)
                .orElseThrow(() -> new IllegalArgumentException("Sesión no encontrada"));

        // Actualizado al nuevo nombre del método
        List<Asistencia> listaAsistencia = asistenciaRepository
                .findBySesion_IdOrderByDetalleMatricula_Matricula_EstudiantePlan_Estudiante_ApellidosAsc(idSesion);

        model.addAttribute("seccion", seccion);
        model.addAttribute("sesion", sesion);
        model.addAttribute("listaAsistencia", listaAsistencia);

        return "seccion/asistencia-detalle";
    }

    // ==========================================
    // 6. GUARDAR CAMBIOS Y CERRAR SESIÓN
    // ==========================================
    @PostMapping("/{id}/asistencia/sesion/{idSesion}/guardar")
    public String guardarAsistenciaYCerrar(
            @PathVariable("id") Long idSeccion,
            @PathVariable("idSesion") Long idSesion,
            @RequestParam Map<String, String> formData,
            Authentication authentication
    ) {
        Usuario usuario = usuarioRepository.findByNombreUsuario(authentication.getName()).orElseThrow();
        Seccion seccion = seccionRepository.findById(idSeccion).orElseThrow();

        if (!seccion.getDocente().getId().equals(usuario.getDocente().getId())) {
            return "redirect:/inicio?error=acceso_denegado";
        }

        SesionClase sesion = sesionClaseRepository.findById(idSesion).orElseThrow();

        if ("CERRADA".equals(sesion.getEstado())) {
            return "redirect:/mis-secciones/" + idSeccion + "/asistencia?error=sesion_ya_cerrada";
        }

        // Iterar y guardar los estados enviados como booleanos
        for (Map.Entry<String, String> entry : formData.entrySet()) {
            if (entry.getKey().startsWith("asistio_")) {
                Long idAsistencia = Long.parseLong(entry.getKey().replace("asistio_", ""));
                Boolean asistio = Boolean.parseBoolean(entry.getValue()); // Convierte "true" o "false"

                asistenciaRepository.findById(idAsistencia).ifPresent(asistencia -> {
                    asistencia.setAsistio(asistio); // Usamos setAsistio en lugar de setEstado
                    asistenciaRepository.save(asistencia);
                });
            }
        }

        // Cerrar acta
        sesion.setEstado("CERRADA");
        sesionClaseRepository.save(sesion);

        return "redirect:/mis-secciones/" + idSeccion + "/asistencia?exito=sesion_cerrada";
    }
    // ==========================================
    // 7. VER REGISTRO DE CALIFICACIONES
    // ==========================================
    @GetMapping("/{id}/calificaciones")
    public String verCalificaciones(@PathVariable("id") Long idSeccion, Authentication authentication, Model model) {
        Usuario usuario = usuarioRepository.findByNombreUsuario(authentication.getName()).orElseThrow();
        Seccion seccion = seccionRepository.findById(idSeccion).orElseThrow();

        if (!seccion.getDocente().getId().equals(usuario.getDocente().getId())) {
            return "redirect:/inicio?error=acceso_denegado";
        }

        // 1. Obtener alumnos inscritos
        List<DetalleMatricula> alumnos = detalleMatriculaRepository.findBySeccion_IdAndEstado(idSeccion, "INSCRITO");

        // 2. Obtener las columnas de Evaluación por SECCIÓN (según tu entidad)
        List<Evaluacion> evaluaciones = evaluacionRepository.findBySeccionId(idSeccion);

        // 3. Obtener todas las notas ya guardadas
        List<Calificacion> calificaciones = calificacionRepository.findByDetalleMatricula_Seccion_Id(idSeccion);

        // 4. Mapear notas para la vista HTML usando getNota()
        java.util.Map<Long, java.util.Map<Long, java.math.BigDecimal>> notasMap = new java.util.HashMap<>();
        for (Calificacion c : calificaciones) {
            notasMap.computeIfAbsent(c.getDetalleMatricula().getId(), k -> new java.util.HashMap<>())
                    .put(c.getEvaluacion().getId(), c.getNota()); // <-- Usamos getNota()
        }

        model.addAttribute("seccion", seccion);
        model.addAttribute("alumnos", alumnos);
        model.addAttribute("evaluaciones", evaluaciones);
        model.addAttribute("notasMap", notasMap);

        return "seccion/calificaciones";
    }

    // ==========================================
    // 8. GUARDAR CALIFICACIONES
    // ==========================================
    @PostMapping("/{id}/calificaciones/guardar")
    public String guardarCalificaciones(
            @PathVariable("id") Long idSeccion,
            @RequestParam Map<String, String> formData,
            Authentication authentication
    ) {
        // Obtenemos el usuario activo porque tu entidad Calificacion requiere "registradoPor"
        Usuario usuario = usuarioRepository.findByNombreUsuario(authentication.getName()).orElseThrow();
        Seccion seccion = seccionRepository.findById(idSeccion).orElseThrow();

        if (!seccion.getDocente().getId().equals(usuario.getDocente().getId())) {
            return "redirect:/inicio?error=acceso_denegado";
        }

        for (Map.Entry<String, String> entry : formData.entrySet()) {
            if (entry.getKey().startsWith("nota_") && !entry.getValue().trim().isEmpty()) {
                String[] parts = entry.getKey().split("_");
                Long idDetalle = Long.parseLong(parts[1]);
                Long idEvaluacion = Long.parseLong(parts[2]);
                java.math.BigDecimal valorNota = new java.math.BigDecimal(entry.getValue().trim());

                Calificacion calificacion = calificacionRepository
                        .findByDetalleMatricula_IdAndEvaluacion_Id(idDetalle, idEvaluacion)
                        .orElse(new Calificacion());

                if (calificacion.getId() == null) {
                    DetalleMatricula dm = detalleMatriculaRepository.findById(idDetalle).orElseThrow();
                    Evaluacion ev = evaluacionRepository.findById(idEvaluacion).orElseThrow();
                    calificacion.setDetalleMatricula(dm);
                    calificacion.setEvaluacion(ev);
                }

                // Seteamos los valores usando tus variables exactas
                calificacion.setNota(valorNota);
                calificacion.setRegistradoPor(usuario);

                calificacionRepository.save(calificacion);
            }
        }

        return "redirect:/mis-secciones/" + idSeccion + "/calificaciones?exito=notas_guardadas";
    }
}