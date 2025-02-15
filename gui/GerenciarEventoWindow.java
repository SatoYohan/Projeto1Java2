package gui;

import entities.Evento;
import entities.StatusEvento;
import entities.CategoriaEvento;
import entities.Administrador;
import service.EventoService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class GerenciarEventoWindow extends JFrame {

    // Campos para cadastro/edição
    private JTextField campoCodigo;         // usado para buscar/atualizar/excluir
    private JTextField campoNome;
    private JTextField campoDescricao;
    private JTextField campoData;           // yyyy-mm-dd
    private JTextField campoDuracao;        // em horas (inteiro)
    private JTextField campoLocal;
    private JTextField campoCapacidade;
    private JComboBox<CategoriaEvento> comboCategoria;
    private JTextField campoPreco;
    private JTextField campoCodigoAdmin;    // ID do admin responsável
    private JComboBox<StatusEvento> comboStatus; // para alteração de status

    // Área para listagem
    private JTextArea areaListagem;

    // Botões
    private JButton botaoCadastrar;
    private JButton botaoListar;
    private JButton botaoAtualizar;
    private JButton botaoExcluir;
    private JButton botaoAlterarStatus;
    private JButton botaoVoltar;

    private EventoService eventoService;

    /**
     * Construtor que recebe a conexão ou o próprio EventoService.
     */
    public GerenciarEventoWindow(Connection conn) {
        // Se já tiver um EventoService fora, você pode passar diretamente
        this.eventoService = new EventoService(conn);

        setTitle("Gerenciar Eventos");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setBorder(new EmptyBorder(10, 10, 10, 10));
        painel.setLayout(null);
        setContentPane(painel);

        // ---------- Campos e Rótulos ----------

        JLabel rotuloCodigo = new JLabel("Código Evento:");
        rotuloCodigo.setBounds(20, 20, 100, 25);
        painel.add(rotuloCodigo);

        campoCodigo = new JTextField();
        campoCodigo.setBounds(130, 20, 100, 25);
        painel.add(campoCodigo);

        JLabel rotuloNome = new JLabel("Nome:");
        rotuloNome.setBounds(20, 60, 100, 25);
        painel.add(rotuloNome);

        campoNome = new JTextField();
        campoNome.setBounds(130, 60, 200, 25);
        painel.add(campoNome);

        JLabel rotuloDescricao = new JLabel("Descrição:");
        rotuloDescricao.setBounds(20, 100, 100, 25);
        painel.add(rotuloDescricao);

        campoDescricao = new JTextField();
        campoDescricao.setBounds(130, 100, 200, 25);
        painel.add(campoDescricao);

        JLabel rotuloData = new JLabel("Data (yyyy-mm-dd):");
        rotuloData.setBounds(20, 140, 120, 25);
        painel.add(rotuloData);

        campoData = new JTextField();
        campoData.setBounds(130, 140, 100, 25);
        painel.add(campoData);

        JLabel rotuloDuracao = new JLabel("Duração (h):");
        rotuloDuracao.setBounds(20, 180, 100, 25);
        painel.add(rotuloDuracao);

        campoDuracao = new JTextField();
        campoDuracao.setBounds(130, 180, 50, 25);
        painel.add(campoDuracao);

        JLabel rotuloLocal = new JLabel("Local:");
        rotuloLocal.setBounds(20, 220, 100, 25);
        painel.add(rotuloLocal);

        campoLocal = new JTextField();
        campoLocal.setBounds(130, 220, 200, 25);
        painel.add(campoLocal);

        JLabel rotuloCapacidade = new JLabel("Capacidade:");
        rotuloCapacidade.setBounds(20, 260, 100, 25);
        painel.add(rotuloCapacidade);

        campoCapacidade = new JTextField();
        campoCapacidade.setBounds(130, 260, 50, 25);
        painel.add(campoCapacidade);

        JLabel rotuloCategoria = new JLabel("Categoria:");
        rotuloCategoria.setBounds(20, 300, 100, 25);
        painel.add(rotuloCategoria);

        comboCategoria = new JComboBox<>(CategoriaEvento.values());
        comboCategoria.setBounds(130, 300, 150, 25);
        painel.add(comboCategoria);

        JLabel rotuloPreco = new JLabel("Preço:");
        rotuloPreco.setBounds(20, 340, 100, 25);
        painel.add(rotuloPreco);

        campoPreco = new JTextField();
        campoPreco.setBounds(130, 340, 100, 25);
        painel.add(campoPreco);

        JLabel rotuloCodigoAdmin = new JLabel("Cód. Admin:");
        rotuloCodigoAdmin.setBounds(20, 380, 100, 25);
        painel.add(rotuloCodigoAdmin);

        campoCodigoAdmin = new JTextField();
        campoCodigoAdmin.setBounds(130, 380, 100, 25);
        painel.add(campoCodigoAdmin);

        // Para alteração de status
        JLabel rotuloStatus = new JLabel("Novo Status:");
        rotuloStatus.setBounds(300, 20, 100, 25);
        painel.add(rotuloStatus);

        comboStatus = new JComboBox<>(StatusEvento.values());
        comboStatus.setBounds(400, 20, 150, 25);
        painel.add(comboStatus);

        // ---------- Botões ----------

        botaoCadastrar = new JButton("Cadastrar");
        botaoCadastrar.setBounds(300, 60, 120, 30);
        painel.add(botaoCadastrar);

        botaoListar = new JButton("Listar Eventos");
        botaoListar.setBounds(300, 100, 120, 30);
        painel.add(botaoListar);

        botaoAtualizar = new JButton("Atualizar");
        botaoAtualizar.setBounds(300, 140, 120, 30);
        painel.add(botaoAtualizar);

        botaoExcluir = new JButton("Excluir");
        botaoExcluir.setBounds(300, 180, 120, 30);
        painel.add(botaoExcluir);

        botaoAlterarStatus = new JButton("Alterar Status");
        botaoAlterarStatus.setBounds(300, 220, 120, 30);
        painel.add(botaoAlterarStatus);

        botaoVoltar = new JButton("Voltar");
        botaoVoltar.setBounds(300, 260, 120, 30);
        painel.add(botaoVoltar);

        // ---------- Área de Listagem ----------
        areaListagem = new JTextArea();
        JScrollPane scroll = new JScrollPane(areaListagem);
        scroll.setBounds(20, 420, 650, 130);
        painel.add(scroll);

        // ---------- Ações dos Botões ----------

        // 1) Cadastrar (CREATE)
        botaoCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Evento evento = new Evento();
                    evento.setNomeEvento(campoNome.getText());
                    evento.setDescEvento(campoDescricao.getText());
                    // Parse da data (yyyy-mm-dd)
                    evento.setDataEvento(Date.valueOf(campoData.getText()));
                    evento.setDuracaoEvento(Integer.parseInt(campoDuracao.getText()));
                    evento.setLocalEvento(campoLocal.getText());
                    evento.setCapacidadeMaxima(Integer.parseInt(campoCapacidade.getText()));
                    // Status inicial será FECHADO (forçado no service), mas podemos setar aqui também se quiser
                    evento.setCategoriaEvento((CategoriaEvento) comboCategoria.getSelectedItem());
                    evento.setPrecoEvento(Float.parseFloat(campoPreco.getText()));

                    // Define o administrador responsável
                    Administrador admin = new Administrador();
                    admin.setCodigoPessoa(Integer.parseInt(campoCodigoAdmin.getText()));
                    evento.setAdministrador(admin);

                    boolean cadastrado = eventoService.cadastrarEvento(evento);
                    if (cadastrado) {
                        JOptionPane.showMessageDialog(GerenciarEventoWindow.this,
                                "Evento cadastrado com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(GerenciarEventoWindow.this,
                                "Erro ao cadastrar evento!");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(GerenciarEventoWindow.this,
                            "Erro no banco de dados: " + ex.getMessage());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(GerenciarEventoWindow.this,
                            "Erro ao cadastrar: " + ex.getMessage());
                }
            }
        });

        // 2) Listar (READ)
        botaoListar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<Evento> eventos = eventoService.listarEventos();
                    areaListagem.setText("");
                    for (Evento ev : eventos) {
                        areaListagem.append(
                                "Código: " + ev.getCodigoEvento() + " | " +
                                        "Nome: " + ev.getNomeEvento() + " | " +
                                        "Status: " + ev.getStatusEvento() + "\n"
                        );
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(GerenciarEventoWindow.this,
                            "Erro ao listar eventos: " + ex.getMessage());
                }
            }
        });

        // 3) Atualizar (UPDATE)
        botaoAtualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Precisamos do código do evento para atualizar
                    int codigo = Integer.parseInt(campoCodigo.getText());
                    Evento eventoExistente = eventoService.buscarEventoPorCodigo(codigo);

                    if (eventoExistente == null) {
                        JOptionPane.showMessageDialog(GerenciarEventoWindow.this,
                                "Evento não encontrado para atualizar.");
                        return;
                    }

                    // Atualiza os campos
                    eventoExistente.setNomeEvento(campoNome.getText());
                    eventoExistente.setDescEvento(campoDescricao.getText());
                    eventoExistente.setDataEvento(Date.valueOf(campoData.getText()));
                    eventoExistente.setDuracaoEvento(Integer.parseInt(campoDuracao.getText()));
                    eventoExistente.setLocalEvento(campoLocal.getText());
                    eventoExistente.setCapacidadeMaxima(Integer.parseInt(campoCapacidade.getText()));
                    eventoExistente.setCategoriaEvento((CategoriaEvento) comboCategoria.getSelectedItem());
                    eventoExistente.setPrecoEvento(Float.parseFloat(campoPreco.getText()));

                    // Atualiza admin
                    Administrador admin = new Administrador();
                    admin.setCodigoPessoa(Integer.parseInt(campoCodigoAdmin.getText()));
                    eventoExistente.setAdministrador(admin);

                    boolean atualizado = eventoService.atualizarEvento(eventoExistente);
                    if (atualizado) {
                        JOptionPane.showMessageDialog(GerenciarEventoWindow.this,
                                "Evento atualizado com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(GerenciarEventoWindow.this,
                                "Erro ao atualizar evento!");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(GerenciarEventoWindow.this,
                            "Erro no banco de dados: " + ex.getMessage());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(GerenciarEventoWindow.this,
                            "Erro ao atualizar: " + ex.getMessage());
                }
            }
        });

        // 4) Excluir (DELETE)
        botaoExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int codigo = Integer.parseInt(campoCodigo.getText());
                    boolean excluido = eventoService.excluirEvento(codigo);
                    if (excluido) {
                        JOptionPane.showMessageDialog(GerenciarEventoWindow.this,
                                "Evento excluído com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(GerenciarEventoWindow.this,
                                "Erro ao excluir evento ou evento não encontrado.");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(GerenciarEventoWindow.this,
                            "Erro no banco de dados: " + ex.getMessage());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(GerenciarEventoWindow.this,
                            "Erro ao excluir: " + ex.getMessage());
                }
            }
        });

        // 5) Alterar Status
        botaoAlterarStatus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int codigo = Integer.parseInt(campoCodigo.getText());
                    StatusEvento novoStatus = (StatusEvento) comboStatus.getSelectedItem();
                    boolean alterado = eventoService.alterarStatusEvento(codigo, novoStatus);
                    if (alterado) {
                        JOptionPane.showMessageDialog(GerenciarEventoWindow.this,
                                "Status do evento alterado para " + novoStatus);
                    } else {
                        JOptionPane.showMessageDialog(GerenciarEventoWindow.this,
                                "Não foi possível alterar o status (evento inexistente?).");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(GerenciarEventoWindow.this,
                            "Erro no banco de dados: " + ex.getMessage());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(GerenciarEventoWindow.this,
                            "Erro ao alterar status: " + ex.getMessage());
                }
            }
        });

        // Voltar/Logout ou ir para outra tela
        botaoVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginWindow().setVisible(true);
                dispose();
            }
        });
    }
}
