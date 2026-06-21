package br.juliana.model;

public class Cliente {
    private Long id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;

    // Construtor Vazio
    public Cliente() {
    }

    // Construtor Completo
    public Cliente(Long id, String nome, String cpf, String telefone, String email) {
        this.id = id;
        this.nome = nome;
        setCpf(cpf);
        setTelefone(telefone);
        this.email = email;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        // Remove pontos e traços caso queira padronizar no banco
        if (cpf != null) {
            this.cpf = cpf.replaceAll("[.\\-]", "");
        } else {
            this.cpf = null;
        }
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}