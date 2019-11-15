package com.levimartines.cursomc.enums;

import lombok.Getter;

@Getter
public enum TipoCliente {

    PESSOAFISICA(1, "Pessoa Física"),
    PESSOAJURIDICA(2, "Pessoa Jurídica");

    private Integer cod;
    private String descricao;

    private TipoCliente(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public static TipoCliente getTipoClienteFromCod(Integer cod){
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
