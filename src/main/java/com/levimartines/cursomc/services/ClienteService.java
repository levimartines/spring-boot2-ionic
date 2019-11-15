package com.levimartines.cursomc.services;

import com.levimartines.cursomc.model.Cliente;
import com.levimartines.cursomc.repositories.ClienteRepository;
import com.levimartines.cursomc.services.exceptions.ObjectNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private ClienteRepository clienteRepository;

    public ClienteService(
        ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente findById(Long id) {
        return clienteRepository.findById(id).orElseThrow(() ->
            new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: "
                + Cliente.class));
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Cliente save(Cliente obj) {
        return clienteRepository.save(obj);
    }
}
