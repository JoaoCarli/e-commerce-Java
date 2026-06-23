package model;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario {
    private static final long serialVersionUID = 1L;
    private final List<ItemPedido> carrinho;

    public Cliente(int id, String nome, String email, String senha) {
        super(id, nome, email, senha);
        this.carrinho = new ArrayList<>();
    }

    public List<ItemPedido> getCarrinho() {
        return carrinho;
    }
    
    public void limparCarrinho() {
        this.carrinho.clear();
    }

    @Override
    public boolean isAdmin() {
        return false;
    }
}