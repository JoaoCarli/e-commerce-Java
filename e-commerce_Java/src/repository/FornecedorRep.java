package repository;

import java.util.ArrayList;
import java.util.List;

import model.Fornecedor;

public class FornecedorRep {
    private List<Fornecedor> fornecedores = new ArrayList<>();

    public void salvarFornecedor(Fornecedor f) {
        fornecedores.add(f);
    }

    public List<Fornecedor> listarFornecedor() {
        return fornecedores;
    }

}
