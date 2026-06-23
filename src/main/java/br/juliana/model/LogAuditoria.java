package br.juliana.model;

import java.sql.Timestamp;

public class LogAuditoria {
    private int id;
    private Timestamp dataAlteracao;
    private int usuarioId;
    private String entidadeAfetada;
    private int registroId;
    private String tipoOperacao;
    private String descricaoMudanca;

    public LogAuditoria() {}

    public LogAuditoria(int usuarioId, String entidadeAfetada, int registroId,
                        String tipoOperacao, String descricaoMudanca) {
        this.usuarioId = usuarioId;
        this.entidadeAfetada = entidadeAfetada;
        this.registroId = registroId;
        this.tipoOperacao = tipoOperacao;
        this.descricaoMudanca = descricaoMudanca;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Timestamp getDataAlteracao() { return dataAlteracao; }
    public void setDataAlteracao(Timestamp dataAlteracao) { this.dataAlteracao = dataAlteracao; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public String getEntidadeAfetada() { return entidadeAfetada; }
    public void setEntidadeAfetada(String entidadeAfetada) { this.entidadeAfetada = entidadeAfetada; }

    public int getRegistroId() { return registroId; }
    public void setRegistroId(int registroId) { this.registroId = registroId; }

    public String getTipoOperacao() { return tipoOperacao; }
    public void setTipoOperacao(String tipoOperacao) { this.tipoOperacao = tipoOperacao; }

    public String getDescricaoMudanca() { return descricaoMudanca; }
    public void setDescricaoMudanca(String descricaoMudanca) { this.descricaoMudanca = descricaoMudanca; }
}
