package Controller;

import DAO.Conexao;
import DAO.ProdutoDAO;
import Model.Produtos;
import java.sql.*;
import java.util.ArrayList;

public class ProdutoController {

    public static boolean adicionarProduto(Produtos p) {
        try (Connection conn = Conexao.getConnection()) {
            ProdutoDAO dao = new ProdutoDAO(conn);
            dao.adicionarProduto(p);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Produtos buscarProduto(String id) {
        try (Connection conn = Conexao.getConnection()) {
            ProdutoDAO dao = new ProdutoDAO(conn);
            return dao.buscarPorId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Produtos> listarProdutos() {
        ArrayList<Produtos> lista = new ArrayList<>();
        try (Connection conn = Conexao.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM produtos");

            while (rs.next()) {
                String nome = rs.getString("nome");
                String preco = rs.getString("preco"); 
                String categoria = rs.getString("categoria");
                String id = rs.getString("id");
                int estoque = rs.getInt("estoque");

                Produtos p = new Produtos(nome, preco, categoria, id, estoque);
                lista.add(p);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static boolean editarProduto(String idAntigo, Produtos novoProduto) {
        try (Connection conn = Conexao.getConnection()) {
            String sql = "UPDATE produtos SET nome = ?, preco = ?, categoria = ?, id = ?, estoque = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, novoProduto.getNome());
            ps.setString(2, novoProduto.getPreco());
            ps.setString(3, novoProduto.getCategoria());
            ps.setString(4, novoProduto.getId());
            ps.setInt(5, novoProduto.getEstoque());
            ps.setString(6, idAntigo);

            int atualizado = ps.executeUpdate();
            ps.close();
            return atualizado > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean removerProduto(String id) {
        try (Connection conn = Conexao.getConnection()) {
            String sql = "DELETE FROM produtos WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            int excluido = ps.executeUpdate();
            ps.close();
            return excluido > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
