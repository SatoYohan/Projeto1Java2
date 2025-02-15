package service;

import dao.PessoaDAO;
import entities.Pessoa;
import utils.PasswordUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PessoaService {
    private PessoaDAO pessoaDAO;

    public PessoaService(Connection conn) {
        this.pessoaDAO = new PessoaDAO(conn);
    }

    public boolean cadastrarUsuario(Pessoa pessoa) throws SQLException {
        if (pessoaDAO.emailExiste(pessoa.getEmail())) {
            throw new SQLException("Email já cadastrado!");
        }
        pessoa.setSenha(PasswordUtils.hashPassword(pessoa.getSenha()));
        return pessoaDAO.cadastrar(pessoa) > 0;
    }
}