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
	    int idPessoaGerado = new PessoaDAO(BancoDados.conectar()).cadastrar(pessoa);
	    return idPessoaGerado;
	}
	
}