package View;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.text.ParseException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import Controller.UsuarioController;
import DAO.Conexao;
import DAO.UsuarioDAO;
import net.miginfocom.swing.MigLayout;

public class TelaCadastroUsuario extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textNome;
    private JComboBox<String> comboBoxFuncao;
    private JTextField textEmail;
    private UsuarioController usuarioController;
    private JFormattedTextField textCPF;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                TelaCadastroUsuario frame = new TelaCadastroUsuario();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public TelaCadastroUsuario() {
        setTitle("Cadastro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 550);
        setMinimumSize(new Dimension(450, 500));
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        // MigLayout: Layout responsivo centralizado
        contentPane.setLayout(new MigLayout(
                "fill, insets 20",
                "[grow, fill]",
                "[]20[]10[]10[]10[]10[]10[]10[]20[]"
        ));

        // -------------------- CONEXÃO COM BANCO (Com Tratamento de Exceção) --------------------
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

        // -------------------- TÍTULO --------------------
        JLabel lbCadastro = new JLabel("Cadastre-se!", SwingConstants.CENTER);
        lbCadastro.setFont(new Font("Tahoma", Font.BOLD, 20));
        contentPane.add(lbCadastro, "cell 0 0, align center");

        // -------------------- NOME --------------------
        JLabel lbNome = new JLabel("Nome:", SwingConstants.CENTER);
        lbNome.setFont(new Font("Tahoma", Font.PLAIN, 18));
        contentPane.add(lbNome, "cell 0 1, align center");

        textNome = new JTextField();
        textNome.setHorizontalAlignment(SwingConstants.CENTER);
        textNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentPane.add(textNome, "cell 0 2, growx, h 35");

        // -------------------- CPF (Com Tratamento de Exceção na Máscara) --------------------
        JLabel lbCPF = new JLabel("CPF:", SwingConstants.CENTER);
        lbCPF.setFont(new Font("Tahoma", Font.PLAIN, 18));
        contentPane.add(lbCPF, "cell 0 3, align center");

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

        // -------------------- EMAIL --------------------
        JLabel lbEmail = new JLabel("Email:", SwingConstants.CENTER);
        lbEmail.setFont(new Font("Tahoma", Font.PLAIN, 18));
        contentPane.add(lbEmail, "cell 0 5, align center");

        textEmail = new JTextField();
        textEmail.setHorizontalAlignment(SwingConstants.CENTER);
        textEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentPane.add(textEmail, "cell 0 6, growx, h 35");

        // -------------------- FUNÇÃO --------------------
        JLabel lblSelecioneSuaFuncao = new JLabel("Selecione sua função!", SwingConstants.CENTER);
        lblSelecioneSuaFuncao.setFont(new Font("Tahoma", Font.PLAIN, 18));
        contentPane.add(lblSelecioneSuaFuncao, "cell 0 7, align center");

        comboBoxFuncao = new JComboBox<>();
        comboBoxFuncao.setModel(new DefaultComboBoxModel<>(new String[] { "Cliente", "Administrador" }));
        comboBoxFuncao.setFont(new Font("Tahoma", Font.PLAIN, 16));
        contentPane.add(comboBoxFuncao, "cell 0 8, growx, h 35");

        // -------------------- PAINEL DE BOTÕES --------------------
        JPanel panelBotoes = new JPanel();
        panelBotoes.setLayout(new MigLayout(
                "fill, insets 0",
                "[grow, fill][grow, fill]",
                "[]"
        ));
        contentPane.add(panelBotoes, "cell 0 9, growx");

        JButton btnCadastrar = new JButton("Cadastrar-se");
        btnCadastrar.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cadastrarUsuario();
            }
        });
        panelBotoes.add(btnCadastrar, "cell 0 0, h 40");

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                voltarParaIdentificacao();
            }
        });
        panelBotoes.add(btnVoltar, "cell 1 0, h 40");
    }

    // -------------------- CADASTRAR USUÁRIO (Com Tratamento de Exceção) --------------------
    private void cadastrarUsuario() {
        try {
            String nome = textNome.getText().trim();
            String cpfMascarado = textCPF.getText();
            String cpfLimpo = cpfMascarado.replaceAll("[^0-9]", "").trim();
            String email = textEmail.getText().trim();
            String funcao = (String) comboBoxFuncao.getSelectedItem();

            // Validações de campos vazios
            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "O campo Nome não pode estar vazio!",
                        "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (cpfLimpo.length() != 11) {
                JOptionPane.showMessageDialog(this,
                        "O CPF deve conter exatamente 11 dígitos!",
                        "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "O campo Email não pode estar vazio!",
                        "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validação de formato de email (simples)
            if (!email.contains("@") || !email.contains(".")) {
                JOptionPane.showMessageDialog(this,
                        "Insira um email válido!",
                        "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Adicionar usuário (pode lançar exceção de banco de dados)
            usuarioController.adicionarUsuario(cpfLimpo, nome, email, funcao);

            JOptionPane.showMessageDialog(this,
                    "Usuário '" + nome + "' cadastrado com sucesso! Faça seu login.",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            // Redirecionar para tela de identificação
            TelaIdentificacao identificacao = new TelaIdentificacao();
            identificacao.setVisible(true);
            TelaCadastroUsuario.this.dispose();

        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro: Controlador de usuários não foi inicializado corretamente!",
                    "Erro de Sistema", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao cadastrar usuário: " + ex.getMessage(),
                    "Erro Geral", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    // -------------------- VOLTAR (Com Tratamento de Exceção) --------------------
    private void voltarParaIdentificacao() {
        try {
            TelaIdentificacao identificacao = new TelaIdentificacao();
            identificacao.setVisible(true);
            TelaCadastroUsuario.this.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao voltar para a tela de identificação!",
                    "Erro de Navegação", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}