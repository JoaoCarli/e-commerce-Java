package model;

import java.util.ArrayList;
import java.util.List;

public class Carga {
    private int id;
    private Transportadora transportadora;
    private List<Produto> produtos;
    private String destino;
    private StatusCarga status = StatusCarga.PENDENTE;

    public enum StatusCarga {
        PENDENTE("Pendente"), 
        EM_TRANSITO("Em trânsito"), 
        ENTREGUE("Entregue");

        private final String label;

        StatusCarga (String label) {
            this.label = label;
        }

        public String getLabel(){
            return label;
        }
    }

    public Carga(int id, Transportadora transportadora, List<Produto> produtos, String destino, StatusCarga status) {
        this.id = id;
        this.transportadora = transportadora;
        this.produtos = produtos == null ? new ArrayList<>() : new ArrayList<>(produtos);
        this.destino = destino;
        this.status = status == null ? StatusCarga.PENDENTE : status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Transportadora getTransportadora() {
        return transportadora;
    }

    public void setTransportadora(Transportadora transportadora) {
        this.transportadora = transportadora;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos == null ? new ArrayList<>() : new ArrayList<>(produtos);
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public StatusCarga getStatus() {
        return status;
    }

    public void setStatus(StatusCarga status) {
        this.status = status;
    }
}
