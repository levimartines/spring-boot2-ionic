package com.levimartines.cursomc.services;

import com.levimartines.cursomc.model.Categoria;
import com.levimartines.cursomc.repositories.CategoriaRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    private CategoriaRepository categoriaRepository;

    public CategoriaService(
        CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria findById(Long id){
        return categoriaRepository.findById(id).orElse(null);
    }

    public List<Categoria> findAll(){
        return categoriaRepository.findAll();
    }

    public Categoria save(Categoria obj){
        return categoriaRepository.save(obj);
    }
}
