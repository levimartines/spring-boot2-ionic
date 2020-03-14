package com.levimartines.cursomc.controller;

import com.levimartines.cursomc.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/estados")
public class EstadoController {

    @Autowired
    private EstadoService service;

    @GetMapping
    public ResponseEntity findAll() {
        return ResponseEntity.ok(service.findAllEstados());
    }

    @GetMapping(value = "/{id}/cidades")
    public ResponseEntity findAllCidadesByEstadoCod(@PathVariable Long id) {
        return ResponseEntity.ok(service.findAllCidades(id));
    }

}
