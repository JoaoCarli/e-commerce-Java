package service;

import java.util.List;

import model.Fornecedor;
import model.Produto;
import repository.FornecedorRep;

public class FornecedorService {
    private FornecedorRep fornecedores;

    public void cadastrarFornecedor(String nome, String cnpj, String email, String telefone, String endereco,
            List<Produto> produtos) {
        Fornecedor newFornecedor = new Fornecedor(nome, cnpj, email, telefone, endereco, produtos);
        fornecedores.salvarFornecedor(newFornecedor);
    }
}
