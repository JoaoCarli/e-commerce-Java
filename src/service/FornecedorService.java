package service;

import java.util.List;
import model.Fornecedor;
import model.Produto;
import repository.FornecedorRep;

public class FornecedorService extends EmpresaService{
    private final FornecedorRep fornecedores;
    private int contadorId = 1;

    public FornecedorService() {
        this.fornecedores = new FornecedorRep();
    }

    @Override
    protected boolean cnpjJaExiste(String cnpj) {
        String cnpjLimpo = limparCnpj(cnpj);

        for (Fornecedor f : fornecedores.listarFornecedor()) {
            if (limparCnpj(f.getCnpj()).equals(cnpjLimpo)) {
                return true;
            }
        }

        return false;
    }

    public void cadastrarFornecedor(String nome, String cnpj, String email, String telefone, String endereco, List<Produto> produtos) {
        if (!verificaCadastro(nome, cnpj, email, telefone, endereco)){
            return;
        }

        Fornecedor newFornecedor = new Fornecedor(produtos, cnpj, email, endereco, contadorId++, nome, telefone);
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
        Fornecedor f = buscarFornecedor(id);
        if (f != null && !f.getProdutos().isEmpty()) {
            System.out.println("Erro: fornecedor possui produtos cadastrados.");
            return false;
        }
        return fornecedores.removerFornecedor(id);
    }

    public boolean atualizarFornecedor(int id, String novoNome, String cnpj, String email, String telefone, String endereco, List<Produto> produtos) {

        Fornecedor novoFornecedor = new Fornecedor(produtos, cnpj, email, endereco, id, novoNome, telefone);
        return fornecedores.atualizarFornecedor(id, novoFornecedor);
    }

    public void adicionarProduto(Produto produto, int fId) {
        Fornecedor f = buscarFornecedor(fId);
        if (f != null && produto != null && !f.getProdutos().contains(produto)) {
            f.getProdutos().add(produto);
        }
    }

    public void removerProduto(Produto produto, int fId) {
        Fornecedor f = buscarFornecedor(fId);
        if (f != null && produto != null && f.getProdutos().contains(produto)) {
            f.getProdutos().remove(produto);
        }
    }
}