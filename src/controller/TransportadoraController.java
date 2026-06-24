package controller;

import java.util.Scanner;
import model.Transportadora;
import service.CargaService;
import service.TransportadoraService;

public class TransportadoraController {
    private final Scanner sc;
    private final TransportadoraService transService;
    private final CargaService cargaService;

    public TransportadoraController(Scanner sc, TransportadoraService transService, CargaService cargaService) {
        this.sc = sc;
        this.transService = transService;
        this.cargaService = cargaService;
    }

    public void menuTransportadoras() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- SUBMENU TRANSPORTADORAS ---");
            System.out.println("1-Incluir \n2-Alterar \n3-Excluir \n4-Consultar \n5-Listar \n0-Voltar");
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
            } catch (NumberFormatException e) {
                System.out.println("Erro na entrada: digite um número válido.");
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
            System.out.println(
                t.getId() + " - " + t.getNome() +
                "\n   CNPJ: " + t.getCnpj() +
                "\n   Email: " + t.getEmail() +
                "\n   Telefone: " + t.getTelefone() +
                "\n   Endereço: " + t.getEndereco() +
                "\n   Cargas: " + cargaService.listarCargas().stream()
                    .filter(c -> c.getTransportadora() != null && c.getTransportadora().getId() == t.getId())
                    .count() + " carga(s)"
            );
            System.out.println("-----------------------------------");
        }
    }

    private void removerTransportadora() {
        try {
            System.out.print("ID para remover: ");
            int id = Integer.parseInt(sc.nextLine());
            if (transService.removerTransportadora(id))
                System.out.println("Removida.");
            else
                System.out.println("ID não encontrado.");
        } catch (NumberFormatException e) {
            System.out.println("Erro: digite um ID numérico válido.");
        }
    }

    private void alterarTransportadora() {
        try {
            listarTransportadoras();
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
        } catch (NumberFormatException e) {
            System.out.println("Erro: digite um ID numérico válido.");
        }
    }

    private void consultarTransportadora() {
        try {
            System.out.print("ID: ");
            int id = Integer.parseInt(sc.nextLine());
            Transportadora t = transService.buscarTransportadora(id);
            if (t != null) {
                System.out.println("Nome: " + t.getNome() + " | CNPJ: " + t.getCnpj() + " | Endereço: " + t.getEndereco());
            } else
                System.out.println("Não encontrada.");
        } catch (NumberFormatException e) {
            System.out.println("Erro: digite um ID numérico válido.");
        }
    }

}