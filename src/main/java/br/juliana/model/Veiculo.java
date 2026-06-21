package br.juliana.model;

public class Veiculo {
    private Long id;
    private String placa;
    private String marca;
    private String modelo;
    private int ano;
    private String cor;
    private int quilometragem;
    private Long clienteId; // Chave estrangeira que liga o veículo ao dono

    // 1. Construtor Vazio Necessário para o DAO
    public Veiculo() {}

    // 2. Construtor Completo exigido pelo Builder
    public Veiculo(Long id, String placa, String marca, String modelo, int ano, String cor, int quilometragem, Long clienteId) {
        this.id = id;
        setPlaca(placa); // Garante que a placa use a regra de maiúsculas ao construir
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.cor = cor;
        this.quilometragem = quilometragem;
        this.clienteId = clienteId;
    }

    // 3. Getters e Setters Puros
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) {
        this.placa = placa != null ? placa.toUpperCase().trim() : null;
    }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }

    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }

    public int getQuilometragem() { return quilometragem; }
    public void setQuilometragem(int quilometragem) { this.quilometragem = quilometragem; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    // 4. Ponto de partida do Builder Manual
    public static VeiculoBuilder builder() {
        return new VeiculoBuilder();
    }

    // 5. Classe interna do Design Pattern Builder
    public static class VeiculoBuilder {
        private Long id;
        private String placa;
        private String marca;
        private String modelo;
        private int ano;
        private String cor;
        private int quilometragem;
        private Long clienteId;

        public VeiculoBuilder id(Long id) { this.id = id; return this; }
        public VeiculoBuilder placa(String placa) { this.placa = placa; return this; }
        public VeiculoBuilder marca(String marca) { this.marca = marca; return this; }
        public VeiculoBuilder modelo(String modelo) { this.modelo = modelo; return this; }
        public VeiculoBuilder ano(int ano) { this.ano = ano; return this; }
        public VeiculoBuilder cor(String cor) { this.cor = cor; return this; }
        public VeiculoBuilder quilometragem(int quilometragem) { this.quilometragem = quilometragem; return this; }
        public VeiculoBuilder clienteId(Long clienteId) { this.clienteId = clienteId; return this; }

        public Veiculo build() {
            return new Veiculo(id, placa, marca, modelo, ano, cor, quilometragem, clienteId);
        }
    }
}