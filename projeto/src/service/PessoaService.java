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
	    
	    public int obterFuncaoPorCredenciais(String email, String senha) throws SQLException, IOException {
	        String sql = "SELECT id_funcao FROM pessoa WHERE email = ? AND senha = ?";
	        try (Connection conn = BancoDados.conectar();
	             PreparedStatement st = conn.prepareStatement(sql)) {

	            // Verifique se os valores não estão nulos ou vazios antes de setar
	            if (email == null || email.isEmpty() || senha == null || senha.isEmpty()) {
	                throw new SQLException("Email ou senha não fornecidos.");
	            }

	            st.setString(1, email);  // Coloca o valor do email
	            st.setString(2, senha);  // Coloca o valor da senha

	            try (ResultSet rs = st.executeQuery()) {
	                if (rs.next()) {
	                    return rs.getInt("id_funcao");
	                } else {
	                    return -1;  // Caso não encontre a função
	                }
	            }
	        }
	    }
	    
	    public boolean isEmailCadastrado(String email) throws SQLException, IOException {
	        return new PessoaDAO(BancoDados.conectar()).emailJaCadastrado(email);
	    }
	
}