package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class GerenciarEventoWindow extends JFrame {

    private JButton botaoGerenciarEventos;
    private JButton botaoInscreverEvento;
    private JButton botaoRelatorios;
    private JButton botaoLogout;

    public GerenciarEventoWindow() {
        setTitle("Sistema de Gerenciamento de Eventos");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(null);
        getContentPane().add(painel);

        botaoGerenciarEventos = new JButton("Gerenciar Eventos");
        botaoGerenciarEventos.setBounds(150, 50, 200, 30);
        painel.add(botaoGerenciarEventos);

        botaoInscreverEvento = new JButton("Inscrever-se em Evento");
        botaoInscreverEvento.setBounds(150, 100, 200, 30);
        painel.add(botaoInscreverEvento);

        botaoRelatorios = new JButton("Relatórios");
        botaoRelatorios.setBounds(150, 150, 200, 30);
        painel.add(botaoRelatorios);

        botaoLogout = new JButton("Logout");
        botaoLogout.setBounds(150, 200, 200, 30);
        painel.add(botaoLogout);

        botaoGerenciarEventos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InscricaoEventoWindow().setVisible(true);
                dispose();
            }
        });

        botaoInscreverEvento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InscricaoEventoWindow().setVisible(true);
                dispose();
            }
        });

        botaoLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginWindow().setVisible(true);
                dispose();
            }
        });
    }
}
