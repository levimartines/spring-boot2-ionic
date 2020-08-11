package com.levimartines.cursomc.controller;

import com.levimartines.cursomc.bean.CidadeBean;
import com.levimartines.cursomc.model.Estado;
import com.levimartines.cursomc.service.EstadoService;
import java.util.List;
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
    public ResponseEntity<List<Estado>> findAll() {
        return ResponseEntity.ok(service.findAllEstados());
    }

    @GetMapping(value = "/{id}/cidades")
    public ResponseEntity<List<CidadeBean>> findAllCidadesByEstadoCod(@PathVariable Long id) {
        return ResponseEntity.ok(service.findAllCidades(id));
    }

}
