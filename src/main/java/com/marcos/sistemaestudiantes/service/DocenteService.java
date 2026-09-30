package com.marcos.sistemaestudiantes.service;


import com.marcos.sistemaestudiantes.model.Docente;
import com.marcos.sistemaestudiantes.repository.DocenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DocenteService {

    @Autowired
    private DocenteRepository docenteRepository;

    @Transactional
    public void RegistrarDocente(Docente docente) {
        docenteRepository.ejecutarInsertarDocente(
                docente.getTipoDocumento(),
                docente.getNumeroDocumento(),
                docente.getNombres(),
                docente.getApellidos(),
                docente.getCorreo(),
                docente.getTelefono()
        );
    }
    @Transactional
    public Long actualizarDocente(Docente docente){
        if (docente.getActivo() == null) {
            docente.setActivo(true);
        }
        return docenteRepository.actualizarDocente(
                docente.getTipoDocumento(),
                docente.getNumeroDocumento(),
                docente.getNombres(),
                docente.getApellidos(),
                docente.getCorreo(),
                docente.getTelefono(),
                docente.getActivo(),
                docente.getIdDocente()
        );
    }
}



