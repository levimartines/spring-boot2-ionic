package com.levimartines.cursomc.service;

import com.levimartines.cursomc.model.PagamentoComBoleto;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class BoletoServiceTest {

	@InjectMocks
	BoletoService boletoService;

	@Test
	void preencherPagamento() {
		PagamentoComBoleto payment = new PagamentoComBoleto();
		boletoService.preencherPagamento(payment, new Date());
		assertNotNull(payment.getDataVencimento());
	}
}