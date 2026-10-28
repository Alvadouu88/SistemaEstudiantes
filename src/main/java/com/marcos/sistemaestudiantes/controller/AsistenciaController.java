package com.marcos.sistemaestudiantes.controller;

import com.marcos.sistemaestudiantes.service.asistenciaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/asistencia")
public class AsistenciaController {
    private final asistenciaService asistencia;
    public AsistenciaController(asistenciaService Asistencia){
        this.asistencia=Asistencia;
    }

    @GetMapping
    public String listar(){
        return "seccion/asistencia";
    }
}
