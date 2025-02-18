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
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class CancelarInscricaoWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTable tblInscritos;
	private DefaultTableModel modeloTabela;

	private JButton btnVoltar;
	private JButton btnCancelar;
	private JButton btnConfirmar;

	private InscricaoEventoService inscricaoEventoService;
	private EventoService eventoService;

	public CancelarInscricaoWindow() throws SQLException, IOException {
		setTitle("Cancelar Inscrição / Confirmar Presença");
		setSize(800, 600); // Janela maior
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		// Instanciar services
		this.inscricaoEventoService = new InscricaoEventoService();
		this.eventoService = new EventoService();

		// Configurar layout principal
		getContentPane().setLayout(new BorderLayout());

		// ---------------------------------------------
		// Painel central com borda e tabela
		// ---------------------------------------------
		JPanel painelCentral = new JPanel(new BorderLayout());
		painelCentral.setBorder(new TitledBorder(
				null,
				"Eventos Inscritos",
				TitledBorder.LEADING,
				TitledBorder.TOP,
				null,
				null
		));

		// Modelo da tabela
		modeloTabela = new DefaultTableModel(
				new Object[][] {},
				new String[] {
						"Código",
						"Nome",
						"Data Evento",
						"Status Evento",
						"Status Inscrição",
						"Presença Confirmada"
				}
		);
		tblInscritos = new JTable(modeloTabela);

		// Se quiser evitar redimensionamento automático
		// tblInscritos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		JScrollPane scrollPane = new JScrollPane(tblInscritos);
		painelCentral.add(scrollPane, BorderLayout.CENTER);

		getContentPane().add(painelCentral, BorderLayout.CENTER);

		// ---------------------------------------------
		// Painel de botões na parte inferior (SOUTH)
		// ---------------------------------------------
		JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));

		btnCancelar = new JButton("Cancelar Inscrição");
		btnConfirmar = new JButton("Confirmar Presença");
		btnVoltar = new JButton("Voltar");

		painelBotoes.add(btnCancelar);
		painelBotoes.add(btnConfirmar);
		painelBotoes.add(btnVoltar);

		getContentPane().add(painelBotoes, BorderLayout.SOUTH);

		// Listeners dos botões
		btnCancelar.addActionListener(e -> cancelarInscricao());
		btnConfirmar.addActionListener(e -> confirmarPresenca());
		btnVoltar.addActionListener(e -> {
			new PrincipalWindowParticipante().setVisible(true);
			dispose();
		});

		// Carregar dados da tabela
		listarEventos();
	}

	private void listarEventos() {
		try {
			// Limpa a tabela
			modeloTabela.setRowCount(0);

			// Pega o participante logado
			Participante participanteLogado = SessaoParticipante.getParticipanteLogado();

			// Busca as inscrições desse participante
			List<InscricaoEvento> inscricoes = this.inscricaoEventoService
					.buscarEventosPorParticipante(participanteLogado.getCodigoPessoa());

			// Adiciona as linhas na tabela
			for (InscricaoEvento inscricaoEvento : inscricoes) {
				modeloTabela.addRow(new Object[] {
						inscricaoEvento.getEvento().getCodigoEvento(),
						inscricaoEvento.getEvento().getNomeEvento(),
						inscricaoEvento.getEvento().getDataEvento(),
						inscricaoEvento.getEvento().getStatusEvento(),
						inscricaoEvento.getStatusInscricao(),
						inscricaoEvento.isPresencaConfirmada()
				});
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(
					this,
					"Erro ao obter eventos: " + e.getMessage(),
					"Erro",
					JOptionPane.ERROR_MESSAGE
			);
		}
	}

	private void confirmarPresenca() {
		int linhaSelecionada = tblInscritos.getSelectedRow();
		if (linhaSelecionada == -1) {
			JOptionPane.showMessageDialog(
					this,
					"Selecione uma inscrição para confirmar presença.",
					"Aviso",
					JOptionPane.WARNING_MESSAGE
			);
			return;
		}

		try {
			int codigoEvento = (int) tblInscritos.getValueAt(linhaSelecionada, 0);
			Participante participanteLogado = SessaoParticipante.getParticipanteLogado();
			Evento evento = eventoService.buscarEventoPorCodigo(codigoEvento);

			// Se o evento já passou
			if (evento.getDataEvento().before(Timestamp.valueOf(LocalDateTime.now()))) {
				JOptionPane.showMessageDialog(
						this,
						"Não é possível confirmar presença pois o evento já ocorreu.",
						"Aviso",
						JOptionPane.WARNING_MESSAGE
				);
				return;
			}

			this.inscricaoEventoService.confirmarPresenca(
					participanteLogado.getCodigoPessoa(),
					codigoEvento
			);

			JOptionPane.showMessageDialog(
					this,
					"Presença confirmada com sucesso!",
					"Sucesso",
					JOptionPane.INFORMATION_MESSAGE
			);
			listarEventos();

		} catch (SQLException | IOException e) {
			JOptionPane.showMessageDialog(
					this,
					"Erro ao confirmar presença: " + e.getMessage(),
					"Erro",
					JOptionPane.ERROR_MESSAGE
			);
		}
	}

	private void cancelarInscricao() {
		int linhaSelecionada = tblInscritos.getSelectedRow();
		if (linhaSelecionada == -1) {
			JOptionPane.showMessageDialog(
					this,
					"Selecione uma inscrição para cancelar.",
					"Aviso",
					JOptionPane.WARNING_MESSAGE
			);
			return;
		}

		try {
			int codigoEvento = (int) tblInscritos.getValueAt(linhaSelecionada, 0);
			Participante participanteLogado = SessaoParticipante.getParticipanteLogado();

			String statusEvento = inscricaoEventoService.buscarStatusEvento(codigoEvento);
			Evento evento = eventoService.buscarEventoPorCodigo(codigoEvento);

			if (!"ABERTO".equalsIgnoreCase(statusEvento)) {
				JOptionPane.showMessageDialog(
						this,
						"Só é possível cancelar inscrições em eventos com status 'ABERTO'.",
						"Aviso",
						JOptionPane.WARNING_MESSAGE
				);
				return;
			}

			// Se o evento já passou
			if (evento.getDataEvento().before(Timestamp.valueOf(LocalDateTime.now()))) {
				JOptionPane.showMessageDialog(
						this,
						"Não é possível cancelar a inscrição pois o evento já ocorreu.",
						"Aviso",
						JOptionPane.WARNING_MESSAGE
				);
				return;
			}

			this.inscricaoEventoService.cancelarInscricao(
					participanteLogado.getCodigoPessoa(),
					codigoEvento
			);

			JOptionPane.showMessageDialog(
					this,
					"Inscrição cancelada com sucesso!",
					"Sucesso",
					JOptionPane.INFORMATION_MESSAGE
			);
			listarEventos();

		} catch (SQLException | IOException e) {
			JOptionPane.showMessageDialog(
					this,
					"Erro ao cancelar inscrição: " + e.getMessage(),
					"Erro",
					JOptionPane.ERROR_MESSAGE
			);
		}
	}
}
