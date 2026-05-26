package controller;

import java.util.Scanner;
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
            System.out.println(" 1-Nova Usuário\n 2-Listar Usuários\n 3-Deletar Usuário\n 0-Voltar\n");
            op = Integer.parseInt(sc.nextLine());
            switch (op) {
                case 1 -> userService.registerFormatado();
                case 2 -> listarUsuarios();
                case 3 -> removerUsuario();
            }
        }
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
