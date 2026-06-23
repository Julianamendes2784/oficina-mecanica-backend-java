package br.juliana.dao;

import br.juliana.config.FabricaConexao;
import br.juliana.model.LogAuditoria;
import br.juliana.model.Servico;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicoDAO {

    private LogAuditoriaDAO logDAO = new LogAuditoriaDAO();

    public Servico buscarPorId(Long id) {
        String sql = "SELECT * FROM servicos WHERE id = ?";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Servico s = new Servico();
                    s.setId(rs.getLong("id"));
                    s.setDescricao(rs.getString("descricao"));
                    s.setPrecoTabela(rs.getDouble("preco_tabela"));
                    return s;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar serviço por ID: " + e.getMessage());
        }
        return null;
    }

    private String descrever(Servico s) {
        return String.format("Descrição: %s | Preço: R$ %.2f", s.getDescricao(), s.getPrecoTabela());
    }

    public boolean existeDescricao(String descricao) {
        String sql = "SELECT COUNT(*) FROM servicos WHERE LOWER(descricao) = LOWER(?)";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, descricao.trim());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao validar duplicidade de descrição: " + e.getMessage());
        }
        return false;
    }

    // 1. CADASTRAR
    public void cadastrar(Servico servico, int usuarioId) {
        if (existeDescricao(servico.getDescricao())) {
            System.out.println("❌ Erro: Já existe um serviço cadastrado com esta descrição!");
            return;
        }
        String sql = "INSERT INTO servicos (descricao, preco_tabela) VALUES (?, ?)";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, servico.getDescricao());
            stmt.setDouble(2, servico.getPrecoTabela());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    long novoId = rs.getLong(1);
                    servico.setId(novoId);
                    logDAO.registrar(new LogAuditoria(usuarioId, "servicos", (int) novoId, "INSERT",
                            "Serviço cadastrado. " + descrever(servico)));
                }
            }
            System.out.println("Serviço cadastrado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar serviço: " + e.getMessage());
        }
    }

    public void cadastrar(Servico servico) { cadastrar(servico, 0); }

    // 2. LISTAR TODOS
    public List<Servico> listarTodos() {
        List<Servico> lista = new ArrayList<>();
        String sql = "SELECT * FROM servicos";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Servico s = new Servico();
                s.setId(rs.getLong("id"));
                s.setDescricao(rs.getString("descricao"));
                s.setPrecoTabela(rs.getDouble("preco_tabela"));
                lista.add(s);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar serviços: " + e.getMessage());
        }
        return lista;
    }

    // 3. ATUALIZAR
    public void atualizar(Servico servico, int usuarioId) {
        Servico antes = buscarPorId(servico.getId());
        String sql = "UPDATE servicos SET descricao = ?, preco_tabela = ? WHERE id = ?";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, servico.getDescricao());
            stmt.setDouble(2, servico.getPrecoTabela());
            stmt.setLong(3, servico.getId());
            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                String descricao = "ANTES: " + (antes != null ? descrever(antes) : "N/A")
                        + " || DEPOIS: " + descrever(servico);
                logDAO.registrar(new LogAuditoria(usuarioId, "servicos", (int) (long) servico.getId(), "UPDATE", descricao));
                System.out.println("Serviço atualizado com sucesso!");
            } else {
                System.out.println("Nenhum serviço encontrado com o ID informado.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar serviço: " + e.getMessage());
        }
    }

    public void atualizar(Servico servico) { atualizar(servico, 0); }

    // 4. EXCLUIR
    public void excluir(Long id, int usuarioId) {
        Servico antes = buscarPorId(id);
        String sql = "DELETE FROM servicos WHERE id = ?";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                logDAO.registrar(new LogAuditoria(usuarioId, "servicos", (int) (long) id, "DELETE",
                        "Serviço excluído. " + (antes != null ? descrever(antes) : "ID: " + id)));
                System.out.println("Serviço excluído com sucesso!");
            } else {
                System.out.println("Nenhum serviço encontrado com o ID informado.");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("\n❌ Erro de Segurança: Não é possível excluir este serviço!");
            System.out.println("💡 Motivo: Este serviço está atrelado a uma Ordem de Serviço ativa.");
        } catch (SQLException e) {
            System.err.println("Erro ao excluir serviço: " + e.getMessage());
        }
    }

    public void excluir(Long id) { excluir(id, 0); }

    // 5. BUSCAR POR DESCRIÇÃO
    public List<Servico> buscarPorDescricao(String termoBusca) {
        List<Servico> lista = new ArrayList<>();
        String sql = "SELECT * FROM servicos WHERE LOWER(descricao) LIKE LOWER(?)";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + termoBusca.trim() + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Servico s = new Servico();
                    s.setId(rs.getLong("id"));
                    s.setDescricao(rs.getString("descricao"));
                    s.setPrecoTabela(rs.getDouble("preco_tabela"));
                    lista.add(s);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar serviços: " + e.getMessage());
        }
        return lista;
    }
}