package entities;

import java.sql.Date;

public class InscricaoEvento {

	private int codigoInscricao;
    private Participante participante;
    private Evento evento;
    private Date dataInscricao;
    private StatusInscricao statusInscricao;
    private boolean presencaConfirmada;

    public InscricaoEvento() {
    }

    public InscricaoEvento(int codigoInscricao, Participante participante, Evento evento, Date dataInscricao,
                           StatusInscricao statusInscricao, boolean presencaConfirmada) {
        this.codigoInscricao = codigoInscricao;
        this.participante = participante;
        this.evento = evento;
        this.dataInscricao = dataInscricao;
        this.statusInscricao = statusInscricao;
        this.presencaConfirmada = presencaConfirmada;
    }

    public int getCodigoInscricao() {
        return codigoInscricao;
    }

    public void setCodigoInscricao(int codigoInscricao) {
        this.codigoInscricao = codigoInscricao;
    }

    public Participante getParticipante() {
        return participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Date getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(Date dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    public StatusInscricao getStatusInscricao() {
        return statusInscricao;
    }

    public void setStatusInscricao(StatusInscricao statusInscricao) {
        this.statusInscricao = statusInscricao;
    }

    public boolean isPresencaConfirmada() {
        return presencaConfirmada;
    }

    public void setPresencaConfirmada(boolean presencaConfirmada) {
        this.presencaConfirmada = presencaConfirmada;
    }
}
