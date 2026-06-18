package br.juliana.model;

public class Servico {
    private Long id;
    private String descricao;
    private Double precoTabela;

    // Construtor Vazio
    public Servico() {}

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPrecoTabela() {
        return precoTabela;
    }

    public void setPrecoTabela(Double precoTabela) {
        this.precoTabela = precoTabela;
    }
}