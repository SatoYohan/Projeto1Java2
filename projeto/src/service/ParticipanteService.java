package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.BancoDados;
import dao.ParticipanteDAO;
import entities.Participante;

public class ParticipanteService {

	public ParticipanteService() {


	}


	public int cadastrar(Participante participante) throws SQLException, IOException {

		Connection conn = BancoDados.conectar();
		return new ParticipanteDAO(conn).cadastrar(participante);
	}
}
