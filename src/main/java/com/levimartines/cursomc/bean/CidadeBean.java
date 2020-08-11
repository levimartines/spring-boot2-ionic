package com.levimartines.cursomc.bean;

import com.levimartines.cursomc.model.Cidade;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CidadeBean {

    private Long id;
    private String nome;

    public CidadeBean(Cidade cidade) {
        this.id = cidade.getId();
        this.nome = cidade.getNome();
    }
}
