package Model;

import java.util.ArrayList;

public class CarrinhoDeCompras {
    
    private ArrayList<Produtos> itens;
    private double total;

    public CarrinhoDeCompras() {
        this.itens = new ArrayList<>();
        this.total = 0.0;
    }

    public void adicionarProduto(Produtos produto) {
        this.itens.add(produto);
        try {
            this.total += Double.parseDouble(produto.getPreco());
        } catch (NumberFormatException e) {
            System.err.println("Erro ao somar preço do produto: " + produto.getNome() + ". Preço não é um número válido.");
        }
    }

    public void removerProdutoPorId(String id) {
        for (int i = 0; i < itens.size(); i++) {
            Produtos p = itens.get(i);
            if (p.getId().equals(id)) {
                try {
                    this.total -= Double.parseDouble(p.getPreco());
                } catch (NumberFormatException e) {
                    System.err.println("Erro ao subtrair preço do produto: " + p.getNome());
                }
                
                itens.remove(i);
                break;
            }
        }
    }
    
    public void limparCarrinho() {
        this.itens.clear();
        this.total = 0.0;
    }

    public double getTotal() {
        return total;
    }

    public ArrayList<Produtos> getItens() {
        return itens;
    }
}