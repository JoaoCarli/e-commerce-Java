package model;

import java.io.Serializable;

public class ItemPedido implements Serializable {
    private static final long serialVersionUID = 1L;

    private Produto produto; // Referência direta ao objeto original
    private int quantidade;
    private double precoUnitarioNoMomento; // Boa prática para registrar o preço pago

    public ItemPedido(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitarioNoMomento = produto.getPreco(); // Guarda o preço atual da compra
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

    // Calcula dinamicamente o valor total deste item multiplicando a quantidade
    public double getSubTotal() {
        return this.produto.getPreco() * this.quantidade;
    }
}