package gui;

import javax.swing.*;
import service.PessoaService;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class LoginWindow extends JFrame {
    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JButton botaoEntrar;
    private JButton botaoCadastrar;
    private PessoaService pessoaService;

    public LoginWindow() {
        this.iniciarComponentes();
        this.pessoaService = new PessoaService(); // A classe de serviço
    }

    private boolean validarLogin(String email, String senha) throws SQLException, IOException {
        // Chama o serviço para verificar o login no banco
        return pessoaService.validarCredenciais(email, senha);
    }

    private void iniciarComponentes() {
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

        // Ação do botão "Entrar"
        botaoEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String email = campoEmail.getText();
                    String senha = new String(campoSenha.getPassword());

                    try {
						if (validarLogin(email, senha)) {
						    // Login válido, abre a janela principal
						    JOptionPane.showMessageDialog(LoginWindow.this, "Login realizado para: " + email);
						    new PrincipalWindow().setVisible(true);
						    dispose(); // Fecha a janela de login
						} else {
						    // Senha ou email incorretos
						    JOptionPane.showMessageDialog(LoginWindow.this, 
						        "Email ou senha incorretos.", "Erro", JOptionPane.ERROR_MESSAGE);
						}
					} catch (HeadlessException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(LoginWindow.this, 
					        "Email ou senha incorretos.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Ação do botão "Cadastrar"
        botaoCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CadastroWindow().setVisible(true);
                dispose(); // Fecha a janela de login e abre a de cadastro
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginWindow().setVisible(true));
    }
}
