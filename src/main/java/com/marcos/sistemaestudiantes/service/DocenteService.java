package com.marcos.sistemaestudiantes.service;


import com.marcos.sistemaestudiantes.dto.DocenteRequestDto;
import com.marcos.sistemaestudiantes.dto.DocenteResponseDto;
import com.marcos.sistemaestudiantes.model.Docente;
import com.marcos.sistemaestudiantes.repository.DocenteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@Transactional(readOnly = true)
public class DocenteService {

    private final DocenteRepository docenteRepository;

    public DocenteService(DocenteRepository docenteRepository) {
        this.docenteRepository = docenteRepository;
    }

    public Page<DocenteResponseDto> listarTodosPaginado(int page, int size){
        Pageable pageable= PageRequest.of(page, size, Sort.by("id").descending());
        return docenteRepository.findAll(pageable).map(this::toResponseDto);
    }

    public List<DocenteResponseDto> listarActivos() {
        return docenteRepository.findByActivoTrue().stream()
                .map(this::toResponseDto)
                .toList();
    }

    public DocenteResponseDto buscarPorId(Long id) {
        return toResponseDto(obtenerPorId(id));
    }

    @Transactional
    public DocenteResponseDto crear(DocenteRequestDto dto) {
        String codigoNormalizado = normalizarTexto(dto.codigo());
        String numDocNormalizado = dto.numeroDocumento().trim();

        if (docenteRepository.existsByCodigoIgnoreCase(codigoNormalizado)) {
            throw new IllegalArgumentException("Ya existe un docente registrado con el código: " + codigoNormalizado);
        }

        if (docenteRepository.existsByTipoDocumentoAndNumeroDocumento(dto.tipoDocumento(), numDocNormalizado)) {
            throw new IllegalArgumentException("Ya existe un docente registrado con ese documento de identidad.");
        }

        Docente docente = Docente.builder()
                .codigo(codigoNormalizado)
                .tipoDocumento(dto.tipoDocumento())
                .numeroDocumento(numDocNormalizado)
                .nombres(dto.nombres().trim())
                .apellidos(dto.apellidos().trim())
                .correo(limpiarCampoOpcional(dto.correo()))
                .telefono(limpiarCampoOpcional(dto.telefono()))
                .activo(dto.activo() != null ? dto.activo() : true)
                .build();

        return toResponseDto(docenteRepository.save(docente));
    }

    @Transactional
    public DocenteResponseDto actualizar(Long id, DocenteRequestDto dto) {
        Docente docente = obtenerPorId(id);
        String codigoNormalizado = normalizarTexto(dto.codigo());
        String numDocNormalizado = dto.numeroDocumento().trim();

        if (docenteRepository.existsByCodigoIgnoreCaseAndIdNot(codigoNormalizado, id)) {
            throw new IllegalArgumentException("Ya existe otro docente registrado con el código: " + codigoNormalizado);
        }

        if (docenteRepository.existsByTipoDocumentoAndNumeroDocumentoAndIdNot(dto.tipoDocumento(), numDocNormalizado, id)) {
            throw new IllegalArgumentException("Ya existe otro docente registrado con ese documento de identidad.");
        }

        docente.setCodigo(codigoNormalizado);
        docente.setTipoDocumento(dto.tipoDocumento());
        docente.setNumeroDocumento(numDocNormalizado);
        docente.setNombres(dto.nombres().trim());
        docente.setApellidos(dto.apellidos().trim());
        docente.setCorreo(limpiarCampoOpcional(dto.correo()));
        docente.setTelefono(limpiarCampoOpcional(dto.telefono()));

        if (dto.activo() != null) {
            docente.setActivo(dto.activo());
        }

        return toResponseDto(docenteRepository.save(docente));
    }

    @Transactional
    public void cambiarEstado(Long id, boolean activo) {
        Docente docente = obtenerPorId(id);
        docente.setActivo(activo);
        docenteRepository.save(docente);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!docenteRepository.existsById(id)) {
            throw new IllegalArgumentException("El docente con ID " + id + " no existe.");
        }
        docenteRepository.deleteById(id);
    }

    // Métodos auxiliares privados
    private Docente obtenerPorId(Long id) {
        return docenteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el docente con ID: " + id));
    }

    private String normalizarTexto(String texto) {
        return texto == null ? "" : texto.trim().toUpperCase(Locale.ROOT);
    }

    private String limpiarCampoOpcional(String texto) {
        return (texto == null || texto.isBlank()) ? null : texto.trim();
    }

    private DocenteResponseDto toResponseDto(Docente entity) {
        return new DocenteResponseDto(
                entity.getId(),
                entity.getCodigo(),
                entity.getTipoDocumento(),
                entity.getNumeroDocumento(),
                entity.getNombres(),
                entity.getApellidos(),
                entity.getCorreo(),
                entity.getTelefono(),
                entity.getActivo()
        );
    }
}