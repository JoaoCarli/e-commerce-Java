package repository;

import java.util.ArrayList;
import java.util.List;
import model.Usuario;

public class UserRep {
    private final List<Usuario> usuarios = new ArrayList<>();

    public void salvarUser(Usuario usuario) {
        usuarios.add(usuario);
    }

    public List<Usuario> listarUser() {
        return usuarios;
    }

    public Usuario buscarPorId(int id) {
        for (Usuario u : usuarios) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    public boolean removerUser(int id) {
        Usuario u = buscarPorId(id);
        if (u != null) {
            usuarios.remove(u);
            return true;
        }
        return false;
    }

    public boolean atualizarUser(int id, Usuario novoUsuario) {
        Usuario u = buscarPorId(id);

        if (u != null) {
            u.setNome(novoUsuario.getNome());
            u.setEmail(novoUsuario.getEmail());
            u.setSenha(novoUsuario.getSenha());
            u.setisAdmin(novoUsuario.isAdmin());
            return true;
        }

        return false;
    }
}