package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Carga;
import model.Produto;
import model.Transportadora;
import service.CargaService;
import service.FornecedorService;
import service.ProdService;
import service.TransportadoraService;

public class CargaController {
    private final Scanner sc;
    private final CargaService cargaService;
    private final TransportadoraService transService = new TransportadoraService();
    private final FornecedorService f = new FornecedorService();
    private final ProdService prodService = new ProdService(f);
    private final ProdutoController produtoController;
    
    public CargaController(Scanner sc, CargaService cargaService) {
        this.sc = sc;
        this.cargaService = cargaService;
        this.produtoController = new ProdutoController(sc, prodService);
    }

    public void menuCargas() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- SUBMENU CARGAS ---");
            System.out.println("1-Nova Carga \n2-Listar Cargas \n3-Alterar Cargas \n4-Alterar Produtos da Carga \n5-Remover Produtos da Carga \n6-Excluir Carga \n0-Voltar");
            op = Integer.parseInt(sc.nextLine());
            switch (op) {
                case 1 -> cadastrarNovaCargaManual();
                case 2 -> listarCargas();
                case 3 -> alterarCarga();
                case 4 -> adicionarProdutoCarga();
                case 5 -> removerProdutoCarga();
                case 6 -> removerCarga();
            }
        }
    }

    public void listarCargas() {
        System.out.println("\n--- LISTA DE CARGAS ---");
        for (Carga c : cargaService.listarCargas()) {
            System.out.println("ID: " + c.getId() + " | Destino: " + c.getDestino() + " | Transp: "
                    + c.getTransportadora().getNome() + " | Status: " + c.getStatus().getLabel());

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

        System.out.println("Escolha o Status: ");
        System.out.println("1 - Pendente");
        System.out.println("2 - Em trânsito");
        System.out.println("3 - Entregue");
        System.out.print("Opção: ");

        int opcaoStatus = Integer.parseInt(sc.nextLine());

        Carga.StatusCarga status = switch (opcaoStatus) {
            case 2 -> Carga.StatusCarga.EM_TRANSITO;
            case 3 -> Carga.StatusCarga.ENTREGUE;
            default -> Carga.StatusCarga.PENDENTE;
        };

        Carga carga = cargaService.cadastrarCarga(t, prods, dest, status);
        if (carga != null) {
            System.out.println("Carga gerada com produtos vinculados!");
        }
    }

    private void alterarCarga(){
        listarCargas();
        System.out.println("ID para alterar: ");
        int id = Integer.parseInt(sc.nextLine());
        Carga c = cargaService.buscarCarga(id);
        if (c == null) {
            System.out.println("Carga não encontrada.");
            return;
        }

        System.out.println("Status (" + c.getStatus() + "): ");
        System.out.println("1 - Pendente");
        System.out.println("2 - Em trânsito");
        System.out.println("3 - Entregue");
        System.out.print("Opção: ");

        int opcaoStatus = Integer.parseInt(sc.nextLine());

        Carga.StatusCarga status = switch (opcaoStatus) {
            case 2 -> Carga.StatusCarga.EM_TRANSITO;
            case 3 -> Carga.StatusCarga.ENTREGUE;
            default -> Carga.StatusCarga.PENDENTE;
        };

        Transportadora transportadora = c.getTransportadora();
        String destino = c.getDestino();

        if (c.getStatus() == Carga.StatusCarga.PENDENTE) {
            transService.listarTransportadoras();
            System.out.print("ID da Transportadora (" + c.getTransportadora().getNome() + "): ");
            String inputTransp = sc.nextLine();
            Transportadora t = transService.buscarTransportadora(Integer.parseInt(inputTransp));
            
            System.out.print("Destino (" + c.getDestino() + "): ");
            String inputDestino = sc.nextLine();
            if (cargaService.validaCarga(t, inputDestino)){
                return;
            }
        }
        cargaService.atualizarCarga(id, transportadora, c.getProdutos(), destino, status);
        System.out.println("Carga atualizada!");
    }

    private void removerCarga(){
        listarCargas();
        System.out.print("ID da Carga: ");
        int id = Integer.parseInt(sc.nextLine());
        Carga c = cargaService.buscarCarga(id);
        if (c == null) {
            System.out.println("Carga não encontrada.");
            return;
        }else if (c.getStatus() != Carga.StatusCarga.PENDENTE){
            System.out.println("Carga em trânsito ou entregue e não pode ser alterada.");
            return;
        }else if (!c.getProdutos().isEmpty()) {
            System.out.println("Erro! Remova os produtos da carga antes de excluí-la.");
            return;
        }

        cargaService.removerCarga(id);
        System.out.println("Carga removida com sucesso!");
    }

    private void adicionarProdutoCarga(){
        listarCargas();
        System.out.println("ID da Carga para alterar: ");
        int idCarga = Integer.parseInt(sc.nextLine());
        Carga c = cargaService.buscarCarga(idCarga);
        if (c == null) {
            System.out.println("Carga não encontrada.");
            return;
        }else if (c.getStatus() != Carga.StatusCarga.PENDENTE){
            System.out.println("Carga em trânsito ou entregue e não pode ser alterada.");
            return;
        }

        produtoController.listarProdutos();
        System.out.print("ID do Produto: ");
        int idProduto = Integer.parseInt(sc.nextLine());
        Produto p = prodService.buscarProduto(idProduto);
        if (p == null) {
            System.out.println("Produto não encontrado.");
            return;
        }

        cargaService.adicionarProduto(p, idCarga);
        System.out.println("Produto adicionado à carga!");
    }

    private void removerProdutoCarga() {
        listarCargas();
        System.out.print("ID da Carga: ");
        int idCarga = Integer.parseInt(sc.nextLine());
        Carga c = cargaService.buscarCarga(idCarga);
        if (c == null) {
            System.out.println("Carga não encontrada.");
            return;
        }else if (c.getStatus() != Carga.StatusCarga.PENDENTE){
            System.out.println("Carga em trânsito ou entregue e não pode ser alterada.");
            return;
        }

        c.getProdutos().forEach(p ->
            System.out.println(p.getId() + " - " + p.getNome()));
        System.out.print("ID do Produto: ");
        int idProduto = Integer.parseInt(sc.nextLine());
        Produto p = prodService.buscarProduto(idProduto);
        if (p == null) {
            System.out.println("Produto não encontrado.");
            return;
        }

        cargaService.removerProduto(p, idCarga);
        System.out.println("Produto removido da carga!");
    }
}
