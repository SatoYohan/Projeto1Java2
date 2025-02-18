package dao;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

class BancoDadosTeste {

	@Test
	void conectarTeste() throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		assertNotNull(conn);
	}

	@Test
	void desconectarTeste() throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		conn = BancoDados.desconectar();
		
		assertNull(conn);
	}
}
