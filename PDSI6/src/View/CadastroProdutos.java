package View;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;

import Controller.ProdutoController;
import Model.Produtos;
import java.awt.Font;

public class CadastroProdutos extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable tabelaprodutos;
    private JTextField textNome1, textPreco1, textCategoria1, textID1, textEstoque1;
    private JTextField textNome2, textPreco2, textCategoria2, textID2, textEstoque2;
    private DefaultTableModel modeloTabela;

    public CadastroProdutos() {
        setTitle("Administrador - Cadastros");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 600);
        setMinimumSize(new Dimension(750, 550));
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        // MigLayout do ContentPane: Menu, Tabela, Ações (OK, Grow Vertical e Horizontal)
        contentPane.setLayout(new MigLayout(
                "fill, insets 10",
                "[grow, fill]",
                "[]10[grow, fill][]"
        ));

        // -------------------- MENU BAR --------------------
        JMenuBar menuBar = new JMenuBar();
        JMenu mnMenu = new JMenu("Menu");
        JMenuItem mntmVoltar = new JMenuItem("Voltar");
        mntmVoltar.addActionListener(e -> {
            // Assumindo que TelaIdentificacao existe e lida com o controle de fluxo.
            TelaIdentificacao identificacao = new TelaIdentificacao(); 
            identificacao.setVisible(true);
            CadastroProdutos.this.dispose();
        });
        mnMenu.add(mntmVoltar);
        menuBar.add(mnMenu);
        setJMenuBar(menuBar);

        // -------------------- TABELA DE PRODUTOS --------------------
        String[] colunas = { "Produto", "Preço", "Categoria", "ID", "Estoque" };
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaprodutos = new JTable(modeloTabela);
        carregarProdutosNaTabela();

        JScrollPane scrollPane = new JScrollPane(tabelaprodutos);
        // Ocupa a célula 0 1 e tem GROW/PUSH para preencher o espaço vertical
        contentPane.add(scrollPane, "cell 0 1, grow, push");

        // -------------------- PAINEL DE AÇÕES (AJUSTADO PARA RESPONSIVIDADE) --------------------
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout(
                "fill, insets 5, wrap 6", // 6 colunas, quebra de linha (wrap) a cada 6
                // 5 Colunas [grow, fill] para os campos de texto + 1 Coluna [150, fill] para os botões
                "[grow, fill][grow, fill][grow, fill][grow, fill][grow, fill]10[150, fill]",
                "[]5[]5[]5[]"
        ));
        contentPane.add(panel, "cell 0 2, growx");

        // ---- Labels (Linha 0) ----
        String[] labels = { "Nome", "Preço", "Categoria", "ID", "Estoque" };
        for (int i = 0; i < labels.length; i++) {
            JLabel lbl = new JLabel(labels[i]);
            lbl.setFont(new Font("Tahoma", Font.BOLD, 14));
            panel.add(lbl, "cell " + i + " 0, align center");
        }

        // -------------------- CAMPOS ADICIONAR (Linha 1) --------------------
        textNome1 = new JTextField();
        panel.add(textNome1, "cell 0 1, h 30");
        textPreco1 = new JTextField();
        panel.add(textPreco1, "cell 1 1, h 30");
        textCategoria1 = new JTextField();
        panel.add(textCategoria1, "cell 2 1, h 30");
        textID1 = new JTextField();
        panel.add(textID1, "cell 3 1, h 30");
        textEstoque1 = new JTextField();
        panel.add(textEstoque1, "cell 4 1, h 30");

        JButton btnAdicionar = new JButton("Adicionar Produto");
        btnAdicionar.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnAdicionar.addActionListener(e -> adicionarProduto());
        panel.add(btnAdicionar, "cell 5 1, growx, h 30");

        // -------------------- CAMPOS EDITAR (Linha 2) --------------------
        textNome2 = new JTextField();
        panel.add(textNome2, "cell 0 2, h 30");
        textPreco2 = new JTextField();
        panel.add(textPreco2, "cell 1 2, h 30");
        textCategoria2 = new JTextField();
        panel.add(textCategoria2, "cell 2 2, h 30");
        textID2 = new JTextField();
        panel.add(textID2, "cell 3 2, h 30");
        textEstoque2 = new JTextField();
        panel.add(textEstoque2, "cell 4 2, h 30");

        JButton btnEditar = new JButton("Editar Produto");
        btnEditar.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnEditar.addActionListener(e -> editarProduto());
        panel.add(btnEditar, "cell 5 2, growx, h 30");

        // -------------------- BOTÃO REMOVER (Linha 3) --------------------
        JButton btnRemover = new JButton("Remover Produto Selecionado");
        btnRemover.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnRemover.addActionListener(e -> removerProduto());
        // Ocupa 6 colunas (span 6)
        panel.add(btnRemover, "cell 0 3 6 1, growx, align center");

        // -------------------- LISTENER PARA PREENCHER CAMPOS DE EDIÇÃO --------------------
        tabelaprodutos.getSelectionModel().addListSelectionListener(e -> {
            int linha = tabelaprodutos.getSelectedRow();
            if (linha >= 0 && !e.getValueIsAdjusting()) {
                textNome2.setText((String) modeloTabela.getValueAt(linha, 0));
                // O preço e a ID são String, o estoque é int/Integer
                textPreco2.setText((String) modeloTabela.getValueAt(linha, 1)); 
                textCategoria2.setText((String) modeloTabela.getValueAt(linha, 2));
                textID2.setText((String) modeloTabela.getValueAt(linha, 3));
                // Convertendo para String, garantindo que o valor seja exibido.
                textEstoque2.setText(String.valueOf(modeloTabela.getValueAt(linha, 4))); 
            }
        });
    }

    // -------------------- CARREGAR TABELA (Com Tratamento de Exceção) --------------------
    private void carregarProdutosNaTabela() {
        modeloTabela.setRowCount(0);
        try {
            ArrayList<Produtos> lista = ProdutoController.listarProdutos(); // Ponto de I/O, pode falhar!
            if (lista != null) {
                for (Produtos p : lista) {
                    modeloTabela.addRow(new Object[] { p.getNome(), p.getPreco(), p.getCategoria(), p.getId(),
                            p.getEstoque() });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar produtos. Verifique o acesso ao arquivo/banco de dados.",
                    "Erro de Dados", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // -------------------- ADICIONAR (Com Tratamento de Exceção) --------------------
    private void adicionarProduto() {
        String nome = textNome1.getText().trim();
        String preco = textPreco1.getText().trim();
        String categoria = textCategoria1.getText().trim();
        String id = textID1.getText().trim();
        String estoqueStr = textEstoque1.getText().trim();

        if (nome.isEmpty() || preco.isEmpty() || categoria.isEmpty() || id.isEmpty() || estoqueStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos para adicionar!", "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Validação de formato (NumberFormatException)
            int estoque = Integer.parseInt(estoqueStr);
            // Simulação de validação de preço (se for String, o Controller/Model fará a conversão final)
            // Se o preço não for um número válido, o try-catch genérico abaixo deverá capturar.
            // Para maior robustez, você pode adicionar: 
            // Double.parseDouble(preco.replace(",", ".")); 

            if (estoque < 0) {
                JOptionPane.showMessageDialog(this, "O estoque não pode ser negativo!", "Erro de Negócio",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Produtos p = new Produtos(nome, preco, categoria, id, estoque);
            ProdutoController.adicionarProduto(p); // Ponto de exceção potencial (I/O, dados)

            // Atualiza a tabela e limpa campos
            carregarProdutosNaTabela();
            JOptionPane.showMessageDialog(this, "Produto adicionado com sucesso!", "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

            textNome1.setText("");
            textPreco1.setText("");
            textCategoria1.setText("");
            textID1.setText("");
            textEstoque1.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erro de formato! Verifique se o ESTOQUE é inteiro e o PREÇO é numérico.", "Erro de Formato",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar produto: " + ex.getMessage(), "Erro Geral",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    // -------------------- EDITAR (Com Tratamento de Exceção) --------------------
    private void editarProduto() {
        int linha = tabelaprodutos.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para editar!", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String novoNome = textNome2.getText().trim();
        String novoPreco = textPreco2.getText().trim();
        String novaCategoria = textCategoria2.getText().trim();
        String novoID = textID2.getText().trim();
        String novoEstoqueStr = textEstoque2.getText().trim();

        if (novoNome.isEmpty() || novoPreco.isEmpty() || novaCategoria.isEmpty() || novoID.isEmpty()
                || novoEstoqueStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos de edição!", "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Validação de formato (NumberFormatException)
            int novoEstoque = Integer.parseInt(novoEstoqueStr);
            // Simulação de validação de preço
            // Double.parseDouble(novoPreco.replace(",", ".")); 
            
            if (novoEstoque < 0) {
                JOptionPane.showMessageDialog(this, "O estoque não pode ser negativo!", "Erro de Negócio",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String idAntigo = (String) modeloTabela.getValueAt(linha, 3);
            Produtos novoProduto = new Produtos(novoNome, novoPreco, novaCategoria, novoID, novoEstoque);
            ProdutoController.editarProduto(idAntigo, novoProduto); // Ponto de exceção potencial (I/O, dados)

            // Atualiza a linha na JTable
            modeloTabela.setValueAt(novoNome, linha, 0);
            modeloTabela.setValueAt(novoPreco, linha, 1);
            modeloTabela.setValueAt(novaCategoria, linha, 2);
            modeloTabela.setValueAt(novoID, linha, 3);
            modeloTabela.setValueAt(novoEstoque, linha, 4);

            JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!", "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erro de formato! Verifique se o ESTOQUE é inteiro e o PREÇO é numérico.", "Erro de Formato",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao editar produto: " + ex.getMessage(), "Erro Geral",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    // -------------------- REMOVER (Com Tratamento de Exceção) --------------------
    private void removerProduto() {
        int linha = tabelaprodutos.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para remover!", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String id = (String) modeloTabela.getValueAt(linha, 3);
            int confirm = JOptionPane.showConfirmDialog(this, "Remover o produto ID: " + id + "?", "Confirmação",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                ProdutoController.removerProduto(id); // Ponto de exceção potencial (I/O, dados)
                modeloTabela.removeRow(linha);
                JOptionPane.showMessageDialog(this, "Produto removido com sucesso!", "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao remover produto!", "Erro Geral", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                CadastroProdutos frame = new CadastroProdutos();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}