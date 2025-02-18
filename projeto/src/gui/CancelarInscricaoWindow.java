package gui;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;

import entities.Evento;
import entities.InscricaoEvento;
import entities.Participante;
import entities.SessaoParticipante;
import service.EventoService;
import service.InscricaoEventoService;
import javax.swing.JScrollPane;

public class CancelarInscricaoWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tblInscritos;
	private JButton btnVoltar;
	private JButton btnCancelar;
	private JButton btnConfirmar;
	private InscricaoEventoService inscricaoEventoService;
	private JScrollPane scrollPane;
	private EventoService eventoService;
	
	public CancelarInscricaoWindow() throws SQLException, IOException {

		this.iniciarComponentes();
		
		this.inscricaoEventoService = new InscricaoEventoService();
		this.eventoService = new EventoService();

		this.listarEventos();

	}
	

	
	private void confirmarPresenca() {
	    int linhaSelecionada = tblInscritos.getSelectedRow();
	    if (linhaSelecionada == -1) {
	        JOptionPane.showMessageDialog(this, "Selecione uma inscrição para confirmar presença.", "Aviso", JOptionPane.WARNING_MESSAGE);
	        return;
	    }

	    try {
	        int codigoEvento = (int) tblInscritos.getValueAt(linhaSelecionada, 0);
	        Participante participanteLogado = SessaoParticipante.getParticipanteLogado();
	        Evento evento = eventoService.buscarEventoPorCodigo(codigoEvento);
	        
	        if (evento.getDataEvento().before(Timestamp.valueOf(LocalDateTime.now()))) {
	            JOptionPane.showMessageDialog(this, "Não é possível confirmar sua presença, pois o evento já ocorreu.", "Aviso", JOptionPane.WARNING_MESSAGE);
	            return;
	        }
	        
	        this.inscricaoEventoService.confirmarPresenca(participanteLogado.getCodigoPessoa(), codigoEvento);

	        JOptionPane.showMessageDialog(this, "Presença confirmada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
	        listarEventos();
	    } catch (SQLException | IOException e) {
	        JOptionPane.showMessageDialog(this, "Erro ao confirmar presença: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
	    }
	}

	private void cancelarInscricao() {
	    int linhaSelecionada = tblInscritos.getSelectedRow();
	    if (linhaSelecionada == -1) {
	        JOptionPane.showMessageDialog(this, "Selecione uma inscrição para cancelar.", "Aviso", JOptionPane.WARNING_MESSAGE);
	        return;
	    }

	    try {
	        int codigoEvento = (int) tblInscritos.getValueAt(linhaSelecionada, 0);
	        Participante participanteLogado = SessaoParticipante.getParticipanteLogado();

	        String statusEvento = inscricaoEventoService.buscarStatusEvento(codigoEvento);
	        Evento evento = eventoService.buscarEventoPorCodigo(codigoEvento);

	        if (!"Aberto".equalsIgnoreCase(statusEvento)) {
	            JOptionPane.showMessageDialog(this, "Só é possível cancelar inscrições em eventos com status 'Aberto'.", "Aviso", JOptionPane.WARNING_MESSAGE);
	            return;
	        }

	        
	        if (evento.getDataEvento().before(Timestamp.valueOf(LocalDateTime.now()))) {
	            JOptionPane.showMessageDialog(this, "Não é possível cancelar a inscrição, pois o evento já ocorreu.", "Aviso", JOptionPane.WARNING_MESSAGE);
	            return;
	        }

	        this.inscricaoEventoService.cancelarInscricao(participanteLogado.getCodigoPessoa(), codigoEvento);

	        JOptionPane.showMessageDialog(this, "Inscrição cancelada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
	        listarEventos();
	    } catch (SQLException | IOException e) {
	        JOptionPane.showMessageDialog(this, "Erro ao cancelar inscrição: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
	    }
	}


	private void listarEventos() {
		
		try {
			
			DefaultTableModel modelo = (DefaultTableModel) tblInscritos.getModel();
			modelo.fireTableDataChanged();
			modelo.setRowCount(0);
			
			Participante participanteLogado = SessaoParticipante.getParticipanteLogado();
			
			List<InscricaoEvento> inscricoes = this.inscricaoEventoService.buscarEventosPorParticipante(participanteLogado.getCodigoPessoa());
			
			for (InscricaoEvento inscricaoEvento : inscricoes) {
				
				modelo.addRow(new Object[] {
						
						inscricaoEvento.getEvento().getCodigoEvento(),
						inscricaoEvento.getEvento().getNomeEvento(),
						inscricaoEvento.getEvento().getDataEvento(),
						inscricaoEvento.getEvento().getStatusEvento(),
						inscricaoEvento.getStatusInscricao(),
						inscricaoEvento.isPresencaConfirmada()
				});
			}
		} catch (SQLException | IOException e) {
			
			System.out.println(e.getMessage());
			System.out.println("Erro ao obter eventos.");
			
		}
	}
	
	public void iniciarComponentes() {
		setTitle("Cancelar Inscrição / Confirmar Presença");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel pnlInscritos = new JPanel();
		pnlInscritos.setBorder(new TitledBorder(null, "Eventos Inscritos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlInscritos.setBounds(10, 11, 414, 195);
		contentPane.add(pnlInscritos);
		pnlInscritos.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 25, 394, 159);
		pnlInscritos.add(scrollPane);
		
		tblInscritos = new JTable();
		scrollPane.setViewportView(tblInscritos);
		tblInscritos.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"C\u00F3digo", "Nome", "Data Evento", "Status Evento", "Status Inscri\u00E7\u00E3o", "Presen\u00E7a Confirmada (1 - Sim, 0 - N\u00E3o)"
			}
		));
		tblInscritos.getColumnModel().getColumn(3).setPreferredWidth(100);
		tblInscritos.getColumnModel().getColumn(4).setPreferredWidth(103);
		
		btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
                new PrincipalWindowParticipante().setVisible(true);
                dispose();
			}
		});
		btnVoltar.setBounds(324, 227, 89, 23);
		contentPane.add(btnVoltar);
		
		btnCancelar = new JButton("Cancelar Inscrição");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelarInscricao();
			}
		});
		btnCancelar.setBounds(10, 227, 127, 23);
		contentPane.add(btnCancelar);
		
		btnConfirmar = new JButton("Confirmar Presença");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirmarPresenca();
			}
		});
		btnConfirmar.setBounds(147, 227, 167, 23);
		contentPane.add(btnConfirmar);
		

	}

}
