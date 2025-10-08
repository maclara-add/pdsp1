package View;

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
import java.sql.Connection;
import java.awt.event.ActionEvent;
import javax.swing.text.MaskFormatter;

import Controller.UsuarioController;
import DAO.Conexao;
import DAO.UsuarioDAO;
import Model.ListaUsuarios;
import Model.Usuario;

import java.text.ParseException;

public class TelaIdentificacao extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textNome;
    private JFormattedTextField TextCPF;
    private UsuarioController usuarioController;

	/**
	 *
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
		
		try {
		    Connection conn = Conexao.getConnection();
		    UsuarioDAO usuarioDAO = new UsuarioDAO(conn);
		    usuarioController = new UsuarioController(usuarioDAO);
		} catch (Exception e) {
		    JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco: " + e.getMessage());
		}
		
		JLabel IbUsu = new JLabel("Bem vindo!");
		IbUsu.setFont(new Font("Tahoma", Font.PLAIN, 16));
		IbUsu.setHorizontalAlignment(SwingConstants.CENTER);
		IbUsu.setBounds(160, 43, 263, 13);
		contentPane.add(IbUsu);
		
		JLabel lblNome = new JLabel("Nome:", SwingConstants.RIGHT);
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNome.setBounds(110, 156, 70, 25);
		contentPane.add(lblNome);
		
		JLabel lblCpf = new JLabel("CPF:", SwingConstants.RIGHT);
		lblCpf.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCpf.setBounds(110, 232, 70, 25);
		contentPane.add(lblCpf);
		
		MaskFormatter mascaraCPF = null;
		try {
		    mascaraCPF = new MaskFormatter("###.###.###-##");
		    mascaraCPF.setPlaceholderCharacter('_');
		} catch (ParseException e) {
		    e.printStackTrace();
		}

		TextCPF = new JFormattedTextField(mascaraCPF);
		TextCPF.setHorizontalAlignment(SwingConstants.CENTER);
		TextCPF.setFont(new Font("Tahoma", Font.PLAIN, 17));
		TextCPF.setBounds(206, 233, 195, 32);
		contentPane.add(TextCPF);
			
		// CAMPO TEXTO PARA O NOME
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
		        
		        String nome = textNome.getText().trim(); 
		        
		        String cpf = TextCPF.getText().replaceAll("[^0-9]", "").trim(); 
		        String funcaoSelecionada = "";
		        
		        if(rdbtnAdministrador.isSelected()) {
		            funcaoSelecionada = "Administrador";
		        } else if(rdbtnCliente.isSelected()) {
		            funcaoSelecionada = "Cliente";
		        } 
		        
		        if(nome.isEmpty() || cpf.length() != 11 || grupoUsuarios.getSelection() == null) {
		            JOptionPane.showMessageDialog(null, "Preencha o Nome, o CPF completo e selecione a função.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);	
		            return;
		        }
		        
		        boolean login = usuarioController.login(cpf, nome);
		        
		        if (login) {
		            
		            Usuario usuario = usuarioController.buscarUsuario(cpf);
		            
		            if (usuario != null && usuario.getFuncao().equals(funcaoSelecionada)) {
		                JOptionPane.showMessageDialog(null, "Login efetuado com sucesso! Bem-vindo(a), " + usuario.getNome() + ".", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
		                
		                if(usuario.getFuncao().equals("Administrador")) {
		                    JFrame cadastroproduto = new CadastroProdutos();
		                    cadastroproduto.setVisible(true);
		                } else if(usuario.getFuncao().equals("Cliente")) {
		                    JFrame compra = new TelaCompra();
		                    compra.setVisible(true);
		                }
		                TelaIdentificacao.this.dispose();
		                
		            } else if (usuario != null) {
		                JOptionPane.showMessageDialog(null, "Função incorreta selecionada. Você está cadastrado(a) como " + usuario.getFuncao() + ".", "Erro de Acesso", JOptionPane.WARNING_MESSAGE);
		            } else {
		                 JOptionPane.showMessageDialog(null, "Usuário não encontrado. Verifique seu Nome e CPF.", "Falha na Identificação", JOptionPane.ERROR_MESSAGE);
		            }
		            
		        } else {
		            JOptionPane.showMessageDialog(null, "Usuário não encontrado. Verifique seu Nome e CPF.", "Falha na Identificação", JOptionPane.ERROR_MESSAGE);
		        }
		    }
		});
		btnEntrar.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnEntrar.setBounds(160, 408, 106, 41);
		contentPane.add(btnEntrar);
		
		JButton btnCadastrarse = new JButton("Cadastrar-se");
		btnCadastrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCadastroUsuario cadastrousuario = new TelaCadastroUsuario();
				cadastrousuario.setVisible(true);
				TelaIdentificacao.this.dispose();
			}
		});
		btnCadastrarse.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnCadastrarse.setBounds(287, 408, 163, 41);
		contentPane.add(btnCadastrarse);
		
	}
}