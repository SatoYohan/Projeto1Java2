package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.InscricaoEvento;
import entities.Participante;
import entities.StatusEvento;
import entities.Administrador;
import entities.CategoriaEvento;
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


    
 /*   private InscricaoEvento mapearInscricaoComEvento(ResultSet rs) throws SQLException {
        InscricaoEvento inscricao = new InscricaoEvento();
        inscricao.setCodigoInscricao(rs.getInt("codigo_inscricao"));
        inscricao.getParticipante().setCodigoPessoa(rs.getInt("codigo_participante"));;
        Participante participante = new Participante();
        participante.setCodigoPessoa(rs.getInt("codigo_participante"));
        inscricao.setParticipante(participante);

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
        evento.getAdministrador().setCodigoPessoa(rs.getInt("codigo_organizador"));

        inscricao.setEvento(evento);

        inscricao.setDataInscricao(rs.getDate("data_inscricao"));
        inscricao.setStatusInscricao(StatusInscricao.valueOf(rs.getString("status_inscricao")));
        inscricao.setPresencaConfirmada(rs.getBoolean("presenca_confirmada"));

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

        //Administrador admin = new Administrador();
        //admin.setCodigoPessoa(rs.getInt("codigo_pessoa"));
        evento.getAdministrador().setCodigoPessoa(rs.getInt("codigo_organizador"));
        
        return inscricao;
    }
*/
    
    private InscricaoEvento mapearInscricao(ResultSet rs) throws SQLException {
        InscricaoEvento inscricao = new InscricaoEvento();
        inscricao.setCodigoInscricao(rs.getInt("codigo_inscricao"));
        inscricao.getParticipante().setCodigoPessoa(rs.getInt("codigo_participante"));;
        inscricao.getEvento().setCodigoEvento(rs.getInt("codigo_evento"));
        inscricao.setDataInscricao(rs.getDate("data_inscricao"));
        inscricao.setStatusInscricao(StatusInscricao.valueOf(rs.getString("status_inscricao")));
        inscricao.setPresencaConfirmada(rs.getBoolean("presenca_confirmada"));

        return inscricao;
    }
    
    public List<InscricaoEvento> buscarEventosPorParticipante(int codigoParticipante) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                "SELECT i.codigo_inscricao, i.codigo_participante, i.codigo_evento AS insc_codigo_evento, i.data_inscricao, i.status_inscricao, i.presenca_confirmada, e.* " +
                "FROM inscricao_evento i " +
                "INNER JOIN evento e ON i.codigo_evento = e.codigo_evento " +
                "WHERE i.codigo_participante = ? "
            );
            st.setInt(1, codigoParticipante);
            rs = st.executeQuery();

            List<InscricaoEvento> listaInscricoes = new ArrayList<>();
            while (rs.next()) {
                InscricaoEvento inscricao = mapearInscricao2(rs);
                listaInscricoes.add(inscricao);
            }

            return listaInscricoes;
        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.finalizarResultSet(rs);
            BancoDados.desconectar();
        }
    }
    
    public int contarInscritos(int codigoEvento) throws SQLException {
        String sql = "SELECT COUNT(*) FROM inscricao_evento WHERE codigo_evento = ?";
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(sql);
            st.setInt(1, codigoEvento);
            rs = st.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } finally {
            BancoDados.finalizarResultSet(rs);
            BancoDados.finalizarStatement(st);
            BancoDados.desconectar();
        }
    }
    
    public boolean verificarInscricaoExistente(int codigoParticipante, int codigoEvento) throws SQLException {
        String sql = "SELECT COUNT(*) FROM inscricao_evento WHERE codigo_participante = ? AND codigo_evento = ? AND status_inscricao != ?";

        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
        	st = conn.prepareStatement(sql);
            st.setInt(1, codigoParticipante);
            st.setInt(2, codigoEvento);
            st.setString(3, StatusInscricao.CANCELADA.name()); 

            rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;  
            }
        } finally {
            BancoDados.finalizarResultSet(rs);
            BancoDados.finalizarStatement(st);
            BancoDados.desconectar();
        }
        
        return false;
    }
    
    public void confirmarPresenca(int codigoPessoa, int codigoEvento) throws SQLException {
        String sql = "UPDATE inscricao_evento SET status_inscricao = ?, presenca_confirmada = ? WHERE codigo_participante = ? AND codigo_evento = ?";
        
        PreparedStatement st = null;
        
        try  {
        	
        	st = conn.prepareStatement(sql);
        	st.setString(1, "ATIVA");
        	st.setBoolean(2, true);
        	st.setInt(3, codigoPessoa);
        	st.setInt(4, codigoEvento);
        	st.executeUpdate();
        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.desconectar();
        }
    }

    public void cancelarInscricao(int codigoPessoa, int codigoEvento) throws SQLException {
        String sql = "UPDATE inscricao_evento SET status_inscricao = ?, presenca_confirmada = ? WHERE codigo_participante = ? AND codigo_evento = ?";
        
        PreparedStatement st = null;
        
        try  {
        	st = conn.prepareStatement(sql);
        	st.setString(1, "CANCELADA");
        	st.setBoolean(2, false);
        	st.setInt(3, codigoPessoa);
        	st.setInt(4, codigoEvento);
        	st.executeUpdate();
        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.desconectar();
        }
    }
    
    public String buscarStatusEvento(int codigoEvento) throws SQLException, IOException {
        String sql = "SELECT status_evento FROM evento WHERE codigo_evento = ?";

        PreparedStatement st = null;
        ResultSet rs = null;
        
        try  {
        	
        	
        	st = conn.prepareStatement(sql);
        	
            st.setInt(1, codigoEvento);
            
            rs = st.executeQuery();

            if (rs.next()) {
               return rs.getString("status_evento");
            } else {
               throw new SQLException("Evento com código " + codigoEvento + " não encontrado.");
            }
            
        } finally {
        	BancoDados.finalizarStatement(st);
        	BancoDados.finalizarResultSet(rs);
            BancoDados.desconectar();
        }
    }
    
    private InscricaoEvento mapearInscricao2(ResultSet rs) throws SQLException {
        InscricaoEvento inscricao = new InscricaoEvento();

        inscricao.setCodigoInscricao(rs.getInt("codigo_inscricao"));
        inscricao.getParticipante().setCodigoPessoa(rs.getInt("codigo_participante"));;
        inscricao.getEvento().setCodigoEvento(rs.getInt("codigo_evento"));
        inscricao.setDataInscricao(rs.getDate("data_inscricao"));
        inscricao.setStatusInscricao(StatusInscricao.valueOf(rs.getString("status_inscricao")));
        inscricao.setPresencaConfirmada(rs.getBoolean("presenca_confirmada"));

        // Mapear os campos do Evento
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
        evento.getAdministrador().setCodigoPessoa(rs.getInt("codigo_organizador"));

        inscricao.setEvento(evento);

        return inscricao;
    }

    
    public List<InscricaoEvento> buscarEventosFuturosPorParticipante(int codigoParticipante) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                "SELECT i.codigo_inscricao, i.codigo_participante, i.codigo_evento AS insc_codigo_evento, i.data_inscricao, i.status_inscricao, i.presenca_confirmada, e.* " +
                "FROM inscricao_evento i " +
                "INNER JOIN evento e ON i.codigo_evento = e.codigo_evento " +
                "WHERE i.codigo_participante = ? and e.data_evento > NOW()"
            );
            st.setInt(1, codigoParticipante);
            rs = st.executeQuery();

            List<InscricaoEvento> listaInscricoes = new ArrayList<>();
            while (rs.next()) {
                InscricaoEvento inscricao = mapearInscricao2(rs);
                listaInscricoes.add(inscricao);
            }

            return listaInscricoes;
        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.finalizarResultSet(rs);
            BancoDados.desconectar();
        }
    }
    
    public List<InscricaoEvento> buscarEventosAntigosPorParticipante(int codigoParticipante) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                "SELECT i.codigo_inscricao, i.codigo_participante, i.codigo_evento AS insc_codigo_evento, i.data_inscricao, i.status_inscricao, i.presenca_confirmada, e.* " +
                "FROM inscricao_evento i " +
                "INNER JOIN evento e ON i.codigo_evento = e.codigo_evento " +
                "WHERE i.codigo_participante = ? and e.data_evento < NOW() "
            );
            st.setInt(1, codigoParticipante);
            rs = st.executeQuery();

            List<InscricaoEvento> listaInscricoes = new ArrayList<>();
            while (rs.next()) {
                InscricaoEvento inscricao = mapearInscricao2(rs);
                listaInscricoes.add(inscricao);
            }

            return listaInscricoes;
        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.finalizarResultSet(rs);
            BancoDados.desconectar();
        }
    }


}