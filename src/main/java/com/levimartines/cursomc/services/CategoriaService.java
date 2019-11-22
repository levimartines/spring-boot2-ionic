package com.levimartines.cursomc.services;

import com.levimartines.cursomc.model.Categoria;
import com.levimartines.cursomc.repositories.CategoriaRepository;
import com.levimartines.cursomc.services.exceptions.ObjectNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    private CategoriaRepository categoriaRepository;

    public CategoriaService(
        CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria findById(Long id) {
        return categoriaRepository.findById(id).orElseThrow(() ->
            new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: "
                + Categoria.class));
    }

    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    public Categoria insert(Categoria obj) {
        obj.setId(null);
        return categoriaRepository.save(obj);
    }
}
