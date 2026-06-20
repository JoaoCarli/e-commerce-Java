package service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Cliente;
import model.ItemPedido;
import model.Pedido;
import model.Produto;
import model.Transportadora;
import repository.PedidoRep;

public class PedidoService {
    private final PedidoRep pedidosGlobal;

    public PedidoService() {
        this.pedidosGlobal = new PedidoRep();
    }

    public Pedido finalizarPedido(Cliente cliente, Transportadora transportadora, double valorFrete) {
        if (cliente.getCarrinho().isEmpty()) {
            return null;
        }

        for (ItemPedido item : cliente.getCarrinho()) {
            Produto prodOriginal = item.getProduto();
            int novoEstoque = prodOriginal.getEstoque() - item.getQuantidade();
            prodOriginal.setEstoque(novoEstoque);
        }

        int proximoId = pedidosGlobal.listarPedidos().size() + 1;

        Pedido novoPedido = new Pedido(proximoId, cliente, new ArrayList<>(cliente.getCarrinho()), transportadora, valorFrete);
        pedidosGlobal.salvarPedido(novoPedido);
        cliente.limparCarrinho();
        return novoPedido;
    }

    public List<Pedido> listarTodosPedidos() {
        return pedidosGlobal.listarPedidos();
    }

    public List<Pedido> listarPedidosPorCliente(Cliente cliente) {
        List<Pedido> filtrados = new ArrayList<>();
        for (Pedido p : pedidosGlobal.listarPedidos()) {
            if (p.getCliente().getId() == cliente.getId()) {
                filtrados.add(p);
            }
        }
        return filtrados;
    }

    public Pedido buscarPedidoPorNumero(int numero) {
        return pedidosGlobal.buscarPorNumero(numero);
    }

    public List<Pedido> buscarPedidosPorIntervaloDatas(LocalDate inicio, LocalDate fim) {
        List<Pedido> filtrados = new ArrayList<>();
        for (Pedido p : pedidosGlobal.listarPedidos()) {
            LocalDate data = p.getDataRealizacao();
            if ((data.isAfter(inicio) || data.isEqual(inicio)) && (data.isBefore(fim) || data.isEqual(fim))) {
                filtrados.add(p);
            }
        }
        return filtrados;
    }

    public boolean alterarStatusPedido(int numero, Pedido.StatusPedido novoStatus) {
        Pedido p = buscarPedidoPorNumero(numero);
        if (p == null) {
            return false;
        }
        
        p.setStatus(novoStatus);
        if (novoStatus == Pedido.StatusPedido.ENVIADO) {
            p.setDataEnvio(LocalDate.now());
            p.setDataCancelamento(null);
        } else if (novoStatus == Pedido.StatusPedido.CANCELADO) {
            p.setDataCancelamento(LocalDate.now());
            p.setDataEnvio(null);
        }
        
        return pedidosGlobal.atualizarPedido(numero, p);
    }
}