package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import entities.Evento;
import entities.InscricaoEvento;
import entities.Participante;
import service.InscricaoEventoService;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.awt.event.ActionEvent;

public class RelatorioParticipanteWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tblInscritos;
	private JTable tblHistorico;
	private JScrollPane scrollPaneInscritos;
	private JScrollPane scrollPaneHistorico;
	private InscricaoEventoService inscricaoEventoService;

	public RelatorioParticipanteWindow() {
		setTitle("Relatório");

		this.iniciarComponentes();

		this.inscricaoEventoService = new InscricaoEventoService();

		this.listarEventosInscritos();
	}


	private void listarHistoricoParticipacao() {
		scrollPaneInscritos.setVisible(false);
		scrollPaneHistorico.setVisible(true);
	}

	private void listarEventosInscritos() {
		scrollPaneInscritos.setVisible(true);
		scrollPaneHistorico.setVisible(false);
		try {

			DefaultTableModel modelo = (DefaultTableModel) tblInscritos.getModel();
			modelo.fireTableDataChanged();
			modelo.setRowCount(0);

			Participante participante = new Participante();
			List<Evento> eventos = this.inscricaoEventoService.buscarEventosPorParticipante(participante.getCodigoPessoa());

			for (Evento evento : eventos) {

				modelo.addRow(new Object[] {

						evento.getCodigoEvento(),
						evento.getNomeEvento(),
						evento.getDescEvento(),
						evento.getDataEvento(),
						evento.getDuracaoEvento(),
						evento.getCapacidadeMaxima(),
						evento.getStatusEvento(),
						evento.getCategoriaEvento(),
						evento.getPrecoEvento(),
						evento.getAdministrador().getCodigoPessoa()
				});
			}
		} catch (SQLException | IOException e) {

			System.out.println(e.getMessage());
		}
	}

	private void iniciarComponentes() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnEventosInscritos = new JButton("Eventos Inscritos");
		btnEventosInscritos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listarEventosInscritos();
			}
		});
		btnEventosInscritos.setBounds(113, 11, 143, 23);
		contentPane.add(btnEventosInscritos);

		JButton btnHistorico = new JButton("Histórico de Participação");
		btnHistorico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listarHistoricoParticipacao();
			}
		});
		btnHistorico.setBounds(266, 11, 158, 23);
		contentPane.add(btnHistorico);

		JPanel pnlEventos = new JPanel();
		pnlEventos.setBorder(new TitledBorder(null, "Eventos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlEventos.setBounds(0, 69, 434, 192);
		contentPane.add(pnlEventos);
		pnlEventos.setLayout(null);

		scrollPaneInscritos = new JScrollPane();
		scrollPaneInscritos.setBounds(10, 23, 414, 154);
		pnlEventos.add(scrollPaneInscritos);

		tblInscritos = new JTable();
		tblInscritos.setModel(new DefaultTableModel(
				new Object[][] {},
				new String[] {
						"Código", "Nome", "Descrição", "Data", "Duração", "Local", "Capacidade Máxima", "Status", "Categoria", "Preço", "Organizador"
				}
		));
		tblInscritos.getColumnModel().getColumn(6).setPreferredWidth(108);
		scrollPaneInscritos.setViewportView(tblInscritos);

		scrollPaneHistorico = new JScrollPane();
		scrollPaneHistorico.setBounds(10, 23, 414, 154);
		pnlEventos.add(scrollPaneHistorico);

		tblHistorico = new JTable();
		tblHistorico.setModel(new DefaultTableModel(
				new Object[][] {},
				new String[] {
						"Código", "Nome", "Descrição", "Data", "Duração", "Local", "Capacidade Máxima", "Status", "Categoria", "Preço", "Organizador"
				}
		));
		tblHistorico.getColumnModel().getColumn(6).setPreferredWidth(108);
		scrollPaneHistorico.setViewportView(tblHistorico);


		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new PrincipalWindowParticipante().setVisible(true);
				dispose();
			}
		});
		btnVoltar.setBounds(14, 11, 89, 23);
		contentPane.add(btnVoltar);

		JButton btnExportar = new JButton("Exportar para xls");
		btnExportar.setBounds(276, 45, 133, 23);
		contentPane.add(btnExportar);
		tblHistorico.getColumnModel().getColumn(6).setPreferredWidth(108);

		scrollPaneInscritos.setVisible(true);
		scrollPaneHistorico.setVisible(false);
	}
}