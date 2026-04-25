package service;

import java.util.List;

import model.Produto;
import model.Transportadora;
import repository.TransportadoraRep;

public class TransportadoraService {
    private TransportadoraRep transportadoras;

    public void cadastrarTransportadora(String nome, String cnpj, String telefone, int prazoEntrega,
            double custoFrete, String regiaoAtendida, List<Produto> produtos) {
        Transportadora newTransp = new Transportadora(nome, cnpj, telefone, prazoEntrega, custoFrete, regiaoAtendida,
                produtos);
        transportadoras.salvarTransp(newTransp);
    }
}
