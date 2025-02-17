package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RelatorioParticipanteWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tblInscritos;
	private JTable tblHistorico;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public RelatorioParticipanteWindow() {
		
		this.iniciarComponentes();

	}
	
	private void listarHistoricoParticipacao() {
		
		tblInscritos.setVisible(false);
		tblHistorico.setVisible(true);
	}
	
	private void listarEventosInscritos() {
		
		tblInscritos.setVisible(true);
		tblHistorico.setVisible(false);
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
		btnEventosInscritos.setBounds(37, 23, 143, 23);
		contentPane.add(btnEventosInscritos);
		
		JButton btnHistorico = new JButton("Histórico de Participação");
		btnHistorico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listarHistoricoParticipacao();
			}
		});
		btnHistorico.setBounds(245, 23, 158, 23);
		contentPane.add(btnHistorico);
		
		JPanel pnlEventos = new JPanel();
		pnlEventos.setBorder(new TitledBorder(null, "Eventos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlEventos.setBounds(0, 69, 434, 192);
		contentPane.add(pnlEventos);
		pnlEventos.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 23, 414, 154);
		pnlEventos.add(scrollPane);
		
		tblInscritos = new JTable();
		scrollPane.setViewportView(tblInscritos);
		tblInscritos.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"C\u00F3digo", "Nome", "Descri\u00E7\u00E3o", "Data", "Dura\u00E7\u00E3o", "Local", "Capacidade M\u00E1xima", "Status", "Categoria", "Pre\u00E7o", "Organizador"
			}
		));
		tblInscritos.getColumnModel().getColumn(6).setPreferredWidth(108);
		
		tblHistorico = new JTable();
		scrollPane.setViewportView(tblHistorico);
		tblHistorico.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"C\\u00F3digo", "Nome", "Descri\u00E7\u00E3o", "Data", "Dura\u00E7\u00E3o", "Local", "Capacidade M\u00E1xima", "Status", "Categoria", "Pre\u00E7o", "Organizador"
			}
		));
		tblHistorico.getColumnModel().getColumn(6).setPreferredWidth(108);
		
		tblInscritos.setVisible(true);
		tblHistorico.setVisible(false);
	}
}
