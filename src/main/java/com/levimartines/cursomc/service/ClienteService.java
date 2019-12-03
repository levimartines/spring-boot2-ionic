package com.levimartines.cursomc.service;

import com.levimartines.cursomc.bean.ClienteBean;
import com.levimartines.cursomc.bean.ClienteNewBean;
import com.levimartines.cursomc.enums.TipoCliente;
import com.levimartines.cursomc.model.Cidade;
import com.levimartines.cursomc.model.Cliente;
import com.levimartines.cursomc.model.Endereco;
import com.levimartines.cursomc.repository.ClienteRepository;
import com.levimartines.cursomc.repository.EnderecoRepository;
import com.levimartines.cursomc.service.exceptions.DataIntegrityException;
import com.levimartines.cursomc.service.exceptions.ObjectNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final EnderecoRepository enderecoRepository;

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
        obj = clienteRepository.save(obj);
        enderecoRepository.saveAll(obj.getEnderecos());
        return obj;
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

    public Cliente fromBean(ClienteNewBean bean){
        Cliente cliente = new Cliente(null, bean.getNome(), bean.getEmail(), bean.getCpfOuCnpj(), TipoCliente
            .toEnum(bean.getTipo()));

        Endereco end = new Endereco();
        end.setLogradouro(bean.getLogradouro());
        end.setNumero(bean.getNumero());
        end.setComplemento(bean.getComplemento());
        end.setBairro(bean.getBairro());
        end.setCep(bean.getCep());
        end.setCidade(new Cidade(bean.getCidadeId(), null, null));

        cliente.getEnderecos().add(end);
        cliente.getTelefones().add(bean.getTelefone1());
        if(bean.getTelefone2()!=null){
            cliente.getTelefones().add(bean.getTelefone2());
        }
        if(bean.getTelefone3()!=null){
            cliente.getTelefones().add(bean.getTelefone3());
        }
        return cliente;
    }

    private void updateData(Cliente newObj, Cliente obj) {
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }
}
