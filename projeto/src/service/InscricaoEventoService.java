package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import entities.Evento;
import entities.InscricaoEvento;
import entities.Participante;
import entities.Pessoa;
import entities.SessaoParticipante;
import entities.StatusEvento;
import entities.StatusInscricao;
import service.EventoService;
import service.InscricaoEventoService;

import javax.swing.JTable;

public class InscricaoEventoWindow extends JFrame {
    private JButton botaoInscrever;
    private JButton botaoVoltar;
    private JTable tblEventos;
    private EventoService eventoService;
    private JScrollPane scrollPane;
    private InscricaoEventoService inscricaoEventoService;
    
    public InscricaoEventoWindow() {

    	this.iniciarComponentes();
    	
    	this.eventoService = new EventoService();
    	this.inscricaoEventoService = new InscricaoEventoService();
    	
    	this.buscarEventos();
    }
    
    private void inscreverEmEvento() {
        int eventoSelecionado = tblEventos.getSelectedRow();

        if (eventoSelecionado == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um evento para se inscrever.");
            return;
        }

        try {
            int codigoEvento = (int) tblEventos.getValueAt(eventoSelecionado, 0);
            Evento evento = eventoService.buscarEventoPorCodigo(codigoEvento);

            if (!evento.getStatusEvento().equals(StatusEvento.ABERTO)) {
                JOptionPane.showMessageDialog(this, "Este evento não está aberto para inscrições.");
                return;
            }


            int quantidadeInscritos = eventoService.contarInscritosNoEvento(codigoEvento);
            if (quantidadeInscritos >= evento.getCapacidadeMaxima()) {
                JOptionPane.showMessageDialog(this, "A capacidade máxima deste evento foi atingida.");
                return;
            }

            Participante participante = SessaoParticipante.getParticipanteLogado();
            boolean jaInscrito = inscricaoEventoService.verificarInscricaoExistente(participante.getCodigoPessoa(), evento.getCodigoEvento());

            if (jaInscrito) {
                JOptionPane.showMessageDialog(this, "Você já está inscrito neste evento.");
                return;
            }
            
            new StatusInscricaoWindow(evento, participante).setVisible(true);
/*            InscricaoEvento inscricao = new InscricaoEvento();
            
            inscricao.setEvento(evento);
            inscricao.setParticipante(participante);
            inscricao.setDataInscricao(new java.sql.Date(System.currentTimeMillis()));
            inscricao.setStatusInscricao(StatusInscricao.PENDENTE_DE_PAGAMENTO);
            inscricao.setPresencaConfirmada(false);

            this.inscricaoEventoService.cadastrar(inscricao);

            JOptionPane.showMessageDialog(this, "Inscrição realizada com sucesso!");
*/
            
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao inscrever-se no evento: " + ex.getMessage());
        }
    }

    
	private void buscarEventos() {
		
		try {
			
			DefaultTableModel modelo = (DefaultTableModel) tblEventos.getModel();
			modelo.fireTableDataChanged();
			modelo.setRowCount(0);
			
			List<Evento> eventos = this.eventoService.listarEventos();
			
			for (Evento evento : eventos) {
				
				modelo.addRow(new Object[] {
						
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
						//evento.getAdministrador().getCodigoPessoa()
				});
			}
		} catch (SQLException | IOException e) {
			
			System.out.println(e.getMessage());
			System.out.println("Erro ao obter eventos.");
			
		}
	}
		
		public void iniciarComponentes() {
			
	        setTitle("Inscrição em Evento");
	        setSize(500, 400);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);

	        JPanel painel = new JPanel();
	        painel.setLayout(null);
	        getContentPane().add(painel);

	        JLabel rotuloTitulo = new JLabel("Eventos Disponíveis:");
	        rotuloTitulo.setBounds(50, 30, 200, 25);
	        painel.add(rotuloTitulo);

	        botaoInscrever = new JButton("Inscrever-se");
	        botaoInscrever.setBounds(50, 250, 106, 30);
	        painel.add(botaoInscrever);

	        botaoVoltar = new JButton("Voltar");
	        botaoVoltar.setBounds(340, 250, 106, 30);
	        painel.add(botaoVoltar);
	        
	        JPanel panel = new JPanel();
	        panel.setBounds(60, 66, 386, 171);
	        painel.add(panel);
	        panel.setLayout(null);
	        
	        scrollPane = new JScrollPane();
	        scrollPane.setBounds(10, 11, 366, 160);
	        panel.add(scrollPane);
	        
	        tblEventos = new JTable();
	        scrollPane.setViewportView(tblEventos);
	        tblEventos.setModel(new DefaultTableModel(
	        	new Object[][] {
	        	},
	        	new String[] {
	        		"Codigo", "Nome", "Descricao", "Data", "Duracao", "Local", "Capacidade Maxima", "Status", "Categoria", "Preco"
	        	}
	        ));

	        botaoInscrever.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	inscreverEmEvento();

	            }
	        });

	        botaoVoltar.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                new PrincipalWindowParticipante().setVisible(true);
	                dispose();
	            }
	        });
		}
}