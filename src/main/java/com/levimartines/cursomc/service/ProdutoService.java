package com.levimartines.cursomc.service;

import com.levimartines.cursomc.exceptions.ObjectNotFoundException;
import com.levimartines.cursomc.model.Categoria;
import com.levimartines.cursomc.model.Produto;
import com.levimartines.cursomc.repository.CategoriaRepository;
import com.levimartines.cursomc.repository.ProdutoRepository;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Cacheable(cacheNames = "productsCache", key = "#id")
    public Produto findById(Long id) {
        return produtoRepository.findById(id).orElseThrow(() ->
            new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: "
                + Produto.class));
    }

    @CacheEvict(cacheNames = "productsCache", key = "#id")
    public Produto update(Long id, Double newPrice) {
        Produto produto = findById(id);
        produto.setPreco(newPrice);
        return produtoRepository.save(produto);
    }

    public Page<Produto> search(String nome, List<Long> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        List<Categoria> categorias = categoriaRepository.findAllById(ids);
        return produtoRepository.findDistinctByNomeIgnoreCaseContainingAndCategoriasIn(nome, categorias, pageRequest);
    }

}
