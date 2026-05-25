package service;

import java.util.List;
import model.Carga;
import model.Produto;
import model.Transportadora;
import repository.CargaRep;

public class CargaService {
    private final CargaRep cargas;
    private int contadorId = 1;

    public CargaService() {
        this.cargas = new CargaRep();
    }

    public Carga cadastrarCarga(Transportadora transportadora, List<Produto> produtos, String destino, String status) {
        if (transportadora == null) {
            System.out.println("Erro! Transportadora não informada.");
            return null;
        }

        if (produtos == null || produtos.isEmpty()) {
            System.out.println("Erro! A carga precisa ter pelo menos um produto.");
            return null;
        }

        if (destino == null || destino.isBlank()) {
            System.out.println("Erro! Destino não pode ser vazio.");
            return null;
        }

        Carga newCarga = new Carga(contadorId++, transportadora, produtos, destino, status);
        cargas.salvarCarga(newCarga);
        return newCarga;
    }

    public List<Carga> listarCargas() {
        return cargas.listarCargas();
    }

    public Carga buscarCarga(int id) {
        return cargas.buscarPorId(id);
    }

    public boolean removerCarga(int id) {
        return cargas.removerCarga(id);
    }

    public boolean atualizarCarga(int id, Transportadora transportadora, List<Produto> produtos, String destino, String status) {
        if (transportadora == null || produtos == null || produtos.isEmpty()) {
            return false;
        }

        Carga novaCarga = new Carga(id, transportadora, produtos, destino, status);
        return cargas.atualizarCarga(id, novaCarga);
    }
}
