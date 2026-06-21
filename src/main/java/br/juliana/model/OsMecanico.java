
package br.juliana.model;

public class OsMecanico {

    private int ordemServicoId;
    private int colaboradorId;

    public OsMecanico() {
    }

    public OsMecanico(int ordemServicoId, int colaboradorId) {
        this.ordemServicoId = ordemServicoId;
        this.colaboradorId = colaboradorId;
    }

    public int getOrdemServicoId() {
        return ordemServicoId;
    }

    public void setOrdemServicoId(int ordemServicoId) {
        this.ordemServicoId = ordemServicoId;
    }

    public int getColaboradorId() {
        return colaboradorId;
    }

    public void setColaboradorId(int colaboradorId) {
        this.colaboradorId = colaboradorId;
    }
}

