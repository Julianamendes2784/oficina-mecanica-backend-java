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

    public Cliente(Long id, String nome, String cpf, String telefone, String email) {
        this.id = id;
        this.nome = nome;
        setCpf(cpf);
        setTelefone(telefone);
        this.email = email;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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
        this.cpf = cpf != null ? cpf.replaceAll("[^0-9]", "") : null;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone != null ? telefone.replaceAll("[^0-9]", "") : null;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    // Métodos Utilitários de Máscara Visual para a View
    public String getCpfFormatado() {
        if (this.cpf != null && this.cpf.length() == 11) {
            return this.cpf.substring(0, 3) + "." +
                    this.cpf.substring(3, 6) + "." +
                    this.cpf.substring(6, 9) + "-" +
                    this.cpf.substring(9, 11);
        }
        return this.cpf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static ClienteBuilder builder() {
        return new ClienteBuilder();
    }

    public static class ClienteBuilder {
        private Long id;
        private String nome;
        private String cpf;
        private String telefone;
        private String email;

        public ClienteBuilder id(Long id) { this.id = id; return this; }
        public ClienteBuilder nome(String nome) { this.nome = nome; return this; }
        public ClienteBuilder cpf(String cpf) { this.cpf = cpf; return this; }
        public ClienteBuilder telefone(String telefone) { this.telefone = telefone; return this; }
        public ClienteBuilder email(String email) { this.email = email; return this; }

        public Cliente build() {
            return new Cliente(id, nome, cpf, telefone, email);
        }
    }
}