package com.levimartines.cursomc.service;

import com.levimartines.cursomc.model.Pedido;
import com.levimartines.cursomc.repository.PedidoRepository;
import com.levimartines.cursomc.service.exceptions.ObjectNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    private PedidoRepository pedidoRepository;

    public PedidoService(
        PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido findById(Long id) {
        return pedidoRepository.findById(id).orElseThrow(() ->
            new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: "
                + Pedido.class));
    }

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public Pedido save(Pedido obj) {
        return pedidoRepository.save(obj);
    }
}
