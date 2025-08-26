package projetoUM;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;

public class TelaIdentificação extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaIdentificação frame = new TelaIdentificação();
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
	public TelaIdentificação() {
		setTitle("Tela de Identificação de Usuário");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 615, 510);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel IbUsu = new JLabel("Bem vindo!");
		IbUsu.setFont(new Font("Tahoma", Font.PLAIN, 16));
		IbUsu.setHorizontalAlignment(SwingConstants.CENTER);
		IbUsu.setBounds(160, 43, 263, 13);
		contentPane.add(IbUsu);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNome.setBounds(110, 156, 70, 25);
		contentPane.add(lblNome);
		
		JLabel lblCpf = new JLabel("CPF:");
		lblCpf.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCpf.setBounds(110, 232, 70, 25);
		contentPane.add(lblCpf);
		
		JFormattedTextField formattedTextField = new JFormattedTextField();
		formattedTextField.setFont(new Font("Tahoma", Font.PLAIN, 17));
		formattedTextField.setBounds(206, 233, 195, 32);
		contentPane.add(formattedTextField);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 17));
		textField.setBounds(206, 157, 195, 32);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JRadioButton rdbtnAdministrador = new JRadioButton("Administrador");
		rdbtnAdministrador.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rdbtnAdministrador.setBounds(143, 327, 143, 21);
		contentPane.add(rdbtnAdministrador);
		
		JRadioButton rdbtnCliente = new JRadioButton("Cliente");
		rdbtnCliente.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rdbtnCliente.setBounds(347, 327, 143, 21);
		contentPane.add(rdbtnCliente);
		
		ButtonGroup grupoUsuarios = new ButtonGroup();
		grupoUsuarios.add(rdbtnAdministrador);
		grupoUsuarios.add(rdbtnCliente);
		
		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnAdministrador.isSelected()) {
					CadastroProdutos cadastroproduto = new CadastroProdutos();
					cadastroproduto.setVisible(true);
					TelaIdentificação.this.setVisible(false);

				} else if(rdbtnCliente.isSelected()) {
					TelaCompra compra = new TelaCompra();
					compra.setVisible(true);
					TelaIdentificação.this.setVisible(false);
				}
			}
		});
		btnEntrar.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnEntrar.setBounds(247, 408, 106, 41);
		contentPane.add(btnEntrar);
	}
}
