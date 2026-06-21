package br.juliana.model;

public class Peca {
    private Long id;
    private String nomeComponente;
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

    public String getNomeComponente() {
        return nomeComponente;
    }

    public void setNomeComponente(String nomeComponente) {
        this.nomeComponente = nomeComponente;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
}