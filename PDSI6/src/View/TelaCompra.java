package View;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;

import Controller.ProdutoController;
import Model.CarrinhoDeCompras;
import Model.Produtos;

public class TelaCompra extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable tabelaProdutos;
    private JTable tabelaCarrinho;
    private DefaultTableModel modeloProdutos;
    private DefaultTableModel modeloCarrinho;
    private JLabel labelTotal;
    private JTextField textNomeCliente;
    private JTextField textCpfCliente;
    private CarrinhoDeCompras carrinho;

    public TelaCompra() {
        carrinho = new CarrinhoDeCompras();
        setTitle("Supermercado - Tela de Compras");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setMinimumSize(new Dimension(800, 550));
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        // duas colunas iguais 
        contentPane.setLayout(new MigLayout("fill, insets 10", "[grow,fill]10[grow,fill]", "[]10[grow,fill]10[][]10[][]10[][][]"));

        JLabel lblDisponiveis = new JLabel("Produtos Disponíveis:");
        lblDisponiveis.setFont(new Font("Tahoma", Font.BOLD, 14));
        contentPane.add(lblDisponiveis, "cell 0 0, align left");

        JLabel lblCarrinho = new JLabel("Seu Carrinho:");
        lblCarrinho.setFont(new Font("Tahoma", Font.BOLD, 14));
        contentPane.add(lblCarrinho, "cell 1 0,alignx left");

        String[] colunas = {"Produto", "Preço", "Categoria", "ID", "Estoque"};
        modeloProdutos = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaProdutos = new JTable(modeloProdutos);
        carregarProdutosNaTabela();
        JScrollPane scrollProdutos = new JScrollPane(tabelaProdutos);
        contentPane.add(scrollProdutos, "cell 0 1, grow, push, w 100%, h 100%");

        String[] colunasCarrinho = {"Produto", "Preço", "Quantidade", "Subtotal", "ID"};
        modeloCarrinho = new DefaultTableModel(colunasCarrinho, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaCarrinho = new JTable(modeloCarrinho);
        JScrollPane scrollCarrinho = new JScrollPane(tabelaCarrinho);
        contentPane.add(scrollCarrinho, "cell 1 1,push,width 100%,height 100%,grow");

        JButton btnAdicionar = new JButton("Adicionar ao Carrinho (->)");
        btnAdicionar.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnAdicionar.addActionListener(e -> adicionarAoCarrinho());
        contentPane.add(btnAdicionar, "cell 0 2, growx");

        JButton btnRemover = new JButton("Remover do Carrinho (<-)");
        btnRemover.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnRemover.addActionListener(e -> removerDoCarrinho());
        contentPane.add(btnRemover, "cell 1 2,growx");

        JLabel lblTotalEstatico = new JLabel("Total a Pagar:");
        lblTotalEstatico.setFont(new Font("Tahoma", Font.BOLD, 14));
        labelTotal = new JLabel("R$ 0.00");
        labelTotal.setFont(new Font("Tahoma", Font.BOLD, 16));
        
        JPanel panelTotal = new JPanel(new MigLayout("fill, insets 0", "[grow, fill][right]", "[]"));
        panelTotal.add(lblTotalEstatico, "align left");
        panelTotal.add(labelTotal, "align right");
        
        contentPane.add(new JPanel(), "cell 0 3");
        contentPane.add(panelTotal, "cell 1 3,growx");

        JLabel lblNome = new JLabel("Nome Cliente:");
        contentPane.add(lblNome, "cell 0 4, align left");

        JButton btnNotaFiscal = new JButton("Emitir Nota Fiscal");
        btnNotaFiscal.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnNotaFiscal.addActionListener(e -> emitirNotaFiscal());
        
        textNomeCliente = new JTextField();
        textNomeCliente.setFont(new Font("Tahoma", Font.PLAIN, 16));
        contentPane.add(textNomeCliente, "cell 0 5,growx,aligny center");
        contentPane.add(btnNotaFiscal, "cell 1 5,growx");

        JLabel lblCpf = new JLabel("CPF Cliente:");
        contentPane.add(lblCpf, "cell 0 6, align left");

        JButton btnVoltar = new JButton("Sair");
        btnVoltar.setForeground(new java.awt.Color(255, 0, 0));
        btnVoltar.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnVoltar.addActionListener(e -> {
            TelaIdentificacao identificacao = new TelaIdentificacao();
            identificacao.setVisible(true);
            TelaCompra.this.setVisible(false);
        });
        contentPane.add(btnVoltar, "cell 1 6,growx");

        textCpfCliente = new JTextField();
        textCpfCliente.setFont(new Font("Tahoma", Font.PLAIN, 16));
        contentPane.add(textCpfCliente, "cell 0 7,growx,aligny center");
    }

    private void carregarProdutosNaTabela() {
        modeloProdutos.setRowCount(0);
        ArrayList<Produtos> lista = ProdutoController.listarProdutos();
        if (lista != null) {
            for (Produtos p : lista) {
                modeloProdutos.addRow(new Object[]{
                        p.getNome(),
                        p.getPreco(),
                        p.getCategoria(),
                        p.getId(),
                        p.getEstoque()
                });
            }
        }
    }

    private void adicionarAoCarrinho() {
        int linhaSelecionada = tabelaProdutos.getSelectedRow();
        if (linhaSelecionada < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um produto da lista para adicionar ao carrinho.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }


        try {
            String nome = modeloProdutos.getValueAt(linhaSelecionada, 0).toString();
            // substitui , por . para garantir o parse correto para double
            String precoStr = modeloProdutos.getValueAt(linhaSelecionada, 1).toString().replace(",", ".");
            String id = modeloProdutos.getValueAt(linhaSelecionada, 3).toString();
            int estoque = Integer.parseInt(modeloProdutos.getValueAt(linhaSelecionada, 4).toString());

            String qtdStr = JOptionPane.showInputDialog(this, "Quantidade desejada:");
            if (qtdStr == null) return; 
            
            int qtdDesejada;
            try {
                qtdDesejada = Integer.parseInt(qtdStr);
                if (qtdDesejada <= 0) {
                    JOptionPane.showMessageDialog(this, "A quantidade deve ser maior que zero.", "Erro de Quantidade", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Valor inválido. Digite apenas números inteiros para a quantidade.", "Erro de Conversão", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (qtdDesejada > estoque) {
                JOptionPane.showMessageDialog(this, "Estoque insuficiente! Estoque atual: " + estoque, "Aviso de Estoque", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Produtos produto = ProdutoController.buscarProduto(id);
            if (produto != null) {
                produto.setEstoque(produto.getEstoque() - qtdDesejada);
                ProdutoController.editarProduto(id, produto);
            }

            carregarProdutosNaTabela();

            
            double precoDouble = Double.parseDouble(precoStr); 
            double subtotal = precoDouble * qtdDesejada;

            modeloCarrinho.addRow(new Object[]{nome, precoStr.replace(".", ","), qtdDesejada, subtotal, id});
            carrinho.adicionarProduto(new Produtos(nome, precoStr.replace(".", ","), "", id, qtdDesejada));

            atualizarTotalCarrinho();
            JOptionPane.showMessageDialog(this, "Produto '" + nome + "' adicionado ao carrinho!");
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erro interno de formato de dados (preço ou estoque inválido).", "Erro Interno", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ocorreu um erro inesperado ao adicionar o produto.", "Erro Desconhecido", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void removerDoCarrinho() {
        int linhaSelecionada = tabelaCarrinho.getSelectedRow();
        if (linhaSelecionada < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um produto no carrinho para remover.");
            return;
        }

        try {
            String id = modeloCarrinho.getValueAt(linhaSelecionada, 4).toString();
            int qtd = Integer.parseInt(modeloCarrinho.getValueAt(linhaSelecionada, 2).toString());

            Produtos p = ProdutoController.buscarProduto(id);
            if (p != null) {
                p.setEstoque(p.getEstoque() + qtd);
                ProdutoController.editarProduto(id, p);
            }
            carrinho.removerProdutoPorId(id);
            modeloCarrinho.removeRow(linhaSelecionada);
            carregarProdutosNaTabela();
            atualizarTotalCarrinho();
            JOptionPane.showMessageDialog(this, "Produto removido do carrinho e devolvido ao estoque.");
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erro interno de formato ao ler quantidade ou ID do carrinho.", "Erro Interno", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ocorreu um erro inesperado ao remover o produto.", "Erro Desconhecido", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void atualizarTotalCarrinho() {
        double total = 0;
        for (int i = 0; i < modeloCarrinho.getRowCount(); i++) {
            try {
                total += Double.parseDouble(modeloCarrinho.getValueAt(i, 3).toString());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Erro ao calcular o total. Subtotal inválido na linha " + (i + 1) + ".", "Erro de Cálculo", JOptionPane.ERROR_MESSAGE);
                System.err.println("Erro ao calcular o subtotal na linha " + i + ": " + e.getMessage());
            }
        }
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

        StringBuilder nota = new StringBuilder();
        nota.append("================ NOTA FISCAL ==================\n");
        nota.append("Cliente: ").append(nome).append("\n");
        nota.append("CPF: ").append(cpf).append("\n");
        nota.append("-----------------------------------------------\n");
        nota.append("ITENS COMPRADOS:\n");
        for (Produtos p : carrinho.getItens()) {
            nota.append(String.format("- %s [ID: %s] - R$ %s - Qtd: %d\n", p.getNome(), p.getId(), p.getPreco(), p.getEstoque()));
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

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                TelaCompra frame = new TelaCompra();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}