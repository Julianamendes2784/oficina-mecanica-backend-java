package br.juliana.model;

public class Peca {

    private int id;
    private String nome;
    private double precoVenda;
    private int estoque;

    public Peca(String nome, double precoVenda, int estoque) {
        this.nome = nome;
        this.precoVenda = precoVenda;
        this.estoque = estoque;
    }

    public Peca(int id, String nome, double precoVenda, int estoque) {
        this.id = id;
        this.nome = nome;
        this.precoVenda = precoVenda;
        this.estoque = estoque;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public double getPrecoVenda() { return precoVenda; }
    public void setPrecoVenda(double precoVenda) { this.precoVenda = precoVenda; }

    public int getEstoque() { return estoque; }
    public void setEstoque(int estoque) { this.estoque = estoque; }

    @Override
    public String toString() {
        return "Peça [id=" + id + ", nome=" + nome +
                ", preço=R$" + precoVenda + ", estoque=" + estoque + "]";
    }
}