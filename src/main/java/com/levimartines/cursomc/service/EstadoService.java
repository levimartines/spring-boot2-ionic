package com.levimartines.cursomc.service;

import com.levimartines.cursomc.exceptions.ObjectNotFoundException;
import com.levimartines.cursomc.model.Estado;
import com.levimartines.cursomc.repository.EstadoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository repository;
    @Autowired
    private CidadeService cidadeService;

    public Estado findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException("Estado não encontrado"));
    }

    public List<Estado> findAllEstados() {
        return repository.findAll(Sort.by("nome"));
    }

    public List<String> findAllCidades(Long estadoCod) {
        return cidadeService.findAllByEstado(findById(estadoCod));
    }
}
