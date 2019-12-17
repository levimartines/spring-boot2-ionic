package com.levimartines.cursomc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.levimartines.cursomc.enums.EstadoPagamento;
import java.util.Date;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@JsonTypeName("pagamentoComBoleto")
public class PagamentoComBoleto extends Pagamento {

    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dataVencimento;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dataPagamento;

    public PagamentoComBoleto(Long id, EstadoPagamento estadoPagamento,
        Pedido pedido, Date dataVencimento, Date dataPagamento) {
        super(id, estadoPagamento, pedido);
        this.dataVencimento = dataVencimento;
        this.dataPagamento = dataPagamento;
    }
}
