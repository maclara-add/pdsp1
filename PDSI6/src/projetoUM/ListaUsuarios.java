package projetoUM;

import java.util.ArrayList;

public class ListaUsuarios {

    private static ArrayList<Usuario> usuarios = new ArrayList<>();

    public static void adicionarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }
    
    public static Usuario buscarUsuarioParaLogin(String nomeDigitado, String cpfLimpoDigitado) { 
        for (Usuario u : usuarios) {
            // p ir inedepente de ser maiusculo ou minusculo
            if (u.getNome().trim().equalsIgnoreCase(nomeDigitado.trim()) && 
                u.getCpf().equals(cpfLimpoDigitado)) {
                
                return u; 
            }
        }
        return null; 
    }
    
    public static ArrayList<Usuario> getLista() {
        return usuarios;
    }
}