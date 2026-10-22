package com.marcos.sistemaestudiantes.service;



import com.marcos.sistemaestudiantes.dto.EstudianteRequestDto;
import com.marcos.sistemaestudiantes.dto.EstudianteResponseDto;
import com.marcos.sistemaestudiantes.model.Estudiante;
import com.marcos.sistemaestudiantes.repository.EstudianteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@Transactional(readOnly = true)
public class EstudianteService {

    private final EstudianteRepository estudianteRepository;

    public EstudianteService(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    // Nuevo método con paginación
    public Page<EstudianteResponseDto> listarTodosPaginado(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return estudianteRepository.findAll(pageable).map(this::toResponseDto);
    }

    public EstudianteResponseDto buscarPorId(Long id) {
        return toResponseDto(obtenerPorId(id));
    }

    @Transactional
    public EstudianteResponseDto crear(EstudianteRequestDto dto) {
        String codigoNormalizado = normalizarTexto(dto.codigo());
        String numDocNormalizado = dto.numeroDocumento().trim();

        if (estudianteRepository.existsByCodigoIgnoreCase(codigoNormalizado)) {
            throw new IllegalArgumentException("Ya existe un estudiante registrado con el código: " + codigoNormalizado);
        }

        if (estudianteRepository.existsByTipoDocumentoAndNumeroDocumento(dto.tipoDocumento(), numDocNormalizado)) {
            throw new IllegalArgumentException("Ya existe un estudiante registrado con ese documento de identidad.");
        }

        Estudiante estudiante = Estudiante.builder()
                .codigo(codigoNormalizado)
                .tipoDocumento(dto.tipoDocumento())
                .numeroDocumento(numDocNormalizado)
                .nombres(dto.nombres().trim())
                .apellidos(dto.apellidos().trim())
                .fechaNacimiento(dto.fechaNacimiento())
                .correo(limpiarCampoOpcional(dto.correo()))
                .telefono(limpiarCampoOpcional(dto.telefono()))
                .estado(dto.estado().toUpperCase(Locale.ROOT))
                .build();

        return toResponseDto(estudianteRepository.save(estudiante));
    }

    @Transactional
    public EstudianteResponseDto actualizar(Long id, EstudianteRequestDto dto) {
        Estudiante estudiante = obtenerPorId(id);
        String codigoNormalizado = normalizarTexto(dto.codigo());
        String numDocNormalizado = dto.numeroDocumento().trim();

        if (estudianteRepository.existsByCodigoIgnoreCaseAndIdNot(codigoNormalizado, id)) {
            throw new IllegalArgumentException("Ya existe otro estudiante registrado con el código: " + codigoNormalizado);
        }

        if (estudianteRepository.existsByTipoDocumentoAndNumeroDocumentoAndIdNot(dto.tipoDocumento(), numDocNormalizado, id)) {
            throw new IllegalArgumentException("Ya existe otro estudiante registrado con ese documento de identidad.");
        }

        estudiante.setCodigo(codigoNormalizado);
        estudiante.setTipoDocumento(dto.tipoDocumento());
        estudiante.setNumeroDocumento(numDocNormalizado);
        estudiante.setNombres(dto.nombres().trim());
        estudiante.setApellidos(dto.apellidos().trim());
        estudiante.setFechaNacimiento(dto.fechaNacimiento());
        estudiante.setCorreo(limpiarCampoOpcional(dto.correo()));
        estudiante.setTelefono(limpiarCampoOpcional(dto.telefono()));
        estudiante.setEstado(dto.estado().toUpperCase(Locale.ROOT));

        return toResponseDto(estudianteRepository.save(estudiante));
    }

    @Transactional
    public void cambiarEstado(Long id, String nuevoEstado) {
        Estudiante estudiante = obtenerPorId(id);
        estudiante.setEstado(nuevoEstado.toUpperCase(Locale.ROOT));
        estudianteRepository.save(estudiante);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!estudianteRepository.existsById(id)) {
            throw new IllegalArgumentException("El estudiante con ID " + id + " no existe.");
        }
        estudianteRepository.deleteById(id);
    }

    private Estudiante obtenerPorId(Long id) {
        return estudianteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el estudiante con ID: " + id));
    }

    private String normalizarTexto(String texto) {
        return texto == null ? "" : texto.trim().toUpperCase(Locale.ROOT);
    }

    private String limpiarCampoOpcional(String texto) {
        return (texto == null || texto.isBlank()) ? null : texto.trim();
    }

    private EstudianteResponseDto toResponseDto(Estudiante entity) {
        return new EstudianteResponseDto(
                entity.getId(),
                entity.getCodigo(),
                entity.getTipoDocumento(),
                entity.getNumeroDocumento(),
                entity.getNombres(),
                entity.getApellidos(),
                entity.getFechaNacimiento(),
                entity.getCorreo(),
                entity.getTelefono(),
                entity.getEstado()
        );
    }
}