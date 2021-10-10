package com.levimartines.cursomc.config;

import com.levimartines.cursomc.service.email.EmailService;
import com.levimartines.cursomc.service.email.MockEmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
public class ProdConfig {

    @Bean
    public EmailService emailService() {
        return new MockEmailService();
    }
}
