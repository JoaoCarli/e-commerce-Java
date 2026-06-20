package repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import model.Usuario;

public class UserRep {
    private List<Usuario> usuarios = new ArrayList<>();
    private final String PATH_ARQUIVO = "usuarios.dat";

    public UserRep() {
        carregarArquivo();
    }

    public void salvarUser(Usuario usuario) {
        usuarios.add(usuario);
        salvarArquivo();
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

    public void removerUser(int id) {
        Usuario u = buscarPorId(id);
        if (u != null) {
            usuarios.remove(u);
            salvarArquivo();
        }
    }

    public boolean atualizarUser(int id, Usuario novoUsuario) {
        Usuario u = buscarPorId(id);

        if (u != null) {
            u.setNome(novoUsuario.getNome());
            u.setEmail(novoUsuario.getEmail());
            u.setSenha(novoUsuario.getSenha());
            salvarArquivo();
            return true;
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    private void carregarArquivo() {
        File arquivo = new File(PATH_ARQUIVO);
        if (!arquivo.exists()) {
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            usuarios = (List<Usuario>) ois.readObject();
        } catch (Exception e) {
            System.out.println("Erro ao carregar o arquivo de usuários: " + e.getMessage());
        }
    }

    private void salvarArquivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PATH_ARQUIVO))) {
            oos.writeObject(usuarios);
        } catch (Exception e) {
            System.out.println("Erro ao salvar o arquivo de usuários: " + e.getMessage());
        }
    }
}