package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Carga;
import model.Produto;
import model.Transportadora;
import service.CargaService;
import service.ProdService;
import service.TransportadoraService;

public class CargaController {
    private final Scanner sc;
    private final CargaService cargaService;
    private final TransportadoraService transService = new TransportadoraService();
    private final ProdService prodService = new ProdService();
    

    public CargaController(Scanner sc, CargaService cargaService) {
        this.sc = sc;
        this.cargaService = cargaService;
    }

    public void menuCargas() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- SUBMENU CARGAS ---");
            System.out.println("1-Nova Carga 2-Listar Cargas 0-Voltar");
            op = Integer.parseInt(sc.nextLine());
            switch (op) {
                case 1 -> cadastrarNovaCargaManual();
                case 2 -> listarCargas();
            }
        }
    }

    public void listarCargas() {
        System.out.println("\n--- LISTA DE CARGAS ---");
        for (Carga c : cargaService.listarCargas()) {
            System.out.println("ID: " + c.getId() + " | Destino: " + c.getDestino() + " | Transp: "
                    + c.getTransportadora().getNome() + " | Status: " + c.getStatus());

            System.out.println("Produtos da carga:");
            for (Produto p : c.getProdutos()) {
                System.out.println("  - " + p.getId() + " | " + p.getNome()
                        + " | Fornecedor: " + p.getFornecedor().getNome());
            }
        }
    }

    private void cadastrarNovaCargaManual() {
        transService.listarTransportadoras();
        System.out.print("ID da Transportadora: ");
        int idT = Integer.parseInt(sc.nextLine());
        Transportadora t = transService.buscarTransportadora(idT);
        if (t == null) {
            System.out.println("Transportadora não encontrada!");
            return;
        }

        System.out.print("Destino: ");
        String dest = sc.nextLine();

        prodService.listarProdutos();
        System.out.print("IDs dos produtos da carga. Ex: 1,4,7: ");
        String entradaProdutos = sc.nextLine();

        List<Produto> prods = new ArrayList<>();
        for (String idTexto : entradaProdutos.split(",")) {
            try {
                int idProduto = Integer.parseInt(idTexto.trim());
                Produto produto = prodService.buscarProduto(idProduto);

                if (produto != null) {
                    prods.add(produto);
                } else {
                    System.out.println("Produto ID " + idProduto + " não encontrado e será ignorado.");
                }
            } catch (NumberFormatException e) {
                System.out.println("ID inválido ignorado: " + idTexto);
            }
        }

        Carga carga = cargaService.cadastrarCarga(t, prods, dest, "Pendente");

        if (carga != null) {
            System.out.println("Carga gerada com produtos vinculados!");
        }
    }
}
