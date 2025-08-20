package projetoUM;

import java.util.ArrayList;

public class ListaProdutos {
	
private static ArrayList<Produtos> produtos = new ArrayList<>();
	
	public static Produtos adicionarConsulta(Produtos a) {
		produtos.add(a);
		return a;
		
	}
	public static Produtos BuscarProduto(String , String , String ) {
		for (Produtos a : produtos) {
			if()) {
			return a;
		}
			}
		return null;
		}

}
