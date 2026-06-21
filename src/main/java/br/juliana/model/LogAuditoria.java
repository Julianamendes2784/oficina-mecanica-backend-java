package br.juliana.model;

import java.time.LocalDateTime;

public class LogAuditoria {

    private int id;
    private LocalDateTime dataAlteracao;
    private int usuarioId;
    private String entidadeAfetada;
    private int registroId;
    private String tipoOperacao; // INSERT, UPDATE ou DELETE
    private String descricaoMudanca;

    public LogAuditoria() {
    }

    public LogAuditoria(LocalDateTime dataAlteracao, int usuarioId, String entidadeAfetada,
                        int registroId, String tipoOperacao, String descricaoMudanca) {
        this.dataAlteracao = dataAlteracao;
        this.usuarioId = usuarioId;
        this.entidadeAfetada = entidadeAfetada;
        this.registroId = registroId;
        this.tipoOperacao = tipoOperacao;
        this.descricaoMudanca = descricaoMudanca;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(LocalDateTime dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEntidadeAfetada() {
        return entidadeAfetada;
    }

    public void setEntidadeAfetada(String entidadeAfetada) {
        this.entidadeAfetada = entidadeAfetada;
    }

    public int getRegistroId() {
        return registroId;
    }

    public void setRegistroId(int registroId) {
        this.registroId = registroId;
    }

    public String getTipoOperacao() {
        return tipoOperacao;
    }

    public void setTipoOperacao(String tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
    }

    public String getDescricaoMudanca() {
        return descricaoMudanca;
    }

    public void setDescricaoMudanca(String descricaoMudanca) {
        this.descricaoMudanca = descricaoMudanca;
    }
}