package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class PrincipalWindowAdministrador extends JFrame {
    private JButton botaoGerenciarEventos;
    private JButton botaoRelatorios;
    private JButton botaoLogout;

    public PrincipalWindowAdministrador() {
        setTitle("Sistema de Gerenciamento de Eventos");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(null);
        getContentPane().add(painel);

        botaoGerenciarEventos = new JButton("Gerenciar Eventos");
        botaoGerenciarEventos.setBounds(150, 98, 200, 30);
        painel.add(botaoGerenciarEventos);

        botaoRelatorios = new JButton("Relatórios");
        botaoRelatorios.setBounds(150, 150, 200, 30);
        painel.add(botaoRelatorios);

        botaoLogout = new JButton("Logout");
        botaoLogout.setBounds(150, 200, 200, 30);
        painel.add(botaoLogout);

        botaoGerenciarEventos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new GerenciarEventoWindow().setVisible(true);
                } catch (SQLException | IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
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