package com.levimartines.cursomc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.levimartines.cursomc.enums.TipoCliente;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String cpfOuCnpj;
    private Long tipo;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private final List<Pedido> pedidos = new ArrayList<>();

    @OneToMany(mappedBy = "cliente")

    private final List<Endereco> enderecos = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "telefones")
    private final Set<String> telefones = new HashSet<>();

    public Cliente(Long id, String nome, String email, String cpfOuCnpj, TipoCliente tipo) {
        super();
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpfOuCnpj = cpfOuCnpj;
        this.tipo = tipo.getCod();
    }

    public TipoCliente getTipoCliente() {
        return TipoCliente.getTipoClienteFromCod(tipo);
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipo = tipoCliente.getCod();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Cliente cliente = (Cliente) o;

        return id != null ? id.equals(cliente.id) : cliente.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
