package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Participante;
import entities.Pessoa;

public class ParticipanteDAO {

    private Connection conn;

    public ParticipanteDAO(Connection conn) {
        this.conn = conn;
    }

    public int cadastrar(Participante participante) throws SQLException {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("INSERT INTO participante (codigo_pessoa, data_nascimento, cpf) VALUES (?, ?, ?)");
            st.setInt(1, participante.getCodigoPessoa());
            st.setDate(2, participante.getDataNascimento());
            st.setString(3, participante.getCpf());
            return st.executeUpdate();
        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.desconectar();
        }
    }

    public List<Participante> buscarTodos() throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM participante ORDER BY codigo_pessoa");
            rs = st.executeQuery();

            List<Participante> listaParticipantes = new ArrayList<>();
            while (rs.next()) {
                Participante participante = new Participante();
                participante.setCodigoPessoa(rs.getInt("codigo_pessoa"));
                participante.setDataNascimento(rs.getDate("data_nascimento"));
                participante.setCpf(rs.getString("cpf"));

                Pessoa pessoa = new Pessoa();
                pessoa.setCodigoPessoa(rs.getInt("codigo_pessoa"));
                participante.setPessoa(pessoa);

                listaParticipantes.add(participante);
            }
            return listaParticipantes;
        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.finalizarResultSet(rs);
            BancoDados.desconectar();
        }
    }

    public Participante buscarPorCodigo(int codigoPessoa) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM participante WHERE codigo_pessoa = ?");
            st.setInt(1, codigoPessoa);

            rs = st.executeQuery();

            if (rs.next()) {
                Participante participante = new Participante();
                participante.setCodigoPessoa(rs.getInt("codigo_pessoa"));
                participante.setDataNascimento(rs.getDate("data_nascimento"));
                participante.setCpf(rs.getString("cpf"));

                Pessoa pessoa = new Pessoa();
                pessoa.setCodigoPessoa(rs.getInt("codigo_pessoa"));
                participante.setPessoa(pessoa);

                return participante;
            }
            return null;
        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.finalizarResultSet(rs);
            BancoDados.desconectar();
        }
    }

    public int atualizar(Participante participante) throws SQLException {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("UPDATE participante SET data_nascimento = ?, cpf = ? WHERE codigo_pessoa = ?");
            st.setDate(1, participante.getDataNascimento());
            st.setString(2, participante.getCpf());
            st.setInt(3, participante.getCodigoPessoa());
            return st.executeUpdate();
        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.desconectar();
        }
    }

    public int excluir(int codigoPessoa) throws SQLException {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM participante WHERE codigo_pessoa = ?");
            st.setInt(1, codigoPessoa);
            return st.executeUpdate();
        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.desconectar();
        }
    }
}