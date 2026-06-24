package controller;

import java.util.List;
import java.util.Scanner;
import model.Cliente;
import model.Pedido;
import model.Transportadora;
import service.PedidoService;
import service.TransportadoraService;

public class CarrinhoController {
    private final Scanner sc;
    private final PedidoService pedidoService;
    private final TransportadoraService transService;

    public CarrinhoController(Scanner sc, PedidoService pedidoService, TransportadoraService transService) {
        this.sc = sc;
        this.pedidoService = pedidoService;
        this.transService = transService;
    }

    public void menuVisualizarCarrinho(Cliente cliente) {
        System.out.println("\n--- MEU CARRINHO DE COMPRAS ---");
        if (cliente.getCarrinho().isEmpty()) {
            System.out.println("Seu carrinho está vazio.");
            return;
        }

        double totalCarrinho = 0;
        for (int i = 0; i < cliente.getCarrinho().size(); i++) {
            var item = cliente.getCarrinho().get(i);
            System.out.println("[" + i + "] " + item.getProduto().getNome() + " | Qtd: " + item.getQuantidade() + " | Subtotal: R$ " + item.getSubTotal());
            totalCarrinho += item.getSubTotal();
        }
        System.out.printf("Total Parcial dos Itens: R$ %.2f%n", totalCarrinho);

        System.out.println("\n1 - Finalizar Pedido\n2 - Limpar Carrinho\n0 - Voltar");
        System.out.print("Escolha: ");
        try {
            int op = Integer.parseInt(sc.nextLine());
            if (op == 1) {
                finalizarPedidoFluxo(cliente);
            } else if (op == 2) {
                cliente.limparCarrinho();
                System.out.println("Carrinho esvaziado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Opção inválida.");
        }
    }

    private void finalizarPedidoFluxo(Cliente cliente) {
        System.out.println("\n--- SELEÇÃO DE TRANSPORTADORA ---");
        List<Transportadora> transps = transService.listarTransportadoras();
        for (int i = 0; i < transps.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + transps.get(i).getNome());
        }
        System.out.print("Escolha uma transportadora pelo número: ");
        try {
            int idxTransp = Integer.parseInt(sc.nextLine()) - 1;
            Transportadora escolhida = (idxTransp >= 0 && idxTransp < transps.size()) ? transps.get(idxTransp) : null;
            double frete = escolhida != null ? 25.0 : 0.0;

            Pedido p = pedidoService.finalizarPedido(cliente, escolhida, frete);
            if (p != null) {
                System.out.println("Pedido número " + p.getNumeroPedido() + " realizado com sucesso!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Operação cancelada.");
        }
    }

}