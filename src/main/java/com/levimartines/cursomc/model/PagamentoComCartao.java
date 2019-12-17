package com.levimartines.cursomc.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.levimartines.cursomc.enums.EstadoPagamento;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@JsonTypeName("pagamentoComCartao")
public class PagamentoComCartao extends Pagamento {

    private Long numeroDeParcelas;

    public PagamentoComCartao(Long id, EstadoPagamento estadoPagamento,
        Pedido pedido, Long numeroDeParcelas) {
        super(id, estadoPagamento, pedido);
        this.numeroDeParcelas = numeroDeParcelas;
    }
}
