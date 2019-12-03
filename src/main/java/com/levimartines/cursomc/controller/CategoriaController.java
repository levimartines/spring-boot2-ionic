package com.levimartines.cursomc.controller;

import com.levimartines.cursomc.bean.CategoriaBean;
import com.levimartines.cursomc.model.Categoria;
import com.levimartines.cursomc.service.CategoriaService;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {

    private CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Categoria> findById(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.findById(id));
    }

    @GetMapping(value = "/page")
    public ResponseEntity<?> findPage(Pageable page){
        Page<Categoria> list = categoriaService.findPage(page);
        return ResponseEntity.ok(list.map(CategoriaBean::new));
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        List<Categoria> list = categoriaService.findAll();
        return ResponseEntity.ok(list.stream().map(CategoriaBean::new).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaBean obj, HttpServletRequest request) {
        categoriaService.save(categoriaService.fromBean(obj));
        URI uri = ServletUriComponentsBuilder.fromRequest(request)
            .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity update(@Valid @RequestBody CategoriaBean obj, @PathVariable Long id) {
        obj.setId(id);
        categoriaService.update(categoriaService.fromBean(obj));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
