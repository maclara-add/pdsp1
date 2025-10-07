package View;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import Model.ListaUsuarios;
import Model.Usuario;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JFormattedTextField;

public class TelaCadastroUsuario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textNome;
    private JComboBox<String> comboBoxFuncao;
    private JTextField textEmail;
    
    private JFormattedTextField textCPF; 

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
		contentPane.setLayout(null);

		JLabel lbCadastro = new JLabel("Cadastre-se!", SwingConstants.CENTER);
		lbCadastro.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbCadastro.setBounds(163, 42, 96, 13);
		contentPane.add(lbCadastro);
		
		JLabel lbNome = new JLabel("Nome:", SwingConstants.CENTER);
		lbNome.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbNome.setBounds(180, 85, 69, 25);
		contentPane.add(lbNome);
		
		MaskFormatter mascaraCPF = null;
		try {
		    mascaraCPF = new MaskFormatter("###.###.###-##");
		    mascaraCPF.setPlaceholderCharacter('_');
		} catch (ParseException e) {
		    e.printStackTrace();
		}

		JLabel lbCPF = new JLabel("CPF:", SwingConstants.CENTER);
		lbCPF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbCPF.setBounds(180, 159, 69, 25);
		contentPane.add(lbCPF);
		
		textCPF = new JFormattedTextField(mascaraCPF); 
		textCPF.setHorizontalAlignment(SwingConstants.CENTER);
		textCPF.setBounds(153, 199, 136, 29);
		contentPane.add(textCPF);
		
		JLabel lbEmail = new JLabel("Email:", SwingConstants.CENTER);
		lbEmail.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbEmail.setBounds(190, 233, 69, 25);
		contentPane.add(lbEmail);
		
		comboBoxFuncao = new JComboBox<>();
		comboBoxFuncao.setModel(new DefaultComboBoxModel<>(new String[] {"Cliente", "Administrador"}));
		comboBoxFuncao.setFont(new Font("Tahoma", Font.PLAIN, 18));
		comboBoxFuncao.setBounds(153, 366, 136, 21);
		contentPane.add(comboBoxFuncao);
		
		textNome = new JTextField();
		textNome.setHorizontalAlignment(SwingConstants.CENTER);
		textNome.setBounds(153, 120, 136, 29);
		contentPane.add(textNome);
		textNome.setColumns(10);
		
		JLabel lblSelecioneSuaFuno = new JLabel("Selecione sua função!", SwingConstants.CENTER);
		lblSelecioneSuaFuno.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSelecioneSuaFuno.setBounds(125, 331, 189, 25);
		contentPane.add(lblSelecioneSuaFuno);
		
		JButton btnCadastrar = new JButton("Cadastrar-se");
		btnCadastrar.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnCadastrar.setBounds(81, 434, 151, 29);
		
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nome = textNome.getText().trim();
				
                String cpfMascarado = textCPF.getText();
                String cpfLimpo = cpfMascarado.replaceAll("[^0-9]", "").trim(); 
                
				String email = textEmail.getText().trim(); 
				String funcao = (String) comboBoxFuncao.getSelectedItem();
				
				
				if (nome.isEmpty() || cpfLimpo.length() != 11 || email.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Preencha o Nome, Email e o CPF completo!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
					return;
				}
				// limpar cpf
				Usuario novoUsuario = new Usuario(nome, cpfLimpo, email, funcao);
				ListaUsuarios.adicionarUsuario(novoUsuario);
				
				JOptionPane.showMessageDialog(null, "Usuário '" + nome + "' cadastrado com sucesso! Faça seu login.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
				
				JFrame identificacao = new TelaIdentificacao(); 
				identificacao.setVisible(true);
				TelaCadastroUsuario.this.dispose(); 
			}
		});
		
		contentPane.add(btnCadastrar);
		
		textEmail = new JTextField();
		textEmail.setHorizontalAlignment(SwingConstants.CENTER);
		textEmail.setColumns(10);
		textEmail.setBounds(138, 268, 176, 29);
		contentPane.add(textEmail);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaIdentificacao identificacao = new TelaIdentificacao();
				identificacao.setVisible(true);
				TelaCadastroUsuario.this.setVisible(false);
			}
		});
		btnVoltar.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnVoltar.setBounds(242, 434, 151, 29);
		contentPane.add(btnVoltar);
		
	}
}