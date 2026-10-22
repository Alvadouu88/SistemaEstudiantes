package com.marcos.sistemaestudiantes.service;

import com.marcos.sistemaestudiantes.dto.CursoRequestDto;
import com.marcos.sistemaestudiantes.dto.CursoResponseDto;
import com.marcos.sistemaestudiantes.model.Curso;
import com.marcos.sistemaestudiantes.repository.CursoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public Page<CursoResponseDto> listarTodosPaginado(int page, int size) {
        // Ordenamos por ID descendente para ver los más nuevos primero
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        // .map() de Page convierte automáticamente las entidades al DTO sin perder la paginación
        return cursoRepository.findAll(pageable)
                .map(this::toResponseDto);
    }

    public List<CursoResponseDto> listarActivos() {
        return cursoRepository.findByActivoTrue().stream()
                .map(this::toResponseDto)
                .toList();
    }

    public CursoResponseDto buscarPorId(Long id) {
        return toResponseDto(obtenerPorId(id));
    }

    @Transactional
    public CursoResponseDto crear(CursoRequestDto dto) {
        String codigoNormalizado = normalizarTexto(dto.codigo());

        if (cursoRepository.existsByCodigoIgnoreCase(codigoNormalizado)) {
            throw new IllegalArgumentException("Ya existe un curso registrado con el código: " + codigoNormalizado);
        }

        Curso curso = Curso.builder()
                .codigo(codigoNormalizado)
                .nombre(dto.nombre().trim())
                .tipo(dto.tipo().toUpperCase(Locale.ROOT))
                .activo(dto.activo() != null ? dto.activo() : true)
                .build();

        return toResponseDto(cursoRepository.save(curso));
    }

    @Transactional
    public CursoResponseDto actualizar(Long id, CursoRequestDto dto) {
        Curso curso = obtenerPorId(id);
        String codigoNormalizado = normalizarTexto(dto.codigo());

        if (cursoRepository.existsByCodigoIgnoreCaseAndIdNot(codigoNormalizado, id)) {
            throw new IllegalArgumentException("Ya existe otro curso registrado con el código: " + codigoNormalizado);
        }

        curso.setCodigo(codigoNormalizado);
        curso.setNombre(dto.nombre().trim());
        curso.setTipo(dto.tipo().toUpperCase(Locale.ROOT));
        if (dto.activo() != null) {
            curso.setActivo(dto.activo());
        }

        return toResponseDto(cursoRepository.save(curso));
    }

    @Transactional
    public void cambiarEstado(Long id, boolean activo) {
        Curso curso = obtenerPorId(id);
        curso.setActivo(activo);
        cursoRepository.save(curso);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!cursoRepository.existsById(id)) {
            throw new IllegalArgumentException("El curso con ID " + id + " no existe.");
        }
        cursoRepository.deleteById(id);
    }

    // Métodos auxiliares privados
    private Curso obtenerPorId(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el curso con ID: " + id));
    }

    private String normalizarTexto(String texto) {
        return texto == null ? "" : texto.trim().toUpperCase(Locale.ROOT);
    }

    private CursoResponseDto toResponseDto(Curso entity) {
        return new CursoResponseDto(
                entity.getId(),
                entity.getCodigo(),
                entity.getNombre(),
                entity.getTipo(),
                entity.getActivo()
        );
    }
}
