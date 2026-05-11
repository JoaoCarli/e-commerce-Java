package app;

import java.util.ArrayList;
import java.util.List;
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
        userService.register("Admin Master", "admin@gmail.com", "123", true);
        userService.register("Usuario Comum", "user@hotmail.com", "321", false);

        fornecedorService.cadastrarFornecedor("Fornecedor A", "12.345.678/0001-95", "a@f.com", "99", "End A", new ArrayList<>());
        fornecedorService.cadastrarFornecedor("Fornecedor B", "98.765.432/0001-23", "b@f.com", "99", "End B", new ArrayList<>());
        fornecedorService.cadastrarFornecedor("Fornecedor C", "45.678.912/0001-06", "c@f.com", "99", "End C", new ArrayList<>());

        for (int fId = 1; fId <= 3; fId++) {
            Fornecedor f = fornecedorService.buscarFornecedor(fId);
            for (int p = 1; p <= 7; p++) {
                prodService.cadastrarProduto(f, "Prod " + p + " do Forn " + fId, 10.0 * p, "Desc", 100);
            }
        }

        transService.cadastrarTransportadora("Trans Rápida", "23.456.789/0001-04", "contato@rapida.com", "88", "Galpão 1");
        transService.cadastrarTransportadora("Trans Lenta", "34.567.891/0001-42", "contato@lenta.com", "88", "Galpão 2");
        transService.cadastrarTransportadora("Trans Global", "56.789.123/0001-38", "contato@global.com", "88", "Galpão 3");

        List<Produto> listaCarga = prodService.listarProdutos().subList(0, 3);
        cargaService.cadastrarCarga(transService.buscarTransportadora(1), listaCarga, "Porto Alegre", "Em trânsito");
        cargaService.cadastrarCarga(transService.buscarTransportadora(2), listaCarga, "Caxias do Sul", "Pendente");
        cargaService.cadastrarCarga(transService.buscarTransportadora(1), listaCarga, "Bento Gonçalves", "Entregue");
        cargaService.cadastrarCarga(transService.buscarTransportadora(3), listaCarga, "Vacaria", "Em trânsito");
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
                        case 1 -> menuFornecedores();
                        case 2 -> menuProdutos();
                        case 3 -> menuTransportadoras();
                        case 4 -> menuCargas();
                        case 5 -> menuUsuario();
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
                        case 1 -> listarProdutos();
                        case 2 -> listarCargas();
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

    private void menuFornecedores() {
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

    private void menuCargas() {
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

    private void menuUsuario() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- SUBMENU USUARIOS ---");
            System.out.println(" 1-Nova Usuário\n 2-Listar Usuários\n 3-Deletar Usuário\n 0-Voltar\n");
            op = Integer.parseInt(sc.nextLine());
            switch (op) {
                case 1 -> cadastrarNovoUsuario();
                case 2 -> listarUsuarios();
                case 3 -> deletarUsuario();
            }
        }
    }

private void cadastrarNovoUsuario() {
    System.out.print("Nome: ");
    String nome = sc.nextLine();

    if (nome.isBlank()) {
        System.out.println("Erro! Nome não pode ser vazio.");
        return;
    }

    System.out.print("Email: ");
    String email = sc.nextLine();

    if (email.isBlank()) {
        System.out.println("Erro! Email não pode ser vazio.");
        return;
    }

    if (userService.emailJaExiste(email)) {
        System.out.println("Erro! Já existe um usuário cadastrado com esse email.");
        return;
    }

    System.out.print("Senha: ");
    String senha = sc.nextLine();

    if (senha.isBlank()) {
        System.out.println("Erro! Senha não pode ser vazia.");
        return;
    }

    System.out.print("É administrador? (s/n): ");
    String respostaAdmin = sc.nextLine();

    boolean admin = respostaAdmin.equalsIgnoreCase("s");

    userService.register(nome, email, senha, admin);

    System.out.println("Usuário cadastrado com sucesso!");
}

