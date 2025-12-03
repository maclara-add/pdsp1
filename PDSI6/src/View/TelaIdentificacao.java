package View;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.text.ParseException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import Controller.UsuarioController;
import Model.Conexao;
import Model.Usuario;
import Model.UsuarioDAO;
import net.miginfocom.swing.MigLayout;

public class TelaIdentificacao extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textNome;
    private JFormattedTextField textCPF;
    private UsuarioController usuarioController;
    private JRadioButton rdbtnAdministrador;
    private JRadioButton rdbtnCliente;
    private ButtonGroup grupoUsuarios;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                TelaIdentificacao frame = new TelaIdentificacao();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public TelaIdentificacao() {
        setTitle("Tela de Identificação de Usuário");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 550);
        setMinimumSize(new Dimension(550, 500));
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        contentPane.setLayout(new MigLayout(
                "fill, insets 20",
                "[grow, fill]",
                "[]20[]10[]10[]10[]20[]10[]20[]"
        ));

        try {
            Connection conn = Conexao.getConnection();
            UsuarioDAO usuarioDAO = new UsuarioDAO(conn);
            usuarioController = new UsuarioController(usuarioDAO);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao conectar ao banco de dados: " + e.getMessage(),
                    "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        JLabel lblBemVindo = new JLabel("Bem vindo!", SwingConstants.CENTER);
        lblBemVindo.setFont(new Font("Tahoma", Font.BOLD, 20));
        contentPane.add(lblBemVindo, "cell 0 0, align center");

        JLabel lblNome = new JLabel("Nome:", SwingConstants.CENTER);
        lblNome.setFont(new Font("Tahoma", Font.PLAIN, 18));
        contentPane.add(lblNome, "cell 0 1, align center");

        textNome = new JTextField();
        textNome.setHorizontalAlignment(SwingConstants.CENTER);
        textNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentPane.add(textNome, "cell 0 2, growx, h 35");

        JLabel lblCpf = new JLabel("CPF:", SwingConstants.CENTER);
        lblCpf.setFont(new Font("Tahoma", Font.PLAIN, 18));
        contentPane.add(lblCpf, "cell 0 3, align center");

        MaskFormatter mascaraCPF = null;
        try {
            mascaraCPF = new MaskFormatter("###.###.###-##");
            mascaraCPF.setPlaceholderCharacter('_');
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao criar máscara de CPF. O campo funcionará sem máscara.",
                    "Erro de Formatação", JOptionPane.WARNING_MESSAGE);
            e.printStackTrace();
        }

        textCPF = new JFormattedTextField(mascaraCPF);
        textCPF.setHorizontalAlignment(SwingConstants.CENTER);
        textCPF.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentPane.add(textCPF, "cell 0 4, growx, h 35");

        JLabel lblFuncao = new JLabel("Selecione sua função:", SwingConstants.CENTER);
        lblFuncao.setFont(new Font("Tahoma", Font.PLAIN, 18));
        contentPane.add(lblFuncao, "cell 0 5, align center");

        JPanel panelRadio = new JPanel();
        panelRadio.setLayout(new MigLayout(
                "fill, insets 0",
                "[grow, fill][grow, fill]",
                "[]"
        ));
        contentPane.add(panelRadio, "cell 0 6, growx");

        rdbtnAdministrador = new JRadioButton("Administrador");
        rdbtnAdministrador.setHorizontalAlignment(SwingConstants.CENTER);
        rdbtnAdministrador.setFont(new Font("Tahoma", Font.PLAIN, 16));
        panelRadio.add(rdbtnAdministrador, "cell 0 0, align center");

        rdbtnCliente = new JRadioButton("Cliente");
        rdbtnCliente.setHorizontalAlignment(SwingConstants.CENTER);
        rdbtnCliente.setFont(new Font("Tahoma", Font.PLAIN, 16));
        panelRadio.add(rdbtnCliente, "cell 1 0, align center");

        grupoUsuarios = new ButtonGroup();
        grupoUsuarios.add(rdbtnAdministrador);
        grupoUsuarios.add(rdbtnCliente);

        JPanel panelBotoes = new JPanel();
        panelBotoes.setLayout(new MigLayout(
                "fill, insets 0",
                "[grow, fill][grow, fill]",
                "[]"
        ));
        contentPane.add(panelBotoes, "cell 0 7, growx");

        JButton btnEntrar = new JButton("Entrar");
        btnEntrar.setFont(new Font("Tahoma", Font.BOLD, 18));
        btnEntrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                realizarLogin();
            }
        });
        panelBotoes.add(btnEntrar, "cell 0 0, h 45");

        JButton btnCadastrarse = new JButton("Cadastrar-se");
        btnCadastrarse.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnCadastrarse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirTelaCadastro();
            }
        });
        panelBotoes.add(btnCadastrarse, "cell 1 0, h 45");
    }

    private void realizarLogin() {
        try {
            String nome = textNome.getText().trim();
            String cpf = textCPF.getText().replaceAll("[^0-9]", "").trim();
            String funcaoSelecionada = "";

            if (rdbtnAdministrador.isSelected()) {
                funcaoSelecionada = "Administrador";
            } else if (rdbtnCliente.isSelected()) {
                funcaoSelecionada = "Cliente";
            }

            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "O campo Nome não pode estar vazio!",
                        "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (cpf.length() != 11) {
                JOptionPane.showMessageDialog(this,
                        "O CPF deve conter exatamente 11 dígitos!",
                        "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (grupoUsuarios.getSelection() == null) {
                JOptionPane.showMessageDialog(this,
                        "Selecione uma função (Administrador ou Cliente)!",
                        "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean login = usuarioController.login(cpf, nome);

            if (login) {
                Usuario usuario = usuarioController.buscarUsuario(cpf);

                if (usuario != null && usuario.getFuncao().equals(funcaoSelecionada)) {
                    JOptionPane.showMessageDialog(this,
                            "Login efetuado com sucesso! Bem-vindo(a), " + usuario.getNome() + ".",
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                    if (usuario.getFuncao().equals("Administrador")) {
                        JFrame cadastroproduto = new CadastroProdutos();
                        cadastroproduto.setVisible(true);
                    } else if (usuario.getFuncao().equals("Cliente")) {
                        JFrame compra = new TelaCompra();
                        compra.setVisible(true);
                    }
                    TelaIdentificacao.this.dispose();

                } else if (usuario != null) {
                    JOptionPane.showMessageDialog(this,
                            "Função incorreta selecionada. Você está cadastrado(a) como " + usuario.getFuncao() + ".",
                            "Erro de Acesso", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Usuário não encontrado. Verifique seu Nome e CPF.",
                            "Falha na Identificação", JOptionPane.ERROR_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(this,
                        "Usuário não encontrado. Verifique seu Nome e CPF.",
                        "Falha na Identificação", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro: Controlador de usuários não foi inicializado corretamente!",
                    "Erro de Sistema", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao realizar login: " + ex.getMessage(),
                    "Erro Geral", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void abrirTelaCadastro() {
        try {
            TelaCadastroUsuario cadastrousuario = new TelaCadastroUsuario();
            cadastrousuario.setVisible(true);
            TelaIdentificacao.this.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao abrir tela de cadastro!",
                    "Erro de Navegação", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}