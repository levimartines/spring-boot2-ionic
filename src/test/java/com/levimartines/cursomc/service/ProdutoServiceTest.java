package com.levimartines.cursomc.service;

import com.levimartines.cursomc.exceptions.ObjectNotFoundException;
import com.levimartines.cursomc.model.Categoria;
import com.levimartines.cursomc.model.Produto;
import com.levimartines.cursomc.repository.CategoriaRepository;
import com.levimartines.cursomc.repository.ProdutoRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
class ProdutoServiceTest {

	@InjectMocks
	ProdutoService produtoService;
	@Mock
	ProdutoRepository produtoRepository;
	@Mock
	CategoriaRepository categoriaRepository;


	@Test
	void findByExistingId() {
		given(produtoRepository.findById(anyLong()))
				.willReturn(Optional.of(new Produto(1L, "Batata Frita", 2.99)));

		Produto produto = produtoService.findById(1L);

		assertNotNull(produto);
	}

	@Test
	void findByNonExistingId() {
		assertThrows(ObjectNotFoundException.class, () -> produtoService.findById(1L));
	}

	@Test
	void search() {
		given(categoriaRepository.findAllById(anyIterable()))
				.willReturn(Arrays.asList(new Categoria(1L, "Games")));
		given(produtoRepository.findDistinctByNomeIgnoreCaseContainingAndCategoriasIn(anyString(), anyList(), any()))
				.willReturn(new PageImpl<>(Arrays.asList(new Produto("Legend of Zelda", 19.90), new Produto("Pokemon Sword", 999.99))));

		Page<Produto> products = produtoService.search("", Collections.singletonList(1L), 0, 3, "nome", "ASC");

		assertFalse(products.isEmpty());
		assertNotNull(products.getContent());
		assertEquals(2, products.getContent().size());
	}

}