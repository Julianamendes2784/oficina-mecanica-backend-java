package br.juliana.model;

public class Peca {
    private Long id;
    private String Nome;
    private double precoUnitario;

    // Construtor Vazio (Resolve o erro do construtor 'Peca()')
    public Peca() {
    }

    // Getters e Setters obrigatórios para a Main
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
}