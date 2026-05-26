package service;

import java.util.List;
import java.util.Scanner;
import model.Usuario;
import repository.UserRep;

public class UserService {
    private final UserRep usuarios;
    private Scanner sc;
    private int contadorId = 1;

    public UserService() {
        this.usuarios = new UserRep();
    }

    public Usuario login(String email, String senha) {
        for (Usuario u : usuarios.listarUser()) {
            if (u.getEmail().equals(email) && u.getSenha().equals(senha)) {
                return u;
            }
        }
        return null;
    }

    public void register(String nome, String email, String senha, boolean admin) {
        if (validarUsuario(nome, email, senha)){
            return;
        }

        Usuario newUser = new Usuario(contadorId++, nome, email, senha, admin);
        usuarios.salvarUser(newUser);
    }

    public void registerFormatado(){
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

        register(nome, email, senha, admin);

        System.out.print("Usuário cadastrado com sucesso!");
        } catch (Exception e){
            System.out.println("Erro ao cadastrar usuário, verifique os dados e tente novamente! " + e.getMessage());
        }
    }

    public boolean validarUsuario(String nome, String email, String senha){
        return !nome.isBlank() && !email.isBlank() && !senha.isBlank() || emailJaExiste(email);
    }

    public String listaUsuarioFormatada() {
        StringBuilder sb = new StringBuilder();

        for (Usuario u : listarUsuarios()) {
            sb.append("ID: ").append(u.getId())
            .append(" | Nome: ").append(u.getNome())
            .append(" | Email: ").append(u.getEmail())
            .append(" | Admin: ").append(u.isAdmin() ? "Sim" : "Não")
            .append(System.lineSeparator());
        }

        return sb.toString();
    }

    public List<Usuario> listarUsuarios() {
        return usuarios.listarUser();
    }

    public Usuario buscarUsuario(int id) {
        return usuarios.buscarPorId(id);
    }

    public boolean removerUsuario(int id) {
        Usuario usuario = buscarUsuario(id);

        if (usuario == null) {
            return false;
        }

        usuarios.removerUser(id);
        return true;
    }

    public boolean atualizarUsuario(int id, String nome, String email, String senha, boolean admin) {
        Usuario novoUsuario = new Usuario(id, nome, email, senha, admin);
        return usuarios.atualizarUser(id, novoUsuario);
    }

    public boolean emailJaExiste(String email) {
        for (Usuario u : usuarios.listarUser()) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }
}