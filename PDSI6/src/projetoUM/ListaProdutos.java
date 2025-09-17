package projetoUM;

import java.util.ArrayList;

public class ListaProdutos {
	
private static ArrayList<Produtos> produtos = new ArrayList<>();
//	
	public static Produtos adicionarProduto(Produtos produto) {
		produtos.add(produto);
		return produto;	
	}
	
	public static Produtos consultarProduto(Produtos produto) {
		return produto;
	
	}
	public static void removerProduto(int linhaSelecionada) {
		produtos.remove(linhaSelecionada);
	}

	public static ArrayList<Produtos> getLista() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getSelectedRow() {
		// TODO Auto-generated method stub
		return 0;
	}
	
//	public static Produtos 

}
