package controller;

import java.util.ArrayList;
import java.util.Scanner;
import model.Fornecedor;
import service.FornecedorService;

public class FornecedorController {
    private final Scanner sc;
    private final FornecedorService fornecedorService;

    public FornecedorController(Scanner sc, FornecedorService fornecedorService) {
        this.sc = sc;
        this.fornecedorService = fornecedorService;
    }

    public void menuFornecedores() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- SUBMENU FORNECEDORES ---");
            System.out.println("1-Incluir 2-Alterar 3-Excluir 4-Consultar 5-Listar 0-Voltar");
            op = Integer.parseInt(sc.nextLine());
            switch (op) {
                case 1 -> cadastrarFornecedor();
                case 2 -> alterarFornecedor();
                case 3 -> removerFornecedor();
                case 4 -> consultarFornecedor();
                case 5 -> listarFornecedores();
            }
        }
    }

    private void cadastrarFornecedor() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("CNPJ: ");
        String cnpj = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Telefone: ");
        String tel = sc.nextLine();

        System.out.print("Endereço: ");
        String end = sc.nextLine();

        fornecedorService.cadastrarFornecedor(nome, cnpj, email, tel, end, new ArrayList<>());
    }

    private void alterarFornecedor() {
        System.out.print("ID do Fornecedor: ");
        int id = Integer.parseInt(sc.nextLine());

        Fornecedor f = fornecedorService.buscarFornecedor(id);

        if (f != null) {
            System.out.print("Novo Nome (" + f.getNome() + "): ");
            String nome = sc.nextLine();

            if (nome.isEmpty()) {
                nome = f.getNome();
            }

            boolean atualizado = fornecedorService.atualizarFornecedor(
                    id,
                    nome,
                    f.getCnpj(),
                    f.getEmail(),
                    f.getTelefone(),
                    f.getEndereco(),
                    f.getProdutos()
            );

            if (atualizado) {
                System.out.println("Atualizado!");
            } else {
                System.out.println("Erro ao atualizar fornecedor.");
            }
        } else {
            System.out.println("Não encontrado.");
        }
    }

    private void removerFornecedor() {
        listarFornecedores();
        System.out.print("ID para remover: ");
        int id = Integer.parseInt(sc.nextLine());
        if (fornecedorService.removerFornecedor(id))
            System.out.println("Removido.");
        else
            System.out.println("Erro ao remover.");
    }

    private void consultarFornecedor() {
        System.out.print("ID: ");
        int id = Integer.parseInt(sc.nextLine());
        Fornecedor f = fornecedorService.buscarFornecedor(id);
        if (f != null)
            System.out.println("Nome: " + f.getNome() + " | CNPJ: " + f.getCnpj());
        else
            System.out.println("Não encontrado.");
    }

    private void listarFornecedores() {
        System.out.println("\n--- LISTA DE FORNECEDORES ---");

        for (Fornecedor f : fornecedorService.listarFornecedores()) {
            System.out.println(f.getId() + " - " + f.getNome() + " | Produtos: " + f.getProdutos().size());
        }
    }

}
