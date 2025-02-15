package dao;

import entities.InscricaoEvento;
import entities.Participante;
import entities.Evento;
import entities.StatusInscricao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InscricaoEventoDAO {

    private Connection conn;

    public InscricaoEventoDAO(Connection conn) {
        this.conn = conn;
    }

    public int cadastrar(InscricaoEvento inscricaoEvento) throws SQLException {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("INSERT INTO inscricao_evento (codigo_participante, codigo_evento, data_inscricao, status_inscricao, presenca_confirmada) VALUES (?, ?, ?, ?, ?)");
            st.setInt(1, inscricaoEvento.getParticipante().getCodigoPessoa());
            st.setInt(2, inscricaoEvento.getEvento().getCodigoEvento());
            st.setDate(3, inscricaoEvento.getDataInscricao());
            st.setString(4, inscricaoEvento.getStatusInscricao().name());
            st.setBoolean(5, inscricaoEvento.isPresencaConfirmada());

            return st.executeUpdate();
        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.desconectar();
        }
    }

    public List<InscricaoEvento> buscarTodos() throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM inscricao_evento ORDER BY data_inscricao");
            rs = st.executeQuery();

            List<InscricaoEvento> listaInscricoes = new ArrayList<>();
            while (rs.next()) {
                InscricaoEvento inscricao = mapearInscricao(rs);
                listaInscricoes.add(inscricao);
            }

            return listaInscricoes;
        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.finalizarResultSet(rs);
            BancoDados.desconectar();
        }
    }

    public InscricaoEvento buscarPorCodigo(int codigo) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM inscricao_evento WHERE codigo_inscricao = ?");
            st.setInt(1, codigo);

            rs = st.executeQuery();

            if (rs.next()) {
                return mapearInscricao(rs);
            }

            return null;
        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.finalizarResultSet(rs);
            BancoDados.desconectar();
        }
    }

    public int atualizar(InscricaoEvento inscricaoEvento) throws SQLException {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("UPDATE inscricao_evento SET codigo_participante = ?, codigo_evento = ?, data_inscricao = ?, status_inscricao = ?, presenca_confirmada = ? WHERE codigo_inscricao = ?");
            st.setInt(1, inscricaoEvento.getParticipante().getCodigoPessoa());
            st.setInt(2, inscricaoEvento.getEvento().getCodigoEvento());
            st.setDate(3, inscricaoEvento.getDataInscricao());
            st.setString(4, inscricaoEvento.getStatusInscricao().name());
            st.setBoolean(5, inscricaoEvento.isPresencaConfirmada());
            st.setInt(6, inscricaoEvento.getCodigoInscricao());

            return st.executeUpdate();
        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.desconectar();
        }
    }

    public int excluir(int codigo) throws SQLException {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM inscricao_evento WHERE codigo_inscricao = ?");
            st.setInt(1, codigo);

            return st.executeUpdate();
        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.desconectar();
        }
    }

    /**
     * Conta quantas inscrições (que não estejam CANCELADAS) existem para um determinado evento.
     */
    public int contarInscricoesAtivasPorEvento(int codigoEvento) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT COUNT(*) AS total FROM inscricao_evento " +
                            "WHERE codigo_evento = ? AND status_inscricao <> 'CANCELADA'"
            );
            st.setInt(1, codigoEvento);
            rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
            return 0;
        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.finalizarResultSet(rs);
        }
    }

    /**
     * Lista todas as inscrições de um determinado participante, para que ele acompanhe suas inscrições.
     */
    public List<InscricaoEvento> buscarPorParticipante(int codigoParticipante) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM inscricao_evento WHERE codigo_participante = ? ORDER BY data_inscricao");
            st.setInt(1, codigoParticipante);
            rs = st.executeQuery();

            List<InscricaoEvento> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(mapearInscricao(rs));
            }
            return lista;
        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.finalizarResultSet(rs);
            BancoDados.desconectar();
        }
    }

    private InscricaoEvento mapearInscricao(ResultSet rs) throws SQLException {
        InscricaoEvento inscricao = new InscricaoEvento();
        inscricao.setCodigoInscricao(rs.getInt("codigo_inscricao"));

        Participante participante = new Participante();
        participante.setCodigoPessoa(rs.getInt("codigo_participante"));
        inscricao.setParticipante(participante);

        Evento evento = new Evento();
        evento.setCodigoEvento(rs.getInt("codigo_evento"));
        inscricao.setEvento(evento);

        inscricao.setDataInscricao(rs.getDate("data_inscricao"));
        inscricao.setStatusInscricao(StatusInscricao.valueOf(rs.getString("status_inscricao")));
        inscricao.setPresencaConfirmada(rs.getBoolean("presenca_confirmada"));

        return inscricao;
    }
}
