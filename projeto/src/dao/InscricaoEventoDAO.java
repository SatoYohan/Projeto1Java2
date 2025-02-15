package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.InscricaoEvento;
import entities.Participante;
import entities.Evento;
import entities.StatusInscricao;

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
