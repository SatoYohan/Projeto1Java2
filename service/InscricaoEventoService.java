package service;

import dao.InscricaoEventoDAO;
import dao.EventoDAO;
import entities.InscricaoEvento;
import entities.Evento;
import entities.Participante;
import entities.StatusEvento;
import entities.StatusInscricao;
import entities.CategoriaEvento; // se precisar
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class InscricaoEventoService {

    private InscricaoEventoDAO inscricaoEventoDAO;
    private EventoDAO eventoDAO;

    public InscricaoEventoService(Connection conn) {
        this.inscricaoEventoDAO = new InscricaoEventoDAO(conn);
        this.eventoDAO = new EventoDAO(conn);
    }

    /**
     * Inscrever um participante em um evento.
     * Regras:
     *  - O evento deve estar com status ABERTO.
     *  - A capacidade máxima não pode ser ultrapassada.
     *  - Se o evento for pago, a inscrição inicia como PENDENTE_PAGAMENTO, senão ATIVA.
     */
    public boolean inscreverParticipanteEmEvento(int codParticipante, int codEvento) throws SQLException {
        // 1) Buscar dados do evento
        Evento evento = eventoDAO.buscarPorCodigo(codEvento);
        if (evento == null) {
            throw new SQLException("Evento não encontrado!");
        }

        // 2) Verificar status do evento
        if (evento.getStatusEvento() != StatusEvento.ABERTO) {
            throw new SQLException("As inscrições só são permitidas para eventos com status ABERTO!");
        }

        // 3) Verificar capacidade
        int inscricoesAtivas = inscricaoEventoDAO.contarInscricoesAtivasPorEvento(codEvento);
        if (inscricoesAtivas >= evento.getCapacidadeMaxima()) {
            throw new SQLException("A capacidade máxima do evento foi atingida!");
        }

        // (Opcional) Verificar se a data do evento já passou ou não:
        // if (evento.getDataEvento().getTime() < System.currentTimeMillis()) {
        //     throw new SQLException("Não é possível se inscrever em evento que já ocorreu!");
        // }

        // 4) Criar objeto de inscrição
        InscricaoEvento inscricao = new InscricaoEvento();
        Participante participante = new Participante();
        participante.setCodigoPessoa(codParticipante);

        inscricao.setParticipante(participante);
        inscricao.setEvento(evento);
        inscricao.setDataInscricao(new Date(System.currentTimeMillis()));
        inscricao.setPresencaConfirmada(false);

        // Definir status inicial da inscrição:
        if (evento.getPrecoEvento() > 0) {
            inscricao.setStatusInscricao(StatusInscricao.PENDENTE_DE_PAGAMENTO);
        } else {
            inscricao.setStatusInscricao(StatusInscricao.ATIVA);
        }

        // 5) Salvar no banco
        int linhas = inscricaoEventoDAO.cadastrar(inscricao);
        return (linhas > 0);
    }

    /**
     * Cancelar a inscrição de um participante em um evento.
     * Regras:
     *  - O evento deve estar com status ABERTO.
     *  - Deve ser antes do evento iniciar (data atual < data do evento).
     */
    public boolean cancelarInscricao(int codigoInscricao) throws SQLException {
        // 1) Buscar a inscrição
        InscricaoEvento inscricao = inscricaoEventoDAO.buscarPorCodigo(codigoInscricao);
        if (inscricao == null) {
            throw new SQLException("Inscrição não encontrada!");
        }

        // 2) Buscar o evento para checar status e data
        Evento evento = eventoDAO.buscarPorCodigo(inscricao.getEvento().getCodigoEvento());
        if (evento == null) {
            throw new SQLException("Evento da inscrição não encontrado!");
        }

        // 3) Verificar se o evento está ABERTO
        if (evento.getStatusEvento() != StatusEvento.ABERTO) {
            throw new SQLException("Só é possível cancelar inscrições de eventos ABERTOS.");
        }

        // 4) Verificar se a data atual ainda é anterior à data do evento
        long hoje = System.currentTimeMillis();
        if (evento.getDataEvento().getTime() <= hoje) {
            throw new SQLException("Não é possível cancelar a inscrição pois o evento já iniciou ou encerrou.");
        }

        // 5) Atualizar status da inscrição para CANCELADA
        inscricao.setStatusInscricao(StatusInscricao.CANCELADA);
        int linhas = inscricaoEventoDAO.atualizar(inscricao);
        return (linhas > 0);
    }

    /**
     * Confirmar a presença do participante em um evento.
     * (A lógica de quando/como permitir essa confirmação pode variar)
     */
    public boolean confirmarPresenca(int codigoInscricao) throws SQLException {
        InscricaoEvento inscricao = inscricaoEventoDAO.buscarPorCodigo(codigoInscricao);
        if (inscricao == null) {
            throw new SQLException("Inscrição não encontrada!");
        }

        // Poderíamos verificar se o evento já ocorreu, mas isso depende da sua regra de negócio.
        inscricao.setPresencaConfirmada(true);

        int linhas = inscricaoEventoDAO.atualizar(inscricao);
        return (linhas > 0);
    }

    /**
     * (Opcional) Caso o evento seja pago e a inscrição esteja como PENDENTE_DE_PAGAMENTO,
     * este método simula a confirmação de pagamento e passa a inscrição para ATIVA.
     */
    public boolean efetuarPagamento(int codigoInscricao) throws SQLException {
        InscricaoEvento inscricao = inscricaoEventoDAO.buscarPorCodigo(codigoInscricao);
        if (inscricao == null) {
            throw new SQLException("Inscrição não encontrada!");
        }

        if (inscricao.getStatusInscricao() != StatusInscricao.PENDENTE_DE_PAGAMENTO) {
            throw new SQLException("Não é possível efetuar pagamento para uma inscrição que não está pendente de pagamento.");
        }

        inscricao.setStatusInscricao(StatusInscricao.ATIVA);
        int linhas = inscricaoEventoDAO.atualizar(inscricao);
        return (linhas > 0);
    }

    /**
     * Lista todas as inscrições no sistema.
     */
    public List<InscricaoEvento> listarTodasInscricoes() throws SQLException {
        return inscricaoEventoDAO.buscarTodos();
    }

    /**
     * Lista as inscrições de um participante específico, para acompanhamento.
     */
    public List<InscricaoEvento> listarInscricoesPorParticipante(int codParticipante) throws SQLException {
        return inscricaoEventoDAO.buscarPorParticipante(codParticipante);
    }

    /**
     * Buscar uma inscrição específica.
     */
    public InscricaoEvento buscarInscricaoPorCodigo(int codigoInscricao) throws SQLException {
        return inscricaoEventoDAO.buscarPorCodigo(codigoInscricao);
    }
}
