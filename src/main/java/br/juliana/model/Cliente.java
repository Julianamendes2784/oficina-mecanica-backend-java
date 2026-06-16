package br.juliana.model;

public class Cliente {
    private Long id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;

    // 1. ADICIONE ESTE CONSTRUTOR VAZIO:
    public Cliente() {
    }

    // Se tiver outros construtores com parâmetros abaixo, pode mantê-los!

    // Getters e Setters...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}