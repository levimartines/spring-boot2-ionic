package com.levimartines.cursomc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ItemPedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @EmbeddedId
    private ItemPedidoPK id = new ItemPedidoPK();

    private Double desconto;
    private Long quantidade;
    private Double preco;

    public ItemPedido() {
    }

    public ItemPedido(Pedido pedido, Produto produto, Double desconto, Long quantidade,
        Double preco) {
        super();
        id.setPedido(pedido);
        id.setProduto(produto);
        this.desconto = desconto;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public Double getSubtotal(){
        return (preco - desconto) * quantidade;
    }

    @JsonIgnore
    public Pedido getPedido() {
        return id.getPedido();
    }

    public void setProduto(Produto produto){
        id.setProduto(produto);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ItemPedido other = (ItemPedido) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        StringBuilder builder = new StringBuilder();
        builder.append(getId().getProduto().getNome());
        builder.append(", Qte: ");
        builder.append(getQuantidade());
        builder.append(", Preço unitário: ");
        builder.append(nf.format(getPreco()));
        builder.append(", Subtotal: ");
        builder.append(nf.format(getSubtotal()));
        builder.append("\n");
        return builder.toString();
    }
}