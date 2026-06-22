
package br.juliana.model;

public class OsServicoPrestado {

    private int ordemServicoId;
    private int servicoId;
    private double precoAplicado;

    public OsServicoPrestado() {
    }

    public OsServicoPrestado(int ordemServicoId, int servicoId, double precoAplicado) {
        this.ordemServicoId = ordemServicoId;
        this.servicoId = servicoId;
        this.precoAplicado = precoAplicado;
    }

    public int getOrdemServicoId() {
        return ordemServicoId;
    }

    public void setOrdemServicoId(int ordemServicoId) {
        this.ordemServicoId = ordemServicoId;
    }

    public int getServicoId() {
        return servicoId;
    }

    public void setServicoId(int servicoId) {
        this.servicoId = servicoId;
    }

    public double getPrecoAplicado() {
        return precoAplicado;
    }

    public void setPrecoAplicado(double precoAplicado) {
        this.precoAplicado = precoAplicado;
    }
}

