package DAO;

import Model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private Connection conn;

    public UsuarioDAO(Connection conn) {
        this.conn = conn;
    }

    public void adicionarUsuario(Usuario u) throws SQLException {
        String sql = "INSERT INTO usuarios (cpf, nome, email, funcao) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, u.getCpf());
        ps.setString(2, u.getNome());
        ps.setString(3, u.getEmail());
        ps.setString(4, u.getFuncao());
        ps.executeUpdate();
        ps.close();
    }

    public List<Usuario> listarUsuarios() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            Usuario u = new Usuario(
                rs.getString("nome"),
                rs.getString("cpf"),
                rs.getString("email"),
                rs.getString("funcao")
            );
            lista.add(u);
        }
        rs.close();
        st.close();
        return lista;
    }

    // Buscar usuário por CPF
    public Usuario buscarPorCpf(String cpf) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE cpf = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, cpf);
        ResultSet rs = ps.executeQuery();
        Usuario u = null;
        if (rs.next()) {
            u = new Usuario(
                rs.getString("nome"),
                rs.getString("cpf"),
                rs.getString("email"),
                rs.getString("funcao")
            );
        }
        rs.close();
        ps.close();
        return u;
    }

    public void removerUsuario(String cpf) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE cpf = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, cpf);
        ps.executeUpdate();
        ps.close();
    }

    // Atualizar usuário
    public void atualizarUsuario(Usuario u) throws SQLException {
        String sql = "UPDATE usuarios SET nome = ?, email = ?, funcao = ? WHERE cpf = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, u.getNome());
        ps.setString(2, u.getEmail());
        ps.setString(3, u.getFuncao());
        ps.setString(4, u.getCpf());
        ps.executeUpdate();
        ps.close();
    }
}
