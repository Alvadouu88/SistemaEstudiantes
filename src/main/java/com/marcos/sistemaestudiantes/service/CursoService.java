package com.marcos.sistemaestudiantes.service;

import com.marcos.sistemaestudiantes.model.Curso;
import com.marcos.sistemaestudiantes.repository.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public List<Curso> listar() {
        return cursoRepository.findAllByOrderByCodigoAsc();
    }

    public Curso guardar(Curso datosFormulario) {
        normalizarYValidar(datosFormulario);

        if (datosFormulario.getIdCurso() == null) {
            if (cursoRepository.existsByCodigoIgnoreCase(datosFormulario.getCodigo())) {
                throw new IllegalArgumentException("Ya existe un curso con ese código.");
            }

            return cursoRepository.save(datosFormulario);
        }

        Curso cursoExistente = buscarPorId(datosFormulario.getIdCurso());

        if (cursoRepository.existsByCodigoIgnoreCaseAndIdCursoNot(
                datosFormulario.getCodigo(), datosFormulario.getIdCurso())) {
            throw new IllegalArgumentException("Ya existe otro curso con ese código.");
        }

        cursoExistente.setCodigo(datosFormulario.getCodigo());
        cursoExistente.setNombre(datosFormulario.getNombre());
        cursoExistente.setTipo(datosFormulario.getTipo());
        cursoExistente.setActivo(datosFormulario.isActivo());

        return cursoRepository.save(cursoExistente);
    }

    public void desactivar(Long idCurso) {
        Curso curso = buscarPorId(idCurso);
        curso.setActivo(false);
        cursoRepository.save(curso);
    }

    private Curso buscarPorId(Long idCurso) {
        return cursoRepository.findById(idCurso)
                .orElseThrow(() -> new IllegalArgumentException("El curso seleccionado ya no existe."));
    }

    private void normalizarYValidar(Curso curso) {
        String codigo = curso.getCodigo() == null ? "" : curso.getCodigo().trim().toUpperCase(Locale.ROOT);
        String nombre = curso.getNombre() == null ? "" : curso.getNombre().trim();
        String tipo = curso.getTipo() == null ? "" : curso.getTipo().trim().toUpperCase(Locale.ROOT);

        if (codigo.isBlank() || nombre.isBlank()) {
            throw new IllegalArgumentException("El código y el nombre son obligatorios.");
        }

        if (!tipo.equals("OBLIGATORIO") && !tipo.equals("ELECTIVO")) {
            throw new IllegalArgumentException("El tipo de curso no es válido.");
        }

        curso.setCodigo(codigo);
        curso.setNombre(nombre);
        curso.setTipo(tipo);
    }
}
