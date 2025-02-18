package gui;

import dao.BancoDados;
import entities.Administrador;
import entities.CategoriaEvento;
import entities.Evento;
import entities.StatusEvento;
import service.EventoService;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

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
    // COMPONENTES DA ABA "ATUALIZAR"
    // ---------------------------------------------
    private JTextField updCodigo;
    private JButton updBotaoBuscar;
    private JTextField updNome;
    private JTextField updDescricao;
    private JFormattedTextField updDataHora;
    private JTextField updDuracao;
    private JTextField updLocal;
    private JTextField updCapacidade;
    private JTextField updPreco;
    private JComboBox<CategoriaEvento> updComboCategoria;
    private JTextField updCodigoAdmin;
    private JComboBox<StatusEvento> updComboStatus;
    private JButton updBotaoAtualizar;
    private JButton updBotaoExcluir;

    // ---------------------------------------------
    // COMPONENTES DA ABA "LISTAR"
    // ---------------------------------------------
    private JTable tabelaEventos;
    private DefaultTableModel tabelaModel;
    private JButton btnListarAbertos;
    private JButton btnListarCancelados;
    private JButton btnListarEncerrados;
    private JButton btnListarFechados;

    // ---------------------------------------------
    // SERVICE
    // ---------------------------------------------
    private EventoService eventoService;

    public GerenciarEventoWindow() throws SQLException, IOException {
        setTitle("Gerenciar Eventos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Instancia o service (ajuste conforme seu construtor)
        eventoService = new EventoService();

        tabbedPane = new JTabbedPane();
        add(tabbedPane);

        initAbaCadastrar();
        initAbaAtualizar();
        initAbaListar();
    }

    // -------------------------------------------------------------------------
    // ABA "CADASTRAR"
    // -------------------------------------------------------------------------
    private void initAbaCadastrar() {
        painelCadastrar = new JPanel(null);

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

        // Máscara para data/hora (19 caracteres)
        MaskFormatter maskDataHora = null;
        try {
            maskDataHora = new MaskFormatter("####-##-## ##:##:##");
            maskDataHora.setPlaceholderCharacter('_');
        } catch (ParseException e) {
            e.printStackTrace();
        }

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

        cadBotaoSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarEvento();
            }
        });

        tabbedPane.addTab("Cadastrar", painelCadastrar);
    }

    private void cadastrarEvento() {
        // Faz as validações e chama o service
        try {
            // 1) Checar campos obrigatórios
            if (cadNome.getText().isEmpty() || cadDescricao.getText().isEmpty() ||
                    cadData.getText().isEmpty() || cadDuracao.getText().isEmpty() ||
                    cadLocal.getText().isEmpty() || cadCapacidade.getText().isEmpty() ||
                    cadPreco.getText().isEmpty() || cadComboCategoria.getSelectedItem() == null ||
                    cadCodigoAdmin.getText().isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "Por favor, preencha todos os campos.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 2) Validar data/hora
            String textoData = cadData.getText();
            if (textoData.contains("_")) {
                JOptionPane.showMessageDialog(this,
                        "Preencha todos os campos de data/hora no formato yyyy-MM-dd HH:mm:ss.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            if (textoData.length() < 19) {
                JOptionPane.showMessageDialog(this,
                        "Data/Hora incompleta! Use yyyy-MM-dd HH:mm:ss.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            Timestamp dataValida;
            try {
                dataValida = Timestamp.valueOf(textoData);
            } catch (IllegalArgumentException exData) {
                JOptionPane.showMessageDialog(this,
                        "Data/Hora inválida! Use valores reais (ex: 2025-12-31 13:45:00).",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // 3) Criar objeto Evento
            Evento novo = new Evento();
            novo.setNomeEvento(cadNome.getText());
            novo.setDescEvento(cadDescricao.getText());
            novo.setDataEvento(dataValida);

            // Duração
            int duracao;
            try {
                duracao = Integer.parseInt(cadDuracao.getText());
            } catch (NumberFormatException ex2) {
                JOptionPane.showMessageDialog(this,
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
                JOptionPane.showMessageDialog(this,
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
                JOptionPane.showMessageDialog(this,
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
                JOptionPane.showMessageDialog(this,
                        "Código de administrador inválido.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            novo.setAdministrador(admin);

            // 4) Salvar no service
            boolean ok = eventoService.cadastrarEvento(novo);
            if (ok) {
                JOptionPane.showMessageDialog(this,
                        "Evento cadastrado com sucesso (status FECHADO)!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE
                );
                limparCamposCadastro();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Erro ao cadastrar evento!",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        } catch (SQLException | IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro no banco de dados: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
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
    // ABA "ATUALIZAR"
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

        JLabel lblDataHora = new JLabel("Data/Hora (yyyy-MM-dd HH:mm:ss):");
        lblDataHora.setBounds(20, 140, 200, 25);
        painelAtualizar.add(lblDataHora);

        // Máscara para data/hora
        MaskFormatter maskUpdDataHora = null;
        try {
            maskUpdDataHora = new MaskFormatter("####-##-## ##:##:##");
            maskUpdDataHora.setPlaceholderCharacter('_');
        } catch (ParseException e) {
            e.printStackTrace();
        }
        updDataHora = new JFormattedTextField(maskUpdDataHora);
        updDataHora.setBounds(220, 140, 150, 25);
        painelAtualizar.add(updDataHora);

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
        lblStatus.setBounds(420, 60, 80, 25);
        painelAtualizar.add(lblStatus);

        updComboStatus = new JComboBox<>(StatusEvento.values());
        updComboStatus.setBounds(480, 60, 120, 25);
        painelAtualizar.add(updComboStatus);

        updBotaoAtualizar = new JButton("Atualizar");
        updBotaoAtualizar.setBounds(420, 100, 120, 30);
        painelAtualizar.add(updBotaoAtualizar);

        updBotaoExcluir = new JButton("Excluir");
        updBotaoExcluir.setBounds(420, 140, 120, 30);
        painelAtualizar.add(updBotaoExcluir);

        // Ação do Buscar
        updBotaoBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarEventoParaAtualizar();
            }
        });

        // Ação do Atualizar
        updBotaoAtualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarEvento();
            }
        });

        // Ação do Excluir
        updBotaoExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirEventoPorCodigo();
            }
        });

        tabbedPane.addTab("Atualizar/Excluir", painelAtualizar);
    }

    private void buscarEventoParaAtualizar() {
        try {
            int codigo = Integer.parseInt(updCodigo.getText());
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
            // Converte o Timestamp para String
            updDataHora.setText(ev.getDataEvento().toString()); // ex: 2025-12-31 13:45:00
            updDuracao.setText(String.valueOf(ev.getDuracaoEvento()));
            updLocal.setText(ev.getLocalEvento());
            updCapacidade.setText(String.valueOf(ev.getCapacidadeMaxima()));
            updPreco.setText(String.valueOf(ev.getPrecoEvento()));
            updComboCategoria.setSelectedItem(ev.getCategoriaEvento());
            updCodigoAdmin.setText(String.valueOf(ev.getAdministrador().getCodigoPessoa()));
            updComboStatus.setSelectedItem(ev.getStatusEvento());

        } catch (NumberFormatException ex2) {
            JOptionPane.showMessageDialog(painelAtualizar,
                    "Código do evento inválido.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(painelAtualizar,
                    "Erro no banco: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void atualizarEvento() {
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

            // Data/hora
            String textoData = updDataHora.getText();
            if (textoData.contains("_") || textoData.length() < 19) {
                JOptionPane.showMessageDialog(painelAtualizar,
                        "Data/Hora incompleta! Use yyyy-MM-dd HH:mm:ss.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            Timestamp ts;
            try {
                ts = Timestamp.valueOf(textoData);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(painelAtualizar,
                        "Data/Hora inválida! Use valores reais (ex: 2025-12-31 13:45:00).",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            existente.setDataEvento(ts);

            // Duração, capacidade, preço
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
        } catch (NumberFormatException ex2) {
            JOptionPane.showMessageDialog(painelAtualizar,
                    "Código do evento inválido.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(painelAtualizar,
                    "Erro no banco: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void excluirEventoPorCodigo() {
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
        } catch (NumberFormatException ex2) {
            JOptionPane.showMessageDialog(painelAtualizar,
                    "Código de evento inválido.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(painelAtualizar,
                    "Erro no banco: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // -------------------------------------------------------------------------
    // ABA "LISTAR"
    // -------------------------------------------------------------------------
    private void initAbaListar() {
        painelListar = new JPanel(null);

        // Modelo da tabela com 10 colunas
        tabelaModel = new DefaultTableModel(
                new Object[]{
                        "ID", "Título", "Descrição", "Data/Hora",
                        "Duração (h)", "Local", "Capacidade",
                        "Status", "Categoria", "Preço"
                },
                0
        );

        // Criação da JTable e JScrollPane
        tabelaEventos = new JTable(tabelaModel);
        JScrollPane scroll = new JScrollPane(tabelaEventos);
        scroll.setBounds(20, 20, 760, 400);
        painelListar.add(scroll);

        // Criação dos botões
        btnListarAbertos = new JButton("Listar Abertos");
        btnListarFechados = new JButton("Listar Fechados");
        btnListarCancelados = new JButton("Listar Cancelados");
        btnListarEncerrados = new JButton("Listar Encerrados");

        // Ajuste para centralizar (exemplo)
        int panelWidth = 800;
        int buttonWidth = 130;
        int buttonHeight = 30;
        int spacing = 20;
        int totalButtonsWidth = (4 * buttonWidth) + (3 * spacing);
        int startX = (panelWidth - totalButtonsWidth) / 2;
        int y = 440;

        btnListarAbertos.setBounds(startX, y, buttonWidth, buttonHeight);
        btnListarFechados.setBounds(startX + (buttonWidth + spacing), y, buttonWidth, buttonHeight);
        btnListarCancelados.setBounds(startX + 2*(buttonWidth + spacing), y, buttonWidth, buttonHeight);
        btnListarEncerrados.setBounds(startX + 3*(buttonWidth + spacing), y, buttonWidth, buttonHeight);

        painelListar.add(btnListarAbertos);
        painelListar.add(btnListarFechados);
        painelListar.add(btnListarCancelados);
        painelListar.add(btnListarEncerrados);

        // Listeners de ação
        btnListarAbertos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarPorStatus(StatusEvento.ABERTO);
            }
        });

        btnListarFechados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarPorStatus(StatusEvento.FECHADO);
            }
        });

        btnListarCancelados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarPorStatus(StatusEvento.CANCELADO);
            }
        });

        btnListarEncerrados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarPorStatus(StatusEvento.ENCERRADO);
            }
        });

        tabbedPane.addTab("Listar", painelListar);
    }

    private void carregarEventosNaTabela() {
        try {
            List<Evento> lista = eventoService.listarEventos();
            // Limpar a tabela
            tabelaModel.setRowCount(0);
            // Adicionar linhas
            for (Evento ev : lista) {
                tabelaModel.addRow(new Object[]{
                        ev.getCodigoEvento(),
                        ev.getNomeEvento(),
                        ev.getDescEvento(),
                        ev.getDataEvento(),
                        ev.getDuracaoEvento(),
                        ev.getLocalEvento(),
                        ev.getCapacidadeMaxima(),
                        ev.getStatusEvento(),
                        ev.getCategoriaEvento(),
                        ev.getPrecoEvento()
                });
            }
        } catch (SQLException | IOException ex) {
            JOptionPane.showMessageDialog(painelListar,
                    "Erro ao listar eventos: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void listarPorStatus(StatusEvento status) {
        try {
            // Precisa de um método no service:
            List<Evento> lista = eventoService.listarEventosPorStatus(status);

            tabelaModel.setRowCount(0);
            for (Evento ev : lista) {
                tabelaModel.addRow(new Object[]{
                        ev.getCodigoEvento(),
                        ev.getNomeEvento(),
                        ev.getDescEvento(),
                        ev.getDataEvento(),
                        ev.getDuracaoEvento(),
                        ev.getLocalEvento(),
                        ev.getCapacidadeMaxima(),
                        ev.getStatusEvento(),
                        ev.getCategoriaEvento(),
                        ev.getPrecoEvento()
                });
            }
        } catch (SQLException | IOException ex) {
            JOptionPane.showMessageDialog(painelListar,
                    "Erro ao listar eventos: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void excluirEventoSelecionado() {
        int row = tabelaEventos.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(painelListar,
                    "Selecione um evento na tabela para excluir.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        // Pegar o ID do evento na coluna 0
        int codigoEvento = (int) tabelaModel.getValueAt(row, 0);
        try {
            boolean ok = eventoService.excluirEvento(codigoEvento);
            if (ok) {
                JOptionPane.showMessageDialog(painelListar,
                        "Evento excluído com sucesso!");
                // Remover linha da tabela
                tabelaModel.removeRow(row);
            } else {
                JOptionPane.showMessageDialog(painelListar,
                        "Erro ao excluir ou evento não encontrado!",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(painelListar,
                    "Erro ao excluir evento: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
