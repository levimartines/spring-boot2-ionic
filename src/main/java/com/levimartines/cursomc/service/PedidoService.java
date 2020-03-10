package com.levimartines.cursomc.service;

import com.levimartines.cursomc.enums.EstadoPagamento;
import com.levimartines.cursomc.enums.Perfil;
import com.levimartines.cursomc.exceptions.AuthorizationException;
import com.levimartines.cursomc.exceptions.ObjectNotFoundException;
import com.levimartines.cursomc.model.Cliente;
import com.levimartines.cursomc.model.ItemPedido;
import com.levimartines.cursomc.model.PagamentoComBoleto;
import com.levimartines.cursomc.model.Pedido;
import com.levimartines.cursomc.repository.ItemPedidoRepository;
import com.levimartines.cursomc.repository.PagamentoRepository;
import com.levimartines.cursomc.repository.PedidoRepository;
import com.levimartines.cursomc.security.CustomUserDetails;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoService produtoService;
    private final BoletoService boletoService;
    private final PagamentoRepository pagamentoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final ClienteService clienteService;
    private final EmailService emailService;

    public Pedido findById(Long id) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(() ->
            new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: "
                + Pedido.class));
        CustomUserDetails userDetails = UserService.authenticated();
        if (userDetails == null || !userDetails.hasRole(Perfil.ADMIN) && !pedido.getCliente()
            .getId().equals(userDetails.getId())) {
            throw new AuthorizationException("Acesso negado");
        }
        return pedido;
    }

    public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy,
        String direction) {
        CustomUserDetails userDetails = UserService.authenticated();
        Cliente cliente = clienteService.findById(userDetails.getId());
        PageRequest pageRequest = PageRequest
            .of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return pedidoRepository.findByCliente(cliente, pageRequest);
    }

    public Pedido save(Pedido obj) {
        obj.setId(null);
        obj.setInstante(new Date());
        obj.getPagamento().setEstadoPagamento(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);

        obj.setCliente(clienteService.findById(obj.getCliente().getId()));

        if (obj.getPagamento() instanceof PagamentoComBoleto) {
            PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
            boletoService.preencherPagamento(obj, obj.getInstante());
        }

        obj = pedidoRepository.save(obj);
        pagamentoRepository.save(obj.getPagamento());

        for (ItemPedido ip : obj.getItens()) {
            ip.setDesconto(0.0);
            ip.setProduto(produtoService.findById(ip.getId().getProduto().getId()));
            ip.setPreco(ip.getId().getProduto().getPreco());
            ip.getId().setPedido(obj);
        }
        itemPedidoRepository.saveAll(obj.getItens());
        emailService.sendOrderConfirmationEmail(obj);
        return obj;
    }
}
