package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Administrador;
import entities.Pessoa;

public class AdministradorDAO {

    private Connection conn;

    public AdministradorDAO(Connection conn) {
        this.conn = conn;
    }

    public int cadastrar(Administrador administrador) throws SQLException {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("INSERT INTO administrador (codigo_pessoa, cargo, data_contratacao) VALUES (?, ?, ?)");
            st.setInt(1, administrador.getCodigoPessoa());
            st.setString(2, administrador.getCargo());
            st.setDate(3, administrador.getDataContratacao());
            return st.executeUpdate();
        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.desconectar();
        }
    }

    public List<Administrador> buscarTodos() throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM administrador ORDER BY codigo_pessoa");
            rs = st.executeQuery();
            List<Administrador> listaAdministradores = new ArrayList<>();
            while (rs.next()) {
                listaAdministradores.add(mapearAdministrador(rs));
            }
            return listaAdministradores;
        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.finalizarResultSet(rs);
            BancoDados.desconectar();
        }
    }

    public Administrador buscarPorCodigo(int codigoPessoa) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM administrador WHERE codigo_pessoa = ?");
            st.setInt(1, codigoPessoa);
            rs = st.executeQuery();
            if (rs.next()) {
                return mapearAdministrador(rs);
            }
            return null;
        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.finalizarResultSet(rs);
            BancoDados.desconectar();
        }
    }

    public int atualizar(Administrador administrador) throws SQLException {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("UPDATE administrador SET cargo = ?, data_contratacao = ? WHERE codigo_pessoa = ?");
            st.setString(1, administrador.getCargo());
            st.setDate(2, administrador.getDataContratacao());
            st.setInt(3, administrador.getCodigoPessoa());
            return st.executeUpdate();
        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.desconectar();
        }
    }

    public int excluir(int codigoPessoa) throws SQLException {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM administrador WHERE codigo_pessoa = ?");
            st.setInt(1, codigoPessoa);
            return st.executeUpdate();
        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.desconectar();
        }
    }

    private Administrador mapearAdministrador(ResultSet rs) throws SQLException {
        Administrador administrador = new Administrador();
        administrador.setCodigoPessoa(rs.getInt("codigo_pessoa"));
        administrador.setCargo(rs.getString("cargo"));
        administrador.setDataContratacao(rs.getDate("data_contratacao"));

        Pessoa pessoa = new Pessoa();
        pessoa.setCodigoPessoa(rs.getInt("codigo_pessoa"));
        administrador.setPessoa(pessoa);

        return administrador;
    }
}