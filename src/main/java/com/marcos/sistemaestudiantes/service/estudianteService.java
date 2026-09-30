package com.marcos.sistemaestudiantes.service;

import com.marcos.sistemaestudiantes.model.estudiante;
import com.marcos.sistemaestudiantes.repository.estudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class estudianteService {
    @Autowired
    private estudianteRepository estudianteRepository;

    @Transactional
    public Long registrarEstudiante(estudiante estudiante) {
        // Asignar un estado por defecto si viene nulo
        if (estudiante.getEstado() == null || estudiante.getEstado().isEmpty()) {
            estudiante.setEstado("ACTIVO");
        }

        return estudianteRepository.ejecutarInsertarEstudiante(
                estudiante.getTipoDocumento(),
                estudiante.getNumeroDocumento(),
                estudiante.getNombres(),
                estudiante.getApellidos(),
                estudiante.getNacimiento(),
                estudiante.getCorreo(),
                estudiante.getTelefono(),
                estudiante.getEstado()
        );
    }
    @Transactional
    public Long actualizarEstudiante(estudiante estudiante){
        if (estudiante.getEstado() == null || estudiante.getEstado().isEmpty()) {
            estudiante.setEstado("ACTIVO");
        }
        return estudianteRepository.actualizarEstudiante(
                estudiante.getTipoDocumento(),
                estudiante.getNumeroDocumento(),
                estudiante.getNombres(),
                estudiante.getApellidos(),
                estudiante.getNacimiento(),
                estudiante.getCorreo(),
                estudiante.getTelefono(),
                estudiante.getEstado(),
                estudiante.getIdEstudiante()
        );
    }
}