private void deletarUsuario() {
    System.out.println("\n--- DELETAR USUÁRIO ---");

    listarUsuarios();

    System.out.print("\nDigite o ID do usuário que deseja deletar: ");
    int id = Integer.parseInt(sc.nextLine());

    Usuario usuario = userService.buscarUsuario(id);

    if (usuario == null) {
        System.out.println("Erro! Usuário não encontrado.");
        return;
    }

    boolean removido = userService.removerUsuario(id);

    if (removido) {
        System.out.println("Usuário deletado com sucesso!");
    } else {
        System.out.println("Erro ao deletar usuário.");
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
            System.out.print("Novo Nome ("+f.getNome()+"): "); String nome = sc.nextLine();
            if(nome.isEmpty()) nome = f.getNome();
            fornecedorService.atualizarFornecedor(id, nome, f.getCnpj(), f.getEmail(), f.getTelefone(), f.getEndereco(), f.getProdutos());
            System.out.println("Atualizado!");
        } else System.out.println("Não encontrado.");
    }

    private void removerFornecedor() {
        listarFornecedores();
        System.out.print("ID para remover: ");
        int id = Integer.parseInt(sc.nextLine());
        if (fornecedorService.removerFornecedor(id)) System.out.println("Removido.");
        else System.out.println("Erro ao remover.");
    }

    private void consultarFornecedor() {
        System.out.print("ID: ");
        int id = Integer.parseInt(sc.nextLine());
        Fornecedor f = fornecedorService.buscarFornecedor(id);
        if (f != null) System.out.println("Nome: " + f.getNome() + " | CNPJ: " + f.getCnpj());
        else System.out.println("Não encontrado.");
    }

    private void listarUsuarios() {
        System.out.println("\n--- LISTA DE USUÁRIOS ---");

        for (Usuario u : userService.listarUsuarios()) {
            System.out.println(
                "ID: " + u.getId() +
                " | Nome: " + u.getNome() +
                " | Email: " + u.getEmail() +
                " | Admin: " + (u.isAdmin() ? "Sim" : "Não")
            );
        }
    }

    private void listarFornecedores() {
        for (Fornecedor f : fornecedorService.listarFornecedores()) {
            System.out.println(f.getId() + " - " + f.getNome());
        }
    }

    private void listarProdutos() {
        System.out.println("\n--- LISTA DE PRODUTOS ---");
        for (Produto p : prodService.listarProdutos()) {
            System.out.println(p.getId() + " - " + p.getNome() + " (Preço: " + p.getPreco() + ")");
        }
    }

    private void listarCargas() {
        System.out.println("\n--- LISTA DE CARGAS ---");
        for (Carga c : cargaService.listarCargas()) {
            System.out.println("ID: " + c.getId() + " | Destino: " + c.getDestino() + " | Transp: " + c.getTransportadora().getNome() + " | Status: " + c.getStatus());
        }
    }

    private void cadastrarNovaCargaManual() {
        System.out.print("ID da Transportadora: ");
        int idT = Integer.parseInt(sc.nextLine());
        Transportadora t = transService.buscarTransportadora(idT);
        if (t == null) { System.out.println("Erro!"); return; }

        System.out.print("Destino: "); String dest = sc.nextLine();
        List<Produto> prods = prodService.listarProdutos().subList(0, 2);
        cargaService.cadastrarCarga(t, prods, dest, "Pendente");
        System.out.println("Carga Gerada!");
    }

    private void menuProdutos() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- SUBMENU PRODUTOS ---");
            System.out.println("1-Incluir 2-Alterar 3-Excluir 4-Consultar 5-Listar 0-Voltar");
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
            } catch (Exception e) {
                System.out.println("Erro na entrada.");
            }
        }
    }

    private void alterarProduto() {
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

            prodService.atualizarProduto(id, p.getFornecedor(), nome, preco, p.getDescricao(), p.getEstoque());
            System.out.println("Produto atualizado!");
        } else System.out.println("Produto não encontrado.");
    }

    private void consultarProduto() {
        System.out.print("ID ou Nome do Produto: ");
        String busca = sc.nextLine();
        try {
            int id = Integer.parseInt(busca);
            Produto p = prodService.buscarProduto(id);
            if (p != null) {
                System.out.println("Prod: " + p.getNome() + " | Fornecedor: " + p.getFornecedor().getNome() + " | Preço: " + p.getPreco());
            } else System.out.println("Não encontrado.");
        } catch (NumberFormatException e) {
            System.out.println("Funcionalidade de busca por nome pode ser implementada com Filter.");
        }
    }

    private void menuTransportadoras() {
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
        System.out.print("Nome: "); String nome = sc.nextLine();
        System.out.print("CNPJ: "); String cnpj = sc.nextLine();
        System.out.print("Email: "); String email = sc.nextLine();
        System.out.print("Telefone: "); String tel = sc.nextLine();
        System.out.print("Endereço: "); String end = sc.nextLine();
        
        transService.cadastrarTransportadora(nome, cnpj, email, tel, end);
        System.out.println("Transportadora cadastrada!");
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
        if (transService.removerTransportadora(id)) System.out.println("Removida.");
        else System.out.println("ID não encontrado.");
    }

    private void alterarTransportadora() {
        System.out.print("ID da Transportadora: ");
        int id = Integer.parseInt(sc.nextLine());
        Transportadora t = transService.buscarTransportadora(id);
        if (t != null) {
            System.out.print("Novo Nome (" + t.getNome() + "): ");
            String nome = sc.nextLine();
            if (nome.isEmpty()) nome = t.getNome();
            
            transService.atualizarTransportadora(id, nome, t.getCnpj(), t.getEmail(), t.getTelefone(), t.getEndereco());
            System.out.println("Dados atualizados!");
        } else System.out.println("Não encontrada.");
    }

    private void consultarTransportadora() {
        System.out.print("ID: ");
        int id = Integer.parseInt(sc.nextLine());
        Transportadora t = transService.buscarTransportadora(id);
        if (t != null) {
            System.out.println("Nome: " + t.getNome() + " | CNPJ: " + t.getCnpj() + " | Endereço: " + t.getEndereco());
        } else System.out.println("Não encontrada.");
    }

    private void cadastrarProduto() {
        System.out.println("\n--- CADASTRO DE PRODUTO ---");
        listarFornecedores();
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
        System.out.print("Descrição: ");
        String desc = sc.nextLine();
        System.out.print("Quantidade em Estoque: ");
        int estoque = Integer.parseInt(sc.nextLine());

        prodService.cadastrarProduto(f, nome, preco, desc, estoque);
        System.out.println("Produto cadastrado com sucesso!");
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