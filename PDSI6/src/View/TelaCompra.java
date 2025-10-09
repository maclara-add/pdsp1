package View;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

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
        setBounds(100, 100, 850, 560);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblDisponiveis = new JLabel("Produtos Disponíveis:");
        lblDisponiveis.setBounds(53, 10, 200, 20);
        lblDisponiveis.setFont(new Font("Tahoma", Font.BOLD, 14));
        contentPane.add(lblDisponiveis);

        String[] colunas = {"Produto", "Preço", "Categoria", "ID", "Estoque"};
        modeloProdutos = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaProdutos = new JTable(modeloProdutos);
        JScrollPane scrollPaneProdutos = new JScrollPane(tabelaProdutos);
        scrollPaneProdutos.setBounds(10, 35, 400, 250);
        contentPane.add(scrollPaneProdutos);
        carregarProdutosNaTabela();

        JLabel lblCarrinho = new JLabel("Seu Carrinho:");
        lblCarrinho.setBounds(430, 10, 200, 20);
        lblCarrinho.setFont(new Font("Tahoma", Font.BOLD, 14));
        contentPane.add(lblCarrinho);

        String[] colunasCarrinho = {"Produto", "Preço", "Quantidade", "Subtotal", "ID"};
        modeloCarrinho = new DefaultTableModel(colunasCarrinho, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaCarrinho = new JTable(modeloCarrinho);
        JScrollPane scrollPaneCarrinho = new JScrollPane(tabelaCarrinho);
        scrollPaneCarrinho.setBounds(430, 35, 380, 250);
        contentPane.add(scrollPaneCarrinho);

        JButton btnAdicionar = new JButton("Adicionar ao Carrinho (->)");
        btnAdicionar.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnAdicionar.setBounds(10, 300, 400, 30);
        btnAdicionar.addActionListener(e -> adicionarAoCarrinho());
        contentPane.add(btnAdicionar);

        JButton btnRemover = new JButton("Remover do Carrinho (<-)");
        btnRemover.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnRemover.setBounds(430, 300, 380, 30);
        btnRemover.addActionListener(e -> removerDoCarrinho());
        contentPane.add(btnRemover);

        JLabel lblTotalEstatico = new JLabel("Total a Pagar:");
        lblTotalEstatico.setBounds(477, 340, 126, 20);
        lblTotalEstatico.setFont(new Font("Tahoma", Font.BOLD, 14));
        contentPane.add(lblTotalEstatico);

        labelTotal = new JLabel("R$ 0.00");
        labelTotal.setBounds(590, 340, 120, 20);
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
        btnNotaFiscal.addActionListener(e -> emitirNotaFiscal());
        contentPane.add(btnNotaFiscal);

        JButton btnVoltar = new JButton("Sair");
        btnVoltar.setForeground(new java.awt.Color(255, 0, 0));
        btnVoltar.addActionListener(e -> {
            TelaIdentificacao identificacao = new TelaIdentificacao();
            identificacao.setVisible(true);
            TelaCompra.this.setVisible(false);
        });
        btnVoltar.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnVoltar.setBounds(487, 432, 200, 40);
        contentPane.add(btnVoltar);
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

        String nome = modeloProdutos.getValueAt(linhaSelecionada, 0).toString();
        String precoStr = modeloProdutos.getValueAt(linhaSelecionada, 1).toString().replace(",", ".");
        String id = modeloProdutos.getValueAt(linhaSelecionada, 3).toString();
        int estoque = Integer.parseInt(modeloProdutos.getValueAt(linhaSelecionada, 4).toString());

        String qtdStr = JOptionPane.showInputDialog(this, "Quantidade desejada:");
        if (qtdStr == null) return;
        int qtdDesejada;
        try {
            qtdDesejada = Integer.parseInt(qtdStr);
            if (qtdDesejada <= 0) { JOptionPane.showMessageDialog(this, "Quantidade inválida."); return; }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite um número válido."); return;
        }

        if (qtdDesejada > estoque) {
            JOptionPane.showMessageDialog(this, "Estoque insuficiente! Estoque atual: " + estoque); return;
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
    }

    private void removerDoCarrinho() {
        int linhaSelecionada = tabelaCarrinho.getSelectedRow();
        if (linhaSelecionada < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um produto no carrinho para remover.");
            return;
        }

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
    }

    private void atualizarTotalCarrinho() {
        double total = 0;
        for (int i = 0; i < modeloCarrinho.getRowCount(); i++) {
            total += Double.parseDouble(modeloCarrinho.getValueAt(i, 3).toString());
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
