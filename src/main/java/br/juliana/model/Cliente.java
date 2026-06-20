package br.juliana.model;

public class Cliente {
    private Long id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;

    public Cliente() {
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) {
        if (cpf != null) {
            this.cpf = cpf.replaceAll("[^0-9]", "");
        } else {
            this.cpf = null;
        }
    }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) {
        if (telefone != null) {
            this.telefone = telefone.replaceAll("[^0-9]", "");
        } else {
            this.telefone = null;
        }
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    // Métodos Utilitários para exibição formatada na View
    public String getCpfFormatado() {
        if (this.cpf != null && this.cpf.length() == 11) {
            return this.cpf.substring(0, 3) + "." +
                    this.cpf.substring(3, 6) + "." +
                    this.cpf.substring(6, 9) + "-" +
                    this.cpf.substring(9, 11);
        }
        return this.cpf;
    }

    public String getTelefoneFormatado() {
        if (this.telefone != null && this.telefone.length() == 11) {
            return "(" + this.telefone.substring(0, 2) + ") " +
                    this.telefone.substring(2, 7) + "-" +
                    this.telefone.substring(7, 11);
        } else if (this.telefone != null && this.telefone.length() == 10) {
            return "(" + this.telefone.substring(0, 2) + ") " +
                    this.telefone.substring(2, 6) + "-" +
                    this.telefone.substring(6, 10);
        }
        return this.telefone;
    }
}