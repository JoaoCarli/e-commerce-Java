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
        Carga novaCarga = new Carga(id, transportadora, produtos, destino, status);
        return cargas.atualizarCarga(id, novaCarga);
    }
}