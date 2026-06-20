package service;

public abstract class EmpresaService {

    protected String limparCnpj(String cnpj) {
        if (cnpj == null) {
            return "";
        }

        return cnpj.replaceAll("[^0-9]", "");
    }

    protected boolean cnpjValido(String cnpj) {
        String numeros = limparCnpj(cnpj);

        if (numeros.length() != 14) {
            return false;
        }

        return !numeros.matches("(\\d)\\1{13}");
    }

    protected abstract boolean cnpjJaExiste(String cnpj);

    protected boolean verificaCadastro(String nome, String cnpj, String email, String telefone, String endereco) {
    if (nome.isBlank()) {
        System.out.println("Erro! Nome não pode ser vazio.");
        return false;
    }

    if (!cnpjValido(cnpj)) {
        System.out.println("Erro! CNPJ inválido.");
        return false;
    }

    if (cnpjJaExiste(cnpj)) {
        System.out.println("Erro! Já existe um cadastro com esse CNPJ.");
        return false;
    }

    if (email.isBlank() || !email.contains("@")) {
        System.out.println("Erro! Email inválido.");
        return false;
    }

    telefone = telefone.replaceAll("\\s+", "");
    if (telefone.isBlank() || !telefone.matches("[0-9]+")) {
        System.out.println("Erro! Telefone deve conter apenas números.");
        return false;
    }

    if (endereco.isBlank()) {
        System.out.println("Erro! Endereço não pode ser vazio.");
        return false;
    }

    return true;
}
}
