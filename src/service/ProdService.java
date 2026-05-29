package service;

import java.util.ArrayList;
import java.util.List;
import model.Fornecedor;
import model.Produto;
import repository.ProdRep;

public class ProdService {
    private final ProdRep produtos;
    private final FornecedorService fornecedorService;
    private int contadorId = 1;

    public ProdService(FornecedorService fornecedorService) {
        this.produtos = new ProdRep();
        this.fornecedorService = new FornecedorService();
    }

    public Produto cadastrarProduto(Fornecedor fornecedor, String nome, double preco, int estoque) {
        if (fornecedor == null) {
            System.out.println("Erro! Fornecedor não informado.");
            return null;
        }

        if (nome == null || nome.isBlank()) {
            System.out.println("Erro! Nome do produto não pode ser vazio.");
            return null;
        }

        if (preco < 0) {
            System.out.println("Erro! Preço não pode ser negativo.");
            return null;
        }

        if (estoque < 0) {
            System.out.println("Erro! Estoque não pode ser negativo.");
            return null;
        }

        Produto newProduto = new Produto(contadorId++, fornecedor, nome, preco, estoque);
        produtos.salvarProd(newProduto);

        fornecedorService.adicionarProduto(newProduto, fornecedor.getId());

        return newProduto;
    }

    public List<Produto> listarProdutos() {
        return produtos.listarProd();
    }

    public Produto buscarProduto(int id) {
        return produtos.buscarPorId(id);
    }

    public List<Produto> buscarProdutosPorIds(int... ids) {
        List<Produto> listaProdutos = new ArrayList<>();

        for (int id : ids) {
            Produto produto = buscarProduto(id);

            if (produto != null) {
                listaProdutos.add(produto);
            }
        }

        return listaProdutos;
    }

    public boolean removerProduto(int id) {
        Produto produto = produtos.buscarPorId(id);

        if (produto == null) {
            return false;
        }

        boolean removido = produtos.removerProd(id);

        if (removido && produto.getFornecedor() != null) {
            fornecedorService.removerProduto(produto, produto.getFornecedor().getId());
        }

        return removido;
    }

    public boolean atualizarProduto(int id, Fornecedor fornecedor, String nome, double preco, int estoque) {

        Produto produtoAtual = produtos.buscarPorId(id);

        if (produtoAtual == null || fornecedor == null) {
            return false;
        }

        Fornecedor fornecedorAntigo = produtoAtual.getFornecedor();
        Produto novoProduto = new Produto(id, fornecedor, nome, preco, estoque);
        boolean atualizado = produtos.atualizarProd(id, novoProduto);

        if (atualizado && fornecedorAntigo != fornecedor) {
            if (fornecedorAntigo != null) {
                fornecedorService.removerProduto(produtoAtual, fornecedorAntigo.getId());
            }
            fornecedorService.adicionarProduto(produtoAtual, fornecedor.getId());
        }

        return atualizado;
    }
}
