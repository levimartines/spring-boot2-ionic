package com.levimartines.cursomc.enums;

import lombok.Getter;

@Getter
public enum EstadoPagamento {

    PENDENTE(1L, "Pendente"),
    QUITADO(2L, "Quitado"),
    CANCELADO(3L, "Cancelado");

    private Long cod;
    private String descricao;

    private EstadoPagamento(Long cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public static EstadoPagamento getEstadoPagamentoFromCod(Long cod){
        if (cod == null){
            return null;
        }
        for(EstadoPagamento tipo : EstadoPagamento.values()){
            if(cod.equals(tipo.cod)){
                return tipo;
            }
        }
        throw new IllegalArgumentException("Id inválido: " + cod);
    }

}
