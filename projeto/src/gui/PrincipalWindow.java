package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class PrincipalWindow extends JFrame {
    private JButton botaoGerenciarEventos;
    private JButton botaoInscreverEvento;
    private JButton botaoRelatorios;
    private JButton botaoLogout;

    public PrincipalWindow() {
        setTitle("Sistema de Gerenciamento de Eventos");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(null);
        add(painel);

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
