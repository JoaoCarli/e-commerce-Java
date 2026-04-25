package repository;

import java.util.ArrayList;
import java.util.List;
import model.Produto;

public class ProdRep {
    private List<Produto> produtos = new ArrayList<>();

    public void salvarProd(Produto produto) {
        produtos.add(produto);
    }

    public List<Produto> listarProd() {
        return produtos;
    }

}
