package com.levimartines.cursomc.repository;

import com.levimartines.cursomc.model.Categoria;
import com.levimartines.cursomc.model.Produto;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("ProdutoRepository Tests")
class ProdutoRepositoryTest extends BaseRepositoryTest {

	@Autowired
	ProdutoRepository produtoRepository;

	@Autowired
	CategoriaRepository categoriaRepository;

	@Test
	void searchAll() {
		Categoria cat1 = createCategoria("Informática");
		Categoria cat2 = createCategoria("Roupas");
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));

		Produto prod1 = createProduto("Mouse", 40.0, cat1.getId());
		Produto prod2 = createProduto("Teclado", 88.80, cat1.getId());
		Produto prod3 = createProduto("Shirt", 49.99, cat2.getId());
		produtoRepository.saveAll(Arrays.asList(prod1, prod2, prod3));

		PageRequest pageRequest = PageRequest.of(0, 5);
		Page<Produto> products = produtoRepository
				.findDistinctByNomeIgnoreCaseContainingAndCategoriasIn("", Arrays.asList(cat1), pageRequest);

		assertFalse(products.isEmpty());
		assertNotNull(products.getContent());
		assertEquals(2, products.getContent().size());
	}



	private Produto createProduto(String name, double price, Long... categories) {
		Produto product = new Produto(name, price);
		for (Long category : categories) {
			product.getCategorias().add(new Categoria(category, null));
		}
		return produtoRepository.save(product);
	}

	private Categoria createCategoria(String name) {
		return categoriaRepository.save(new Categoria(name));
	}

}