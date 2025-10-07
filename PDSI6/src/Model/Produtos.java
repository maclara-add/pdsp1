package Model;

public class Produtos {
	
	private String id;
	private String nome;
	private String categoria;
	private String preco;
	private int estoque;
	
	
	
	public Produtos(String nome, String preco, String categoria, String id, int estoque) {
		super();
		this.nome = nome;         
		this.preco = preco;       
		this.categoria = categoria;
		this.id = id;
		this.estoque = estoque;	
	}
	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getPreco() {
		return preco;
	}
	public void setPreco(String preco) {
		this.preco = preco;
	}


	public int getEstoque() {
		return estoque;
	}

	public void setEstoque(int estoque) {
		this.estoque = estoque;
	}
	
}