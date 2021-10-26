package com.levimartines.cursomc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.levimartines.cursomc.enums.Perfil;
import com.levimartines.cursomc.enums.TipoCliente;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Column(unique = true)
    private String email;
    private String cpfOuCnpj;
    private Long tipo;

    @JsonIgnore
    private String senha;
    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private final List<Pedido> pedidos = new ArrayList<>();
    @OneToMany(mappedBy = "cliente")
    @Cascade(CascadeType.ALL)
    private final List<Endereco> enderecos = new ArrayList<>();
    @ElementCollection
    @CollectionTable(name = "telefones")
    private final Set<String> telefones = new HashSet<>();
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "perfis")
    private final Set<Long> perfis = new HashSet<>();

    public Cliente(Long id) {
        this.id = id;
    }

    public Cliente(Long id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        addPerfil(Perfil.CLIENTE);
    }

    public Cliente(Long id, String nome, String email, String cpfOuCnpj, TipoCliente tipo,
                   String senha) {
        super();
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpfOuCnpj = cpfOuCnpj;
        this.tipo = tipo == null ? null : tipo.getCod();
        this.senha = senha;
        addPerfil(Perfil.CLIENTE);
    }

    public Set<Perfil> getPerfisId() {
        return perfis.stream().map(Perfil::toEnum).collect(Collectors.toSet());
    }

    public void addPerfil(Perfil perfil) {
        perfis.add(perfil.getCod());
    }

    public TipoCliente getTipo() {
        return TipoCliente.toEnum(tipo);
    }

    public void setTipo(TipoCliente tipo) {
        this.tipo = tipo.getCod();
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
