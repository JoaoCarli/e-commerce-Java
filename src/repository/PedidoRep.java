package repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import model.Pedido;

public class PedidoRep {
    private List<Pedido> pedidos = new ArrayList<>();
    private final String PATH_ARQUIVO = "pedidos.dat";

    public PedidoRep() {
        carregarArquivo();
    }

    public void salvarPedido(Pedido pedido) {
        pedidos.add(pedido);
        salvarArquivo();
    }

    public List<Pedido> listarPedidos() {
        return pedidos;
    }

    public Pedido buscarPorNumero(int numero) {
        for (Pedido p : pedidos) {
            if (p.getNumeroPedido() == numero) {
                return p;
            }
        }
        return null;
    }

    public boolean atualizarPedido(int numero, Pedido novoPedido) {
        Pedido p = buscarPorNumero(numero);

        if (p != null) {
            p.setStatus(novoPedido.getStatus());
            p.setDataEnvio(novoPedido.getDataEnvio());
            p.setDataCancelamento(novoPedido.getDataCancelamento());
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
            pedidos = (List<Pedido>) ois.readObject();
        } catch (Exception e) {
            System.out.println("Erro ao carregar o arquivo de pedidos: " + e.getMessage());
        }
    }

    private void salvarArquivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PATH_ARQUIVO))) {
            oos.writeObject(pedidos);
        } catch (Exception e) {
            System.out.println("Erro ao salvar o arquivo de pedidos: " + e.getMessage());
        }
    }
}