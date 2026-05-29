package service;

import java.util.List;
import model.Usuario;
import repository.UserRep;

public class UserService {
    private final UserRep usuarios;
    private int contadorId = 1;

    public UserService() {
        this.usuarios = new UserRep();
    }

    public Usuario login(String email, String senha) {
        email = email.trim();
        senha = senha.trim();

        for (Usuario u : usuarios.listarUser()) {
            if (u.getEmail().equalsIgnoreCase(email) && u.getSenha().equals(senha)) {
                return u;
            }
        }
        return null;
    }

    public void register(String nome, String email, String senha, boolean admin) {
        if (validarUsuario(nome, email, senha)){
            System.out.println("kkk");
            return;
        }

        Usuario newUser = new Usuario(contadorId++, nome, email, senha, admin);
        usuarios.salvarUser(newUser);
    }

    public boolean validarUsuario(String nome, String email, String senha){
        return nome.isBlank() || email.isBlank() || senha.isBlank() || emailJaExiste(email);
    }

    public List<Usuario> listarUsuarios() {
        return usuarios.listarUser();
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
        Usuario atual = usuarios.buscarPorId(id);
        if (atual == null) return false;

        if (nome.isBlank()) nome = atual.getNome();
        if (email.isBlank()) email = atual.getEmail();
        if (senha.isBlank()) senha = atual.getSenha();

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