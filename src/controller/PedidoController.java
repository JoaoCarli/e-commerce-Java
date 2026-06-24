package controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Cliente;
import model.Pedido;
import service.PedidoService;

public class PedidoController {
    private final Scanner sc;
    private final PedidoService pedidoService;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public PedidoController(Scanner sc, PedidoService pedidoService) {
        this.sc = sc;
        this.pedidoService = pedidoService;
    }

    public void menuClientePedidos(Cliente cliente) {
        System.out.println("\n--- MEUS PEDIDOS REALIZADOS ---");
        System.out.println("1 - Buscar por Número\n2 - Filtrar por Intervalo de Datas\n3 - Listar Todos os Meus Pedidos");
        System.out.print("Escolha: ");
        try {
            int op = Integer.parseInt(sc.nextLine());
            List<Pedido> resultados = new ArrayList<>();

            switch (op) {
                case 1 -> {
                    System.out.print("Número do Pedido: ");
                    int num = Integer.parseInt(sc.nextLine());
                    Pedido p = pedidoService.buscarPedidoPorNumero(num);
                    if (p != null && p.getCliente().getId() == cliente.getId()) resultados.add(p);
                    break;}

                case 2 -> {
                    System.out.print("Data Inicial (dd/MM/yyyy): ");
                    LocalDate inicio = LocalDate.parse(sc.nextLine(), dtf);

                    System.out.print("Data Final (dd/MM/yyyy): ");
                    LocalDate fim = LocalDate.parse(sc.nextLine(), dtf);

                    for (Pedido ped : pedidoService.buscarPedidosPorIntervaloDatas(inicio, fim)) {
                        if (ped.getCliente().getId() == cliente.getId()) resultados.add(ped);
                    }
                    break;}

                case 3 -> {
                    resultados = pedidoService.listarPedidosPorCliente(cliente);
                    break;}
            }
            exibirTelaMestreDetalhe(resultados);
        } catch (RuntimeException e) {
            System.out.println("Erro na consulta ou formato de data inválido.");
        }
    }

    public void menuAdminPedidos() {
        System.out.println("\n--- GERENCIAMENTO DE PEDIDOS (ADMIN) ---");
        System.out.println("1 - Buscar por Número\n2 - Filtrar por Intervalo de Datas\n3 - Listar Todos os Pedidos\n4 - Alterar Status de um Pedido");
        System.out.print("Escolha: ");
        try {
            int op = Integer.parseInt(sc.nextLine());
            if (op == 4) {
                alterarStatusFluxo();
                return;
            }

            List<Pedido> resultados = new ArrayList<>();
            switch(op) {
                case 1 -> {
                    System.out.print("Número do Pedido: ");
                    int num = Integer.parseInt(sc.nextLine());
                    Pedido p = pedidoService.buscarPedidoPorNumero(num);
                    if (p != null) resultados.add(p);
                    break;}

                case 2 -> {
                    System.out.print("Data Inicial (dd/MM/yyyy): ");
                    LocalDate inicio = LocalDate.parse(sc.nextLine(), dtf);
                    System.out.print("Data Final (dd/MM/yyyy): ");
                    LocalDate fim = LocalDate.parse(sc.nextLine(), dtf);
                    resultados = pedidoService.buscarPedidosPorIntervaloDatas(inicio, fim);
                    break;}

                case 3 -> {
                    resultados = pedidoService.listarTodosPedidos();
                    break;}

            }
            exibirTelaMestreDetalhe(resultados);
        } catch (RuntimeException e) {
            System.out.println("Erro na entrada de dados.");
        }
    }

    private void alterarStatusFluxo() {
        System.out.print("Número do Pedido para alterar: ");
        int num = Integer.parseInt(sc.nextLine());
        System.out.println("Selecione o Novo Status:\n1 - PENDENTE\n2 - ENVIADO\n3 - CANCELADO");
        int stOp = Integer.parseInt(sc.nextLine());
        Pedido.StatusPedido st = stOp == 2 ? Pedido.StatusPedido.ENVIADO : stOp == 3 ? Pedido.StatusPedido.CANCELADO : Pedido.StatusPedido.PENDENTE;
        
        if (pedidoService.alterarStatusPedido(num, st)) {
            System.out.println("Status do pedido alterado com sucesso!");
        } else {
            System.out.println("Pedido não encontrado.");
        }
    }

    private void exibirTelaMestreDetalhe(List<Pedido> lista) {
        if (lista.isEmpty()) {
            System.out.println("Nenhum pedido encontrado.");
            return;
        }
        for (Pedido p : lista) {
            System.out.println("\n=======================================================");
            System.out.println("MESTRE (CABEÇALHO DO PEDIDO)");
            System.out.println("=======================================================");
            System.out.println("Número do Pedido: " + p.getNumeroPedido());
            System.out.println("Cliente: " + p.getCliente().getNome() + " (" + p.getCliente().getEmail() + ")");
            System.out.println("Data de Realização: " + p.getDataRealizacao().format(dtf));
            System.out.println("Status Atual: " + p.getStatus().getLabel());
            System.out.println("Data Envio: " + (p.getDataEnvio() != null ? p.getDataEnvio().format(dtf) : "N/A"));
            System.out.println("Data Cancelamento: " + (p.getDataCancelamento() != null ? p.getDataCancelamento().format(dtf) : "N/A"));
            System.out.println("Transportadora: " + (p.getTransportadora() != null ? p.getTransportadora().getNome() : "N/A"));
            System.out.printf("Valor do Frete: R$ %.2f%n", p.getValorFrete());
            System.out.printf("VALOR TOTAL DO PEDIDO: R$ %.2f%n", p.getValorTotalPedido());
            System.out.println("-------------------------------------------------------");
            System.out.println("DETALHE (CORPO / ITENS SOLICITADOS)");
            System.out.println("-------------------------------------------------------");
            
            for (var item : p.getItens()) {
                System.out.println("    Descrição/Nome: " + item.getProduto().getNome());
                System.out.println("    Quantidade: " + item.getQuantidade());
                System.out.printf("    Valor Unitário: R$ %.2f%n", item.getPrecoUnitarioNoMomento());
                System.out.printf("    Valor Total do Item: R$ %.2f%n", item.getSubTotal());
                System.out.println("  .....................................................");
            }
            System.out.println("=======================================================");
        }
    }

}