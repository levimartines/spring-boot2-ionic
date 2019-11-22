package com.levimartines.cursomc.beans;

import com.levimartines.cursomc.model.Categoria;
import java.io.Serializable;
import lombok.Data;

@Data
public class CategoriaBean implements Serializable {
    private Long id;
    private String nome;

    public CategoriaBean (Categoria obj){
        this.id = obj.getId();
        this.nome = obj.getNome();
    }
}
