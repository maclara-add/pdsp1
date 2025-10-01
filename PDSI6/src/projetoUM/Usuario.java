package projetoUM;

public class Usuario {
    private String nome;
    private String cpf;
    private String email;
    private String funcao;

    public Usuario(String nome, String cpf, String email, String funcao) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.funcao = funcao;
    }

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFuncao() {
		return funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}
    
    
}