package br.juliana.model;

import java.sql.Timestamp;

public class OrdemServico {
    private Long id;
    private int numeroOs;
    private String situacao;
    private Timestamp dataAbertura;
    private Timestamp dataEncerramento;
    private Timestamp dataAguardandoPecasDesde;

    // Checklist
    private boolean chkEstepe;
    private boolean chkMacacoChaveRoda;
    private boolean chkTriangulo;
    private boolean chkRadio;
    private String chkNivelCombustivel;
    private String chkObservacoesAvarias; // Usaremos também para o motivo de cancelamento

    // Chaves Estrangeiras e Totais
    private Long clienteId;
    private Long veiculoId;
    private Long abertoPorColaboradorId;
    private double valorTotalServicos;
    private double valorTotalPecas;
    private double valorTotalGeral;

    // Construtor Vazio
    public OrdemServico() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getNumeroOs() { return numeroOs; }
    public void setNumeroOs(int numeroOs) { this.numeroOs = numeroOs; }

    public String getSituacao() { return situacao; }
    public void setSituacao(String situacao) { this.situacao = situacao; }

    public Timestamp getDataAbertura() { return dataAbertura; }
    public void setDataAbertura(Timestamp dataAbertura) { this.dataAbertura = dataAbertura; }

    public Timestamp getDataEncerramento() { return dataEncerramento; }
    public void setDataEncerramento(Timestamp dataEncerramento) { this.dataEncerramento = dataEncerramento; }

    public Timestamp getDataAguardandoPecasDesde() { return dataAguardandoPecasDesde; }
    public void setDataAguardandoPecasDesde(Timestamp dataAguardandoPecasDesde) { this.dataAguardandoPecasDesde = dataAguardandoPecasDesde; }

    public boolean isChkEstepe() { return chkEstepe; }
    public void setChkEstepe(boolean chkEstepe) { this.chkEstepe = chkEstepe; }

    public boolean isChkMacacoChaveRoda() { return chkMacacoChaveRoda; }
    public void setChkMacacoChaveRoda(boolean chkMacacoChaveRoda) { this.chkMacacoChaveRoda = chkMacacoChaveRoda; }

    public boolean isChkTriangulo() { return chkTriangulo; }
    public void setChkTriangulo(boolean chkTriangulo) { this.chkTriangulo = chkTriangulo; }

    public boolean isChkRadio() { return chkRadio; }
    public void setChkRadio(boolean chkRadio) { this.chkRadio = chkRadio; }

    public String getChkNivelCombustivel() { return chkNivelCombustivel; }
    public void setChkNivelCombustivel(String chkNivelCombustivel) { this.chkNivelCombustivel = chkNivelCombustivel; }

    public String getChkObservacoesAvarias() { return chkObservacoesAvarias; }
    public void setChkObservacoesAvarias(String chkObservacoesAvarias) { this.chkObservacoesAvarias = chkObservacoesAvarias; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public Long getVeiculoId() { return veiculoId; }
    public void setVeiculoId(Long veiculoId) { this.veiculoId = veiculoId; }

    public Long getAbertoPorColaboradorId() { return abertoPorColaboradorId; }
    public void setAbertoPorColaboradorId(Long abertoPorColaboradorId) { this.abertoPorColaboradorId = abertoPorColaboradorId; }

    public double getValorTotalServicos() { return valorTotalServicos; }
    public void setValorTotalServicos(double valorTotalServicos) { this.valorTotalServicos = valorTotalServicos; }

    public double getValorTotalPecas() { return valorTotalPecas; }
    public void setValorTotalPecas(double valorTotalPecas) { this.valorTotalPecas = valorTotalPecas; }

    public double getValorTotalGeral() { return valorTotalGeral; }
    public void setValorTotalGeral(double valorTotalGeral) { this.valorTotalGeral = valorTotalGeral; }
}
