package com.levimartines.cursomc.controller;

import com.levimartines.cursomc.annotation.Auditable;
import com.levimartines.cursomc.bean.CategoriaBean;
import com.levimartines.cursomc.model.Categoria;
import com.levimartines.cursomc.service.CategoriaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/categorias")
@Api(tags = "CategoriaController - Endpoints relacionados a Categorias", produces = "application/json")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(
        value = "Busca de Categoria por id",
        notes = "Retorna a categoria específica",
        produces = "application/json",
        httpMethod = "GET",
        response = Categoria.class
    )
    @Auditable
    public ResponseEntity<Categoria> findById(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.findById(id));
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ApiOperation(
        value = "Retorna todas categorias com paginação",
        notes = "Retorna uma página de categorias",
        produces = "application/json",
        httpMethod = "GET",
        response = Page.class
    )
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
            value = "Número da página que você deseja buscar (0..N)", example = "0", defaultValue = "0"),
        @ApiImplicitParam(name = "linesPerPage", dataType = "integer", paramType = "query",
            value = "Quantidade máxima de resultados que será retornada.", example = "12", defaultValue = "24"),
        @ApiImplicitParam(name = "direction", dataType = "string", paramType = "query",
            value = "Critério de ordenação", example = "DESC", defaultValue = "ASC", allowableValues = ",ASC, DESC"),
        @ApiImplicitParam(name = "orderBy", dataType = "string", paramType = "query",
            value = "Critério de ordenação", example = "id", defaultValue = "nome", allowableValues = ",id, nome"),
    })
    @ApiResponses({
        @ApiResponse(code = 200, message = "Requisição concluída com sucesso", responseContainer = "content", response = CategoriaBean.class),
        @ApiResponse(code = 500, message = "Erro interno do servidor")
    })
    public ResponseEntity<Page<CategoriaBean>> findPage(
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
        @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
        @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Page<Categoria> list = categoriaService.findPage(page, linesPerPage, orderBy, direction);
        Page<CategoriaBean> listDto = list.map(CategoriaBean::new);
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping
    @ApiOperation(
        value = "Retorna todas categorias",
        produces = "application/json",
        httpMethod = "GET",
        response = Page[].class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Requisição concluída com sucesso", response = CategoriaBean[].class),
        @ApiResponse(code = 500, message = "Erro interno do servidor")
    })
    public ResponseEntity<?> findAll() {
        List<Categoria> list = categoriaService.findAll();
        return ResponseEntity
            .ok(list.stream().map(CategoriaBean::new).collect(Collectors.toList()));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ApiOperation(value = "Insere categoria")
    public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaBean obj,
        HttpServletRequest request) {
        categoriaService.save(categoriaService.fromBean(obj));
        URI uri = ServletUriComponentsBuilder.fromRequest(request)
            .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ApiOperation(value = "Atualiza categoria")
    public ResponseEntity update(@Valid @RequestBody CategoriaBean obj, @PathVariable Long id) {
        obj.setId(id);
        categoriaService.update(categoriaService.fromBean(obj));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ApiOperation(value = "Remove categoria")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Não é possível excluir uma categoria que possui produtos"),
        @ApiResponse(code = 404, message = "Código inexistente")})
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
