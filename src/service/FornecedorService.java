package service;

import java.util.List;

import model.Fornecedor;
import model.Produto;
import repository.FornecedorRep;

public class FornecedorService {
    private FornecedorRep fornecedores;
    private int contadorId = 1;

    public FornecedorService() {
        this.fornecedores = new FornecedorRep();
    }

    public void cadastrarFornecedor(String nome, String cnpj, String email, String telefone, String endereco,
            List<Produto> produtos) {
        Fornecedor newFornecedor = new Fornecedor(contadorId++, nome, cnpj, email, telefone, endereco, produtos);
        fornecedores.salvarFornecedor(newFornecedor);
    }

    public List<Fornecedor> listarFornecedores() {
        return fornecedores.listarFornecedor();
    }

    public Fornecedor buscarFornecedor(int id) {
        return fornecedores.buscarPorId(id);
    }

    public boolean removerFornecedor(int id) {
        return fornecedores.removerFornecedor(id);
    }

    public boolean atualizarFornecedor(int id, String novoNome, String cnpj, String email,
            String telefone, String endereco, List<Produto> produtos) {

        Fornecedor novoFornecedor = new Fornecedor(id, novoNome, cnpj, email, telefone, endereco, produtos);
        return fornecedores.atualizarFornecedor(id, novoFornecedor);
    }
}