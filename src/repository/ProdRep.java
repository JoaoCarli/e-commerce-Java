package repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import model.Produto;

public class ProdRep {
    private List<Produto> produtos = new ArrayList<>();
    private final String PATH_ARQUIVO = "produtos.dat";

    public ProdRep() {
        carregarArquivo();
    }

    public void salvarProd(Produto produto) {
        produtos.add(produto);
        salvarArquivo();
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
            salvarArquivo();
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
            salvarArquivo();
            return true;
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    private void carregarArquivo() {
        File arquivo = new File(PATH_ARQUIVO);
        if (!arquivo.exists()) {
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            produtos = (List<Produto>) ois.readObject();
        } catch (Exception e) {
            System.out.println("Erro ao carregar o arquivo de produtos: " + e.getMessage());
        }
    }

    private void salvarArquivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PATH_ARQUIVO))) {
            oos.writeObject(produtos);
        } catch (Exception e) {
            System.out.println("Erro ao salvar o arquivo de produtos: " + e.getMessage());
        }
    }
}