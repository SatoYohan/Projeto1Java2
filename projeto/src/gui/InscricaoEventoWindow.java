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
import service.EventoService;

import javax.swing.JTable;

public class InscricaoEventoWindow extends JFrame {
    private JButton botaoInscrever;
    private JButton botaoVoltar;
    private JTable tblEventos;
    private EventoService eventoService;
    private JScrollPane scrollPane;

    public InscricaoEventoWindow() {

    	this.iniciarComponentes();
    	
    	this.eventoService = new EventoService();
    	
    	this.buscarEventos();
    }
    
	private void buscarEventos() {
		
		try {
			
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			
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
						evento.getCapacidadeMaxima(),
						evento.getStatusEvento(),
						evento.getCategoriaEvento(),
						evento.getPrecoEvento()
				});
			}
		} catch (SQLException | IOException e) {
			
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
	        botaoInscrever.setBounds(50, 250, 150, 30);
	        painel.add(botaoInscrever);

	        botaoVoltar = new JButton("Voltar");
	        botaoVoltar.setBounds(250, 250, 150, 30);
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
	                int eventoSelecionado = tblEventos.getSelectedRow();
	                if (eventoSelecionado != -1) {
	                    JOptionPane.showMessageDialog(InscricaoEventoWindow.this, 
	                        "Inscrição realizada no evento: " + eventoSelecionado);
	                } else {
	                    JOptionPane.showMessageDialog(InscricaoEventoWindow.this, 
	                        "Selecione um evento para se inscrever.");
	                }
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
