package com.levimartines.cursomc.integration;

import com.levimartines.cursomc.model.Cliente;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class ClienteControllerIT extends BaseControllerIT {

	@Test
	void findById() {
		Cliente cliente = createCliente("Test", "test@test.com", "test", false);
		String url = "/clientes/" + cliente.getId();
		ResponseEntity<Cliente> response = restTemplate.exchange(url, HttpMethod.GET, getEntity(), Cliente.class);
		assertResponseOk(response);
		Assertions.assertNotNull(response.getBody().getId());
	}

	@Test
	void findByEmail() {
		Cliente cliente = createCliente("Test", "test@test.com", "test", false);
		String url = "/clientes/email?email=" + cliente.getEmail();
		ResponseEntity<Cliente> response = restTemplate.exchange(url, HttpMethod.GET, getEntity(), Cliente.class);
		assertResponseOk(response);
		Assertions.assertNotNull(response.getBody().getId());
	}
}
