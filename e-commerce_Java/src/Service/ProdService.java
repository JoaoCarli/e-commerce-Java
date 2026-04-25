package service;

import model.Fornecedor;
import model.Produto;
import repository.ProdRep;

public class ProdService {
    private ProdRep produtos;

    public void cadastrarProduto(Fornecedor fornecedor, String nome, double preco, String descricao, int estoque) {
        Produto newProduto = new Produto(fornecedor, nome, preco, descricao, estoque);
        produtos.salvarProd(newProduto);
    }
}
