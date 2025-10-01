package projetoUM;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;

public class TelaCompra extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tabelaProdutos;
	private JTable tabelaCarrinho;
	private DefaultTableModel modeloCarrinho;
	private JLabel labelTotal;
	private JTextField textNomeCliente; 
	private JTextField textCpfCliente; 

	private CarrinhoDeCompras carrinho; 
	
	public TelaCompra() {
	
		this.carrinho = new CarrinhoDeCompras(); 
		
		setTitle("Supermercado - Tela de Compras");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 790, 559); 
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);  

		
		JLabel lblDisponiveis = new JLabel("Produtos Disponíveis:");
		lblDisponiveis.setBounds(53, 10, 200, 20);
		lblDisponiveis.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPane.add(lblDisponiveis);
		
		String[] colunas = {"Produto", "Preço", "Categoria", "ID"};
		DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0) {
		    
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tabelaProdutos = new JTable(modeloTabela);  

		JScrollPane scrollPaneProdutos = new JScrollPane(tabelaProdutos);  
		scrollPaneProdutos.setBounds(10, 35, 350, 250);
		contentPane.add(scrollPaneProdutos);
	
		carregarProdutosNaTabela(modeloTabela);
		
		JLabel lblCarrinho = new JLabel("Seu Carrinho:");
		lblCarrinho.setBounds(450, 10, 200, 20);
		lblCarrinho.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPane.add(lblCarrinho);
		
		String[] colunasCarrinho = {"Produto", "Preço", "ID"};
		modeloCarrinho = new DefaultTableModel(colunasCarrinho, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tabelaCarrinho = new JTable(modeloCarrinho);  

		JScrollPane scrollPaneCarrinho = new JScrollPane(tabelaCarrinho);  
		scrollPaneCarrinho.setBounds(392, 35, 350, 250); 
		contentPane.add(scrollPaneCarrinho);
		
		JButton btnAdicionar = new JButton("Adicionar ao Carrinho (->)");
		btnAdicionar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAdicionar.setBounds(10, 300, 350, 30);
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarAoCarrinho(tabelaProdutos);
			}
		});
		contentPane.add(btnAdicionar);

		JButton btnRemover = new JButton("Remover do Carrinho (<-)");
		btnRemover.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnRemover.setBounds(392, 300, 350, 30);
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removerDoCarrinho(tabelaCarrinho);
			}
		});
		contentPane.add(btnRemover);
		
		JLabel lblTotalEstatico = new JLabel("Total a Pagar:");
		lblTotalEstatico.setBounds(477, 340, 126, 20);
		lblTotalEstatico.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPane.add(lblTotalEstatico);
		
		labelTotal = new JLabel("R$ 0.00");
		labelTotal.setBounds(590, 340, 84, 20);
		labelTotal.setFont(new Font("Tahoma", Font.BOLD, 16));
		contentPane.add(labelTotal);
		
		JLabel lblNome = new JLabel("Nome Cliente:");
		lblNome.setBounds(53, 380, 100, 20);
		contentPane.add(lblNome);
		
		textNomeCliente = new JTextField();
		textNomeCliente.setBounds(150, 380, 253, 25);
		contentPane.add(textNomeCliente);
		
		JLabel lblCpf = new JLabel("CPF Cliente:");
		lblCpf.setBounds(53, 420, 100, 20);
		contentPane.add(lblCpf);

		textCpfCliente = new JTextField();
		textCpfCliente.setBounds(150, 420, 253, 25);
		contentPane.add(textCpfCliente);
		
		JButton btnNotaFiscal = new JButton("Emitir Nota Fiscal");
		btnNotaFiscal.setBounds(487, 370, 200, 40);
		btnNotaFiscal.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNotaFiscal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emitirNotaFiscal();
			}
		});
		contentPane.add(btnNotaFiscal);
		
		JButton btnVoltar = new JButton("Sair");
		btnVoltar.setForeground(new Color(255, 0, 0));
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnVoltar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnVoltar.setBounds(487, 432, 200, 40);
		contentPane.add(btnVoltar);
	}
	
	
	private void carregarProdutosNaTabela(DefaultTableModel modelo) {
		ArrayList<Produtos> lista = ListaProdutos.getLista();
		
		if (lista != null && !lista.isEmpty()) {
			for (Produtos p : lista) {
				modelo.addRow(new Object[] {
					p.getNome(), 
					p.getPreco(), 
					p.getCategoria(), 
					p.getId()
				});
			}
		} else {
			System.out.println("Lista de produtos vazia ou nula.");
		}
	}
	
	private void adicionarAoCarrinho(JTable tabelaProdutos) {
		int linhaSelecionada = tabelaProdutos.getSelectedRow();
		
		if (linhaSelecionada >= 0) {
			String nome = (String) tabelaProdutos.getValueAt(linhaSelecionada, 0);
			String precoStr = (String) tabelaProdutos.getValueAt(linhaSelecionada, 1);
			String id = (String) tabelaProdutos.getValueAt(linhaSelecionada, 3);
			
			try {
				double preco = Double.parseDouble(precoStr);
				
				Produtos produtoParaCarrinho = new Produtos(nome, precoStr, "", id); // Categoria não é necessária no carrinho
				carrinho.adicionarProduto(produtoParaCarrinho);
				
				// Atualiza o modelo da JTable do carrinho
				modeloCarrinho.addRow(new Object[] {nome, precoStr, id});
				
				// Atualiza o total
				atualizarTotalCarrinho();
				
				JOptionPane.showMessageDialog(this, "Produto '" + nome + "' adicionado ao carrinho!");
				
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Erro: Preço inválido para o produto.", "Erro", JOptionPane.ERROR_MESSAGE);
			}
			
		} else {
			JOptionPane.showMessageDialog(this, "Selecione um produto da lista para adicionar ao carrinho.", "Aviso", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void removerDoCarrinho(JTable tabelaCarrinho) {
		int linhaSelecionada = tabelaCarrinho.getSelectedRow();
		
		if (linhaSelecionada >= 0) {
			// Pega o ID para remover do objeto CarrinhoDeCompras
			String id = (String) modeloCarrinho.getValueAt(linhaSelecionada, 2);
			
			// Remove do objeto CarrinhoDeCompras
			carrinho.removerProdutoPorId(id);
			
			// Remove da JTable do carrinho
			modeloCarrinho.removeRow(linhaSelecionada);
			
			// Atualiza o total
			atualizarTotalCarrinho();
			
			JOptionPane.showMessageDialog(this, "Produto removido do carrinho.");
			
		} else {
			JOptionPane.showMessageDialog(this, "Selecione um produto no carrinho para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void atualizarTotalCarrinho() {
		double total = carrinho.getTotal();
		labelTotal.setText(String.format("R$ %.2f", total));
	}
	
	private void emitirNotaFiscal() {
	    if (carrinho.getItens().isEmpty()) {
	        JOptionPane.showMessageDialog(this, "O carrinho está vazio. Adicione produtos antes de emitir a nota.", "Aviso", JOptionPane.WARNING_MESSAGE);
	        return;
	    }
	    
	    String nome = textNomeCliente.getText().trim();
	    String cpf = textCpfCliente.getText().trim();
	    
	    if (nome.isEmpty() || cpf.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Preencha o Nome e CPF do cliente para emitir a nota.", "Aviso", JOptionPane.WARNING_MESSAGE);
	        return;
	    }
	    
	    // Monta o corpo da nota fiscal
	    StringBuilder nota = new StringBuilder();
	    nota.append("================ NOTA FISCAL ==================\n");
	    nota.append("Cliente: ").append(nome).append("\n");
	    nota.append("CPF: ").append(cpf).append("\n");
	    nota.append("-----------------------------------------------\n");
	    nota.append("ITENS COMPRADOS:\n");
	    
	    for (Produtos p : carrinho.getItens()) {
	    	// Formata a linha como Nome [ID] - R$ Preço
	        nota.append(String.format("- %s [ID: %s] - R$ %s\n", 
	            p.getNome(), 
	            p.getId(), 
	            p.getPreco()));
	    }
	    
	    nota.append("-----------------------------------------------\n");
	    nota.append(String.format("TOTAL PAGO: R$ %.2f\n", carrinho.getTotal()));
	    nota.append("===============================================\n");
	    
	    JOptionPane.showMessageDialog(this, nota.toString(), "Nota Fiscal Emitida", JOptionPane.INFORMATION_MESSAGE);
	    
	    carrinho.limparCarrinho();
	    modeloCarrinho.setRowCount(0);
	    atualizarTotalCarrinho();
	    textNomeCliente.setText("");
	    textCpfCliente.setText("");
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCompra frame = new TelaCompra();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}