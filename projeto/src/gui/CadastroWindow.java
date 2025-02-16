package gui;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

import entities.Administrador;
import entities.Participante;
import entities.Pessoa;
import service.AdministradorService;
import service.ParticipanteService;
import service.PessoaService;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CadastroWindow extends JFrame {

    private JTextField campoNome;
    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JComboBox<String> caixaTipoUsuario;
    private JButton botaoCadastrar;
    private JButton botaoVoltar;
    private PessoaService pessoaService;
    private ParticipanteService participanteService;
    private AdministradorService administradorService;

    // -- Campos específicos para Administrador
    private JLabel labelCargo;
    private JTextField campoCargo;
    private JLabel labelDataContratacao;
    private JFormattedTextField campoDataContratacao; // Mask para data

    // -- Campos específicos para Participante
    private JLabel labelDataNascimento;
    private JFormattedTextField campoDataNascimento;  // Mask para data
    private JLabel labelCpf;
    private JFormattedTextField campoCpf;             // Mask para CPF

    public CadastroWindow() {
    	
    	this.iniciarComponentes();
        this.pessoaService = new PessoaService();
        this.participanteService = new ParticipanteService();
        this.administradorService = new AdministradorService();
    }

    private void cadastrarUsuario() throws SQLException, IOException {
    	
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Pessoa pessoa = new Pessoa();
			
			pessoa.setNomeCompleto(this.campoNome.getText());
			pessoa.setEmail(this.campoEmail.getText());
			pessoa.setSenha(new String(campoSenha.getPassword()));
			
            // Verificar o tipo de usuário e definir o ID da função (Administrador = 1, Participante = 2)
            String tipoUsuario = (String) caixaTipoUsuario.getSelectedItem();
            int idFuncao = 0; // Valor padrão para erro ou caso não seja definido
            if ("Administrador".equals(tipoUsuario)) {
                idFuncao = 2;
            } else if ("Participante".equals(tipoUsuario)) {
                idFuncao = 1;
            }
            pessoa.setIdFuncao(idFuncao);

//            pessoaService.cadastrarUsuario(pessoa);
            int idPessoaGerado = pessoaService.cadastrarUsuario(pessoa);
            pessoa.setCodigoPessoa(idPessoaGerado);
			
			//aluno.setDataIngresso(new java.sql.Date(sdf.parse(this.ftxtDataIngresso.getText()).getTime()));
			
			
            if (idFuncao == 2) {
            	
            	Administrador administrador = new Administrador();
            	
            	administrador.setCodigoPessoa(idPessoaGerado);
            	administrador.setCargo(campoCargo.getText());
            	administrador.setDataContratacao(new java.sql.Date(sdf.parse(campoDataContratacao.getText()).getTime()));
            	

            	
            	administradorService.cadastrar(administrador);
               
            } else { // Para Participante
            	
            	Participante participante = new Participante();
            	
            	participante.setCodigoPessoa(pessoa.getCodigoPessoa());
            	participante.setDataNascimento(new java.sql.Date(sdf.parse(campoDataNascimento.getText()).getTime()));
            	participante.setCpf(campoCpf.getText());

            	
            	participanteService.cadastrar(participante);
            }

            
            // Após o cadastro, pode redirecionar para a tela de login
            new LoginWindow().setVisible(true);
            dispose();
            
		} catch (ParseException ee) {
			
			System.out.println("Erro: Data não informada!");
		} catch (Exception ee) {
			
			System.out.println("Erro: " + ee.getMessage());
		}
    }
    
    private void iniciarComponentes() {
        setTitle("Cadastro de Usuário");
        setSize(450, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(null);
        setContentPane(painel);
        

        // -------------------- CAMPOS BÁSICOS --------------------
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

        String[] tiposUsuarios = {"", "Administrador", "Participante"};
        caixaTipoUsuario = new JComboBox<>(tiposUsuarios);
        caixaTipoUsuario.setBounds(180, 150, 200, 25);
        painel.add(caixaTipoUsuario);

        // -------------------- MÁSCARAS --------------------
        // Máscara para data (dd/MM/yyyy)
        MaskFormatter maskData = null;
        // Máscara para CPF (###.###.###-##)
        MaskFormatter maskCpf = null;

        try {
            maskData = new MaskFormatter("##/##/####");
            maskData.setPlaceholderCharacter('_');

            maskCpf = new MaskFormatter("###.###.###-##");
            maskCpf.setPlaceholderCharacter('_');
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // -------------------- CAMPOS ADMINISTRADOR --------------------
        labelCargo = new JLabel("Cargo:");
        labelCargo.setBounds(50, 190, 120, 25);
        painel.add(labelCargo);

        campoCargo = new JTextField();
        campoCargo.setBounds(180, 190, 200, 25);
        painel.add(campoCargo);

        labelDataContratacao = new JLabel("Data Contratação:");
        labelDataContratacao.setBounds(50, 230, 120, 25);
        painel.add(labelDataContratacao);

        campoDataContratacao = new JFormattedTextField(maskData);
        campoDataContratacao.setBounds(180, 230, 200, 25);
        painel.add(campoDataContratacao);

        // -------------------- CAMPOS PARTICIPANTE --------------------
        labelDataNascimento = new JLabel("Data Nascimento:");
        labelDataNascimento.setBounds(50, 190, 120, 25);
        painel.add(labelDataNascimento);

        campoDataNascimento = new JFormattedTextField(maskData);
        campoDataNascimento.setBounds(180, 190, 200, 25);
        painel.add(campoDataNascimento);

        labelCpf = new JLabel("CPF:");
        labelCpf.setBounds(50, 230, 120, 25);
        painel.add(labelCpf);

        campoCpf = new JFormattedTextField(maskCpf);
        campoCpf.setBounds(180, 230, 200, 25);
        painel.add(campoCpf);

        // Inicialmente, escondemos os campos específicos
        exibirCamposAdministrador(false);
        exibirCamposParticipante(false);

        // -------------------- AÇÃO DO COMBOBOX --------------------
        caixaTipoUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipo = (String) caixaTipoUsuario.getSelectedItem();
                if ("Administrador".equals(tipo)) {
                    exibirCamposAdministrador(true);
                    exibirCamposParticipante(false);
                } else {
                    exibirCamposAdministrador(false);
                    exibirCamposParticipante(true);
                }
            }
        });

        // -------------------- BOTÕES --------------------
        botaoCadastrar = new JButton("Cadastrar");
        botaoCadastrar.setBounds(50, 280, 120, 30);
        painel.add(botaoCadastrar);

        botaoVoltar = new JButton("Voltar");
        botaoVoltar.setBounds(200, 280, 120, 30);
        painel.add(botaoVoltar);

        // Ação do botão "Cadastrar"
        botaoCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
					cadastrarUsuario();
				} catch (SQLException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

        	/*	
                // Campos básicos
                String nome = campoNome.getText();
                String email = campoEmail.getText();
                String senha = new String(campoSenha.getPassword());
                String tipoUsuario = (String) caixaTipoUsuario.getSelectedItem();

                // Dependendo do tipo, pegamos os campos específicos
                if ("Administrador".equals(tipoUsuario)) {
                    String cargo = campoCargo.getText();
                    String dataContratacao = campoDataContratacao.getText(); // Ex.: "12/05/2025"

                    // -> Aqui você faria a validação e conversão da dataContratacao
                    // -> Chamaria o service/DAO para salvar no banco
                    JOptionPane.showMessageDialog(CadastroWindow.this,
                        "Administrador cadastrado!\n" +
                        "Nome: " + nome + "\n" +
                        "Email: " + email + "\n" +
                        "Cargo: " + cargo + "\n" +
                        "Data Contratação: " + dataContratacao
                    );

                } else { // Participante
                    String dataNascimento = campoDataNascimento.getText();  // Ex.: "15/09/1990"
                    String cpf = campoCpf.getText();                        // Ex.: "123.456.789-01"

                    // -> Aqui você faria a validação e conversão da dataNascimento
                    // -> Chamaria o service/DAO para salvar no banco
                    JOptionPane.showMessageDialog(CadastroWindow.this,
                        "Participante cadastrado!\n" +
                        "Nome: " + nome + "\n" +
                        "Email: " + email + "\n" +
                        "Data Nascimento: " + dataNascimento + "\n" +
                        "CPF: " + cpf
                    );
                }

                // Após cadastrar, volte para a tela de login, se esse for o fluxo
                new LoginWindow().setVisible(true);
                dispose();
                */
            }
        });

        // Botão Voltar
        botaoVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginWindow().setVisible(true);
                dispose();
            }
        });
        

    }
    /**
     * Exibe ou oculta os campos de Administrador.
     */
    private void exibirCamposAdministrador(boolean visivel) {
        labelCargo.setVisible(visivel);
        campoCargo.setVisible(visivel);
        labelDataContratacao.setVisible(visivel);
        campoDataContratacao.setVisible(visivel);
    }

    /**
     * Exibe ou oculta os campos de Participante.
     */
    private void exibirCamposParticipante(boolean visivel) {
        labelDataNascimento.setVisible(visivel);
        campoDataNascimento.setVisible(visivel);
        labelCpf.setVisible(visivel);
        campoCpf.setVisible(visivel);
    }
    

}
