package com.levimartines.cursomc.model;

import com.levimartines.cursomc.enums.EstadoPagamento;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
