package com.levimartines.cursomc.integration;

import com.levimartines.cursomc.model.Categoria;
import com.levimartines.cursomc.model.Produto;
import com.levimartines.cursomc.wrapper.RestResponsePage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class ProdutoControllerIT extends BaseControllerIT {

	@Test
	void findById() {
		Produto product = createProduto("Playstation 5", 5000.0);
		String url = "/produtos/" + product.getId();
		ResponseEntity<Produto> response = restTemplate.getForEntity(url, Produto.class);
		assertResponseOk(response);
		Assertions.assertEquals(response.getBody().getNome(), product.getNome());
	}

	@Test
	void search() {
		Categoria categoria = createCategoria("Games");

		Produto play4 = createProduto("Playstation 4", 800.0);
		addCategoria(play4, categoria);
		Produto play5 = createProduto("Playstation 5", 5000.0);
		addCategoria(play5, categoria);
		Produto playPlus = createProduto("Playstation Plus", 20.0);
		addCategoria(playPlus, categoria);
		Produto xBox = createProduto("XBox One X", 5000.0);
		addCategoria(xBox, categoria);

		ParameterizedTypeReference<RestResponsePage<Produto>> type = new ParameterizedTypeReference<>() {
		};
		String url = "/produtos?nome=playstation&categorias=" + categoria.getId();
		var response = restTemplate.exchange(url, HttpMethod.GET, null, type);
		assertResponseOk(response);
		assertNotNull(response.getBody().getContent());
		assertEquals(3, response.getBody().getContent().size());
	}
}
