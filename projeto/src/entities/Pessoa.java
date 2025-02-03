package entities;

public class Pessoa {

	private int codigoPessoa;
    private String nomeCompleto;
    private String email;
    private String senha;
    private FuncaoPessoa funcaoPessoa;

    public Pessoa() {
    }

    public Pessoa(int codigoPessoa, String nomeCompleto, String email, String senha, FuncaoPessoa funcaoPessoa) {
        this.codigoPessoa = codigoPessoa;
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.senha = senha;
        this.funcaoPessoa = funcaoPessoa;
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

    public FuncaoPessoa getFuncaoPessoa() {
        return funcaoPessoa;
        
    }

    public void setFuncaoPessoa(FuncaoPessoa funcaoPessoa) {
        this.funcaoPessoa = funcaoPessoa;
        
    }

}
