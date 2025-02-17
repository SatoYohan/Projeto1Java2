package gui;

import dao.BancoDados;
import entities.Administrador;
import entities.CategoriaEvento;
import entities.Evento;
import entities.StatusEvento;
import service.EventoService;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.sql.Timestamp;

public class GerenciarEventoWindow extends JFrame {

    private JTabbedPane tabbedPane;

    // ---------------------------------------------
    // ABAS / PAINEIS
    // ---------------------------------------------
    private JPanel painelCadastrar;
    private JPanel painelAtualizar;
    private JPanel painelListar;

    // ---------------------------------------------
    // COMPONENTES DA ABA "CADASTRAR"
    // ---------------------------------------------
    private JTextField cadNome;
    private JTextField cadDescricao;
    private JFormattedTextField cadData;
    private JTextField cadDuracao;
    private JTextField cadLocal;
    private JTextField cadCapacidade;
    private JTextField cadPreco;
    private JComboBox<CategoriaEvento> cadComboCategoria;
    private JTextField cadCodigoAdmin;
    private JButton cadBotaoSalvar;

    // ---------------------------------------------
    // COMPONENTES DA ABA "ATUALIZAR/EXCLUIR"a
    // ---------------------------------------------
    private JTextField updCodigo;      
    private JButton updBotaoBuscar;
    private JTextField updNome;
    private JTextField updDescricao;
    private JTextField updData;
    private JTextField updDuracao;
    private JTextField updLocal;
    private JTextField updCapacidade;
    private JTextField updPreco;
    private JComboBox<CategoriaEvento> updComboCategoria;
    private JTextField updCodigoAdmin;
    private JComboBox<StatusEvento> updComboStatus; // Aqui podemos alterar o status
    private JButton updBotaoAtualizar;
    private JButton updBotaoExcluir;

    // ---------------------------------------------
    // COMPONENTES DA ABA "LISTAR"
    // ---------------------------------------------
    private JTextArea txtAreaListagem;
    private JButton btnListarEventos;

    // ---------------------------------------------
    // SERVICE
    // ---------------------------------------------
    private EventoService eventoService;

    public GerenciarEventoWindow() throws SQLException, IOException {
        setTitle("Gerenciar Eventos");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        this.eventoService = new EventoService();

        // Exemplo de uso de JTabbedPane (caso tenha várias abas)
        JTabbedPane tabbedPane = new JTabbedPane();
        add(tabbedPane);

        initAbaCadastrar(tabbedPane);
        //initAbaAtualizar(tabbedPane);
        //initAbaListar(tabbedPane);
    }

