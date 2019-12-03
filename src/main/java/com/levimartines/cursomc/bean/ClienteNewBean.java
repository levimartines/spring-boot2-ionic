package com.levimartines.cursomc.bean;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteNewBean implements Serializable {

    private Long id;
    private String nome;
    private String email;
    private String cpfOuCnpj;
    private Long tipo;

    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cep;

    private String telefone1;
    private String telefone2;
    private String telefone3;

    private Long cidadeId;

}
