package com.levimartines.cursomc.beans;

import com.levimartines.cursomc.model.Categoria;
import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class CategoriaBean implements Serializable {

    private Long id;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Length(min = 5, max = 80, message = "O tamanho deve ser entre 5 e 80 catacteres")
    private String nome;

    public CategoriaBean (Categoria obj){
        this.id = obj.getId();
        this.nome = obj.getNome();
    }
}
