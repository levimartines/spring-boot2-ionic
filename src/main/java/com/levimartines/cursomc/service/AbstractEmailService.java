package com.levimartines.cursomc.service;

import com.levimartines.cursomc.model.Cliente;
import com.levimartines.cursomc.model.Pedido;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

public abstract class AbstractEmailService implements EmailService {

    @Value("${default.sender}")
    private String sender;

    @Override
    public void sendOrderConfirmationEmail(Pedido obj) {
        SimpleMailMessage sm = prepareSimpleMailMessage(obj);
        sendEmail(sm);
    }

    protected SimpleMailMessage prepareSimpleMailMessage(Pedido obj) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(obj.getCliente().getEmail());
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setSubject("Pedido confirmado: " + obj.getId());
        simpleMailMessage.setSentDate(new Date(System.currentTimeMillis()));
        simpleMailMessage.setText(obj.toString());
        return simpleMailMessage;
    }

    @Override
    public void sendNewPasswordEmail(Cliente cliente, String newPass) {
        sendEmail(prepareNewPasswordEmail(cliente, newPass));
    }

    protected SimpleMailMessage prepareNewPasswordEmail(Cliente cli, String pass) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(cli.getEmail());
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setSubject("Solicitação de nova senha");
        simpleMailMessage.setSentDate(new Date(System.currentTimeMillis()));
        simpleMailMessage.setText("Nova senha: " + pass);
        return simpleMailMessage;
    }

}
