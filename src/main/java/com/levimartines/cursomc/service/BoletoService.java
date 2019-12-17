package com.levimartines.cursomc.service;

import com.levimartines.cursomc.model.Pedido;
import java.util.Calendar;
import java.util.Date;
import org.springframework.stereotype.Service;

@Service
public class BoletoService {

    public void preencherPagamento(Pedido obj, Date instante) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(instante);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        obj.setInstante(calendar.getTime());
    }
}
