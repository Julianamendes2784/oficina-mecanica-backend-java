package br.juliana.dao;

import br.juliana.config.FabricaConexao;
import br.juliana.model.Colaborador;
import br.juliana.model.LogAuditoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ColaboradorDAO {

    private LogAuditoriaDAO logDAO = new LogAuditoriaDAO();

    public Colaborador buscarPorId(Long id) {
        String sql = "SELECT * FROM colaboradores WHERE id = ?";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Colaborador c = new Colaborador();
                    c.setId(rs.getLong("id"));
                    c.setNome(rs.getString("nome"));
                    c.setCargo(rs.getString("cargo"));
                    c.setEspecialidade(rs.getString("especialidade"));
                    return c;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar colaborador por ID: " + e.getMessage());
        }
        return null;
    }

    private String descrever(Colaborador c) {
        return String.format("Nome: %s | Cargo: %s | Especialidade: %s", c.getNome(), c.getCargo(), c.getEspecialidade());
    }

    public boolean existeNome(String nome) {
        String sql = "SELECT COUNT(*) FROM colaboradores WHERE LOWER(nome) = LOWER(?)";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome.trim());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao validar duplicidade de nome: " + e.getMessage());
        }
        return false;
    }

    // 1. CADASTRAR
    public void cadastrar(Colaborador colaborador, int usuarioId) {
        if (existeNome(colaborador.getNome())) {
            System.out.println("❌ Erro: Já existe um colaborador cadastrado com este nome!");
            return;
        }
        String sql = "INSERT INTO colaboradores (nome, cargo, especialidade) VALUES (?, ?, ?)";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, colaborador.getNome().trim());
            stmt.setString(2, colaborador.getCargo().trim());
            stmt.setString(3, colaborador.getEspecialidade() != null ? colaborador.getEspecialidade().trim() : "");
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    long novoId = rs.getLong(1);
                    colaborador.setId(novoId);
                    logDAO.registrar(new LogAuditoria(usuarioId, "colaboradores", (int) novoId, "INSERT",
                            "Colaborador cadastrado. " + descrever(colaborador)));
                }
            }
            System.out.println("Colaborador cadastrado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar colaborador: " + e.getMessage());
        }
    }

    public void cadastrar(Colaborador colaborador) { cadastrar(colaborador, 0); }

    // 2. LISTAR TODOS
    public List<Colaborador> listarTodos() {
        List<Colaborador> lista = new ArrayList<>();
        String sql = "SELECT * FROM colaboradores";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Colaborador c = new Colaborador();
                c.setId(rs.getLong("id"));
                c.setNome(rs.getString("nome"));
                c.setCargo(rs.getString("cargo"));
                c.setEspecialidade(rs.getString("especialidade"));
                lista.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar colaboradores: " + e.getMessage());
        }
        return lista;
    }

    // 3. ATUALIZAR
    public void atualizar(Colaborador colaborador, int usuarioId) {
        Colaborador antes = buscarPorId(colaborador.getId());
        String sql = "UPDATE colaboradores SET nome = ?, cargo = ?, especialidade = ? WHERE id = ?";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, colaborador.getNome().trim());
            stmt.setString(2, colaborador.getCargo().trim());
            stmt.setString(3, colaborador.getEspecialidade().trim());
            stmt.setLong(4, colaborador.getId());
            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                String descricao = "ANTES: " + (antes != null ? descrever(antes) : "N/A")
                        + " || DEPOIS: " + descrever(colaborador);
                logDAO.registrar(new LogAuditoria(usuarioId, "colaboradores", (int) (long) colaborador.getId(), "UPDATE", descricao));
                System.out.println("Colaborador atualizado com sucesso!");
            } else {
                System.out.println("Nenhum colaborador encontrado com o ID informado.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar colaborador: " + e.getMessage());
        }
    }

    public void atualizar(Colaborador colaborador) { atualizar(colaborador, 0); }

    // 4. EXCLUIR
    public void excluir(Long id, int usuarioId) {
        Colaborador antes = buscarPorId(id);
        String sql = "DELETE FROM colaboradores WHERE id = ?";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                logDAO.registrar(new LogAuditoria(usuarioId, "colaboradores", (int) (long) id, "DELETE",
                        "Colaborador excluído. " + (antes != null ? descrever(antes) : "ID: " + id)));
                System.out.println("Colaborador excluído com sucesso!");
            } else {
                System.out.println("Nenhum colaborador encontrado com o ID informado.");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("\n❌ Erro: Não é possível excluir este colaborador pois ele possui registros vinculados em Ordens de Serviço!");
        } catch (SQLException e) {
            System.err.println("Erro ao excluir colaborador: " + e.getMessage());
        }
    }

    public void excluir(Long id) { excluir(id, 0); }

    // 5. BUSCAR POR NOME
    public List<Colaborador> buscarPorNome(String nomeBusca) {
        List<Colaborador> lista = new ArrayList<>();
        String sql = "SELECT * FROM colaboradores WHERE LOWER(nome) LIKE LOWER(?)";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nomeBusca.trim() + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Colaborador c = new Colaborador();
                    c.setId(rs.getLong("id"));
                    c.setNome(rs.getString("nome"));
                    c.setCargo(rs.getString("cargo"));
                    c.setEspecialidade(rs.getString("especialidade"));
                    lista.add(c);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar colaboradores: " + e.getMessage());
        }
        return lista;
    }
}