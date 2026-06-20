package model;

public class Administrador extends Usuario {
    private static final long serialVersionUID = 1L;

    public Administrador(int id, String nome, String email, String senha) {
        super(id, nome, email, senha);
    }

    @Override
    public boolean isAdmin() {
        return true;
    }
}