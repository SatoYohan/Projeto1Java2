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
import entities.SessaoParticipante;
import service.InscricaoEventoService;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.awt.event.ActionEvent;

public class RelatorioAdministradorWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tblRelatorio;
	private JScrollPane scrollPaneRelatorio;
	private InscricaoEventoService inscricaoEventoService;

	public RelatorioAdministradorWindow() {
		setTitle("Módulo de Relatórios");
		this.iniciarComponentes();
		this.inscricaoEventoService = new InscricaoEventoService();
	}

	private void listarParticipantesEvento() {

	}

	private void listarEventosPopulares() {

	}

	private void listarEventosNaoOcorridos() {

	}

	private void listarDetalhesEvento() {

	}

	// Initialize GUI components
	private void iniciarComponentes() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Buttons for each functionality
		JButton btnParticipantesEvento = new JButton("Participantes do Evento");
		btnParticipantesEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnParticipantesEvento.setBounds(30, 20, 200, 23);
		contentPane.add(btnParticipantesEvento);
		
		JButton btnEventosPopulares = new JButton("Eventos Populares");
		btnEventosPopulares.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnEventosPopulares.setBounds(240, 20, 180, 23);
		contentPane.add(btnEventosPopulares);
		
		JButton btnEventosNaoOcorridos = new JButton("Eventos Não Ocorridos");
		btnEventosNaoOcorridos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnEventosNaoOcorridos.setBounds(430, 20, 180, 23);
		contentPane.add(btnEventosNaoOcorridos);
		
		JButton btnDetalhesEvento = new JButton("Detalhes do Evento");
		btnDetalhesEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnDetalhesEvento.setBounds(30, 60, 200, 23);
		contentPane.add(btnDetalhesEvento);
		
		JPanel pnlRelatorio = new JPanel();
		pnlRelatorio.setBorder(new TitledBorder(null, "Relatório", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlRelatorio.setBounds(10, 100, 560, 240);
		contentPane.add(pnlRelatorio);
		pnlRelatorio.setLayout(null);
		
		scrollPaneRelatorio = new JScrollPane();
		scrollPaneRelatorio.setBounds(10, 23, 540, 190);
		pnlRelatorio.add(scrollPaneRelatorio);
		
		tblRelatorio = new JTable();
		tblRelatorio.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {
				"Código", "Nome", "Descrição", "Data", "Duração", "Local", "Capacidade Máxima", "Status", "Categoria", "Preço"
			}
		));
		scrollPaneRelatorio.setViewportView(tblRelatorio);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                new PrincipalWindowAdministrador().setVisible(true);
                dispose();
			}
		});
		btnVoltar.setBounds(267, 60, 140, 23);
		contentPane.add(btnVoltar);
	}
}
