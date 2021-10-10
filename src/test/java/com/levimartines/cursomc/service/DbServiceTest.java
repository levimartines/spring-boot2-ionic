package com.levimartines.cursomc.service;

import java.text.ParseException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DbServiceTest {

	@Autowired
	DbService dbService;

	@Test
	void testInstantiateTestDb() throws ParseException {
		dbService.instantiateTestDatabase();

		dbService.cleanTestDatabase();
	}
}