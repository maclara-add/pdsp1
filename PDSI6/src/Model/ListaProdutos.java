package Model;

import java.util.ArrayList;

public class ListaProdutos {
	
	private static ArrayList<Produtos> produtos = new ArrayList<>();
	
	public static Produtos adicionarProduto(Produtos produto) {
		produtos.add(produto);
		return produto;	
	}
	
	public static void removerProdutoPorId(String id) {
		for(int i = 0; i<produtos.size(); i++) {
			if(produtos.get(i).getId().equals(id)) {
				produtos.remove(i);
				break;
			}
		}
	}
	
	public static void editarProdutoPorId(String idAntigo, String novoNome, String novoPreco, String novaCategoria, String novoId, int novoEstoque) {
		for(Produtos p : produtos) {
			if(p.getId().equals(idAntigo)) { 
				p.setNome(novoNome);
				p.setPreco(novoPreco);
				p.setCategoria(novaCategoria);
				p.setId(novoId); 
				p.setEstoque(novoEstoque);
				break;
			}
		}
	}
	
	public static Produtos buscarPorId(String id) {
	    for (Produtos p : produtos) {
	        if (p.getId().equals(id)) return p;
	    }
	    return null;
	}

	public static Produtos buscarPorNome(String nome) {
	    for (Produtos p : produtos) {
	        if (p.getNome().equalsIgnoreCase(nome)) return p;
	    }
	    return null;
	}

	public static ArrayList<Produtos> getLista() {
		return produtos;
	}
	
}