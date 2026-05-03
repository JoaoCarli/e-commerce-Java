package repository;

import java.util.ArrayList;
import java.util.List;

import model.Transportadora;

public class TransportadoraRep {
    private List<Transportadora> transportadoras = new ArrayList<>();

    public void salvarTransp(Transportadora transp) {
        transportadoras.add(transp);
    }

    public List<Transportadora> listarTransp() {
        return transportadoras;
    }

    public Transportadora buscarPorId(int id) {
        for (Transportadora t : transportadoras) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }

    public boolean removerTransp(int id) {
        Transportadora t = buscarPorId(id);
        if (t != null) {
            transportadoras.remove(t);
            return true;
        }
        return false;
    }

    public boolean atualizarTransp(int id, Transportadora nova) {
        Transportadora t = buscarPorId(id);

        if (t != null) {
            t.setNome(nova.getNome());
            t.setCnpj(nova.getCnpj());
            t.setTelefone(nova.getTelefone());
            t.setPrazoEntrega(nova.getPrazoEntrega());
            t.setCustoFrete(nova.getCustoFrete());
            t.setEndereco(nova.getEndereco());
            t.setProdutos(nova.getProdutos());
            return true;
        }

        return false;
    }
}