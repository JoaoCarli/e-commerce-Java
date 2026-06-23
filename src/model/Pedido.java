package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum StatusPedido {
        PENDENTE("Pendente"),
        ENVIADO("Enviado"),
        CANCELADO("Cancelado");

        private final String label;
        StatusPedido(String label) { this.label = label; }
        public String getLabel() { return label; }
    }

    private int numeroPedido;
    private Cliente cliente;
    private LocalDate dataRealizacao;
    private LocalDate dataEnvio;
    private LocalDate dataCancelamento;
    private StatusPedido status;
    private List<ItemPedido> itens;
    private Transportadora transportadora;
    private double valorFrete;

    public Pedido(int numeroPedido, Cliente cliente, List<ItemPedido> itens, Transportadora transportadora, double valorFrete) {
        this.numeroPedido = numeroPedido;
        this.cliente = cliente;
        this.dataRealizacao = LocalDate.now();
        this.status = StatusPedido.PENDENTE;
        this.itens = itens == null ? new ArrayList<>() : new ArrayList<>(itens);
        this.transportadora = transportadora;
        this.valorFrete = valorFrete;
    }

    public int getNumeroPedido() { return numeroPedido; }
    public void setNumeroPedido(int numeroPedido) { this.numeroPedido = numeroPedido; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public LocalDate getDataRealizacao() { return dataRealizacao; }
    public void setDataRealizacao(LocalDate dataRealizacao) { this.dataRealizacao = dataRealizacao; }

    public LocalDate getDataEnvio() { return dataEnvio; }
    public void setDataEnvio(LocalDate dataEnvio) { this.dataEnvio = dataEnvio; }

    public LocalDate getDataCancelamento() { return dataCancelamento; }
    public void setDataCancelamento(LocalDate dataCancelamento) { this.dataCancelamento = dataCancelamento; }

    public StatusPedido getStatus() { return status; }
    public void setStatus(StatusPedido status) { this.status = status; }

    public List<ItemPedido> getItens() { return itens; }
    public void setItens(List<ItemPedido> itens) { this.itens = itens; }

    public Transportadora getTransportadora() { return transportadora; }
    public void setTransportadora(Transportadora transportadora) { this.transportadora = transportadora; }

    public double getValorFrete() { return valorFrete; }
    public void setValorFrete(double valorFrete) { this.valorFrete = valorFrete; }

    public double getValorTotalPedido() {
        double totalItens = 0;
        for (ItemPedido item : itens) {
            totalItens += item.getSubTotal();
        }
        return totalItens + valorFrete;
    }
}