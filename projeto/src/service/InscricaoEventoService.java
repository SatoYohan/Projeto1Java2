package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.InscricaoEventoDAO;
import dao.BancoDados;
import entities.Evento;
import entities.InscricaoEvento;

public class InscricaoEventoService {

    public InscricaoEventoService() {

    }
    
	public void cadastrar(InscricaoEvento inscricaoEvento) throws SQLException, IOException {

		Connection conn = BancoDados.conectar();
		new InscricaoEventoDAO(conn).cadastrar(inscricaoEvento);
	}

    public boolean verificarInscricaoExistente(int codigoParticipante, int codigoEvento) throws SQLException, IOException {
        return new InscricaoEventoDAO(BancoDados.conectar()).verificarInscricaoExistente(codigoParticipante, codigoEvento);
        
    }
	
    public List<InscricaoEvento> buscarEventosPorParticipante(int codigoParticipante) throws SQLException, IOException {
        
    		Connection conn = null;
    		if (conn == null || conn.isClosed()) {
    			conn = BancoDados.conectar(); 
    		}
        
            InscricaoEventoDAO inscricaoEventoDAO = new InscricaoEventoDAO(conn);
            List<InscricaoEvento> inscricoes = inscricaoEventoDAO.buscarEventosPorParticipante(codigoParticipante);

            List<Evento> eventos = new ArrayList<>();
            for (InscricaoEvento inscricao : inscricoes) {
                eventos.add(inscricao.getEvento());
            }

            return inscricoes;
        
    }
    
    public List<InscricaoEvento> buscarEventosFuturosPorParticipante(int codigoParticipante) throws SQLException, IOException {
        
		Connection conn = null;
		if (conn == null || conn.isClosed()) {
			conn = BancoDados.conectar(); 
		}
    
        InscricaoEventoDAO inscricaoEventoDAO = new InscricaoEventoDAO(conn);
        List<InscricaoEvento> inscricoes = inscricaoEventoDAO.buscarEventosFuturosPorParticipante(codigoParticipante);

        List<Evento> eventos = new ArrayList<>();
        for (InscricaoEvento inscricao : inscricoes) {
            eventos.add(inscricao.getEvento());
        }

        return inscricoes;
    
}
    
    public List<InscricaoEvento> buscarEventosAntigosPorParticipante(int codigoParticipante) throws SQLException, IOException {
        
		Connection conn = null;
		if (conn == null || conn.isClosed()) {
			conn = BancoDados.conectar(); 
		}
    
        InscricaoEventoDAO inscricaoEventoDAO = new InscricaoEventoDAO(conn);
        List<InscricaoEvento> inscricoes = inscricaoEventoDAO.buscarEventosAntigosPorParticipante(codigoParticipante);

        List<Evento> eventos = new ArrayList<>();
        for (InscricaoEvento inscricao : inscricoes) {
            eventos.add(inscricao.getEvento());
        }

        return inscricoes;
    
}
	
	public void confirmarPresenca(int codigoPessoa, int codigoEvento) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
	    new InscricaoEventoDAO(conn).confirmarPresenca(codigoPessoa, codigoEvento);
	}

	public void cancelarInscricao(int codigoPessoa, int codigoEvento) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
	    new InscricaoEventoDAO(conn).cancelarInscricao(codigoPessoa, codigoEvento);

	}
	
	public String buscarStatusEvento(int codigoEvento) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
	    return new InscricaoEventoDAO(conn).buscarStatusEvento(codigoEvento);

	}
}