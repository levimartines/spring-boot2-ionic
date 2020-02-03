package com.levimartines.cursomc.service;

import com.levimartines.cursomc.model.Pedido;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    void sendOrderConfirmationEmail(Pedido obj);
    void sendEmail(SimpleMailMessage msg);
}