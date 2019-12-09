package com.levimartines.cursomc.bean;

import com.levimartines.cursomc.service.validation.ClienteInsert;
import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ClienteInsert
public class ClienteNewBean implements Serializable {

    private Long id;
    @NotEmpty(message="Preenchimento obrigatório")
    @Length(min=5, max=120, message="O tamanho deve ser entre 5 e 120 caracteres")
    private String nome;
    @NotEmpty(message="Preenchimento obrigatório")
    @Email(message="Email inválido")
    private String email;
    @NotEmpty(message="Preenchimento obrigatório")
    private String cpfOuCnpj;
    private Long tipo;

    @NotEmpty(message="Preenchimento obrigatório")
    private String logradouro;
    @NotEmpty(message="Preenchimento obrigatório")
    private String numero;
    private String complemento;
    private String bairro;
    @NotEmpty(message="Preenchimento obrigatório")
    private String cep;

    private String telefone1;
    private String telefone2;
    private String telefone3;

    private Long cidadeId;

}
