package repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import model.Carga;

public class CargaRep {
    private List<Carga> cargas = new ArrayList<>();
    private final String PATH_ARQUIVO = "cargas.dat";

    public CargaRep() {
        carregarArquivo();
    }

    public void salvarCarga(Carga c) {
        cargas.add(c);
        salvarArquivo();
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
            salvarArquivo();
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
            cargas = (List<Carga>) ois.readObject();
        } catch (Exception e) {
            System.out.println("Erro ao carregar o arquivo de cargas: " + e.getMessage());
        }
    }

    private void salvarArquivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PATH_ARQUIVO))) {
            oos.writeObject(cargas);
        } catch (Exception e) {
            System.out.println("Erro ao salvar o arquivo de cargas: " + e.getMessage());
        }
    }

}