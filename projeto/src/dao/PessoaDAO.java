package dao;

import entities.Pessoa;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaDAO {   
	private Connection conn;

    public PessoaDAO(Connection conn) {
        this.conn = conn;
    }

    public Connection getConnection() {
        return this.conn;
    }

    public int cadastrar(Pessoa pessoa) throws SQLException {
/*        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("INSERT INTO pessoa (nome_completo, email, senha, id_funcao) VALUES (?, ?, ?, ?)");
            
            st.setString(1, pessoa.getNomeCompleto());
            st.setString(2, pessoa.getEmail());
            st.setString(3, pessoa.getSenha());
            st.setInt(4, pessoa.getIdFuncao());
            return st.executeUpdate();
        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.desconectar();
        }
*/
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                "INSERT INTO pessoa (nome_completo, email, senha, id_funcao) VALUES (?, ?, ?, ?)", 
                Statement.RETURN_GENERATED_KEYS); // Indica que queremos os IDs gerados

            st.setString(1, pessoa.getNomeCompleto());
            st.setString(2, pessoa.getEmail());
            st.setString(3, pessoa.getSenha());
            st.setInt(4, pessoa.getIdFuncao());
            st.executeUpdate();

            // Obter o ID gerado
            rs = st.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // Retorna o ID gerado
            }
            return -1; // Se não houver ID gerado, retorna um valor inválido
        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.desconectar();
            if (rs != null) {
                rs.close();
            }
        }
    }

    public List<Pessoa> buscarTodos() throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Pessoa> pessoas = new ArrayList<>();
        try {
            st = conn.prepareStatement("SELECT * FROM pessoa ORDER BY nome_completo");
            rs = st.executeQuery();
            while (rs.next()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setCodigoPessoa(rs.getInt("codigo_pessoa"));
                pessoa.setNomeCompleto(rs.getString("nome_completo"));
                pessoa.setEmail(rs.getString("email"));
                pessoa.setSenha(rs.getString("senha"));
                pessoa.setIdFuncao(rs.getInt("id_funcao"));
                pessoas.add(pessoa);
            }
            return pessoas;
        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.finalizarResultSet(rs);
            BancoDados.desconectar();
        }
    }

    public Pessoa buscarPorCodigo(int codigo) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM pessoa WHERE codigo_pessoa = ?");
            st.setInt(1, codigo);
            rs = st.executeQuery();
            if (rs.next()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setCodigoPessoa(rs.getInt("codigo_pessoa"));
                pessoa.setNomeCompleto(rs.getString("nome_completo"));
                pessoa.setEmail(rs.getString("email"));
                pessoa.setSenha(rs.getString("senha"));
                pessoa.setIdFuncao(rs.getInt("id_funcao"));
                return pessoa;
            }
            return null;
        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.finalizarResultSet(rs);
            BancoDados.desconectar();
        }
    }

    public int atualizar(Pessoa pessoa) throws SQLException {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("UPDATE pessoa SET nome_completo = ?, email = ?, senha = ?, id_funcao = ? WHERE codigo_pessoa = ?");
            st.setString(1, pessoa.getNomeCompleto());
            st.setString(2, pessoa.getEmail());
            st.setString(3, pessoa.getSenha());
            st.setInt(4, pessoa.getIdFuncao());
            st.setInt(5, pessoa.getCodigoPessoa());
            return st.executeUpdate();
        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.desconectar();
        }
    }

    public int excluir(int codigo) throws SQLException {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM pessoa WHERE codigo_pessoa = ?");
            st.setInt(1, codigo);
            return st.executeUpdate();
        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.desconectar();
        }
    }

/*    public boolean emailExiste(String email) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT 1 FROM pessoa WHERE email = ?");
            st.setString(1, email);
            rs = st.executeQuery();
            return rs.next();
        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.finalizarResultSet(rs);
        }
    }*/
    


        public boolean validarCredenciais(String email, String senha) throws SQLException, IOException {
            String query = "SELECT 1 FROM pessoa WHERE email = ? AND senha = ?";
            try (Connection conn = BancoDados.conectar();
                 PreparedStatement st = conn.prepareStatement(query)) {
                st.setString(1, email);
                st.setString(2, senha);

                try (ResultSet rs = st.executeQuery()) {
                    return rs.next();
                }
            }
        }



}