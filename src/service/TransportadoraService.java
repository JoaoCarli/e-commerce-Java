package service;

import java.util.List;
import model.Transportadora;
import repository.TransportadoraRep;

public class TransportadoraService extends EmpresaService{
    private final TransportadoraRep transportadoras;
    private int contadorId = 1;

    public TransportadoraService() {
        this.transportadoras = new TransportadoraRep();
    }

    @Override
    protected boolean cnpjJaExiste(String cnpj) {
        String cnpjLimpo = limparCnpj(cnpj);

        for (Transportadora t : transportadoras.listarTransp()) {
            if (limparCnpj(t.getCnpj()).equals(cnpjLimpo)) {
                return true;
            }
        }

        return false;
    }

    public void cadastrarTransportadora(String nome, String cnpj, String email, String telefone, String endereco) {
        if (!verificaCadastro(nome, cnpj, email, telefone, endereco)){
            return;
        }

        Transportadora newTransp = new Transportadora(cnpj, email, endereco, contadorId++, nome, telefone);
        transportadoras.salvarTransp(newTransp);

        System.out.println("Transportadora cadastrada com sucesso!");
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
        Transportadora nova = new Transportadora(cnpj, email, endereco, id, nome, telefone);
        return transportadoras.atualizarTransp(id, nova);
    }
}
