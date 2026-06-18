package br.juliana.model;

public class Veiculo {
    private Long id;
    private String placa;
    private String modelo;
    private int ano;
    private String cor;
    private int quilometragem;
    private Long clienteId; // Chave estrangeira que liga o veículo ao dono (cliente_id)

    // Construtor Vazio
    public Veiculo() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

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
}
