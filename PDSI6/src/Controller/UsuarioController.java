package Controller;

import Model.Usuario;
import Model.UsuarioDAO;

import javax.swing.*;

public class UsuarioController {

    private UsuarioDAO usuarioDAO;

    public UsuarioController(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

  //tratamento de exceção
    public void adicionarUsuario(String cpf, String nome, String email, String funcao) {
        try {
            if (usuarioDAO.buscarPorCpf(cpf) != null) {
                JOptionPane.showMessageDialog(null, "CPF já cadastrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Usuario u = new Usuario(nome, cpf, email, funcao);
            usuarioDAO.adicionarUsuario(u);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar usuário: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    //login //tratamento de exceção
    public boolean login(String cpf, String nome) {
        try {
            Usuario usuario = usuarioDAO.buscarPorCpf(cpf);

            if (usuario != null && usuario.getNome().equalsIgnoreCase(nome)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao realizar login: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

  
    public Usuario buscarUsuario(String cpf) {
        try {
            return usuarioDAO.buscarPorCpf(cpf);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar usuário: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }
    }
}
