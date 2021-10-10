package com.levimartines.cursomc.integration;

import com.levimartines.cursomc.model.Categoria;
import io.restassured.RestAssured;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CategoriaControllerIT extends BaseControllerIT {

	@Test
	void findAll() {
		/* Setting up RestAssured */
		RestAssured.baseURI = "http://localhost:" + port;

		createCategoria("Games");

		Categoria[] categorias = when().get("/categorias")
				.then().statusCode(200).extract().as(Categoria[].class);

		assertNotNull(categorias);
		assertEquals(1, categorias.length);
	}
}
