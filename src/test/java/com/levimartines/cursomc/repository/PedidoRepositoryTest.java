package com.levimartines.cursomc.repository;

import com.levimartines.cursomc.model.Cliente;
import com.levimartines.cursomc.model.Pedido;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.assertFalse;

@DisplayName("PedidoRepository Tests")
@DataJpaTest
class PedidoRepositoryTest {

	@Autowired
	PedidoRepository pedidoRepository;

	@Autowired
	ClienteRepository clienteRepository;

	@Test
	void findByCliente() {
		Pedido pedido = createPedido();
		Page<Pedido> pedidos = pedidoRepository
				.findByCliente(Cliente.builder().id(pedido.getCliente().getId()).build(), PageRequest.of(0, 1));
		assertFalse(pedidos.isEmpty());
	}

	Pedido createPedido() {
		Cliente cliente = clienteRepository.save(new Cliente());
		Pedido pedido = new Pedido();
		pedido.setCliente(cliente);
		return pedidoRepository.save(pedido);
	}

}