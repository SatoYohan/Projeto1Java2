package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;

public class PrincipalWindowParticipante extends JFrame {
    private JButton botaoInscreverEvento;
    private JButton botaoRelatorios;
    private JButton botaoLogout;

    public PrincipalWindowParticipante() {
        setTitle("Sistema de Gerenciamento de Eventos");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(null);
        getContentPane().add(painel);

        botaoInscreverEvento = new JButton("Inscrever-se em Evento");
        botaoInscreverEvento.setBounds(136, 54, 231, 30);
        painel.add(botaoInscreverEvento);

        botaoRelatorios = new JButton("Relatórios");
        botaoRelatorios.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    new RelatorioParticipanteWindow().setVisible(true);
                } catch (SQLException | IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                dispose();

            }
        });
        botaoRelatorios.setBounds(136, 150, 231, 30);
        painel.add(botaoRelatorios);

        botaoLogout = new JButton("Logout");
        botaoLogout.setBounds(136, 200, 231, 30);
        painel.add(botaoLogout);

        JButton botaoCancelarConfirmar = new JButton("Cancelar Inscrição / Confirmar Presença");
        botaoCancelarConfirmar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    new CancelarInscricaoWindow().setVisible(true);
                } catch (SQLException | IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                dispose();
            }
        });
        botaoCancelarConfirmar.setFont(new Font("Tahoma", Font.PLAIN, 11));
        botaoCancelarConfirmar.setBounds(136, 95, 231, 46);
        painel.add(botaoCancelarConfirmar);

        botaoInscreverEvento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new InscricaoEventoWindow().setVisible(true);
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