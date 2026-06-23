package br.juliana.dao;

import br.juliana.config.FabricaConexao;
import br.juliana.model.LogAuditoria;
import br.juliana.model.Peca;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PecaDAO {

    private LogAuditoriaDAO logDAO = new LogAuditoriaDAO();

    public Peca buscarPorId(Long id) {
        String sql = "SELECT id, nome, preco_venda FROM pecas WHERE id = ?";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Peca p = new Peca();
                    p.setId(rs.getLong("id"));
                    p.setNome(rs.getString("nome"));
                    p.setPrecoUnitario(rs.getDouble("preco_venda"));
                    return p;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar peça por ID: " + e.getMessage());
        }
        return null;
    }

    private String descrever(Peca p) {
        return String.format("Nome: %s | Preço: R$ %.2f", p.getNome(), p.getPrecoUnitario());
    }

    // 1. CADASTRAR
    public void cadastrar(Peca peca, int usuarioId) {
        String sql = "INSERT INTO pecas (nome, preco_venda, estoque) VALUES (?, ?, ?)";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, peca.getNome());
            stmt.setDouble(2, peca.getPrecoUnitario());
            stmt.setInt(3, 0);
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    long novoId = rs.getLong(1);
                    peca.setId(novoId);
                    logDAO.registrar(new LogAuditoria(usuarioId, "pecas", (int) novoId, "INSERT",
                            "Peça cadastrada. " + descrever(peca)));
                }
            }
            System.out.println("✅ Peça cadastrada com sucesso!");
        } catch (SQLException e) {
            System.err.println("❌ Erro ao cadastrar peça: " + e.getMessage());
        }
    }

    public void cadastrar(Peca peca) { cadastrar(peca, 0); }

    // 2. LISTAR TODAS
    public List<Peca> listarTodas() {
        List<Peca> lista = new ArrayList<>();
        String sql = "SELECT id, nome, preco_venda FROM pecas";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Peca p = new Peca();
                p.setId(rs.getLong("id"));
                p.setNome(rs.getString("nome"));
                p.setPrecoUnitario(rs.getDouble("preco_venda"));
                lista.add(p);
            }
        } catch (SQLException e) {
            System.err.println("❌ Erro ao listar peças: " + e.getMessage());
        }
        return lista;
    }

    // 3. BUSCAR POR TERMO
    public List<Peca> buscarPorTermo(String termo) {
        List<Peca> resultados = new ArrayList<>();
        String sql = "SELECT id, nome, preco_venda FROM pecas WHERE nome LIKE ? OR CAST(id AS CHAR) = ?";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + termo + "%");
            stmt.setString(2, termo);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Peca p = new Peca();
                p.setId(rs.getLong("id"));
                p.setNome(rs.getString("nome"));
                p.setPrecoUnitario(rs.getDouble("preco_venda"));
                resultados.add(p);
            }
        } catch (SQLException e) {
            System.err.println("❌ Erro ao buscar peça: " + e.getMessage());
        }
        return resultados;
    }

    // 4. EXCLUIR
    public void excluir(Long id, int usuarioId) {
        Peca antes = buscarPorId(id);
        String sqlVerifica = "SELECT COUNT(*) FROM os_pecas_utilizadas WHERE peca_id = ?";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmtVerifica = conn.prepareStatement(sqlVerifica)) {
            stmtVerifica.setLong(1, id);
            ResultSet rs = stmtVerifica.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("⚠️ Não é possível excluir esta peça pois ela está vinculada a uma ou mais Ordens de Serviço.");
                return;
            }
        } catch (SQLException e) {
            System.err.println("❌ Erro ao verificar vínculo da peça: " + e.getMessage());
            return;
        }
        String sqlExclui = "DELETE FROM pecas WHERE id = ?";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmtExclui = conn.prepareStatement(sqlExclui)) {
            stmtExclui.setLong(1, id);
            int linhasAfetadas = stmtExclui.executeUpdate();
            if (linhasAfetadas > 0) {
                logDAO.registrar(new LogAuditoria(usuarioId, "pecas", (int) (long) id, "DELETE",
                        "Peça excluída. " + (antes != null ? descrever(antes) : "ID: " + id)));
                System.out.println("✅ Peça excluída com sucesso!");
            } else {
                System.out.println("❌ Nenhuma peça encontrada com o ID informado.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Erro ao excluir peça: " + e.getMessage());
        }
    }

    public void excluir(Long id) { excluir(id, 0); }
}