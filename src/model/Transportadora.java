package model;

import java.util.List;

public class Transportadora {
    private int id;
    private String nome;
    private String cnpj;
    private String email;
    private String telefone;
    private int prazoEntrega;
    private double custoFrete;
    private String endereco; 
    private List<Produto> produtos;


    public Transportadora(int id, String nome, String cnpj, String email, String telefone, int prazoEntrega, double custoFrete,
            String endereco, List<Produto> produtos) {
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.email = email;
        this.telefone = telefone;
        this.prazoEntrega = prazoEntrega;
        this.custoFrete = custoFrete;
        this.endereco = endereco;
        this.produtos = produtos;
    }

    public String getEmail() {
        return email;
    }

    public String getEndereco() {
        return endereco;
    }

    public int getId() { 
        return id; 
    }
    public void setId(int id) { 
        this.id = id; 
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

    public List<Produto> getProdutos() { 
        return produtos; 
    }

    public void setProdutos(List<Produto> produtos) { 
        this.produtos = produtos; 
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}