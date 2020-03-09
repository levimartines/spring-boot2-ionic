package com.levimartines.cursomc.enums;

import lombok.Getter;

@Getter
public enum Perfil {

    ADMIN(1L, "ROLE_ADMIN"),
    CLIENTE(2L, "ROLE_CLIENTE");

    private Long cod;
    private String descricao;

    private Perfil(Long cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public static Perfil toEnum(Long cod) {

        if (cod == null) {
            return null;
        }

        for (Perfil x : Perfil.values()) {
            if (cod.equals(x.getCod())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Id inválido: " + cod);
    }

}
