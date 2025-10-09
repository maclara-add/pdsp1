package DAO;

import Model.Produtos;
import java.sql.*;

public class ProdutoDAO {

    private Connection conn;

    public ProdutoDAO(Connection conn) {
        this.conn = conn;
    }

    public void adicionarProduto(Produtos p) throws SQLException {
        String sql = "INSERT INTO produtos (nome, preco, categoria, id, estoque) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        
        ps.setString(1, p.getNome());
        ps.setString(2, p.getPreco()); 
        ps.setString(3, p.getCategoria());
        ps.setString(4, p.getId());
        ps.setInt(5, p.getEstoque());
        
        ps.executeUpdate();
        ps.close();
    }

    public Produtos buscarPorId(String id) throws SQLException {
        String sql = "SELECT * FROM produtos WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        
        ps.setString(1, id); 
        
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            String nome = rs.getString("nome");
            String precoStr = rs.getString("preco"); 
            String categoria = rs.getString("categoria");
            int estoque = rs.getInt("estoque");
            String produtoId = rs.getString("id"); 
            
            Produtos produtoEncontrado = new Produtos(nome, precoStr, categoria, produtoId, estoque);
            
            rs.close();
            ps.close();
            return produtoEncontrado;
        }
        
        rs.close();
        ps.close();
        return null; 
    }
}