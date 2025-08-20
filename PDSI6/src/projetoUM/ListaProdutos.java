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
	
//	public static Produtos 

}
