package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class CadastroWindow extends JFrame {
    private JTextField campoNome;
    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JComboBox<String> caixaTipoUsuario;
    private JButton botaoCadastrar;
    private JButton botaoVoltar;

    public CadastroWindow() {
        setTitle("Cadastro de Usuário");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(null);
        add(painel);

        JLabel rotuloNome = new JLabel("Nome Completo:");
        rotuloNome.setBounds(50, 30, 120, 25);
        painel.add(rotuloNome);

        campoNome = new JTextField();
        campoNome.setBounds(180, 30, 200, 25);
        painel.add(campoNome);

        JLabel rotuloEmail = new JLabel("Email:");
        rotuloEmail.setBounds(50, 70, 120, 25);
        painel.add(rotuloEmail);

        campoEmail = new JTextField();
        campoEmail.setBounds(180, 70, 200, 25);
        painel.add(campoEmail);

        JLabel rotuloSenha = new JLabel("Senha:");
        rotuloSenha.setBounds(50, 110, 120, 25);
        painel.add(rotuloSenha);

        campoSenha = new JPasswordField();
        campoSenha.setBounds(180, 110, 200, 25);
        painel.add(campoSenha);

        JLabel rotuloTipoUsuario = new JLabel("Tipo de Usuário:");
        rotuloTipoUsuario.setBounds(50, 150, 120, 25);
        painel.add(rotuloTipoUsuario);

        String[] tiposUsuarios = {"Administrador", "Participante"};
        caixaTipoUsuario = new JComboBox<>(tiposUsuarios);
        caixaTipoUsuario.setBounds(180, 150, 200, 25);
        painel.add(caixaTipoUsuario);

        botaoCadastrar = new JButton("Cadastrar");
        botaoCadastrar.setBounds(50, 200, 120, 30);
        painel.add(botaoCadastrar);

        botaoVoltar = new JButton("Voltar");
        botaoVoltar.setBounds(200, 200, 120, 30);
        painel.add(botaoVoltar);

        botaoCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = campoNome.getText();
                String email = campoEmail.getText();
                String senha = new String(campoSenha.getPassword());
                String tipoUsuario = (String) caixaTipoUsuario.getSelectedItem();
                JOptionPane.showMessageDialog(CadastroWindow.this, "Usuário cadastrado: " + nome);
                new LoginWindow().setVisible(true);
                dispose();
            }
        });

        botaoVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginWindow().setVisible(true);
                dispose();
            }
        });
    }
}
