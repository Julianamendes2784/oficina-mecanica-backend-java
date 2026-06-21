package br.juliana.model;

public class Colaborador {
    private Long id;
    private String nome;
    private String papel;          // Mantido para compatibilidade interna
    private String cargo;          // O que a Main e o DAO usam
    private String especialidade;  // Campo opcional exigido na Main

    // 1. Construtor Vazio Necessário para o DAO
    public Colaborador() {}

    // 2. Construtor Completo exigido pelo Builder
    public Colaborador(Long id, String nome, String cargo, String especialidade) {
        this.id = id;
        this.nome = nome;
        setCargo(cargo);
        this.especialidade = especialidade;
        this.papel = this.cargo; // Sincroniza papel com cargo
    }

    // 3. Getters e Setters Puros e Normalizados
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getPapel() { return papel; }
    public void setPapel(String papel) {
        this.papel = papel != null ? papel.toUpperCase().trim() : null;
        this.cargo = this.papel;
    }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) {
        this.cargo = cargo != null ? cargo.toUpperCase().trim() : null;
        this.papel = this.cargo; // Mantém sincronizado
    }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }

    // 4. Ponto de partida do Builder Manual
    public static ColaboradorBuilder builder() {
        return new ColaboradorBuilder();
    }

    // 5. Classe interna do Design Pattern Builder (Corrigida e única)
    public static class ColaboradorBuilder {
        private Long id;
        private String nome;
        private String cargo;
        private String especialidade;

        public ColaboradorBuilder id(Long id) { this.id = id; return this; }
        public ColaboradorBuilder nome(String nome) { this.nome = nome; return this; }
        public ColaboradorBuilder papel(String papel) { this.cargo = papel; return this; }
        public ColaboradorBuilder cargo(String cargo) { this.cargo = cargo; return this; }
        public ColaboradorBuilder especialidade(String especialidade) { this.especialidade = especialidade; return this; }

        public Colaborador build() {
            return new Colaborador(id, nome, cargo, especialidade);
        }
    }
}