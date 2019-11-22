package com.levimartines.cursomc.beans;

import com.levimartines.cursomc.model.Cliente;
import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class ClienteBean implements Serializable {

    private Long id;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Length(min = 5, max = 80, message = "O tamanho deve ser entre 5 e 80 catacteres")
    private String nome;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Email(message = "Email inválido")
    private String email;

    public ClienteBean(Cliente obj){
        id = obj.getId();
        nome = obj.getNome();
        email = obj.getEmail();
    }
}
