package dao;

import entities.Evento;
import entities.StatusEvento;
import entities.CategoriaEvento;
import entities.Administrador;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventoDAO {

    private Connection conn;

    public EventoDAO(Connection conn) {
        this.conn = conn;
    }
    
    public Connection getConnection() {
        return this.conn;
    }

    public int cadastrar(Evento evento) throws SQLException {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                "INSERT INTO evento (" +
                "nome_evento, desc_evento, data_evento, duracao_evento, local_evento, " +
                "capacidade_maxima, status_evento, categoria_evento, preco_evento, codigo_organizador" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );
            st.setString(1, evento.getNomeEvento());
            st.setString(2, evento.getDescEvento());
            st.setTimestamp(3, evento.getDataEvento());
            st.setInt(4, evento.getDuracaoEvento());
            st.setString(5, evento.getLocalEvento());
            st.setInt(6, evento.getCapacidadeMaxima());
            st.setString(7, evento.getStatusEvento().name());
            st.setString(8, evento.getCategoriaEvento().name());
            st.setFloat(9, evento.getPrecoEvento());
            st.setInt(10, evento.getAdministrador().getCodigoPessoa());

            return st.executeUpdate();
        } catch (SQLException e) {
            // Trate as exceções adequadamente (log, rethrow, etc.)
            throw new SQLException("Erro no cadastrar evento: " + e.getMessage(), e);
        } finally {
            // Fechar o Statement após a execução
            BancoDados.finalizarStatement(st);
            BancoDados.desconectar();
        }
    }


    public List<Evento> buscarTodos() throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("select * from evento order by data_evento");
            rs = st.executeQuery();

            List<Evento> listaEventos = new ArrayList<>();

            while (rs.next()) {
                Evento evento = mapearEvento(rs);
                listaEventos.add(evento);
            }

            return listaEventos;
        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.finalizarResultSet(rs);
        }
    }

    public Evento buscarPorCodigo(int codigoEvento) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("select * from evento where codigo_evento = ?");
            st.setInt(1, codigoEvento);
            rs = st.executeQuery();

            if (rs.next()) {
                return mapearEvento(rs);
            }

            return null;
        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.finalizarResultSet(rs);        
        }
    }

    public int atualizar(Evento evento) throws SQLException {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("update evento set nome_evento = ?, desc_evento = ?, data_evento = ?, duracao_evento = ?, local_evento = ?, capacidade_maxima = ?, status_evento = ?, categoria_evento = ?, preco_evento = ?, codigo_administrador = ? where codigo_evento = ?");

            st.setString(1, evento.getNomeEvento());
            st.setString(2, evento.getDescEvento());
            st.setTimestamp(3, evento.getDataEvento());
            st.setInt(4, evento.getDuracaoEvento());
            st.setString(5, evento.getLocalEvento());
            st.setInt(6, evento.getCapacidadeMaxima());
            st.setString(7, evento.getStatusEvento().name());
            st.setString(8, evento.getCategoriaEvento().name());
            st.setFloat(9, evento.getPrecoEvento());
            st.setInt(10, evento.getAdministrador().getCodigoPessoa());
            st.setInt(11, evento.getCodigoEvento());

            return st.executeUpdate();
        } finally {
            BancoDados.finalizarStatement(st);
        }
    }

    public int excluir(int codigoEvento) throws SQLException {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("delete from evento where codigo_evento = ?");
            st.setInt(1, codigoEvento);

            return st.executeUpdate();
        } finally {
            BancoDados.finalizarStatement(st);
        }
    }

    private Evento mapearEvento(ResultSet rs) throws SQLException {
        Evento evento = new Evento();
        evento.setCodigoEvento(rs.getInt("codigo_evento"));
        evento.setNomeEvento(rs.getString("nome_evento"));
        evento.setDescEvento(rs.getString("desc_evento"));
        evento.setDataEvento(rs.getTimestamp("data_evento"));
        evento.setDuracaoEvento(rs.getInt("duracao_evento"));
        evento.setLocalEvento(rs.getString("local_evento"));
        evento.setCapacidadeMaxima(rs.getInt("capacidade_maxima"));
        evento.setStatusEvento(StatusEvento.valueOf(rs.getString("status_evento")));
        evento.setCategoriaEvento(CategoriaEvento.valueOf(rs.getString("categoria_evento")));
        evento.setPrecoEvento(rs.getFloat("preco_evento"));

        Administrador admin = new Administrador();
        admin.setCodigoPessoa(rs.getInt("codigo_pessoa"));
        evento.setAdministrador(admin);

        return evento;
    }

}
