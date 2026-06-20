package repository;

import java.util.ArrayList;
import java.util.List;
import model.Fornecedor;

public class FornecedorRep {
    private final List<Fornecedor> fornecedores = new ArrayList<>();

    public void salvarFornecedor(Fornecedor f) {
        fornecedores.add(f);
    }

    public List<Fornecedor> listarFornecedor() {
        return fornecedores;
    }

    public Fornecedor buscarPorId(int id) {
        for (Fornecedor f : fornecedores) {
            if (f.getId() == id) {
                return f;
            }
        }
        return null;
    }

    public boolean removerFornecedor(int id) {
        Fornecedor f = buscarPorId(id);
        if (f != null) {
            fornecedores.remove(f);
            return true;
        }
        return false;
    }

    public boolean atualizarFornecedor(int id, Fornecedor novoFornecedor) {
        Fornecedor f = buscarPorId(id);

        if (f != null) {
            f.setNome(novoFornecedor.getNome());
            f.setCnpj(novoFornecedor.getCnpj());
            f.setEmail(novoFornecedor.getEmail());
            f.setTelefone(novoFornecedor.getTelefone());
            f.setEndereco(novoFornecedor.getEndereco());
            f.setProdutos(novoFornecedor.getProdutos());
            return true;
        }

        return false;
    }
}