package service;

import java.util.List;
import model.Fornecedor;
import model.Produto;
import repository.FornecedorRep;

public class FornecedorService {
    private final FornecedorRep fornecedores;
    private int contadorId = 1;

    public FornecedorService() {
        this.fornecedores = new FornecedorRep();
    }

    public void cadastrarFornecedor(String nome, String cnpj, String email, String telefone, String endereco, List<Produto> produtos) {

        if (nome.isBlank()) {
            System.out.println("Erro! Nome não pode ser vazio.");
            return;
        }

        if (!cnpjValido(cnpj)) {
            System.out.println("Erro! CNPJ inválido.");
            return;
        }

        if (cnpjJaExiste(cnpj)) {
            System.out.println("Erro! Já existe um fornecedor com esse CNPJ.");
            return;
        }

        if (email.isBlank()) {
            System.out.println("Erro! Email não pode ser vazio.");
            return;
        }

        if (!email.contains("@")) {
            System.out.println("Erro! Email inválido.");
            return;
        }

        if (telefone.isBlank()) {
            System.out.println("Erro! Telefone não pode ser vazio.");
            return;
        }

        if (!telefone.matches("[0-9]+")) {
            System.out.println("Erro! Telefone deve conter apenas números.");
            return;
        }

        if (endereco.isBlank()) {
            System.out.println("Erro! Endereço não pode ser vazio.");
            return;
        }

        Fornecedor newFornecedor = new Fornecedor(contadorId++, nome, cnpj, email, telefone, endereco, produtos);
        fornecedores.salvarFornecedor(newFornecedor);

        System.out.println("Fornecedor cadastrado com sucesso!");
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

    public boolean atualizarFornecedor(int id, String novoNome, String cnpj, String email, String telefone, String endereco, List<Produto> produtos) {

        Fornecedor novoFornecedor = new Fornecedor(id, novoNome, cnpj, email, telefone, endereco, produtos);
        return fornecedores.atualizarFornecedor(id, novoFornecedor);
    }

    private String limparCnpj(String cnpj) {
        if (cnpj == null) {
            return "";
        }

        return cnpj.replaceAll("[^0-9]", "");
    }

    public boolean cnpjValido(String cnpj) {
        String numeros = limparCnpj(cnpj);

        if (numeros.length() != 14) {
            return false;
        }

        return !numeros.matches("(\\d)\\1{13}");
    }

    public boolean cnpjJaExiste(String cnpj) {
        String cnpjLimpo = limparCnpj(cnpj);

        for (Fornecedor f : fornecedores.listarFornecedor()) {
            if (limparCnpj(f.getCnpj()).equals(cnpjLimpo)) {
                return true;
            }
        }

        return false;
    }
}