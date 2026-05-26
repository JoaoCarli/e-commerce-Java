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
    private final ProdService prodService = new ProdService();
    private final TransportadoraService transService = new TransportadoraService();
    private final CargaService cargaService = new CargaService();

    private final UserController userController = new UserController(sc, userService);
    private final FornecedorController fornecedorController = new FornecedorController(sc, fornecedorService);
    private final ProdutoController produtoController = new ProdutoController(sc, prodService);
    private final TransportadoraController transController = new TransportadoraController(sc, transService);
    private final CargaController cargaController = new CargaController(sc, cargaService);

    private Usuario logado;
    private boolean sistemaAtivo = true;

    public void start() {
        inicializarDados();

        while (sistemaAtivo) {
            login();

            if (sistemaAtivo) {
                menu();
            }
        }
    }

    private void inicializarDados() {
        userService.register("Admin Master", "admin@gmail.com", "Mortadela1", true);
        userService.register("Usuario Comum", "user@hotmail.com", "Gargamel", false);

        fornecedorService.cadastrarFornecedor("Fornecedor A", "12.345.678/0001-95", "fornecedorA@gmail.com", "54 996259874", "End A",
                new ArrayList<>());
        fornecedorService.cadastrarFornecedor("Fornecedor B", "98.765.432/0001-23", "fornecedorB@gmail.com", "11 996352541", "End B",
                new ArrayList<>());
        fornecedorService.cadastrarFornecedor("Fornecedor C", "45.678.912/0001-06", "fornecedorC@gmail.com", "55 996986532", "End C",
                new ArrayList<>());

        for (int fId = 1; fId <= 3; fId++) {
            Fornecedor f = fornecedorService.buscarFornecedor(fId);
            for (int p = 1; p <= 3; p++) {
                prodService.cadastrarProduto(f, "Produto " + p + " do Fornecedor " + fId, 10.0 * p, "Desc", 100);
            }
        }

        transService.cadastrarTransportadora("Transp Rápida", "23.456.789/0001-04", "contato@rapida.com", "54 996254875",
                "Galpão 1");
        transService.cadastrarTransportadora("Transp Lenta", "34.567.891/0001-42", "contato@lenta.com", "11 996359864",
                "Galpão 2");
        transService.cadastrarTransportadora("Transp Global", "56.789.123/0001-38", "contato@global.com", "88 996854796",
                "Galpão 3");

        cargaService.cadastrarCarga(transService.buscarTransportadora(1), prodService.buscarProdutosPorIds(1, 2, 3), "Porto Alegre", "Em trânsito");
        cargaService.cadastrarCarga(transService.buscarTransportadora(2), prodService.buscarProdutosPorIds(4, 5), "Caxias do Sul", "Pendente");
        cargaService.cadastrarCarga(transService.buscarTransportadora(1), prodService.buscarProdutosPorIds(6, 7, 8), "Bento Gonçalves", "Entregue");
        cargaService.cadastrarCarga(transService.buscarTransportadora(3), prodService.buscarProdutosPorIds(9), "Vacaria", "Em trânsito");
    }

    private void login() {
        while (logado == null) {
            System.out.println("=== LOGIN SISTEMA E-COMMERCE ===");
            System.out.print("Email: ");
            String email = sc.nextLine();
            System.out.print("Senha: ");
            String senha = sc.nextLine();
            logado = userService.login(email, senha);
            if (logado == null)
                System.out.println("Credenciais incorretas!\n");
        }
    }

    private void logout() {
        logado = null;
        System.out.println("Logout realizado com sucesso!");
    }

    private void menu() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n=== MENU PRINCIPAL (Acesso: " + (logado.isAdmin() ? "ADMIN" : "USER") + ") ===");
            if (logado.isAdmin()) {
                System.out.println("1 - Gerenciar Fornecedores");
                System.out.println("2 - Gerenciar Produtos");
                System.out.println("3 - Gerenciar Transportadoras");
                System.out.println("4 - Gerenciar Cargas");
                System.out.println("5 - Gerenciar Usuários");
            } else {
                System.out.println("1 - Consultar Produtos");
                System.out.println("2 - Consultar Cargas");
            }
            System.out.println("9 - Logout");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            try {
                opcao = Integer.parseInt(sc.nextLine());
                if (logado.isAdmin()) {
                    switch (opcao) {
                        case 1 -> fornecedorController.menuFornecedores();
                        case 2 -> produtoController.menuProdutos();
                        case 3 -> transController.menuTransportadoras();
                        case 4 -> cargaController.menuCargas();
                        case 5 -> userController.menuUsuario();
                        case 9 -> {
                            logout();
                            return;
                        }
                        case 0 -> {
                            sistemaAtivo = false;
                            System.out.println("Encerrando...");
                            return;
                        }
                    }
                } else {
                    switch (opcao) {
                        case 1 -> produtoController.listarProdutos();
                        case 2 -> cargaController.listarCargas();
                        case 9 -> {
                            logout();
                            return;
                        }
                        case 0 -> {
                            sistemaAtivo = false;
                            System.out.println("Encerrando...");
                            return;
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida.");
            }
        }
    }

    
}