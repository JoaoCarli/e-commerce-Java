package model;

import java.io.Serializable;

public class ItemPedido implements Serializable {
    private static final long serialVersionUID = 1L;

    private Produto produto;
    private int quantidade;
    private final double precoUnitarioNoMomento;

    public ItemPedido(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitarioNoMomento = produto.getPreco();
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoUnitarioNoMomento() {
        return precoUnitarioNoMomento;
    }

    public double getSubTotal() {
        return this.produto.getPreco() * this.quantidade;
    }

}