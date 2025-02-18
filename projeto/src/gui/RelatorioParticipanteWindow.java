package gui;

import java.awt.Font;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.awt.event.ActionEvent;
import entities.InscricaoEvento;
import entities.Participante;
import entities.SessaoParticipante;
import service.InscricaoEventoService;

public class RelatorioParticipanteWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tblInscritos;
	private JTable tblHistorico;
	private JScrollPane scrollPaneInscritos;
	private JScrollPane scrollPaneHistorico;
	private InscricaoEventoService inscricaoEventoService;

	public RelatorioParticipanteWindow() throws SQLException, IOException {
		setTitle("Relatório de Participação");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		setLocationRelativeTo(null);

		this.inscricaoEventoService = new InscricaoEventoService();

		iniciarComponentes();
		listarEventosInscritos();
	}

	private void iniciarComponentes() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.setBounds(10, 10, 100, 30);
		btnVoltar.addActionListener(e -> {
			new PrincipalWindowParticipante().setVisible(true);
			dispose();
		});
		contentPane.add(btnVoltar);

		JButton btnEventosInscritos = new JButton("Eventos Inscritos");
		btnEventosInscritos.setBounds(120, 10, 180, 30);
		btnEventosInscritos.addActionListener(e -> {
			try {
				listarEventosInscritos();
			} catch (SQLException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		contentPane.add(btnEventosInscritos);

		JButton btnHistorico = new JButton("Histórico de Participação");
		btnHistorico.setBounds(310, 10, 200, 30);
		btnHistorico.addActionListener(e -> {
			try {
				listarHistoricoParticipacao();
			} catch (SQLException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		contentPane.add(btnHistorico);

		JButton btnExportar = new JButton("Exportar para XLS");
		btnExportar.setBounds(420, 320, 150, 30);
		contentPane.add(btnExportar);

		JPanel pnlEventos = new JPanel();
		pnlEventos.setBorder(new TitledBorder(null, "Eventos", TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), null));
		pnlEventos.setBounds(10, 50, 560, 260);
		pnlEventos.setLayout(null);
		contentPane.add(pnlEventos);

		scrollPaneInscritos = new JScrollPane();
		scrollPaneInscritos.setBounds(10, 20, 540, 230);
		pnlEventos.add(scrollPaneInscritos);

		tblInscritos = new JTable();
		tblInscritos.setModel(new DefaultTableModel(
				new Object[][] {},
				new String[] {"Código", "Nome", "Descrição", "Data", "Duração", "Local", "Capacidade", "Status", "Categoria", "Preço"}
		));
		scrollPaneInscritos.setViewportView(tblInscritos);

		scrollPaneHistorico = new JScrollPane();
		scrollPaneHistorico.setBounds(10, 20, 540, 230);
		pnlEventos.add(scrollPaneHistorico);

		tblHistorico = new JTable();
		tblHistorico.setModel(new DefaultTableModel(
				new Object[][] {},
				new String[] {"Código", "Nome", "Descrição", "Data", "Duração", "Local", "Capacidade", "Status", "Categoria", "Preço"}
		));
		scrollPaneHistorico.setViewportView(tblHistorico);

		scrollPaneInscritos.setVisible(true);
		scrollPaneHistorico.setVisible(false);
	}

	private void listarEventosInscritos() throws SQLException, IOException {
		scrollPaneInscritos.setVisible(true);
		scrollPaneHistorico.setVisible(false);
		carregarDadosNaTabela(tblInscritos, inscricaoEventoService.buscarEventosFuturosPorParticipante(getParticipanteLogado()));
	}

	private void listarHistoricoParticipacao() throws SQLException, IOException {
		scrollPaneInscritos.setVisible(false);
		scrollPaneHistorico.setVisible(true);
		carregarDadosNaTabela(tblHistorico, inscricaoEventoService.buscarEventosAntigosPorParticipante(getParticipanteLogado()));
	}

	private void carregarDadosNaTabela(JTable tabela, List<InscricaoEvento> eventos) {
		DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
		modelo.setRowCount(0);
		for (InscricaoEvento evento : eventos) {
			modelo.addRow(new Object[]{
					evento.getEvento().getCodigoEvento(),
					evento.getEvento().getNomeEvento(),
					evento.getEvento().getDescEvento(),
					evento.getEvento().getDataEvento(),
					evento.getEvento().getDuracaoEvento(),
					evento.getEvento().getLocalEvento(),
					evento.getEvento().getCapacidadeMaxima(),
					evento.getEvento().getStatusEvento(),
					evento.getEvento().getCategoriaEvento(),
					evento.getEvento().getPrecoEvento()
			});
		}
	}

	private int getParticipanteLogado() throws SQLException, IOException {
		return SessaoParticipante.getParticipanteLogado().getCodigoPessoa();
	}
}
