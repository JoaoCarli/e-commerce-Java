package service;

import java.util.ArrayList;
import java.util.List;
import model.Transportadora;
import repository.TransportadoraRep;

public class TransportadoraService {
    private TransportadoraRep transportadoras;
    private int contadorId = 1;

    public TransportadoraService() {
        this.transportadoras = new TransportadoraRep();
    }

    public void cadastrarTransportadora(String nome, String cnpj, String email, String telefone, String endereco) {
        Transportadora newTransp = new Transportadora(
                contadorId++, nome, cnpj, email, telefone, 0, 0.0, endereco, new ArrayList<>()
        );

        transportadoras.salvarTransp(newTransp);
    }

    public List<Transportadora> listarTransportadoras() {
        return transportadoras.listarTransp();
    }

    public Transportadora buscarTransportadora(int id) {
        return transportadoras.buscarPorId(id);
    }

    public boolean removerTransportadora(int id) {
        return transportadoras.removerTransp(id);
    }

    public boolean atualizarTransportadora(int id, String nome, String cnpj, String email, String telefone, String endereco) {
        Transportadora nova = new Transportadora(id, nome, cnpj, email, telefone, 0, 0.0, endereco, new ArrayList<>());
        return transportadoras.atualizarTransp(id, nova);
    }
}