package com.levimartines.cursomc.model;

import com.levimartines.cursomc.enums.EstadoPagamento;
import javax.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class PagamentoComCartao extends Pagamento {

    private Long numeroDeParcelas;

    public PagamentoComCartao(Long id, EstadoPagamento estadoPagamento,
        Pedido pedido, Long numeroDeParcelas) {
        super(id, estadoPagamento, pedido);
        this.numeroDeParcelas = numeroDeParcelas;
    }
}
