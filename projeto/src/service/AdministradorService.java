package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import dao.AdministradorDAO;
import dao.BancoDados;
import dao.ParticipanteDAO;
import entities.Administrador;
import entities.Participante;

public class AdministradorService {

	public AdministradorService() {


	}


	public int cadastrar(Administrador administrador) throws SQLException, IOException {

		Connection conn = BancoDados.conectar();
		return new AdministradorDAO(conn).cadastrar(administrador);
	}
}
