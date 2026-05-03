package service;

import java.util.List;

import model.Fornecedor;
import model.Produto;
import repository.ProdRep;

public class ProdService {
    private ProdRep produtos;
    private int contadorId = 1;

    public ProdService() {
        this.produtos = new ProdRep();
    }

    public void cadastrarProduto(Fornecedor fornecedor, String nome, double preco, String descricao, int estoque) {
        Produto newProduto = new Produto(contadorId++, fornecedor, nome, preco, descricao, estoque);
        produtos.salvarProd(newProduto);
    }

    public List<Produto> listarProdutos() {
        return produtos.listarProd();
    }

    public Produto buscarProduto(int id) {
        return produtos.buscarPorId(id);
    }

    public boolean removerProduto(int id) {
        return produtos.removerProd(id);
    }

    public boolean atualizarProduto(int id, Fornecedor fornecedor, String nome,
            double preco, String descricao, int estoque) {

        Produto novoProduto = new Produto(id, fornecedor, nome, preco, descricao, estoque);
        return produtos.atualizarProd(id, novoProduto);
    }
}