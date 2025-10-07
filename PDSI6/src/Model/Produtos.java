package Model;

public class Produtos {
	
	String id;
	String nome;
	String categoria;
	String preco;
	
	
	public Produtos(String nome, String preco, String categoria, String id) {
		super();
		this.nome = nome;         
		this.preco = preco;       
		this.categoria = categoria;
		this.id = id;
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
}