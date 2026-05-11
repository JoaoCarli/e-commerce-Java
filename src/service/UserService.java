package service;

import java.util.List;
import model.Usuario;
import repository.UserRep;

public class UserService {
    private UserRep usuarios;
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
        Usuario newUser = new Usuario(contadorId++, nome, email, senha, admin);
        usuarios.salvarUser(newUser);
    }

    public List<Usuario> listarUsuarios() {
        return usuarios.listarUser();
    }

    public Usuario buscarUsuario(int id) {
        return usuarios.buscarPorId(id);
    }

    public boolean removerUsuario(int id) {
        return usuarios.removerUser(id);
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