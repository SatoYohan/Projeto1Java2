package service;


import dao.BancoDados;
import dao.PessoaDAO;
import entities.Pessoa;
import utils.PasswordUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PessoaService {
	//private PessoaDAO pessoaDAO;

	public PessoaService() {

	}
/*    public int cadastrarUsuario(Pessoa pessoa) throws SQLException, IOException {
        Connection conn = BancoDados.conectar();
        PessoaDAO pessoaDAO = new PessoaDAO(conn);

        if (pessoaDAO.emailExiste(pessoa.getEmail())) {
            throw new SQLException("Email já cadastrado!");
        }

        pessoa.setSenha(PasswordUtils.hashPassword(pessoa.getSenha()));

        return pessoaDAO.cadastrar(pessoa);
    }
  */
  /*  public PessoaService(Connection conn) {
        this.pessoaDAO = new PessoaDAO(conn);
    }

    public boolean cadastrarUsuario(Pessoa pessoa) throws SQLException {
        if (pessoaDAO.emailExiste(pessoa.getEmail())) {
            throw new SQLException("Email já cadastrado!");
        }
        pessoa.setSenha(PasswordUtils.hashPassword(pessoa.getSenha()));
        return pessoaDAO.cadastrar(pessoa) > 0;
    }
    */

	public int cadastrarUsuario(Pessoa pessoa) throws SQLException, IOException {


		/*Connection conn = BancoDados.conectar();
		return new PessoaDAO(conn).cadastrar(pessoa);*/

	   /* if (new PessoaDAO(BancoDados.conectar()).emailJaCadastrado(pessoa.getEmail())) {
	        throw new SQLException("O e-mail já está cadastrado. Por favor, use outro.");
	    }*/

		int idPessoaGerado = new PessoaDAO(BancoDados.conectar()).cadastrar(pessoa);
		return idPessoaGerado;
	}


	public boolean validarCredenciais(String email, String senha) throws SQLException, IOException {

		return new PessoaDAO(BancoDados.conectar()).validarCredenciais(email, senha);
	}


	public boolean isEmailCadastrado(String email) throws SQLException, IOException {
		return new PessoaDAO(BancoDados.conectar()).emailJaCadastrado(email);
	}

	public int buscarFuncaoPorEmailSenha(String email, String senha) throws SQLException, IOException {
		return new PessoaDAO(BancoDados.conectar()).buscarFuncaoPorEmailSenha(email, senha);
	}

}