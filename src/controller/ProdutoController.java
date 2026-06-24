package controller;

import exception.EstoqueInsuficienteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Cliente;
import model.Fornecedor;
import model.ItemPedido;
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
            System.out.println("\n--- SUBMENU PRODUTOS (ADMIN) ---");
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

    public void menuConsultaCarrinho(Cliente cliente) {
        System.out.println("\n=== CONSULTA DE PRODUTOS E REALIZAÇÃO DE PEDIDOS ===");
        System.out.print("Digite o código ou parte do nome/descrição para pesquisar: ");
        String busca = sc.nextLine().trim().toLowerCase();

        List<Produto> resultados = new ArrayList<>();
        
        for (Produto p : prodService.listarProdutos()) {
            String idStr = String.valueOf(p.getId());
            if (idStr.equals(busca) || p.getNome().toLowerCase().contains(busca)) {
                resultados.add(p);
            }
        }

        if (resultados.isEmpty()) {
            System.out.println("Nenhum produto encontrado para essa pesquisa.");
            return;
        }

        System.out.println("\n--- RESULTADOS ENCONTRADOS ---");
        for (int i = 0; i < resultados.size(); i++) {
            Produto p = resultados.get(i);
            String statusEstoque = (p.getEstoque() == 0) ? "[INDISPONÍVEL - ESTOQUE ZERO]" : "(Estoque: " + p.getEstoque() + ")";
            System.out.println("[" + (i + 1) + "] ID: " + p.getId() + " - " + p.getNome() 
                               + " | Preço: R$ " + p.getPreco() + " " + statusEstoque);
        }

        try {
            System.out.print("\nDigite o número da posição (ou ID do produto) para adicionar ao carrinho: ");
            int opcaoSelecao = Integer.parseInt(sc.nextLine());
            Produto produtoSelecionado;

            if (opcaoSelecao >= 1 && opcaoSelecao <= resultados.size()) {
                produtoSelecionado = resultados.get(opcaoSelecao - 1);
            } else {
                produtoSelecionado = prodService.buscarProduto(opcaoSelecao);
            }

            if (produtoSelecionado == null) {
                System.out.println("Seleção inválida.");
                return;
            }

            if (produtoSelecionado.getEstoque() == 0) {
                System.out.println("Erro: Não é possível vender um item indisponível.");
                return;
            }

            System.out.print("Informe a quantidade que deseja comprar: ");
            int qtd = Integer.parseInt(sc.nextLine());

            if (qtd <= 0) {
                System.out.println("Quantidade deve ser maior que zero.");
                return;
            }

            if (qtd > produtoSelecionado.getEstoque()) {
                throw new EstoqueInsuficienteException("Quantidade insuficiente de produtos para efetuar um pedido! Máximo disponível: " + produtoSelecionado.getEstoque());
            }

            double totalItem = produtoSelecionado.getPreco() * qtd;
            System.out.printf("Total do Item: R$ %.2f%n", totalItem);
            System.out.print("Confirmar adição ao carrinho? (s/n): ");
            
            if (sc.nextLine().equalsIgnoreCase("s")) {
                ItemPedido novoItem = new ItemPedido(produtoSelecionado, qtd);
                cliente.getCarrinho().add(novoItem);
                System.out.println("Produto adicionado ao seu carrinho com sucesso!");
            } else {
                System.out.println("Operação cancelada.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite apenas números válidos.");
        } catch (EstoqueInsuficienteException e) {
            System.out.println("\n[AVISO DE ESTOQUE] " + e.getMessage());
        }
    }

    private void alterarProduto() {
        listarProdutos();
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
            System.out.println("Produto updated!");
        } else {
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
                        + " | Preço: " + p.getPreco() + " | Estoque: " + p.getEstoque());
            } else
                System.out.println("Não encontrado.");
        } catch (NumberFormatException e) {
            System.out.println("Funcionalidade de busca por nome pode ser implementada com Filter.");
        }
    }

    public void listarProdutos() {
        System.out.println("\n--- LISTA DE PRODUTOS ---");
        for (Produto p : prodService.listarProdutos()) {
            String status = (p.getEstoque() == 0) ? "[INDISPONÍVEL]" : "(Estoque: " + p.getEstoque() + ")";
            System.out.println(p.getId() + " - " + p.getNome()
                    + " | Fornecedor: " + p.getFornecedor().getNome()
                    + " | Preço: R$ " + p.getPreco() + " " + status);
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