    // -------------------------------------------------------------------------
    // ABA "CADASTRAR"
    // -------------------------------------------------------------------------
    private void cadastrarEvento() throws SQLException, IOException {
        try {
            // Verificar se todos os campos obrigatórios estão preenchidos
            if (cadNome.getText().isEmpty() || cadDescricao.getText().isEmpty() ||
                cadData.getText().isEmpty() || cadDuracao.getText().isEmpty() ||
                cadLocal.getText().isEmpty() || cadCapacidade.getText().isEmpty() || 
                cadPreco.getText().isEmpty() || cadComboCategoria.getSelectedItem() == null || 
                cadCodigoAdmin.getText().isEmpty()) {
                
                JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
                return; 
            }

            // Validar e formatar a data
            String textoData = cadData.getText();
            if (textoData.contains("_")) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos de data/hora no formato yyyy-MM-dd HH:mm:ss.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (textoData.length() < 19) {
                JOptionPane.showMessageDialog(this, "Data/Hora incompleta! Use yyyy-MM-dd HH:mm:ss.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Timestamp dataValida;
            try {
                dataValida = Timestamp.valueOf(textoData);
            } catch (IllegalArgumentException exData) {
                JOptionPane.showMessageDialog(this, "Data/Hora inválida! Use valores reais (ex: 2025-12-31 13:45:00).", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Criação do objeto Evento
            Evento novoEvento = new Evento();
            novoEvento.setNomeEvento(cadNome.getText());
            novoEvento.setDescEvento(cadDescricao.getText());
            novoEvento.setDataEvento(dataValida);

            // Validar duração
            int duracao;
            try {
                duracao = Integer.parseInt(cadDuracao.getText());
            } catch (NumberFormatException ex2) {
                JOptionPane.showMessageDialog(this, "Duração inválida. Digite um número inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            novoEvento.setDuracaoEvento(duracao);

            novoEvento.setLocalEvento(cadLocal.getText());
            // Validar capacidade
            int capacidade;
            try {
                capacidade = Integer.parseInt(cadCapacidade.getText());
            } catch (NumberFormatException ex2) {
                JOptionPane.showMessageDialog(this, "Capacidade inválida. Digite um número inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            novoEvento.setCapacidadeMaxima(capacidade);

            // Validar preço
            float preco;
            try {
                preco = Float.parseFloat(cadPreco.getText());
            } catch (NumberFormatException ex2) {
                JOptionPane.showMessageDialog(this, "Preço inválido. Digite um valor numérico (ex: 99.90).", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            novoEvento.setPrecoEvento(preco);

            // Definir categoria
            novoEvento.setCategoriaEvento((CategoriaEvento) cadComboCategoria.getSelectedItem());

            // Validar e associar o administrador
            Administrador admin = new Administrador();
            try {
                admin.setCodigoPessoa(Integer.parseInt(cadCodigoAdmin.getText()));
            } catch (NumberFormatException ex2) {
                JOptionPane.showMessageDialog(this, "Código de administrador inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            novoEvento.setAdministrador(admin);

            // Chama o serviço para salvar o evento
            boolean sucesso = eventoService.cadastrarEvento(novoEvento);
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Evento cadastrado com sucesso!", "Cadastro", JOptionPane.INFORMATION_MESSAGE);
                limparCamposCadastro();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar evento.", "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException | IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro no banco de dados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    private void initAbaCadastrar(JTabbedPane tabbedPane) {
        painelCadastrar = new JPanel(null);

        // Labels e Campos
        JLabel lblNome = new JLabel("Título do Evento:");
        lblNome.setBounds(20, 20, 120, 25);
        painelCadastrar.add(lblNome);

        cadNome = new JTextField();
        cadNome.setBounds(150, 20, 250, 25);
        painelCadastrar.add(cadNome);

        JLabel lblDesc = new JLabel("Descrição:");
        lblDesc.setBounds(20, 60, 120, 25);
        painelCadastrar.add(lblDesc);

        cadDescricao = new JTextField();
        cadDescricao.setBounds(150, 60, 250, 25);
        painelCadastrar.add(cadDescricao);

        JLabel lblData = new JLabel("Data/Hora (yyyy-MM-dd HH:mm:ss):");
        lblData.setBounds(20, 100, 200, 25);
        painelCadastrar.add(lblData);

        // 1) Criar MaskFormatter para yyyy-MM-dd
        MaskFormatter maskDataHora = null;
        try {
            // 2025-12-31 13:45:00 => 19 posições
            maskDataHora = new MaskFormatter("####-##-## ##:##:##");
            maskDataHora.setPlaceholderCharacter('_');
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 2) Criar o JFormattedTextField com a máscara
        cadData = new JFormattedTextField(maskDataHora);
        cadData.setBounds(220, 100, 150, 25);
        painelCadastrar.add(cadData);

        JLabel lblDuracao = new JLabel("Duração (h):");
        lblDuracao.setBounds(20, 140, 120, 25);
        painelCadastrar.add(lblDuracao);

        cadDuracao = new JTextField();
        cadDuracao.setBounds(150, 140, 50, 25);
        painelCadastrar.add(cadDuracao);

        JLabel lblLocal = new JLabel("Local/Endereço:");
        lblLocal.setBounds(20, 180, 120, 25);
        painelCadastrar.add(lblLocal);

        cadLocal = new JTextField();
        cadLocal.setBounds(150, 180, 250, 25);
        painelCadastrar.add(cadLocal);

        JLabel lblCap = new JLabel("Capacidade:");
        lblCap.setBounds(20, 220, 120, 25);
        painelCadastrar.add(lblCap);

        cadCapacidade = new JTextField();
        cadCapacidade.setBounds(150, 220, 50, 25);
        painelCadastrar.add(cadCapacidade);

        JLabel lblPreco = new JLabel("Preço:");
        lblPreco.setBounds(20, 260, 120, 25);
        painelCadastrar.add(lblPreco);

        cadPreco = new JTextField();
        cadPreco.setBounds(150, 260, 80, 25);
        painelCadastrar.add(cadPreco);

        JLabel lblCat = new JLabel("Categoria:");
        lblCat.setBounds(20, 300, 120, 25);
        painelCadastrar.add(lblCat);

        cadComboCategoria = new JComboBox<>(CategoriaEvento.values());
        cadComboCategoria.setBounds(150, 300, 150, 25);
        painelCadastrar.add(cadComboCategoria);

        JLabel lblAdm = new JLabel("Organizador (Cód. Admin):");
        lblAdm.setBounds(20, 340, 160, 25);
        painelCadastrar.add(lblAdm);

        cadCodigoAdmin = new JTextField();
        cadCodigoAdmin.setBounds(180, 340, 60, 25);
        painelCadastrar.add(cadCodigoAdmin);

        // Botão Salvar
        cadBotaoSalvar = new JButton("Salvar Evento (Status: FECHADO)");
        cadBotaoSalvar.setBounds(20, 400, 280, 30);
        painelCadastrar.add(cadBotaoSalvar);

        // Ação do botão
        cadBotaoSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
					cadastrarEvento();
				} catch (SQLException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
                /* Cria objeto Evento e valida campos
                try {
                    Evento novo = new Evento();
                    novo.setNomeEvento(cadNome.getText());
                    novo.setDescEvento(cadDescricao.getText());

                    String textoData = cadData.getText();

                 // 1) Checar underscores
                 if (textoData.contains("_")) {
                     JOptionPane.showMessageDialog(
                         painelCadastrar,
                         "Preencha todos os campos de data/hora no formato yyyy-MM-dd HH:mm:ss.",
                         "Erro",
                         JOptionPane.ERROR_MESSAGE
                     );
                     return;
                 }

                 // 2) Checar tamanho (deveria ter 19 caracteres)
                 if (textoData.length() < 19) {
                     JOptionPane.showMessageDialog(
                         painelCadastrar,
                         "Data/Hora incompleta! Use yyyy-MM-dd HH:mm:ss.",
                         "Erro",
                         JOptionPane.ERROR_MESSAGE
                     );
                     return;
                 }

                 // 3) Tentar converter
                 Timestamp dataValida;
                 try {
                     dataValida = Timestamp.valueOf(textoData);
                 } catch (IllegalArgumentException exData) {
                     JOptionPane.showMessageDialog(
                         painelCadastrar,
                         "Data/Hora inválida! Use valores reais (ex: 2025-12-31 13:45:00).",
                         "Erro",
                         JOptionPane.ERROR_MESSAGE
                     );
                     return;
                 }
                 novo.setDataEvento(dataValida);

                    // Duração
                    int duracao;
                    try {
                        duracao = Integer.parseInt(cadDuracao.getText());
                    } catch (NumberFormatException ex2) {
                        JOptionPane.showMessageDialog(painelCadastrar,
                            "Duração inválida. Digite um número inteiro.",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                    novo.setDuracaoEvento(duracao);

                    // Local
                    novo.setLocalEvento(cadLocal.getText());

                    // Capacidade
                    int cap;
                    try {
                        cap = Integer.parseInt(cadCapacidade.getText());
                    } catch (NumberFormatException ex2) {
                        JOptionPane.showMessageDialog(painelCadastrar,
                            "Capacidade inválida. Digite um número inteiro.",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                    novo.setCapacidadeMaxima(cap);

                    // Preço
                    float preco;
                    try {
                        preco = Float.parseFloat(cadPreco.getText());
                    } catch (NumberFormatException ex2) {
                        JOptionPane.showMessageDialog(painelCadastrar,
                            "Preço inválido. Digite um valor numérico (ex: 99.90).",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                    novo.setPrecoEvento(preco);

                    // Categoria
                    novo.setCategoriaEvento((CategoriaEvento) cadComboCategoria.getSelectedItem());

                    // Organizador
                    Administrador admin = new Administrador();
                    try {
                        admin.setCodigoPessoa(Integer.parseInt(cadCodigoAdmin.getText()));
                    } catch (NumberFormatException ex2) {
                        JOptionPane.showMessageDialog(painelCadastrar,
                            "Código de administrador inválido.",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                    novo.setAdministrador(admin);

                    // Chama o service (que definirá status como FECHADO)
                    boolean ok = eventoService.cadastrarEvento(novo);
                    if (ok) {
                        JOptionPane.showMessageDialog(painelCadastrar,
                            "Evento cadastrado com sucesso (status FECHADO)!");
                        limparCamposCadastro();
                    } else {
                        JOptionPane.showMessageDialog(painelCadastrar,
                            "Erro ao cadastrar evento!",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }

                } catch (SQLException | IOException ex) {
                    JOptionPane.showMessageDialog(painelCadastrar,
                        "Erro no banco de dados: " + ex.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }*/
        });

        tabbedPane.addTab("Cadastrar Evento", painelCadastrar);
    }

    private void limparCamposCadastro() {
        cadNome.setText("");
        cadDescricao.setText("");
        cadData.setText("");
        cadDuracao.setText("");
        cadLocal.setText("");
        cadCapacidade.setText("");
        cadPreco.setText("");
        cadComboCategoria.setSelectedIndex(0);
        cadCodigoAdmin.setText("");
    }


    // -------------------------------------------------------------------------
    // ABA "ATUALIZAR/EXCLUIR"
    // -------------------------------------------------------------------------
    private void initAbaAtualizar() {
        painelAtualizar = new JPanel(null);

        JLabel lblCodigo = new JLabel("Código do Evento:");
        lblCodigo.setBounds(20, 20, 120, 25);
        painelAtualizar.add(lblCodigo);

        updCodigo = new JTextField();
        updCodigo.setBounds(150, 20, 60, 25);
        painelAtualizar.add(updCodigo);

        updBotaoBuscar = new JButton("Buscar");
        updBotaoBuscar.setBounds(220, 20, 100, 25);
        painelAtualizar.add(updBotaoBuscar);

        JLabel lblNome = new JLabel("Título:");
        lblNome.setBounds(20, 60, 120, 25);
        painelAtualizar.add(lblNome);

        updNome = new JTextField();
        updNome.setBounds(150, 60, 250, 25);
        painelAtualizar.add(updNome);

        JLabel lblDesc = new JLabel("Descrição:");
        lblDesc.setBounds(20, 100, 120, 25);
        painelAtualizar.add(lblDesc);

        updDescricao = new JTextField();
        updDescricao.setBounds(150, 100, 250, 25);
        painelAtualizar.add(updDescricao);

        JLabel lblData = new JLabel("Data (yyyy-mm-dd):");
        lblData.setBounds(20, 140, 120, 25);
        painelAtualizar.add(lblData);

        updData = new JTextField();
        updData.setBounds(150, 140, 120, 25);
        painelAtualizar.add(updData);

        JLabel lblDuracao = new JLabel("Duração (h):");
        lblDuracao.setBounds(20, 180, 120, 25);
        painelAtualizar.add(lblDuracao);

        updDuracao = new JTextField();
        updDuracao.setBounds(150, 180, 50, 25);
        painelAtualizar.add(updDuracao);

        JLabel lblLocal = new JLabel("Local:");
        lblLocal.setBounds(20, 220, 120, 25);
        painelAtualizar.add(lblLocal);

        updLocal = new JTextField();
        updLocal.setBounds(150, 220, 250, 25);
        painelAtualizar.add(updLocal);

        JLabel lblCap = new JLabel("Capacidade:");
        lblCap.setBounds(20, 260, 120, 25);
        painelAtualizar.add(lblCap);

        updCapacidade = new JTextField();
        updCapacidade.setBounds(150, 260, 50, 25);
        painelAtualizar.add(updCapacidade);

        JLabel lblPreco = new JLabel("Preço:");
        lblPreco.setBounds(20, 300, 120, 25);
        painelAtualizar.add(lblPreco);

        updPreco = new JTextField();
        updPreco.setBounds(150, 300, 80, 25);
        painelAtualizar.add(updPreco);

        JLabel lblCat = new JLabel("Categoria:");
        lblCat.setBounds(20, 340, 120, 25);
        painelAtualizar.add(lblCat);

        updComboCategoria = new JComboBox<>(CategoriaEvento.values());
        updComboCategoria.setBounds(150, 340, 150, 25);
        painelAtualizar.add(updComboCategoria);

        JLabel lblAdm = new JLabel("Organizador (Cód. Admin):");
        lblAdm.setBounds(20, 380, 160, 25);
        painelAtualizar.add(lblAdm);

        updCodigoAdmin = new JTextField();
        updCodigoAdmin.setBounds(180, 380, 60, 25);
        painelAtualizar.add(updCodigoAdmin);

        JLabel lblStatus = new JLabel("Status:");
        lblStatus.setBounds(350, 60, 80, 25);
        painelAtualizar.add(lblStatus);

        updComboStatus = new JComboBox<>(StatusEvento.values());
        updComboStatus.setBounds(400, 60, 120, 25);
        painelAtualizar.add(updComboStatus);

        updBotaoAtualizar = new JButton("Atualizar");
        updBotaoAtualizar.setBounds(350, 100, 120, 30);
        painelAtualizar.add(updBotaoAtualizar);

        updBotaoExcluir = new JButton("Excluir");
        updBotaoExcluir.setBounds(350, 140, 120, 30);
        painelAtualizar.add(updBotaoExcluir);

        // Ações
        updBotaoBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int codigo;
                    try {
                        codigo = Integer.parseInt(updCodigo.getText());
                    } catch (NumberFormatException ex2) {
                        JOptionPane.showMessageDialog(painelAtualizar,
                            "Código do evento inválido.",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }

                    Evento ev = eventoService.buscarEventoPorCodigo(codigo);
                    if (ev == null) {
                        JOptionPane.showMessageDialog(painelAtualizar,
                            "Evento não encontrado!",
                            "Aviso",
                            JOptionPane.WARNING_MESSAGE
                        );
                        return;
                    }

                    // Preenche os campos
                    updNome.setText(ev.getNomeEvento());
                    updDescricao.setText(ev.getDescEvento());
                    updData.setText(ev.getDataEvento().toString()); // yyyy-mm-dd
                    updDuracao.setText(String.valueOf(ev.getDuracaoEvento()));
                    updLocal.setText(ev.getLocalEvento());
                    updCapacidade.setText(String.valueOf(ev.getCapacidadeMaxima()));
                    updPreco.setText(String.valueOf(ev.getPrecoEvento()));
                    updComboCategoria.setSelectedItem(ev.getCategoriaEvento());
                    updCodigoAdmin.setText(String.valueOf(ev.getAdministrador().getCodigoPessoa()));
                    updComboStatus.setSelectedItem(ev.getStatusEvento());

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(painelAtualizar,
                        "Erro no banco: " + ex.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        updBotaoAtualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int codigo = Integer.parseInt(updCodigo.getText());
                    Evento existente = eventoService.buscarEventoPorCodigo(codigo);
                    if (existente == null) {
                        JOptionPane.showMessageDialog(painelAtualizar,
                            "Evento não encontrado!",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }

                    // Atualiza dados
                    existente.setNomeEvento(updNome.getText());
                    existente.setDescEvento(updDescricao.getText());

                    Date dt = parseData(updData.getText());
                    if (dt == null) return; // se inválida, já mostrou erro
                    //existente.setDataEvento(dt);

                    try {
                        existente.setDuracaoEvento(Integer.parseInt(updDuracao.getText()));
                        existente.setCapacidadeMaxima(Integer.parseInt(updCapacidade.getText()));
                        existente.setPrecoEvento(Float.parseFloat(updPreco.getText()));
                    } catch (NumberFormatException ex2) {
                        JOptionPane.showMessageDialog(painelAtualizar,
                            "Verifique se duração, capacidade e preço são numéricos válidos.",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }

                    existente.setLocalEvento(updLocal.getText());
                    existente.setCategoriaEvento((CategoriaEvento) updComboCategoria.getSelectedItem());

                    Administrador adm = new Administrador();
                    try {
                        adm.setCodigoPessoa(Integer.parseInt(updCodigoAdmin.getText()));
                    } catch (NumberFormatException ex2) {
                        JOptionPane.showMessageDialog(painelAtualizar,
                            "Código de administrador inválido.",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                    existente.setAdministrador(adm);

                    // Agora podemos alterar status
                    existente.setStatusEvento((StatusEvento) updComboStatus.getSelectedItem());

                    boolean ok = eventoService.atualizarEvento(existente);
                    if (ok) {
                        JOptionPane.showMessageDialog(painelAtualizar,
                            "Evento atualizado com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(painelAtualizar,
                            "Erro ao atualizar evento!",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(painelAtualizar,
                        "Erro no banco: " + ex.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        updBotaoExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int codigo = Integer.parseInt(updCodigo.getText());
                    boolean ok = eventoService.excluirEvento(codigo);
                    if (ok) {
                        JOptionPane.showMessageDialog(painelAtualizar,
                            "Evento excluído!");
                    } else {
                        JOptionPane.showMessageDialog(painelAtualizar,
                            "Erro ao excluir ou evento não encontrado!",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(painelAtualizar,
                        "Erro no banco: " + ex.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                    );
                } catch (NumberFormatException ex2) {
                    JOptionPane.showMessageDialog(painelAtualizar,
                        "Código de evento inválido.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        tabbedPane.addTab("Atualizar/Excluir", painelAtualizar);
    }

    // -------------------------------------------------------------------------
    // ABA "LISTAR"
    // -------------------------------------------------------------------------
    private void initAbaListar() {
        painelListar = new JPanel(null);

        txtAreaListagem = new JTextArea();
        JScrollPane scroll = new JScrollPane(txtAreaListagem);
        scroll.setBounds(20, 20, 740, 450);
        painelListar.add(scroll);

        btnListarEventos = new JButton("Listar Eventos");
        btnListarEventos.setBounds(20, 480, 140, 30);
        painelListar.add(btnListarEventos);

        btnListarEventos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<Evento> lista = eventoService.listarEventos();
                    txtAreaListagem.setText("");
                    for (Evento ev : lista) {
                        txtAreaListagem.append(
                            "ID: " + ev.getCodigoEvento() +
                            " | Título: " + ev.getNomeEvento() +
                            " | Status: " + ev.getStatusEvento() +
                            " | Data: " + ev.getDataEvento() +
                            " | Local: " + ev.getLocalEvento() + "\n"
                        );
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(painelListar,
                        "Erro ao listar eventos: " + ex.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        tabbedPane.addTab("Listar", painelListar);
    }

    // -------------------------------------------------------------------------
    // MÉTODOS DE APOIO
    // -------------------------------------------------------------------------
    /**
     * Tenta converter a string em uma data no formato yyyy-mm-dd.
     * Retorna null e exibe mensagem de erro se for inválido.
     */
    private Date parseData(String texto) {
        try {
            return Date.valueOf(texto); // Lança IllegalArgumentException se inválido
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                "Data inválida! Use o formato yyyy-mm-dd (ex: 2025-12-31).",
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );
            return null;
        }
    }
}
