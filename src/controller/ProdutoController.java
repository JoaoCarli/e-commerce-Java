package controller;

import java.util.Scanner;
import model.Fornecedor;
import model.Produto;
import service.FornecedorService;
import service.ProdService;

public class ProdutoController {
    private final Scanner sc;
    private final ProdService prodService;
    private final FornecedorService fornecedorService = new FornecedorService();

    public ProdutoController(Scanner sc, ProdService prodService) {
        this.sc = sc;
        this.prodService = prodService;
    }

    public void menuProdutos() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- SUBMENU PRODUTOS ---");
            System.out.println("1-Incluir \n2-Alterar \n3-Excluir \n4-Consultar \n5-Listar \n0-Voltar");
            System.out.print("Escolha: ");
            try {
                op = Integer.parseInt(sc.nextLine());
                switch (op) {
                    case 1 -> cadastrarProduto();
                    case 2 -> alterarProduto();
                    case 3 -> removerProduto();
                    case 4 -> consultarProduto();
                    case 5 -> listarProdutos();
                    case 0 -> System.out.println("Voltando...");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro na entrada: formato inválido.");
            }
        }
    }

    private void alterarProduto() {
        System.out.print("ID do Produto: ");
        int id = Integer.parseInt(sc.nextLine());
        Produto p = prodService.buscarProduto(id);
        if (p != null) {
            System.out.print("Novo Nome (" + p.getNome() + "): ");
            String nome = sc.nextLine();
            System.out.print("Novo Preço (" + p.getPreco() + "): ");
            String precoStr = sc.nextLine();

            if (nome.isEmpty()) nome = p.getNome();
            double preco = precoStr.isEmpty() ? p.getPreco() : Double.parseDouble(precoStr);

            System.out.print("Alterar Fornecedor? (s/n): ");
            String alterarForn = sc.nextLine();
            Fornecedor fornecedor = p.getFornecedor();
            if (alterarForn.equalsIgnoreCase("s")) {
                fornecedorService.listarFornecedores().forEach(f ->
                    System.out.println(f.getId() + " - " + f.getNome()));
                System.out.print("ID do Fornecedor: ");
                int idForn = Integer.parseInt(sc.nextLine());
                Fornecedor novoForn = fornecedorService.buscarFornecedor(idForn);
                if (novoForn != null)
                    fornecedor = novoForn;
                else
                    System.out.println("Fornecedor não encontrado, mantendo o atual.");
            }

            prodService.atualizarProduto(id, fornecedor, nome, preco, p.getEstoque());
            System.out.println("Produto atualizado!");
        }else {
            System.out.println("Produto não encontrado.");
        }
    }

    private void consultarProduto() {
        System.out.print("ID ou Nome do Produto: ");
        String busca = sc.nextLine();
        try {
            int id = Integer.parseInt(busca);
            Produto p = prodService.buscarProduto(id);
            if (p != null) {
                System.out.println("Prod: " + p.getNome() + " | Fornecedor: " + p.getFornecedor().getNome()
                        + " | Preço: " + p.getPreco());
            } else
                System.out.println("Não encontrado.");
        } catch (NumberFormatException e) {
            System.out.println("Funcionalidade de busca por nome pode ser implementada com Filter.");
        }
    }

    public void listarProdutos() {
        System.out.println("\n--- LISTA DE PRODUTOS ---");
        for (Produto p : prodService.listarProdutos()) {
            System.out.println(p.getId() + " - " + p.getNome()
                    + " | Fornecedor: " + p.getFornecedor().getNome()
                    + " | Preço: " + p.getPreco());
        }
    }

    private void cadastrarProduto() {
        System.out.println("\n--- CADASTRO DE PRODUTO ---");
        fornecedorService.listarFornecedores();
        System.out.print("ID do Fornecedor: ");
        int idForn = Integer.parseInt(sc.nextLine());

        Fornecedor f = fornecedorService.buscarFornecedor(idForn);
        if (f == null) {
            System.out.println("Fornecedor não encontrado!");
            return;
        }

        System.out.print("Nome do Produto: ");
        String nome = sc.nextLine();
        System.out.print("Preço: ");
        double preco = Double.parseDouble(sc.nextLine());
        System.out.print("Quantidade em Estoque: ");
        int estoque = Integer.parseInt(sc.nextLine());

        Produto produto = prodService.cadastrarProduto(f, nome, preco, estoque);
        if (produto != null) {
            System.out.println("Produto cadastrado com sucesso!");
        }
    }

    private void removerProduto() {
        System.out.print("Digite o ID do produto para remover: ");
        int id = Integer.parseInt(sc.nextLine());
        if (prodService.removerProduto(id)) {
            System.out.println("Produto removido!");
        } else {
            System.out.println("ID não encontrado.");
        }
    }
}
