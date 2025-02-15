package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class LoginWindow extends JFrame {
    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JButton botaoEntrar;
    private JButton botaoCadastrar;

    public LoginWindow() {
        setTitle("Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(null);
        add(painel);

        JLabel rotuloEmail = new JLabel("Email:");
        rotuloEmail.setBounds(50, 50, 80, 25);
        painel.add(rotuloEmail);

        campoEmail = new JTextField();
        campoEmail.setBounds(150, 50, 200, 25);
        painel.add(campoEmail);

        JLabel rotuloSenha = new JLabel("Senha:");
        rotuloSenha.setBounds(50, 100, 80, 25);
        painel.add(rotuloSenha);

        campoSenha = new JPasswordField();
        campoSenha.setBounds(150, 100, 200, 25);
        painel.add(campoSenha);

        botaoEntrar = new JButton("Entrar");
        botaoEntrar.setBounds(50, 150, 100, 30);
        painel.add(botaoEntrar);

        botaoCadastrar = new JButton("Cadastrar");
        botaoCadastrar.setBounds(200, 150, 100, 30);
        painel.add(botaoCadastrar);

        botaoEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = campoEmail.getText();
                String senha = new String(campoSenha.getPassword());
                JOptionPane.showMessageDialog(LoginWindow.this, "Login realizado para: " + email);
                new PrincipalWindow().setVisible(true);
                dispose();
            }
        });

        botaoCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CadastroWindow().setVisible(true);
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginWindow().setVisible(true));
    }
}
