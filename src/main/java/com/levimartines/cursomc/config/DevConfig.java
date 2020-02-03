package com.levimartines.cursomc.config;

import com.levimartines.cursomc.service.DbService;
import com.levimartines.cursomc.service.EmailService;
import com.levimartines.cursomc.service.MockEmailService;
import com.levimartines.cursomc.service.SmtpEmailService;
import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevConfig {

    @Autowired
    private DbService dbService;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;

    @Bean
    public boolean instantiateDatabase() throws ParseException {
        if (!strategy.equals("create")) {
            return false;
        }
        dbService.instantiateTestDatabase();
        return true;
    }

    @Bean
    public EmailService emailService() {
        return new SmtpEmailService();
    }
}
