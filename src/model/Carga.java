package model;

import java.util.ArrayList;
import java.util.List;

public class Carga {
    private int id;
    private Transportadora transportadora;
    private List<Produto> produtos;
    private String destino;
    private String status; // Ex: "Em trânsito", "Entregue", "Pendente"

    public Carga(int id, Transportadora transportadora, List<Produto> produtos, String destino, String status) {
        this.id = id;
        this.transportadora = transportadora;
        this.produtos = produtos == null ? new ArrayList<>() : new ArrayList<>(produtos);
        this.destino = destino;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Transportadora getTransportadora() {
        return transportadora;
    }

    public void setTransportadora(Transportadora transportadora) {
        this.transportadora = transportadora;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos == null ? new ArrayList<>() : new ArrayList<>(produtos);
    }

    public void adicionarProduto(Produto produto) {
        if (produto != null && !produtos.contains(produto)) {
            produtos.add(produto);
        }
    }

    public void removerProduto(Produto produto) {
        produtos.remove(produto);
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
