package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class InscricaoEventoWindow extends JFrame {
    private JButton botaoInscrever;
    private JButton botaoVoltar;

    public InscricaoEventoWindow() {
        setTitle("Inscrição em Evento");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(null);
        add(painel);

        JLabel rotuloTitulo = new JLabel("Eventos Disponíveis:");
        rotuloTitulo.setBounds(50, 30, 200, 25);
        painel.add(rotuloTitulo);

        String[] eventos = {"Evento 1", "Evento 2", "Evento 3"};
        JList<String> listaEventos = new JList<>(eventos);
        JScrollPane painelRolagem = new JScrollPane(listaEventos);
        painelRolagem.setBounds(50, 60, 400, 150);
        painel.add(painelRolagem);

        botaoInscrever = new JButton("Inscrever-se");
        botaoInscrever.setBounds(50, 250, 150, 30);
        painel.add(botaoInscrever);

        botaoVoltar = new JButton("Voltar");
        botaoVoltar.setBounds(250, 250, 150, 30);
        painel.add(botaoVoltar);

        botaoInscrever.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String eventoSelecionado = listaEventos.getSelectedValue();
                if (eventoSelecionado != null) {
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
