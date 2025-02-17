package dao;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.junit.jupiter.api.Test;

import entities.Administrador;
import entities.CategoriaEvento;
import entities.Evento;
import entities.StatusEvento;

class EventoDAOTeste {

	@Test
	void cadastrarEventoTeste() throws SQLException, IOException {
        // Preparar o evento
        Evento evento = new Evento();
        
        //Administrador
        Administrador admin = new Administrador();
        admin.setCodigoPessoa(74);  // Código do administrador
        
        
        evento.setNomeEvento("Festival de Música");
        evento.setDescEvento("Evento com várias atrações musicais.");
        
        // Definir data para o evento (formato Timestamp)
        Timestamp dataEvento = Timestamp.valueOf("2025-12-31 20:00:00");
        evento.setDataEvento(dataEvento);
        
        evento.setDuracaoEvento(5);  // Duração de 5 horas
        evento.setLocalEvento("Arena de Eventos");
        evento.setCapacidadeMaxima(5000);  // Capacidade de 5000 pessoas
        evento.setStatusEvento(StatusEvento.FECHADO);  // Status FECHADO
        evento.setPrecoEvento(150.00f);  // Preço de 150.00
        evento.setCategoriaEvento(CategoriaEvento.CONFERENCIA);  // Categoria música
        //evento.set
        evento.setAdministrador(admin);
        

        

        // Estabelece a conexão
        Connection conn = BancoDados.conectar();

        // Testar o método de cadastro
 //       EventoDAO eventoDAO = new EventoDAO(conn);
        int resultado = new EventoDAO(conn).cadastrar(evento);
        
        // Verificar se o evento foi cadastrado com sucesso
        assertEquals(1, resultado);  // A expectativa é que o número de linhas afetadas seja 1

        // Fechar a conexão após o teste
        BancoDados.desconectar();
    }

}
