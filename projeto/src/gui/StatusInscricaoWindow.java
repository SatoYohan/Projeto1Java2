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
    private JButton botaoConfirmar;
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
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        JPanel painel = new JPanel();
        painel.setBounds(0, 0, 384, 161);
        getContentPane().add(painel);
        painel.setLayout(null);

        JLabel labelStatus = new JLabel("Selecione o status da inscrição:");
        labelStatus.setBounds(26, 40, 170, 33);
        painel.add(labelStatus);

        comboStatus = new JComboBox<>(new String[]{"PENDENTE_DE_PAGAMENTO", "ATIVA"});
        comboStatus.setBounds(206, 40, 170, 33);
        painel.add(comboStatus);

        botaoConfirmar = new JButton("Confirmar");
        botaoConfirmar.setBounds(26, 103, 320, 33);
        painel.add(botaoConfirmar);

        botaoConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String statusSelecionado = (String) comboStatus.getSelectedItem();
                StatusInscricao status = StatusInscricao.valueOf(statusSelecionado);
                inscreverParticipante(status);
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

            dispose();
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao inscrever-se no evento: " + ex.getMessage());
        }
    }
}
