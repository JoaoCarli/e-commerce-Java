package repository;

import java.util.ArrayList;
import java.util.List;
import model.Usuario;

public class UserRep {
    private List<Usuario> usuarios = new ArrayList<>();

    public void salvarUser(Usuario usuario) {
        usuarios.add(usuario);
    }

    public List<Usuario> listarUser() {
        return usuarios;
    }

}
