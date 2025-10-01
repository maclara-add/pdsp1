package projetoUM;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
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

public class TelaIdentificacao extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textNome;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaIdentificacao frame = new TelaIdentificacao();
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
	public TelaIdentificacao() {
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
		
		MaskFormatter mascaraCPF = null;
		try {
		    mascaraCPF = new MaskFormatter("###.###.###-##"); // formato do CPF
		    mascaraCPF.setPlaceholderCharacter('_'); // underline nos espaços vazios
		} catch (ParseException e) {
		    e.printStackTrace();
		}

		JFormattedTextField TextCPF = new JFormattedTextField(mascaraCPF);
		TextCPF.setHorizontalAlignment(SwingConstants.CENTER);
		TextCPF.setFont(new Font("Tahoma", Font.PLAIN, 17));
		TextCPF.setBounds(206, 233, 195, 32);
		contentPane.add(TextCPF);
			
		textNome = new JTextField();
		textNome.setHorizontalAlignment(SwingConstants.CENTER);
		textNome.setFont(new Font("Tahoma", Font.PLAIN, 17));
		textNome.setBounds(206, 157, 195, 32);
		contentPane.add(textNome);
		textNome.setColumns(10);
		
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
					TelaIdentificacao.this.setVisible(false);

				} else if(rdbtnCliente.isSelected()) {
					TelaCompra compra = new TelaCompra();
					compra.setVisible(true);
					TelaIdentificacao.this.setVisible(false);
				} else if((textNome.getText().isEmpty()) || (TextCPF.getText().isEmpty()) || grupoUsuarios.getSelection() == null) {
					 JOptionPane.showMessageDialog(null, "Preencha todos os campos", "Erro", JOptionPane.ERROR_MESSAGE);	
				}
			}
		});
		btnEntrar.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnEntrar.setBounds(247, 408, 106, 41);
		contentPane.add(btnEntrar);
		
	}
}
