package projetoUM;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants; // Adicionado para melhor alinhamento

public class TelaCadastroUsuario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textNome;
	private JTextField textCPF;
	private JTextField textEmail;
    private JComboBox<String> comboBoxFuncao; // Tipagem adicionada

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCadastroUsuario frame = new TelaCadastroUsuario();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaCadastroUsuario() {
		setTitle("Cadastro");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 464, 510);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null); // Mantendo o null layout original para manter as coordenadas

		// Componentes (mantidas as coordenadas originais, mas com nomes de variáveis limpos)
		JLabel lbCadastro = new JLabel("Cadastre-se!", SwingConstants.CENTER);
		lbCadastro.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbCadastro.setBounds(169, 63, 96, 13);
		contentPane.add(lbCadastro);
		
		JLabel lbNome = new JLabel("Nome:", SwingConstants.CENTER);
		lbNome.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbNome.setBounds(180, 118, 69, 25);
		contentPane.add(lbNome);
		
		JLabel lbCPF = new JLabel("CPF:", SwingConstants.CENTER);
		lbCPF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbCPF.setBounds(196, 192, 69, 25);
		contentPane.add(lbCPF);
		
		JLabel lbEmail = new JLabel("Email:", SwingConstants.CENTER);
		lbEmail.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbEmail.setBounds(190, 266, 69, 25);
		contentPane.add(lbEmail);
		
		comboBoxFuncao = new JComboBox<>();
		comboBoxFuncao.setModel(new DefaultComboBoxModel<>(new String[] {"Cliente", "Administrador"}));
		comboBoxFuncao.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboBoxFuncao.setBounds(153, 389, 136, 21);
		contentPane.add(comboBoxFuncao);
		
		textNome = new JTextField();
		textNome.setBounds(149, 153, 127, 29);
		contentPane.add(textNome);
		textNome.setColumns(10);
		
		textCPF = new JTextField();
		textCPF.setColumns(10);
		textCPF.setBounds(149, 227, 127, 29);
		contentPane.add(textCPF);
		
		textEmail = new JTextField();
		textEmail.setColumns(10);
		textEmail.setBounds(125, 302, 189, 29);
		contentPane.add(textEmail);
		
		JLabel lblSelecioneSuaFuno = new JLabel("Selecione sua função!", SwingConstants.CENTER);
		lblSelecioneSuaFuno.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSelecioneSuaFuno.setBounds(125, 354, 189, 25);
		contentPane.add(lblSelecioneSuaFuno);
		
		JButton btnCadastrar = new JButton("Cadastrar-se");
		btnCadastrar.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnCadastrar.setBounds(138, 442, 151, 21);
		
		// **LÓGICA DE CADASTRO E REDIRECIONAMENTO**
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nome = textNome.getText().trim();
				String cpf = textCPF.getText().trim();
				String email = textEmail.getText().trim();
				String funcao = (String) comboBoxFuncao.getSelectedItem();
				
				// 1. Validação
				if (nome.isEmpty() || cpf.isEmpty() || email.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				// 2. Salva o usuário na lista
				Usuario novoUsuario = new Usuario(nome, cpf, email, funcao);
				ListaUsuarios.adicionarUsuario(novoUsuario);
				
				JOptionPane.showMessageDialog(null, "Usuário '" + nome + "' cadastrado com sucesso! Faça seu login.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
				
				// 3. Redireciona para a Tela de Identificação
				// ATENÇÃO: A CLASSE TelaIdentificacao PRECISA EXISTIR!
				try {
					JFrame identificacao = new TelaIdentificacao(); // Assumindo o nome da classe
					identificacao.setVisible(true);
					TelaCadastroUsuario.this.dispose(); // Fecha a tela de cadastro
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Erro ao abrir a Tela de Identificação. Certifique-se de que a classe TelaIdentificacao.java existe.", "Erro de Navegação", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		contentPane.add(btnCadastrar);
	}
}