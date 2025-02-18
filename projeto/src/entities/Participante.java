package entities;

import java.sql.Date;

public class Participante {

    private int codigoPessoa;
    private Date dataNascimento;
    private String cpf;
    private Pessoa pessoa;

    public Participante() {

    }

    public Participante(int codigoPessoa, Date dataNascimento, String cpf, Pessoa pessoa) {
        this.codigoPessoa = codigoPessoa;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.pessoa = pessoa;

    }

    public int getCodigoPessoa() {
        return codigoPessoa;

    }

    public void setCodigoPessoa(int codigoPessoa) {
        this.codigoPessoa = codigoPessoa;

    }

    public Date getDataNascimento() {
        return dataNascimento;

    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;

    }

    public String getCpf() {
        return cpf;

    }

    public void setCpf(String cpf) {
        this.cpf = cpf;

    }

    public Pessoa getPessoa() {
        return pessoa;

    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;

    }
}