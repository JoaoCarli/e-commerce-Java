package controller;

import java.util.Scanner;
import model.Transportadora;
import service.TransportadoraService;

public class TransportadoraController {
    private final Scanner sc;
    private final TransportadoraService transService;

    public TransportadoraController(Scanner sc, TransportadoraService transService) {
        this.sc = sc;
        this.transService = transService;
    }

    public void menuTransportadoras() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- SUBMENU TRANSPORTADORAS ---");
            System.out.println("1-Incluir 2-Alterar 3-Excluir 4-Consultar 5-Listar 0-Voltar");
            System.out.print("Escolha: ");
            try {
                op = Integer.parseInt(sc.nextLine());
                switch (op) {
                    case 1 -> cadastrarTransportadora();
                    case 2 -> alterarTransportadora();
                    case 3 -> removerTransportadora();
                    case 4 -> consultarTransportadora();
                    case 5 -> listarTransportadoras();
                    case 0 -> System.out.println("Voltando...");
                }
            } catch (Exception e) {
                System.out.println("Erro na entrada.");
            }
        }
    }

    private void cadastrarTransportadora() {
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

        transService.cadastrarTransportadora(nome, cnpj, email, tel, end);
    }

    private void listarTransportadoras() {
        System.out.println("\n--- TRANSPORTADORAS CADASTRADAS ---");
        for (Transportadora t : transService.listarTransportadoras()) {
            System.out.println(t.getId() + " - " + t.getNome() + " (" + t.getEmail() + ")");
        }
    }

    private void removerTransportadora() {
        System.out.print("ID para remover: ");
        int id = Integer.parseInt(sc.nextLine());
        if (transService.removerTransportadora(id))
            System.out.println("Removida.");
        else
            System.out.println("ID não encontrado.");
    }

    private void alterarTransportadora() {
        System.out.print("ID da Transportadora: ");
        int id = Integer.parseInt(sc.nextLine());
        Transportadora t = transService.buscarTransportadora(id);
        if (t != null) {
            System.out.print("Novo Nome (" + t.getNome() + "): ");
            String nome = sc.nextLine();
            if (nome.isEmpty())
                nome = t.getNome();

            transService.atualizarTransportadora(id, nome, t.getCnpj(), t.getEmail(), t.getTelefone(), t.getEndereco());
            System.out.println("Dados atualizados!");
        } else
            System.out.println("Não encontrada.");
    }

    private void consultarTransportadora() {
        System.out.print("ID: ");
        int id = Integer.parseInt(sc.nextLine());
        Transportadora t = transService.buscarTransportadora(id);
        if (t != null) {
            System.out.println("Nome: " + t.getNome() + " | CNPJ: " + t.getCnpj() + " | Endereço: " + t.getEndereco());
        } else
            System.out.println("Não encontrada.");
    }
}
