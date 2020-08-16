package com.levimartines.cursomc.config;

import com.levimartines.cursomc.service.EmailService;
import com.levimartines.cursomc.service.MockEmailService;
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
