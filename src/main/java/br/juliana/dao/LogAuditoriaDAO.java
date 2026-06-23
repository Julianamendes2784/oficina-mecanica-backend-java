package br.juliana.dao;

import br.juliana.config.FabricaConexao;
import br.juliana.model.LogAuditoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LogAuditoriaDAO {

    // ── Registra um log de auditoria ─────────────────────────────────────────
    public void registrar(LogAuditoria log) {
        String sql = "INSERT INTO logs_auditoria (usuario_id, entidade_afetada, registro_id, tipo_operacao, descricao_mudanca) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, log.getUsuarioId());
            stmt.setString(2, log.getEntidadeAfetada());
            stmt.setInt(3, log.getRegistroId());
            stmt.setString(4, log.getTipoOperacao());
            stmt.setString(5, log.getDescricaoMudanca());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("⚠️ Erro ao registrar log de auditoria: " + e.getMessage());
        }
    }

    // ── Lista todos os logs (somente ADMIN) ───────────────────────────────────
    public List<LogAuditoria> listarTodos() {
        List<LogAuditoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM logs_auditoria ORDER BY data_alteracao DESC";

        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                LogAuditoria log = new LogAuditoria();
                log.setId(rs.getInt("id"));
                log.setDataAlteracao(rs.getTimestamp("data_alteracao"));
                log.setUsuarioId(rs.getInt("usuario_id"));
                log.setEntidadeAfetada(rs.getString("entidade_afetada"));
                log.setRegistroId(rs.getInt("registro_id"));
                log.setTipoOperacao(rs.getString("tipo_operacao"));
                log.setDescricaoMudanca(rs.getString("descricao_mudanca"));
                lista.add(log);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar logs de auditoria: " + e.getMessage());
        }
        return lista;
    }

    // ── Filtra logs por entidade (ex: "clientes", "veiculos") ────────────────
    public List<LogAuditoria> listarPorEntidade(String entidade) {
        List<LogAuditoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM logs_auditoria WHERE LOWER(entidade_afetada) = LOWER(?) ORDER BY data_alteracao DESC";

        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, entidade.trim());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    LogAuditoria log = new LogAuditoria();
                    log.setId(rs.getInt("id"));
                    log.setDataAlteracao(rs.getTimestamp("data_alteracao"));
                    log.setUsuarioId(rs.getInt("usuario_id"));
                    log.setEntidadeAfetada(rs.getString("entidade_afetada"));
                    log.setRegistroId(rs.getInt("registro_id"));
                    log.setTipoOperacao(rs.getString("tipo_operacao"));
                    log.setDescricaoMudanca(rs.getString("descricao_mudanca"));
                    lista.add(log);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao filtrar logs de auditoria: " + e.getMessage());
        }
        return lista;
    }

    // ── Filtra logs por tipo de operação (INSERT, UPDATE, DELETE) ────────────
    public List<LogAuditoria> listarPorTipoOperacao(String tipoOperacao) {
        List<LogAuditoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM logs_auditoria WHERE LOWER(tipo_operacao) = LOWER(?) ORDER BY data_alteracao DESC";

        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tipoOperacao.trim());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    LogAuditoria log = new LogAuditoria();
                    log.setId(rs.getInt("id"));
                    log.setDataAlteracao(rs.getTimestamp("data_alteracao"));
                    log.setUsuarioId(rs.getInt("usuario_id"));
                    log.setEntidadeAfetada(rs.getString("entidade_afetada"));
                    log.setRegistroId(rs.getInt("registro_id"));
                    log.setTipoOperacao(rs.getString("tipo_operacao"));
                    log.setDescricaoMudanca(rs.getString("descricao_mudanca"));
                    lista.add(log);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao filtrar logs por operação: " + e.getMessage());
        }
        return lista;
    }
}