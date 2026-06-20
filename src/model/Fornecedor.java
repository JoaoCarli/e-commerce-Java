package model;

import java.util.ArrayList;
import java.util.List;

public class Fornecedor extends Empresa {
    
    private List<Produto> produtos;

    public Fornecedor(List<Produto> produtos, String cnpj, String email, String endereco, int id, String nome, String telefone) {
        super(cnpj, email, endereco, id, nome, telefone);
        this.produtos = produtos == null ? new ArrayList<>() : new ArrayList<>(produtos);
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos == null ? new ArrayList<>() : new ArrayList<>(produtos);
    }
}