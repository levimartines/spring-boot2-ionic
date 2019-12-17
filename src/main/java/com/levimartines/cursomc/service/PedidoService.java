package com.levimartines.cursomc.service;

import com.levimartines.cursomc.enums.EstadoPagamento;
import com.levimartines.cursomc.model.ItemPedido;
import com.levimartines.cursomc.model.PagamentoComBoleto;
import com.levimartines.cursomc.model.Pedido;
import com.levimartines.cursomc.repository.ItemPedidoRepository;
import com.levimartines.cursomc.repository.PagamentoRepository;
import com.levimartines.cursomc.repository.PedidoRepository;
import com.levimartines.cursomc.service.exceptions.ObjectNotFoundException;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoService produtoService;
    private final BoletoService boletoService;
    private final PagamentoRepository pagamentoRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    public Pedido findById(Long id) {
        return pedidoRepository.findById(id).orElseThrow(() ->
            new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: "
                + Pedido.class));
    }

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public Pedido save(Pedido obj) {
        obj.setId(null);
        obj.setInstante(new Date());
        obj.getPagamento().setEstadoPagamento(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);

        if (obj.getPagamento() instanceof PagamentoComBoleto) {
            PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
            boletoService.preencherPagamento(obj, obj.getInstante());
        }

        obj = pedidoRepository.save(obj);
        pagamentoRepository.save(obj.getPagamento());

        for (ItemPedido ip : obj.getItens()) {
            ip.setDesconto(0.0);
            ip.setPreco(produtoService.findById(ip.getId().getProduto().getId()).getPreco());
            ip.getId().setPedido(obj);
        }
        itemPedidoRepository.saveAll(obj.getItens());
        return obj;
    }
}
