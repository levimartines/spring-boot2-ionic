package com.levimartines.cursomc.service;

import com.levimartines.cursomc.model.Cidade;
import com.levimartines.cursomc.model.Estado;
import com.levimartines.cursomc.repository.CidadeRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository repository;

    public List<String> findAllByEstado(Estado e) {
        return repository.findAllByEstadoOrderByNome(e).stream().map(Cidade::getNome)
            .collect(Collectors.toList());
    }

}
