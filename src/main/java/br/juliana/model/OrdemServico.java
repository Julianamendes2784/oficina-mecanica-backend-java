package br.juliana.model;

import java.sql.Timestamp;

public class OrdemServico {
    private Long id;
    private int numeroOs;
    private String situacao;
    private Timestamp dataAbertura;
    private Timestamp dataEncerramento;
    private Timestamp dataAguardandoPecasDesde;


    private boolean chkEstepe;
    private boolean chkMacacoChaveRoda;
    private boolean chkTriangulo;
    private boolean chkRadio;
    private String chkNivelCombustivel;
    private String chkObservacoesAvarias;


    private Long clienteId;
    private Long veiculoId;
    private Long abertoPorColaboradorId;
    private double valorTotalServicos;
    private double valorTotalPecas;
    private double valorTotalGeral;


    public OrdemServico() {}


    public OrdemServico(Long id, int numeroOs, String situacao, Timestamp dataAbertura, Timestamp dataEncerramento,
                        Timestamp dataAguardandoPecasDesde, boolean chkEstepe, boolean chkMacacoChaveRoda,
                        boolean chkTriangulo, boolean chkRadio, String chkNivelCombustivel, String chkObservacoesAvarias,
                        Long clienteId, Long veiculoId, Long abertoPorColaboradorId, double valorTotalServicos,
                        double valorTotalPecas) {
        this.id = id;
        this.numeroOs = numeroOs;
        this.situacao = situacao != null ? situacao.toUpperCase().trim() : "ABERTA"; // Padrão "ABERTA" se nulo
        this.dataAbertura = dataAbertura;
        this.dataEncerramento = dataEncerramento;
        this.dataAguardandoPecasDesde = dataAguardandoPecasDesde;
        this.chkEstepe = chkEstepe;
        this.chkMacacoChaveRoda = chkMacacoChaveRoda;
        this.chkTriangulo = chkTriangulo;
        this.chkRadio = chkRadio;
        this.chkNivelCombustivel = chkNivelCombustivel;
        this.chkObservacoesAvarias = chkObservacoesAvarias;
        this.clienteId = clienteId;
        this.veiculoId = veiculoId;
        this.abertoPorColaboradorId = abertoPorColaboradorId;
        this.valorTotalServicos = valorTotalServicos;
        this.valorTotalPecas = valorTotalPecas;
        recalcularTotalGeral();
    }


    private void recalcularTotalGeral() {
        this.valorTotalGeral = this.valorTotalServicos + this.valorTotalPecas;
    }


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
    public void setValorTotalServicos(double valorTotalServicos) {
        this.valorTotalServicos = valorTotalServicos;
        recalcularTotalGeral(); // Dispara o recálculo
    }

    public double getValorTotalPecas() { return valorTotalPecas; }
    public void setValorTotalPecas(double valorTotalPecas) {
        this.valorTotalPecas = valorTotalPecas;
        recalcularTotalGeral(); // Dispara o recálculo
    }

    public double getValorTotalGeral() { return valorTotalGeral; }

    public void setValorTotalGeral(double valorTotalGeral) {
        this.valorTotalGeral = valorTotalGeral;
    }


    public static OrdemServicoBuilder builder() {
        return new OrdemServicoBuilder();
    }


    public static class OrdemServicoBuilder {
        private Long id;
        private int numeroOs;
        private String situacao;
        private Timestamp dataAbertura;
        private Timestamp dataEncerramento;
        private Timestamp dataAguardandoPecasDesde;
        private boolean chkEstepe;
        private boolean chkMacacoChaveRoda;
        private boolean chkTriangulo;
        private boolean chkRadio;
        private String chkNivelCombustivel;
        private String chkObservacoesAvarias;
        private Long clienteId;
        private Long veiculoId;
        private Long abertoPorColaboradorId;
        private double valorTotalServicos;
        private double valorTotalPecas;

        public OrdemServicoBuilder id(Long id) { this.id = id; return this; }
        public OrdemServicoBuilder numeroOs(int numeroOs) { this.numeroOs = numeroOs; return this; }
        public OrdemServicoBuilder situacao(String situacao) { this.situacao = situacao; return this; }
        public OrdemServicoBuilder dataAbertura(Timestamp dataAbertura) { this.dataAbertura = dataAbertura; return this; }
        public OrdemServicoBuilder dataEncerramento(Timestamp dataEncerramento) { this.dataEncerramento = dataEncerramento; return this; }
        public OrdemServicoBuilder dataAguardandoPecasDesde(Timestamp dataAguardandoPecasDesde) { this.dataAguardandoPecasDesde = dataAguardandoPecasDesde; return this; }
        public OrdemServicoBuilder chkEstepe(boolean chkEstepe) { this.chkEstepe = chkEstepe; return this; }
        public OrdemServicoBuilder chkMacacoChaveRoda(boolean chkMacacoChaveRoda) { this.chkMacacoChaveRoda = chkMacacoChaveRoda; return this; }
        public OrdemServicoBuilder chkTriangulo(boolean chkTriangulo) { this.chkTriangulo = chkTriangulo; return this; }
        public OrdemServicoBuilder chkRadio(boolean chkRadio) { this.chkRadio = chkRadio; return this; }
        public OrdemServicoBuilder chkNivelCombustivel(String chkNivelCombustivel) { this.chkNivelCombustivel = chkNivelCombustivel; return this; }
        public OrdemServicoBuilder chkObservacoesAvarias(String chkObservacoesAvarias) { this.chkObservacoesAvarias = chkObservacoesAvarias; return this; }
        public OrdemServicoBuilder clienteId(Long clienteId) { this.clienteId = clienteId; return this; }
        public OrdemServicoBuilder veiculoId(Long veiculoId) { this.veiculoId = veiculoId; return this; }
        public OrdemServicoBuilder abertoPorColaboradorId(Long abertoPorColaboradorId) { this.abertoPorColaboradorId = abertoPorColaboradorId; return this; }
        public OrdemServicoBuilder valorTotalServicos(double valorTotalServicos) { this.valorTotalServicos = valorTotalServicos; return this; }
        public OrdemServicoBuilder valorTotalPecas(double valorTotalPecas) { this.valorTotalPecas = valorTotalPecas; return this; }

        public OrdemServico build() {
            return new OrdemServico(id, numeroOs, situacao, dataAbertura, dataEncerramento, dataAguardandoPecasDesde,
                    chkEstepe, chkMacacoChaveRoda, chkTriangulo, chkRadio, chkNivelCombustivel, chkObservacoesAvarias,
                    clienteId, veiculoId, abertoPorColaboradorId, valorTotalServicos, valorTotalPecas);
        }
    }
}