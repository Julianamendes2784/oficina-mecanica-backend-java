package br.juliana.dao;

import br.juliana.config.FabricaConexao;
import br.juliana.model.LogAuditoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LogAuditoriaDAO {

    public void inserir(LogAuditoria log) throws SQLException {
        String sql = "INSERT INTO logs_auditoria (data_alteracao, usuario_id, entidade_afetada, " +
                "registro_id, tipo_operacao, descricao_mudanca) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(log.getDataAlteracao()));
            stmt.setInt(2, log.getUsuarioId());
            stmt.setString(3, log.getEntidadeAfetada());
            stmt.setInt(4, log.getRegistroId());
            stmt.setString(5, log.getTipoOperacao());
            stmt.setString(6, log.getDescricaoMudanca());
            stmt.executeUpdate();
        }
    }

    public List<LogAuditoria> listarTodos() throws SQLException {
        List<LogAuditoria> logs = new ArrayList<>();
        String sql = "SELECT * FROM logs_auditoria ORDER BY data_alteracao DESC";

        try (Connection conn = FabricaConexao.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                logs.add(mapearLog(rs));
            }
        }
        return logs;
    }

    public List<LogAuditoria> listarPorEntidade(String entidade) throws SQLException {
        List<LogAuditoria> logs = new ArrayList<>();
        String sql = "SELECT * FROM logs_auditoria WHERE entidade_afetada = ? ORDER BY data_alteracao DESC";

        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, entidade);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    logs.add(mapearLog(rs));
                }
            }
        }
        return logs;
    }

    private LogAuditoria mapearLog(ResultSet rs) throws SQLException {
        LogAuditoria log = new LogAuditoria();
        log.setId(rs.getInt("id"));
        log.setDataAlteracao(rs.getTimestamp("data_alteracao").toLocalDateTime());
        log.setUsuarioId(rs.getInt("usuario_id"));
        log.setEntidadeAfetada(rs.getString("entidade_afetada"));
        log.setRegistroId(rs.getInt("registro_id"));
        log.setTipoOperacao(rs.getString("tipo_operacao"));
        log.setDescricaoMudanca(rs.getString("descricao_mudanca"));
        return log;
    }
}