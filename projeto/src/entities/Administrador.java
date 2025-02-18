package entities;

import java.sql.Date;

public class Administrador {

    private int codigoPessoa;
    private String cargo;
    private Date dataContratacao;
    private Pessoa pessoa;

    public Administrador() {
    	
    }

    public Administrador(int codigoPessoa, String cargo, Date dataContratacao, Pessoa pessoa) {
        this.codigoPessoa = codigoPessoa;
        this.cargo = cargo;
        this.dataContratacao = dataContratacao;
        this.pessoa = pessoa;
        
    }

    public int getCodigoPessoa() {
        return codigoPessoa;
        
    }

    public void setCodigoPessoa(int codigoPessoa) {
        this.codigoPessoa = codigoPessoa;
        
    }

    public String getCargo() {
        return cargo;
        
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
        
    }

    public Date getDataContratacao() {
        return dataContratacao;
        
    }

    public void setDataContratacao(Date dataContratacao) {
        this.dataContratacao = dataContratacao;
        
    }

    public Pessoa getPessoa() {
        return pessoa;
        
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        
    }
    
    
}