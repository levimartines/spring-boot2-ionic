package com.levimartines.cursomc.services;

import com.levimartines.cursomc.beans.ClienteBean;
import com.levimartines.cursomc.model.Cliente;
import com.levimartines.cursomc.repositories.ClienteRepository;
import com.levimartines.cursomc.services.exceptions.DataIntegrityException;
import com.levimartines.cursomc.services.exceptions.ObjectNotFoundException;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<Cliente> findPage(Pageable page) {
        return clienteRepository.findAll(page);
    }

    public Cliente save(Cliente obj) {
        return clienteRepository.save(obj);
    }
    
    public void update(Cliente obj) {
        Cliente newObj = findById(obj.getId());
        updateData(newObj, obj);
        clienteRepository.save(newObj);
    }

    public void delete(Long id) {
        try {
            findById(id);
            clienteRepository.deleteById(id);
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é possível excluir um Cliente que possui Entidades relacionadas");
        }
    }

    public Cliente fromBean(ClienteBean bean){
        return new Cliente(bean.getId(),bean.getNome(),bean.getEmail());
    }

    private void updateData(Cliente newObj, Cliente obj) {
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }
}
