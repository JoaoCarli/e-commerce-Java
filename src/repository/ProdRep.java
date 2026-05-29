package repository;

import java.util.ArrayList;
import java.util.List;
import model.Produto;

public class ProdRep {
    private final List<Produto> produtos = new ArrayList<>();

    public void salvarProd(Produto produto) {
        produtos.add(produto);
    }

    public List<Produto> listarProd() {
        return produtos;
    }

    public Produto buscarPorId(int id) {
        for (Produto p : produtos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public boolean removerProd(int id) {
        Produto p = buscarPorId(id);
        if (p != null) {
            produtos.remove(p);
            return true;
        }
        return false;
    }

    public boolean atualizarProd(int id, Produto novoProduto) {
        Produto p = buscarPorId(id);

        if (p != null) {
            p.setFornecedor(novoProduto.getFornecedor());
            p.setNome(novoProduto.getNome());
            p.setPreco(novoProduto.getPreco());
            p.setEstoque(novoProduto.getEstoque());
            return true;
        }

        return false;
    }
}