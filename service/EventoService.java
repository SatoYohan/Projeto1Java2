package service;

import dao.EventoDAO;
import dao.PessoaDAO;
import entities.Evento;
import entities.Pessoa;
import entities.StatusEvento;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class EventoService {
    private EventoDAO eventoDAO;

    public EventoService(Connection conn) {
        this.eventoDAO = new EventoDAO(conn);
    }

    public boolean cadastrarEvento(Evento evento) throws SQLException {
        evento.setStatusEvento(StatusEvento.FECHADO);
        return eventoDAO.cadastrar(evento) > 0;
    }

    public List<Evento> listarEventos() throws SQLException {
        return eventoDAO.buscarTodos();
    }

    public Evento buscarEventoPorCodigo(int codigo) throws SQLException {
        return eventoDAO.buscarPorCodigo(codigo);
    }

    public boolean atualizarEvento(Evento evento) throws SQLException {
        return eventoDAO.atualizar(evento) > 0;
    }

    public boolean excluirEvento(int codigo) throws SQLException {
        return eventoDAO.excluir(codigo) > 0;
    }

    public boolean alterarStatusEvento(int codigoEvento, StatusEvento novoStatus) throws SQLException {
        Evento evento = eventoDAO.buscarPorCodigo(codigoEvento);
        if (evento != null) {
            evento.setStatusEvento(novoStatus);
            return eventoDAO.atualizar(evento) > 0;
        }
        return false;
    }
}