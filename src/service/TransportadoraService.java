package service;

import java.util.ArrayList;
import java.util.List;

import model.Fornecedor;
import model.Transportadora;
import repository.TransportadoraRep;

public class TransportadoraService {
    private TransportadoraRep transportadoras;
    private int contadorId = 1;

    public TransportadoraService() {
        this.transportadoras = new TransportadoraRep();
    }

    public void cadastrarTransportadora(String nome, String cnpj, String email, String telefone, String endereco) {
        if (nome.isBlank()) {
            System.out.println("Erro! Nome não pode ser vazio.");
            return;
        }

        if (!cnpjValido(cnpj)) {
            System.out.println("Erro! CNPJ inválido.");
            return;
        }

        if (cnpjJaExiste(cnpj)) {
            System.out.println("Erro! Já existe um fornecedor com esse CNPJ.");
            return;
        }

        if (email.isBlank()) {
            System.out.println("Erro! Email não pode ser vazio.");
            return;
        }

        if (!email.contains("@")) {
            System.out.println("Erro! Email inválido.");
            return;
        }

        if (telefone.isBlank()) {
            System.out.println("Erro! Telefone não pode ser vazio.");
            return;
        }

        if (!telefone.matches("[0-9]+")) {
            System.out.println("Erro! Telefone deve conter apenas números.");
            return;
        }

        if (endereco.isBlank()) {
            System.out.println("Erro! Endereço não pode ser vazio.");
            return;
        }

        Transportadora newTransp = new Transportadora(
                contadorId++, nome, cnpj, email, telefone, 0, 0.0, endereco, new ArrayList<>());
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

    public boolean atualizarTransportadora(int id, String nome, String cnpj, String email, String telefone,
            String endereco) {
        Transportadora nova = new Transportadora(id, nome, cnpj, email, telefone, 0, 0.0, endereco, new ArrayList<>());
        return transportadoras.atualizarTransp(id, nova);
    }

    private String limparCnpj(String cnpj) {
        if (cnpj == null) {
            return "";
        }

        return cnpj.replaceAll("[^0-9]", "");
    }

    public boolean cnpjValido(String cnpj) {
        String numeros = limparCnpj(cnpj);

        if (numeros.length() != 14) {
            return false;
        }

        return !numeros.matches("(\\d)\\1{13}");
    }

    public boolean cnpjJaExiste(String cnpj) {
        String cnpjLimpo = limparCnpj(cnpj);

        for (Transportadora f : transportadoras.listarTransp()) {
            if (limparCnpj(f.getCnpj()).equals(cnpjLimpo)) {
                return true;
            }
        }

        return false;
    }
}