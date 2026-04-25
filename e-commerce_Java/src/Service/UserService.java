package service;

import model.Usuario;
import repository.UserRep;

public class UserService {
    private UserRep usuarios;

    public boolean login(String email, String senha) {
        for (Usuario u : usuarios.listarUser()) {
            if (u.getSenha().equals(senha) && u.getEmail().equals(email)) {
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

    public Usuario pesquisarUserPorEmail(String email) {
        for (Usuario u : usuarios.listarUser()) {
            if (u.getEmail().equals(email)) {
                return u;
            }
        }
        return null;
    }

    public boolean alterarUserPorEmail(String emailRef, String novoNome, String novaSenha, String novoEmail,
            boolean novoAdmin) {
        Usuario u = pesquisarUserPorEmail(emailRef);

        if (u != null) {
            if (novoNome != null && !novoNome.trim().isEmpty()) {
                u.setNome(novoNome);
            }
            if (novaSenha != null && !novaSenha.trim().isEmpty()) {
                u.setSenha(novaSenha);
            }

            if (novoEmail != null && !novoEmail.trim().isEmpty() && !novoEmail.equals(emailRef)) {
                Usuario usuarioExistente = pesquisarUserPorEmail(novoEmail);

                if (usuarioExistente == null) {
                    u.setEmail(novoEmail);
                } else {
                    System.out.println("Erro: O e-mail " + novoEmail + " já está sendo usado por outro usuário.");
                    return false;
                }
            }

            u.setIsAdmin(novoAdmin);
            return true;
        }

        return false;
    }
}
