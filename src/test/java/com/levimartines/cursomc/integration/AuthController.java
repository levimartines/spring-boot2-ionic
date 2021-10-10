package com.levimartines.cursomc.integration;

import com.levimartines.cursomc.bean.LoginBean;
import com.levimartines.cursomc.handler.StandardError;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AuthController extends BaseControllerIT {

	@Test
	void login() {
		LoginBean loginBean = new LoginBean("admin@admin.com", "#hackeado");
		ResponseEntity<StandardError> response = restTemplate.postForEntity("/login", loginBean, StandardError.class);
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(401, response.getBody().getStatus());
	}
}
