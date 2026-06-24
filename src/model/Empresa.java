package model;

public class Empresa implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    protected int id;
    protected String nome;
    protected String cnpj;
    protected String email;
    protected String telefone;
    protected String endereco;

    public Empresa(String cnpj, String email, String endereco, int id, String nome, String telefone) {
        this.cnpj = cnpj;
        this.email = email;
        this.endereco = endereco;
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

}
