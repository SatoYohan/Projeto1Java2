package gui;

import javax.swing.*;
import service.InscricaoEventoService;
import entities.InscricaoEvento;
import entities.Participante;
import entities.Evento;
import entities.StatusInscricao;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.io.IOException;

public class StatusInscricaoWindow extends JFrame {

    private JComboBox<String> comboStatus;
    private JButton botaoConfirmar, botaoVoltar;
    private Evento evento;
    private Participante participante;
    private InscricaoEventoService inscricaoEventoService;

    public StatusInscricaoWindow(Evento evento, Participante participante) {
        this.evento = evento;
        this.participante = participante;
        this.inscricaoEventoService = new InscricaoEventoService();
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Selecione o Status da Inscrição");
        setSize(500, 250); // Ajuste de tamanho
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel principal
        JPanel painel = new JPanel();
        painel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Label
        JLabel labelStatus = new JLabel("Selecione o status da inscrição:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        painel.add(labelStatus, gbc);

        // ComboBox
        comboStatus = new JComboBox<>(new String[]{"PENDENTE_DE_PAGAMENTO", "ATIVA"});
        gbc.gridx = 1;
        gbc.gridy = 0;
        painel.add(comboStatus, gbc);

        // Botão Confirmar
        botaoConfirmar = new JButton("Confirmar");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        painel.add(botaoConfirmar, gbc);

        // Botão Voltar
        botaoVoltar = new JButton("Voltar");
        gbc.gridy = 2;
        painel.add(botaoVoltar, gbc);

        add(painel, BorderLayout.CENTER);

        // Listeners
        botaoConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String statusSelecionado = (String) comboStatus.getSelectedItem();
                StatusInscricao status = StatusInscricao.valueOf(statusSelecionado);
                inscreverParticipante(status);
            }
        });

        botaoVoltar.addActionListener(new ActionListener() {
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
    }

    private void inscreverParticipante(StatusInscricao status) {
        try {
            InscricaoEvento inscricao = new InscricaoEvento();
            inscricao.setEvento(evento);
            inscricao.setParticipante(participante);
            inscricao.setDataInscricao(new java.sql.Date(System.currentTimeMillis()));
            inscricao.setStatusInscricao(status);
            inscricao.setPresencaConfirmada(false);

            inscricaoEventoService.cadastrar(inscricao);

            JOptionPane.showMessageDialog(this, "Inscrição realizada com sucesso!");

            // Retorna para a tela de inscrição
            new InscricaoEventoWindow().setVisible(true);
            dispose();
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao inscrever-se no evento: " + ex.getMessage());
        }
    }
}
