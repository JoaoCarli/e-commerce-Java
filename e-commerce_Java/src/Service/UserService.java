package service;

import model.Usuario;
import repository.UserRep;

public class UserService {
    private UserRep usuarios;

    public boolean login(Usuario email, Usuario senha) {
        for (Usuario u : usuarios.listarUser()) {
            if (u.equals(senha) && u.equals(email)) {
                return true;
            }
        }
        return false;
    }

    public void register(String nome, String email, String senha, boolean admin) {
        Usuario newUser = new Usuario(nome, email, senha, admin);
        usuarios.salvarUser(newUser);
    }

    public void deleteUser(Usuario usuario) {
        usuarios.listarUser().removeIf(u -> u.equals(usuario));
    }
}
