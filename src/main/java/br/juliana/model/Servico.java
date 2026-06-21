package br.juliana.model;

public class Servico {
    private Long id;
    private String nome;
    private double valor;

    // Construtor Vazio
    public Servico() {}

    // Construtor Completo
    public Servico(Long id, String nome, double valor) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
    }

    // Getters e Setters Originais
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    // ⭐ MÉTODOS PONTE: Criados especificamente para consertar os erros do ServicoDAO!
    public String getDescricao() { return this.nome; }
    public void setDescricao(String descricao) { this.nome = descricao; }

    public double getPrecoTabela() { return this.valor; }
    public void setPrecoTabela(double precoTabela) { this.valor = precoTabela; }

    // Ponto de partida do Builder Manual
    public static ServicoBuilder builder() {
        return new ServicoBuilder();
    }

    // Classe interna do Builder (Adicionado suporte a descricao e precoTabela se o teste usar)
    public static class ServicoBuilder {
        private Long id;
        private String nome;
        private double valor;

        public ServicoBuilder id(Long id) { this.id = id; return this; }
        public ServicoBuilder nome(String nome) { this.nome = nome; return this; }
        public ServicoBuilder descricao(String descricao) { this.nome = descricao; return this; }
        public ServicoBuilder valor(double valor) { this.valor = valor; return this; }
        public ServicoBuilder precoTabela(double precoTabela) { this.valor = precoTabela; return this; }

        public Servico build() {
            return new Servico(id, nome, valor);
        }
    }
}