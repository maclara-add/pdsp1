package projetoUM;

import java.util.ArrayList;

public class ListaUsuarios {
    
    private static ArrayList<Usuario> usuarios = new ArrayList<>();

    public static void adicionarUsuario(Usuario usuario) {
        // futuro INSERT SQL
        usuarios.add(usuario);
        System.out.println("LOG: Usuário cadastrado: " + usuario.getNome() + " (" + usuario.getFuncao() + ")");
    }

    // metodo login
    //  query SELECT SQL
    public static Usuario buscarUsuarioParaLogin(String cpf, String email) {
        for (Usuario u : usuarios) {
  
            if (u.getCpf().equals(cpf) && u.getEmail().equals(email)) {
                return u; 
            }
        }
        return null;
    }

    public static ArrayList<Usuario> getLista() {
        return usuarios;
    }
}