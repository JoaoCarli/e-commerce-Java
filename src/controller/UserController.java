package controller;

import java.util.Scanner;
import model.Usuario;
import service.UserService;

public class UserController {
    private final Scanner sc;
    private final UserService userService;

    public UserController(Scanner sc, UserService userService) {
        this.sc = sc;
        this.userService = userService;
    }

    public void menuUsuario() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- SUBMENU USUARIOS ---");
            System.out.println("1-Nova Usuário \n2-Listar Usuários \n3-Alterar Usuário \n4-Deletar Usuário \n0-Voltar");
            op = Integer.parseInt(sc.nextLine());
            switch (op) {
                case 1 -> registrUsuario();
                case 2 -> listarUsuarios();
                case 3 -> alterarUsuario();
                case 4 -> removerUsuario();
            }
        }
    }

    public void registrUsuario(){
        try{
            System.out.print("Nome: ");
            String nome = sc.nextLine();

            System.out.print("Email: ");
            String email = sc.nextLine();

            System.out.print("Senha: ");
            String senha = sc.nextLine();

            System.out.print("É administrador? (s/n): ");
            String respostaAdmin = sc.nextLine();
            boolean admin = respostaAdmin.equalsIgnoreCase("s");

            userService.register(nome, email, senha, admin);

            System.out.print("Usuário cadastrado com sucesso!");
        } catch (Exception e){
            System.out.println("Erro ao cadastrar usuário, verifique os dados e tente novamente! " + e.getMessage());
        }
    }

    public void alterarUsuario() {
        listarUsuarios();
        System.out.print("ID do Usuário: ");
        int id = Integer.parseInt(sc.nextLine());
        Usuario u = userService.buscarUsuario(id);
        if (u == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }

        System.out.print("Nome (" + u.getNome() + "): ");
        String nome = sc.nextLine();

        System.out.print("Email (" + u.getEmail() + "): ");
        String email = sc.nextLine();

        System.out.print("Senha (" + u.getSenha() + "): ");
        String senha = sc.nextLine();

        System.out.print("É administrador? (s/n) (" + (u.isAdmin() ? "s" : "n") + "): ");
        boolean admin = sc.nextLine().equalsIgnoreCase("s");

        userService.atualizarUsuario(id, nome, email, senha, admin);
        System.out.println("Usuário atualizado!");
    }

    private void removerUsuario(){
        System.out.println("\n--- DELETAR USUÁRIO ---");

        listarUsuarios();

        System.out.print("\nDigite o ID do usuário que deseja deletar: ");
        int id = Integer.parseInt(sc.nextLine());

        boolean removido = userService.removerUsuario(id);

        if (removido) {
            System.out.println("Usuário deletado com sucesso!");
        } else {
            System.out.println("Erro! Usuário não encontrado.");
        }
    }

    private void listarUsuarios() {
        System.out.println("\n--- LISTA DE USUÁRIOS ---");

        System.out.print(userService.listaUsuarioFormatada());
    }

}
