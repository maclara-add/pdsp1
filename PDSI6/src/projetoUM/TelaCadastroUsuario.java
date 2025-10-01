package projetoUM;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

public class TelaCadastroUsuario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

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
		
		JLabel lbCadastro = new JLabel("Cadastre-se!");
		lbCadastro.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbCadastro.setBounds(169, 63, 96, 13);
		contentPane.add(lbCadastro);
		
		JLabel lbNome = new JLabel("Nome:");
		lbNome.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbNome.setBounds(180, 118, 69, 25);
		contentPane.add(lbNome);
		
		JLabel lbCPF = new JLabel("CPF:");
		lbCPF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbCPF.setBounds(196, 192, 69, 25);
		contentPane.add(lbCPF);
		
		JLabel lbEmail = new JLabel("Email:");
		lbEmail.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbEmail.setBounds(171, 266, 69, 25);
		contentPane.add(lbEmail);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Administrador", "Cliente"}));
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboBox.setToolTipText("Cliente\r\nAdministrador");
		comboBox.setBounds(153, 389, 136, 21);
		contentPane.add(comboBox);
		
		textField = new JTextField();
		textField.setBounds(149, 153, 127, 29);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(149, 227, 127, 29);
		contentPane.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(125, 302, 189, 29);
		contentPane.add(textField_2);
		
		JLabel lblSelecioneSuaFuno = new JLabel("Selecione sua função!");
		lblSelecioneSuaFuno.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSelecioneSuaFuno.setBounds(125, 354, 189, 25);
		contentPane.add(lblSelecioneSuaFuno);
		
		JButton btnNewButton = new JButton("Cadastrar-se");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnNewButton.setBounds(138, 442, 151, 21);
		contentPane.add(btnNewButton);
	}
}
