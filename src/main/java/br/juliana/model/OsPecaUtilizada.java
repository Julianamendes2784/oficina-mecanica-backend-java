
package br.juliana.model;

public class OsPecaUtilizada {

    private int ordemServicoId;
    private int pecaId;
    private int quantidade;
    private double precoAplicado;

    public OsPecaUtilizada() {
    }

    public OsPecaUtilizada(int ordemServicoId, int pecaId, int quantidade, double precoAplicado) {
        this.ordemServicoId = ordemServicoId;
        this.pecaId = pecaId;
        this.quantidade = quantidade;
        this.precoAplicado = precoAplicado;
    }

    public int getOrdemServicoId() {
        return ordemServicoId;
    }

    public void setOrdemServicoId(int ordemServicoId) {
        this.ordemServicoId = ordemServicoId;
    }

    public int getPecaId() {
        return pecaId;
    }

    public void setPecaId(int pecaId) {
        this.pecaId = pecaId;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoAplicado() {
        return precoAplicado;
    }

    public void setPrecoAplicado(double precoAplicado) {
        this.precoAplicado = precoAplicado;
    }
}
