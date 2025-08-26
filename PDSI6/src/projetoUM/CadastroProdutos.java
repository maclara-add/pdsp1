package projetoUM;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class CadastroProdutos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable produtos;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastroProdutos frame = new CadastroProdutos();
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
	public CadastroProdutos() {
		setTitle("Administrador - Cadastros");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 615, 510);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);  

		// Para definir as colunas da tabela
		String[] colunas = {"Produtos"};  // Uma coluna só para produtos
		DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);  // Inicializa a tabela com 0 linhas
		produtos = new JTable(modeloTabela);  // Criando a JTable com o modelo de dados

		// Definindo o painel de rolagem para a tabela
		JScrollPane scrollPane = new JScrollPane(produtos);  
		scrollPane.setBounds(53, 54, 209, 345);  

		// Adicionando o JScrollPane no contentPane (onde está a tabela)
		contentPane.add(scrollPane);

		
		// Criando um painel para os botões
		JPanel panel = new JPanel();
		panel.setBounds(326, 161, 224, 109);  
		contentPane.add(panel);
		
		JButton btnVisualizar = new JButton("Visualizar produto");
		btnVisualizar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel.add(btnVisualizar);
		
		JButton btnRemover = new JButton("Remover produto");
		btnRemover.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel.add(btnRemover);
		
		JButton btnAdicionar = new JButton("Adicionar produto");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnAdicionar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel.add(btnAdicionar);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 101, 22);
		contentPane.add(menuBar);
		
		JMenu mnSair = new JMenu("Menu");
		mnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaIdentificação identificacao = new TelaIdentificação();
				identificacao.setVisible(true);
				CadastroProdutos.this.setVisible(false);
			}
		});
		menuBar.add(mnSair);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Voltar");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaIdentificação identificacao = new TelaIdentificação();
				identificacao.setVisible(true);
				CadastroProdutos.this.setVisible(false);
			}
		});
		mnSair.add(mntmNewMenuItem);

		
	}
}
