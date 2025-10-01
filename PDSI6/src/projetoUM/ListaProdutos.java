package projetoUM;

import java.util.ArrayList;

public class ListaProdutos {
	
	private static ArrayList<Produtos> produtos = new ArrayList<>();
	
	public static Produtos adicionarProduto(Produtos produto) {
		produtos.add(produto);
		return produto;	
	}
	
	// Método de remoção (mantido como estava)
	public static void removerProdutoPorId(String id) {
		for(int i = 0; i<produtos.size(); i++) {
			if(produtos.get(i).getId().equals(id)) {
				produtos.remove(i);
				break;
			}
		}
	}
	
	// **MÉTODO EDITAR CORRIGIDO**
	public static void editarProdutoPorId(String idAntigo, String novoNome, String novoPreco, String novaCategoria, String novoId) {
		for(Produtos p : produtos) {
			// Acha o produto usando o ID ANTIGO/ATUAL
			if(p.getId().equals(idAntigo)) { 
				p.setNome(novoNome);
				p.setPreco(novoPreco);
				p.setCategoria(novaCategoria);
				p.setId(novoId); // Atualiza o ID, se alterado
				break;
			}
		}
	}

	// **MÉTODO getLista CORRIGIDO**
	public static ArrayList<Produtos> getLista() {
		return produtos;
	}
	
	// Métodos não utilizados no contexto da classe, removidos para limpar o código:
	// public static Produtos consultarProduto(Produtos produto) { return produto; }
	// public static void listarProdutos(Produtos produto) { }
}