package com.levimartines.cursomc.controller;

import com.levimartines.cursomc.bean.ClienteBean;
import com.levimartines.cursomc.bean.ClienteNewBean;
import com.levimartines.cursomc.model.Cliente;
import com.levimartines.cursomc.service.ClienteService;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id){
        return ResponseEntity.ok(clienteService.findById(id));
    }

    @GetMapping(value = "/page")
    public ResponseEntity<?> findPage(Pageable page){
        Page<Cliente> list = clienteService.findPage(page);
        return ResponseEntity.ok(list.map(ClienteBean::new));
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        List<Cliente> list = clienteService.findAll();
        return ResponseEntity.ok(list.stream().map(ClienteBean::new).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewBean obj, HttpServletRequest request) {
        clienteService.save(clienteService.fromBean(obj));
        URI uri = ServletUriComponentsBuilder.fromRequest(request)
            .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity update(@Valid @RequestBody ClienteBean obj, @PathVariable Long id) {
        obj.setId(id);
        clienteService.update(clienteService.fromBean(obj));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
