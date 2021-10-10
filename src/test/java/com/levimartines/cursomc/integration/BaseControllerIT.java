package com.levimartines.cursomc.integration;

import com.levimartines.cursomc.bean.LoginBean;
import com.levimartines.cursomc.enums.Perfil;
import com.levimartines.cursomc.enums.TipoCliente;
import com.levimartines.cursomc.model.Categoria;
import com.levimartines.cursomc.model.Cliente;
import com.levimartines.cursomc.model.Produto;
import com.levimartines.cursomc.repository.CategoriaRepository;
import com.levimartines.cursomc.repository.ClienteRepository;
import com.levimartines.cursomc.repository.ProdutoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseControllerIT {
	@Autowired
	TestRestTemplate restTemplate;
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@LocalServerPort
	int port;

	String bearerToken;

	@Autowired
	CategoriaRepository categoriaRepository;
	@Autowired
	ClienteRepository clienteRepository;
	@Autowired
	ProdutoRepository produtoRepository;

	Categoria createCategoria(String name) {
		Categoria categoria = categoriaRepository.save(new Categoria(name));
		assertNotNull(categoria.getId());
		return categoria;
	}

	void addCategoria(Produto produto, Categoria categoria) {
		produto.getCategorias().add(categoria);
		produtoRepository.save(produto);
	}

	Cliente createAdmin() {
		return createCliente("admin", "admin@admin.com", "admin", true);
	}

	Cliente createCliente(String name, String email, String passwd, boolean isAdmin) {
		Cliente cliente = Cliente.builder()
				.nome(name)
				.email(email)
				.senha(passwordEncoder.encode(passwd))
				.tipo(TipoCliente.PESSOAFISICA.getCod())
				.build();
		cliente.addPerfil(Perfil.CLIENTE);
		if (isAdmin) {
			cliente.addPerfil(Perfil.ADMIN);
		}
		clienteRepository.save(cliente);
		assertNotNull(cliente.getId());
		return cliente;
	}

	Produto createProduto(String name, Double price) {
		Produto produto = produtoRepository.save(new Produto(name, price));
		assertNotNull(produto.getId());
		return produto;
	}

	void assertResponseOk(ResponseEntity<?> response) {
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
	}

	@BeforeEach
	void beforeEach() {
		deleteAll();
		createAdmin();
		login();
	}

	void login() {
		LoginBean loginBean = new LoginBean("admin@admin.com", "admin");
		String url = "/login";
		ResponseEntity<Void> response = restTemplate.postForEntity(url, loginBean, Void.class);
		assertNotNull(response.getHeaders().get("Authorization"));
		bearerToken = response.getHeaders().get("Authorization").get(0);
	}

	HttpEntity<?> getEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", bearerToken);
		return new HttpEntity<>(headers);
	}

	void deleteAll() {
		clienteRepository.deleteAll();
		produtoRepository.deleteAll();
		categoriaRepository.deleteAll();
	}
}
