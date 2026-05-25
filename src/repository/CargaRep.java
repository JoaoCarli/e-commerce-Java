package repository;

import java.util.ArrayList;
import java.util.List;
import model.Carga;

public class CargaRep {
    private final List<Carga> cargas = new ArrayList<>();

    public void salvarCarga(Carga c) {
        cargas.add(c);
    }

    public List<Carga> listarCargas() {
        return cargas;
    }

    public Carga buscarPorId(int id) {
        for (Carga c : cargas) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public boolean removerCarga(int id) {
        Carga c = buscarPorId(id);
        if (c != null) {
            cargas.remove(c);
            return true;
        }
        return false;
    }

    public boolean atualizarCarga(int id, Carga novaCarga) {
        Carga c = buscarPorId(id);

        if (c != null) {
            c.setTransportadora(novaCarga.getTransportadora());
            c.setProdutos(novaCarga.getProdutos());
            c.setDestino(novaCarga.getDestino());
            c.setStatus(novaCarga.getStatus());
            return true;
        }

        return false;
    }
}