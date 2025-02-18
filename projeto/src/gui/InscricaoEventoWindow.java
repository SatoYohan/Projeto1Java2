package gui;

import entities.Evento;
import entities.InscricaoEvento;
import entities.Participante;
import entities.SessaoParticipante;
import entities.StatusEvento;
import entities.StatusInscricao;
import service.EventoService;
import service.InscricaoEventoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class InscricaoEventoWindow extends JFrame {

	private JTable tblEventos;
	private DefaultTableModel modeloTabela;
	private JButton botaoInscrever;
	private JButton botaoVoltar;

	private EventoService eventoService;
	private InscricaoEventoService inscricaoEventoService;

	public InscricaoEventoWindow() throws SQLException, IOException {
		setTitle("Inscrição em Evento");
		setSize(800, 600); // Janela maior
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		// Instancia os services
		eventoService = new EventoService();
		inscricaoEventoService = new InscricaoEventoService();

		// Configura o layout principal como BorderLayout
		getContentPane().setLayout(new BorderLayout());

		// -------------------------------------------
		// Rótulo no topo (NORTH)
		// -------------------------------------------
		JPanel painelTopo = new JPanel();
		JLabel rotuloTitulo = new JLabel("Eventos Disponíveis:");
		rotuloTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelTopo.add(rotuloTitulo);
		add(painelTopo, BorderLayout.NORTH);

		// -------------------------------------------
		// Tabela no centro (CENTER)
		// -------------------------------------------
		// Modelo da tabela com colunas
		modeloTabela = new DefaultTableModel(
				new Object[][]{},
				new String[]{
						"Código", "Nome", "Descrição", "Data/Hora", "Duração",
						"Local", "Capacidade", "Status", "Categoria", "Preço"
				}
		);
		tblEventos = new JTable(modeloTabela);
		tblEventos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		// Se quiser que as colunas não redimensionem automaticamente
		// e mostre scrollbar horizontal se for muito grande.

		// ScrollPane para a tabela
		JScrollPane scrollPane = new JScrollPane(tblEventos);
		add(scrollPane, BorderLayout.CENTER);

		// -------------------------------------------
		// Painel de botões no sul (SOUTH)
		// -------------------------------------------
		JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		botaoInscrever = new JButton("Inscrever-se");
		botaoVoltar = new JButton("Voltar");

		painelBotoes.add(botaoInscrever);
		painelBotoes.add(botaoVoltar);

		add(painelBotoes, BorderLayout.SOUTH);

		// -------------------------------------------
		// Listeners dos botões
		// -------------------------------------------
		botaoInscrever.addActionListener(e -> {
			inscreverEmEvento();
		});

		botaoVoltar.addActionListener(e -> {
			new PrincipalWindowParticipante().setVisible(true);
			dispose();
		});

		// Carrega os eventos para exibir na tabela
		buscarEventos();
	}

	private void buscarEventos() {
		try {
			// Limpa a tabela
			modeloTabela.setRowCount(0);

			// Busca todos os eventos
			List<Evento> eventos = eventoService.listarEventos();
			for (Evento evento : eventos) {
				modeloTabela.addRow(new Object[]{
						evento.getCodigoEvento(),
						evento.getNomeEvento(),
						evento.getDescEvento(),
						evento.getDataEvento(),
						evento.getDuracaoEvento(),
						evento.getLocalEvento(),
						evento.getCapacidadeMaxima(),
						evento.getStatusEvento(),
						evento.getCategoriaEvento(),
						evento.getPrecoEvento()
				});
			}
		} catch (SQLException | IOException e) {
			JOptionPane.showMessageDialog(this,
					"Erro ao obter eventos: " + e.getMessage(),
					"Erro",
					JOptionPane.ERROR_MESSAGE
			);
		}
	}

	private void inscreverEmEvento() {
		int linhaSelecionada = tblEventos.getSelectedRow();

		if (linhaSelecionada == -1) {
			JOptionPane.showMessageDialog(
					this,
					"Selecione um evento para se inscrever.",
					"Aviso",
					JOptionPane.WARNING_MESSAGE
			);
			return;
		}

		try {
			int codigoEvento = (int) tblEventos.getValueAt(linhaSelecionada, 0);
			Evento evento = eventoService.buscarEventoPorCodigo(codigoEvento);

			if (!evento.getStatusEvento().equals(StatusEvento.ABERTO)) {
				JOptionPane.showMessageDialog(this,
						"Este evento não está aberto para inscrições.");
				return;
			}

			int quantidadeInscritos = eventoService.contarInscritosNoEvento(codigoEvento);
			if (quantidadeInscritos >= evento.getCapacidadeMaxima()) {
				JOptionPane.showMessageDialog(this,
						"A capacidade máxima deste evento foi atingida.");
				return;
			}

			Participante participante = SessaoParticipante.getParticipanteLogado();
			boolean jaInscrito = inscricaoEventoService.verificarInscricaoExistente(
					participante.getCodigoPessoa(),
					evento.getCodigoEvento()
			);

			if (jaInscrito) {
				JOptionPane.showMessageDialog(this,
						"Você já está inscrito neste evento.");
				return;
			}

			// Exemplo: criar InscricaoEvento e cadastrar
			// ou abrir uma tela de StatusInscricaoWindow
			// Abaixo, chamando a tela de status:
			new StatusInscricaoWindow(evento, participante).setVisible(true);
			dispose();

		} catch (SQLException | IOException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this,
					"Erro ao inscrever-se no evento: " + ex.getMessage());
		}
	}
}
