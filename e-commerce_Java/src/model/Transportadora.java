package model;

import java.util.List;

public class Transportadora {
    private String nome;
    private String cnpj;
    private String telefone;
    private int prazoEntrega;
    private double custoFrete;
    private String regiaoAtendida;
    private List<Produto> produtos;

    public Transportadora(String nome, String cnpj, String telefone, int prazoEntrega, double custoFrete,
            String regiaoAtendida, List<Produto> produtos) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.prazoEntrega = prazoEntrega;
        this.custoFrete = custoFrete;
        this.regiaoAtendida = regiaoAtendida;
        this.produtos = produtos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getPrazoEntrega() {
        return prazoEntrega;
    }

    public void setPrazoEntrega(int prazoEntrega) {
        this.prazoEntrega = prazoEntrega;
    }

    public double getCustoFrete() {
        return custoFrete;
    }

    public void setCustoFrete(double custoFrete) {
        this.custoFrete = custoFrete;
    }

    public String getRegiaoAtendida() {
        return regiaoAtendida;
    }

    public void setRegiaoAtendida(String regiaoAtendida) {
        this.regiaoAtendida = regiaoAtendida;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

}
