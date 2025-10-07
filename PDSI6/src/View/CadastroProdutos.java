package View;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import Model.ListaProdutos;
import Model.Produtos;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import Model.Usuario;

public class CadastroProdutos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tabelaprodutos;
	private JTextField textNome1;
	private JTextField textNome2;
	private JTextField textPreco1;
	private JTextField textCategoria1;
	private JTextField textID1;
	private JTextField textPreco2;
	private JTextField textCategoria2;
	private JTextField textID2;
	private JTextField textEstoque;
	private JTextField textEstoque2;

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

	public CadastroProdutos() {
		setTitle("Administrador - Cadastros");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 756, 558);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		String[] colunas = {"Produto", "Preço", "Categoria", "ID", "Estoque"};
		DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);
		tabelaprodutos = new JTable(modeloTabela);
		JScrollPane scrollPane = new JScrollPane(tabelaprodutos);
		scrollPane.setBounds(113, 42, 506, 272);
		contentPane.add(scrollPane);

		JPanel panel = new JPanel();
		panel.setBounds(10, 335, 722, 165);
		contentPane.add(panel);
		panel.setLayout(null);

		JButton btnEditar = new JButton("Editar produto");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = tabelaprodutos.getSelectedRow();
				if (linhaSelecionada >= 0) {
					String idAntigo = (String) modeloTabela.getValueAt(linhaSelecionada, 3);
					String novoNome = textNome2.getText();
					String novoPreco = textPreco2.getText();
					String novaCategoria = textCategoria2.getText();
					String novoID = textID2.getText();
					String novoEstoqueStr = textEstoque2.getText();
					if (novoNome.isEmpty() || novoPreco.isEmpty() || novaCategoria.isEmpty() || novoID.isEmpty() || novoEstoqueStr.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Preencha todos os campos para editar!", "Erro", JOptionPane.ERROR_MESSAGE);
						return;
					}
					int novoEstoque;
					try {
						novoEstoque = Integer.parseInt(novoEstoqueStr);
					} catch (NumberFormatException o) {
						JOptionPane.showMessageDialog(null, "Digite um valor numérico para o estoque!", "Erro", JOptionPane.ERROR_MESSAGE);
						return;
					}
					ListaProdutos.editarProdutoPorId(idAntigo, novoNome, novoPreco, novaCategoria, novoID, novoEstoque);
					modeloTabela.setValueAt(novoNome, linhaSelecionada, 0);
					modeloTabela.setValueAt(novoPreco, linhaSelecionada, 1);
					modeloTabela.setValueAt(novaCategoria, linhaSelecionada, 2);
					modeloTabela.setValueAt(novoID, linhaSelecionada, 3);
					modeloTabela.setValueAt(novoEstoque, linhaSelecionada, 4);
					JOptionPane.showMessageDialog(null, "Produto atualizado com sucesso!");
					textNome2.setText("");
					textPreco2.setText("");
					textCategoria2.setText("");
					textID2.setText("");
					textEstoque2.setText("");
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um produto para editar!", "Aviso", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnEditar.setBounds(561, 68, 151, 27);
		btnEditar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel.add(btnEditar);

		ListaProdutos produtos = new ListaProdutos();

		JButton btnAdicionar = new JButton("Adicionar produto");
		btnAdicionar.setBounds(561, 31, 151, 27);
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nome = textNome1.getText();
				String preco = textPreco1.getText();
				String categoria = textCategoria1.getText();
				String id = textID1.getText();
				String estoqueStr = textEstoque.getText();
				int estoque;
				try {
					estoque = Integer.parseInt(estoqueStr);
				} catch (NumberFormatException o) {
					JOptionPane.showMessageDialog(null, "Digite um valor numérico para o estoque!", "Erro", JOptionPane.ERROR_MESSAGE);
					return;
				}
				Produtos p = new Produtos(nome, preco, categoria, id, estoque);
				ListaProdutos.adicionarProduto(p);
				modeloTabela.addRow(new Object[] {nome, preco, categoria, id, estoque});
				textNome1.setText("");
				textPreco1.setText("");
				textCategoria1.setText("");
				textID1.setText("");
				textEstoque.setText("");
			}
		});
		btnAdicionar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel.add(btnAdicionar);

		JButton btnRemover = new JButton("Remover produto");
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = tabelaprodutos.getSelectedRow();
				if (linhaSelecionada >= 0) {
					String id = (String) modeloTabela.getValueAt(linhaSelecionada, 3);
					modeloTabela.removeRow(linhaSelecionada);
					ListaProdutos.removerProdutoPorId(id);
					JOptionPane.showMessageDialog(null, "Produto removido com sucesso");
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um produto para remover", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnRemover.setBounds(273, 116, 171, 27);
		btnRemover.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel.add(btnRemover);

		textNome1 = new JTextField();
		textNome1.setHorizontalAlignment(SwingConstants.CENTER);
		textNome1.setBounds(10, 31, 96, 27);
		panel.add(textNome1);

		textNome2 = new JTextField();
		textNome2.setHorizontalAlignment(SwingConstants.CENTER);
		textNome2.setBounds(10, 68, 96, 27);
		panel.add(textNome2);

		textPreco1 = new JTextField();
		textPreco1.setHorizontalAlignment(SwingConstants.CENTER);
		textPreco1.setBounds(116, 31, 96, 27);
		panel.add(textPreco1);

		textCategoria1 = new JTextField();
		textCategoria1.setHorizontalAlignment(SwingConstants.CENTER);
		textCategoria1.setBounds(222, 31, 96, 27);
		panel.add(textCategoria1);

		textID1 = new JTextField();
		textID1.setHorizontalAlignment(SwingConstants.CENTER);
		textID1.setBounds(328, 31, 96, 27);
		panel.add(textID1);

		textPreco2 = new JTextField();
		textPreco2.setHorizontalAlignment(SwingConstants.CENTER);
		textPreco2.setBounds(116, 68, 96, 27);
		panel.add(textPreco2);

		textCategoria2 = new JTextField();
		textCategoria2.setHorizontalAlignment(SwingConstants.CENTER);
		textCategoria2.setBounds(222, 68, 96, 27);
		panel.add(textCategoria2);

		textID2 = new JTextField();
		textID2.setHorizontalAlignment(SwingConstants.CENTER);
		textID2.setBounds(328, 68, 96, 27);
		panel.add(textID2);

		textEstoque = new JTextField();
		textEstoque.setHorizontalAlignment(SwingConstants.CENTER);
		textEstoque.setBounds(434, 31, 96, 27);
		panel.add(textEstoque);

		textEstoque2 = new JTextField();
		textEstoque2.setHorizontalAlignment(SwingConstants.CENTER);
		textEstoque2.setBounds(434, 68, 96, 27);
		panel.add(textEstoque2);

		JLabel lblNewLabel = new JLabel("Nome");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(34, 10, 45, 13);
		panel.add(lblNewLabel);

		JLabel lblPreo = new JLabel("Preço");
		lblPreo.setHorizontalAlignment(SwingConstants.CENTER);
		lblPreo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPreo.setBounds(138, 12, 45, 13);
		panel.add(lblPreo);

		JLabel lblCategoria = new JLabel("Categoria");
		lblCategoria.setHorizontalAlignment(SwingConstants.CENTER);
		lblCategoria.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCategoria.setBounds(236, 12, 64, 13);
		panel.add(lblCategoria);

		JLabel lblId = new JLabel("ID");
		lblId.setHorizontalAlignment(SwingConstants.CENTER);
		lblId.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblId.setBounds(342, 12, 64, 13);
		panel.add(lblId);

		JLabel lblEstoque = new JLabel("Estoque");
		lblEstoque.setHorizontalAlignment(SwingConstants.CENTER);
		lblEstoque.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEstoque.setBounds(447, 12, 64, 13);
		panel.add(lblEstoque);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 101, 22);
		contentPane.add(menuBar);

		JMenu mnSair = new JMenu("Menu");
		mnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaIdentificacao identificacao = new TelaIdentificacao();
				identificacao.setVisible(true);
				CadastroProdutos.this.setVisible(false);
			}
		});
		menuBar.add(mnSair);

		JMenuItem mntmNewMenuItem = new JMenuItem("Voltar");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaIdentificacao identificacao = new TelaIdentificacao();
				identificacao.setVisible(true);
				CadastroProdutos.this.setVisible(false);
			}
		});
		mnSair.add(mntmNewMenuItem);
	}
}
