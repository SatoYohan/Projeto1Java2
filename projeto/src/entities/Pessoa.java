package entities;

public class Pessoa {

    private int codigoPessoa;
    private String nomeCompleto;
    private String email;
    private String senha;
    private int idFuncao;

    public Pessoa() {
    }

    public Pessoa(int codigoPessoa, String nomeCompleto, String email, String senha, int idFuncao) {
        this.codigoPessoa = codigoPessoa;
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.senha = senha;
        this.idFuncao = idFuncao;
    }

    public int getCodigoPessoa() {
        return codigoPessoa;

    }

    public void setCodigoPessoa(int codigoPessoa) {
        this.codigoPessoa = codigoPessoa;

    }

    public String getNomeCompleto() {
        return nomeCompleto;

    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;

    }

    public String getEmail() {
        return email;

    }

    public void setEmail(String email) {
        this.email = email;

    }

    public String getSenha() {
        return senha;

    }

    public void setSenha(String senha) {
        this.senha = senha;

    }

    public int getIdFuncao() {
        return idFuncao;
    }

    public void setIdFuncao(int idFuncao) {
        this.idFuncao = idFuncao;
    }

}