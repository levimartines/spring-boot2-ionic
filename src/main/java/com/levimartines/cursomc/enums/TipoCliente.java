package com.levimartines.cursomc.enums;

import lombok.Getter;

@Getter
public enum TipoCliente {

    PESSOAFISICA(1L, "Pessoa Física"),
    PESSOAJURIDICA(2L, "Pessoa Jurídica");

    private Long cod;
    private String descricao;

    private TipoCliente(Long cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public static TipoCliente getTipoClienteFromCod(Long cod){
        if (cod == null){
            return null;
        }
        for(TipoCliente tipo : TipoCliente.values()){
            if(cod.equals(tipo.cod)){
                return tipo;
            }
        }
        throw new IllegalArgumentException("Id inválido: " + cod);
    }

}
