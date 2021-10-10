package com.levimartines.cursomc.service;

import com.levimartines.cursomc.exceptions.ObjectNotFoundException;
import com.levimartines.cursomc.model.Cliente;
import com.levimartines.cursomc.repository.ClienteRepository;
import com.levimartines.cursomc.service.email.EmailService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
class AuthServiceTest {
	@InjectMocks
	AuthService authService;
	@Mock
	ClienteRepository clienteRepository;
	@Mock
	BCryptPasswordEncoder passwordEncoder;
	@Mock
	EmailService emailService;

	@Test
	void generateAndSendNewPassword() {
		given(clienteRepository.findByEmail(anyString())).willReturn(new Cliente());
		given(passwordEncoder.encode(any())).willReturn("myPasswordHash");

		authService.generateAndSendNewPassword("test@test.com");

		Mockito.verify(emailService, Mockito.atLeastOnce()).sendNewPasswordEmail(any(), anyString());
	}

	@Test
	void generateAndSendNewPasswordClientNotFound() {
		assertThrows(ObjectNotFoundException.class, () -> authService.generateAndSendNewPassword("test@test.com"));
	}

}