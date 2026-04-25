package repository;

import java.util.ArrayList;
import java.util.List;

import model.Transportadora;

public class TransportadoraRep {
    private List<Transportadora> transportadoras = new ArrayList<>();

    public void salvarTransp(Transportadora transp) {
        transportadoras.add(transp);
    }

    public List<Transportadora> listarUser() {
        return transportadoras;
    }
}
