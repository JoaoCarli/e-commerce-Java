package app;

import controller.*;
import java.util.ArrayList;
import java.util.Scanner;
import model.*;
import service.*;

public class Sistema {
    private final Scanner sc = new Scanner(System.in);
    private final UserService userService = new UserService();
    private final FornecedorService fornecedorService = new FornecedorService();
    private final ProdService prodService = new ProdService(fornecedorService);
    private final TransportadoraService transService = new TransportadoraService();
    private final CargaService cargaService = new CargaService();
    private final PedidoService pedidoService = new PedidoService();

    private final UserController userController = new UserController(sc, userService);
    private final FornecedorController fornecedorController = new FornecedorController(sc, fornecedorService);
    private final ProdutoController produtoController = new ProdutoController(sc, prodService);
    private final TransportadoraController transController = new TransportadoraController(sc, transService, cargaService);
    private final CargaController cargaController = new CargaController(sc, cargaService);
    private final CarrinhoController carrinhoController = new CarrinhoController(sc, pedidoService, transService);
    private final PedidoController pedidoController = new PedidoController(sc, pedidoService);

    private Usuario logado;
    private boolean sistemaAtivo = true;

    public void start() {
        inicializarDados();
        while (sistemaAtivo) {
            login();
            if (sistemaAtivo) menu();
        }
    }

    private void inicializarDados() {
        if (userService.listarUsuarios().isEmpty()) {
            userService.register("Admin Master", "admin@gmail.com", "Mortadela1", true);
            userService.register("Usuario Comum", "user@hotmail.com", "Gargamel", false);

            fornecedorService.cadastrarFornecedor("Móveis Sul", "12.345.678/0001-95", "fornecedorA@gmail.com", "54 996259874", "End A", new ArrayList<>());
            fornecedorService.cadastrarFornecedor("Peças InfoGames", "98.765.432/0001-23", "fornecedorB@gmail.com", "11 996352541", "End B", new ArrayList<>());
            fornecedorService.cadastrarFornecedor("Oficina & Cia", "45.678.912/0001-06", "fornecedorC@gmail.com", "55 996986532", "End C", new ArrayList<>());

            String[][] nomes = {
                {"Cadeira", "Mesa", "Porta de Madeira", "Geladeira", "Fogão"},
                {"Teclado", "Mouse", "Monitor", "Processador", "Placa de vídeo", "Memória"},
                {"Chave de Fenda", "Martelo", "Alicate", "Furadeira"}
            };

            for (int fId = 1; fId <= fornecedorService.listarFornecedores().size(); fId++) {
                Fornecedor f = fornecedorService.buscarFornecedor(fId);
                for (int p = 0; p < nomes[fId - 1].length; p++) {
                    prodService.cadastrarProduto(f, nomes[fId - 1][p], 10.0 * (p + 1), (int) (Math.random() * 50) + 10);
                }
            }

            transService.cadastrarTransportadora("Transp Rápida", "23.456.789/0001-04", "contato@rapida.com", "54 996254875", "Galpão 1");
            transService.cadastrarTransportadora("Transp Lenta", "34.567.891/0001-42", "contato@lenta.com", "11 996359864", "Galpão 2");
            transService.cadastrarTransportadora("Transp Global", "56.789.123/0001-38", "contato@global.com", "88 996854796", "Galpão 3");

            cargaService.cadastrarCarga(transService.buscarTransportadora(1), prodService.buscarProdutosPorIds(1, 2, 3), "Porto Alegre", Carga.StatusCarga.EM_TRANSITO);
            cargaService.cadastrarCarga(transService.buscarTransportadora(2), prodService.buscarProdutosPorIds(4, 5), "Caxias do Sul", Carga.StatusCarga.PENDENTE);
            cargaService.cadastrarCarga(transService.buscarTransportadora(1), prodService.buscarProdutosPorIds(6, 7, 8), "Bento Gonçalves", Carga.StatusCarga.ENTREGUE);
            cargaService.cadastrarCarga(transService.buscarTransportadora(3), prodService.buscarProdutosPorIds(9), "Vacaria", Carga.StatusCarga.EM_TRANSITO);
        }
    }

    private void login() {
        while (logado == null) {
            System.out.println("=== LOGIN SISTEMA E-COMMERCE ===");
            System.out.print("Email: ");
            String email = sc.nextLine();
            System.out.print("Senha: ");
            String senha = sc.nextLine();
            logado = userService.login(email, senha);
            if (logado == null) System.out.println("Credenciais incorretas!\n");
        }
    }

    private void logout() {
        logado = null;
        System.out.println("Logout realizado com sucesso!");
    }

    private void menu() {
        System.out.println("\n=== MENU PRINCIPAL (Acesso: " + (logado.isAdmin() ? "ADMIN" : "USER") + ") ===");
        if (logado.isAdmin()) {
            System.out.println("1 - Gerenciar Fornecedores\n2 - Gerenciar Produtos\n3 - Gerenciar Transportadoras\n4 - Gerenciar Cargas\n5 - Gerenciar Usuários\n6 - Gerenciar Pedidos da Loja");
        } else {
            System.out.println("1 - Consultar Produtos / Comprar\n2 - Ver Meu Carrinho\n3 - Meus Pedidos Realizados\n4 - Consultar Cargas");
        }
        System.out.println("9 - Logout\n0 - Sair\nEscolha: ");

        try {
            int opcao = Integer.parseInt(sc.nextLine());
            if (logado.isAdmin()) {
                switch (opcao) {
                    case 1 -> fornecedorController.menuFornecedores();
                    case 2 -> produtoController.menuProdutos();
                    case 3 -> transController.menuTransportadoras();
                    case 4 -> cargaController.menuCargas();
                    case 5 -> userController.menuUsuario();
                    case 6 -> pedidoController.menuAdminPedidos();
                    case 9 -> logout();
                    case 0 -> sistemaAtivo = false;
                }
            } else {
                Cliente clienteAtual = (Cliente) logado;
                switch (opcao) {
                    case 1 -> produtoController.menuConsultaCarrinho(clienteAtual);
                    case 2 -> carrinhoController.menuVisualizarCarrinho(clienteAtual);
                    case 3 -> pedidoController.menuClientePedidos(clienteAtual);
                    case 4 -> cargaController.listarCargas();
                    case 9 -> logout();
                    case 0 -> sistemaAtivo = false;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
        }
    }
}