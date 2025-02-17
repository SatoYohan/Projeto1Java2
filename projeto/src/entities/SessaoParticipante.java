package entities;

public class SessaoParticipante {

	    private static Participante participanteLogado;

	    public static Participante getParticipanteLogado() {
	        return participanteLogado;
	    }

	    public static void setParticipanteLogado(Participante participante) {
	        participanteLogado = participante;
	    }

	    public static void encerrarSessao() {
	        participanteLogado = null;
	    }
	

}
