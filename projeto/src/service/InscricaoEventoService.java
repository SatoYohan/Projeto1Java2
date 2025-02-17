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

    public List<Evento> buscarEventosPorParticipante(int codigoParticipante) throws SQLException, IOException {
        
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

            return eventos;
        
    }
}
