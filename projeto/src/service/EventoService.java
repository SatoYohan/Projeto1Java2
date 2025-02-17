package service;

import dao.BancoDados;
import dao.EventoDAO;
import dao.PessoaDAO;
import entities.Evento;
import entities.StatusEvento;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class EventoService {

	private EventoDAO eventoDAO;

    public EventoService() throws SQLException, IOException {
    	// Abre conexão e instancia o DAO
        Connection conn = BancoDados.conectar();
        this.eventoDAO = new EventoDAO(conn);
    }

    /**
     * Cadastrar um novo evento.
     * Regra: Ao criar, o evento deve iniciar como FECHADO.
     * @throws IOException 
     */
    public boolean cadastrarEvento(Evento evento) throws SQLException, IOException {
        // Força o astatus FECHADO conforme a regra
    	BancoDados.conectar();
        evento.setStatusEvento(StatusEvento.FECHADO);
        boolean result = new EventoDAO(BancoDados.conectar()).cadastrar(evento) > 0;
        return result;

    }


    public List<Evento> listarEventos() throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		return new EventoDAO(conn).buscarTodos();

    }


//     * Buscar um evento pelo código (ID).*/
   
    public Evento buscarEventoPorCodigo(int codigoEvento) throws SQLException {
		return eventoDAO.buscarPorCodigo(codigoEvento);
    }

    /* Atualizar um evento existente.*/
     
    public boolean atualizarEvento(Evento evento) throws SQLException {
        return eventoDAO.atualizar(evento) > 0;
    }

    /* Excluir um evento pelo código. */
     
    public boolean excluirEvento(int codigoEvento) throws SQLException {
        return eventoDAO.excluir(codigoEvento) > 0;
    }

    /**
     * Alterar apenas o status de um evento.*/
     
    public boolean alterarStatusEvento(int codigoEvento, StatusEvento novoStatus) throws SQLException {
        Evento evento = eventoDAO.buscarPorCodigo(codigoEvento);
        if (evento == null) {
            return false;
        }
        evento.setStatusEvento(novoStatus);
        return eventoDAO.atualizar(evento) > 0;
    }
    
    public List<Evento> listarEventosPorStatus(StatusEvento status) throws SQLException, IOException {
        Connection conn = BancoDados.conectar();
        return new EventoDAO(conn).buscarPorStatus(status);
    }

}
