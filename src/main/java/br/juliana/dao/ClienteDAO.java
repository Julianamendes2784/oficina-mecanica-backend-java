package br.juliana.dao;

import br.juliana.config.FabricaConexao;
import br.juliana.model.Cliente;
import br.juliana.model.LogAuditoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private LogAuditoriaDAO logDAO = new LogAuditoriaDAO();

    // ── Busca cliente por ID (usado internamente para log antes/depois) ───────
    public Cliente buscarPorId(Long id) {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cliente c = new Cliente();
                    c.setId(rs.getLong("id"));
                    c.setNome(rs.getString("nome"));
                    c.setCpf(rs.getString("cpf"));
                    c.setTelefone(rs.getString("telefone"));
                    c.setEmail(rs.getString("email"));
                    return c;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente por ID: " + e.getMessage());
        }
        return null;
    }

    // ── Formata cliente para descrição de log ────────────────────────────────
    private String descrever(Cliente c) {
        return String.format("Nome: %s | CPF: %s | Tel: %s | Email: %s",
                c.getNome(), c.getCpf(), c.getTelefone(), c.getEmail());
    }

    // 1. CADASTRAR
    public void cadastrar(Cliente cliente, int usuarioId) {
        String sql = "INSERT INTO clientes (nome, cpf, telefone, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEmail());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    long novoId = rs.getLong(1);
                    cliente.setId(novoId);
                    logDAO.registrar(new LogAuditoria(usuarioId, "clientes", (int) novoId, "INSERT",
                            "Cliente cadastrado. " + descrever(cliente)));
                }
            }
            System.out.println("Cliente cadastrado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    // Sobrecarga sem usuarioId para compatibilidade com fluxos internos (ex: abertura de OS)
    public void cadastrar(Cliente cliente) {
        cadastrar(cliente, 0);
    }

    // 2. LISTAR TODOS
    public List<Cliente> listarTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getLong("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEmail(rs.getString("email"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar clientes: " + e.getMessage());
        }
        return clientes;
    }

    // 3. ATUALIZAR
    public void atualizar(Cliente cliente, int usuarioId) {
        Cliente antes = buscarPorId(cliente.getId());

        String sql = "UPDATE clientes SET nome = ?, cpf = ?, telefone = ?, email = ? WHERE id = ?";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEmail());
            stmt.setLong(5, cliente.getId());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                String descricao = "ANTES: " + (antes != null ? descrever(antes) : "N/A")
                        + " || DEPOIS: " + descrever(cliente);
                logDAO.registrar(new LogAuditoria(usuarioId, "clientes", (int) (long) cliente.getId(), "UPDATE", descricao));
                System.out.println("Cliente atualizado com sucesso!");
            } else {
                System.out.println("Nenhum cliente encontrado com o ID informado.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    public void atualizar(Cliente cliente) { atualizar(cliente, 0); }

    // 4. EXCLUIR
    public void excluir(Long id, int usuarioId) {
        Cliente antes = buscarPorId(id);

        String sql = "DELETE FROM clientes WHERE id = ?";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                logDAO.registrar(new LogAuditoria(usuarioId, "clientes", (int) (long) id, "DELETE",
                        "Cliente excluído. " + (antes != null ? descrever(antes) : "ID: " + id)));
                System.out.println("Cliente excluído com sucesso!");
            } else {
                System.out.println("Nenhum cliente encontrado com o ID informado.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao excluir cliente: " + e.getMessage());
        }
    }

    public void excluir(Long id) { excluir(id, 0); }

    // 5. BUSCAR POR NOME
    public List<Cliente> buscarPorNome(String nomeBusca) {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes WHERE LOWER(nome) LIKE LOWER(?)";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nomeBusca.trim() + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Cliente c = new Cliente();
                    c.setId(rs.getLong("id"));
                    c.setNome(rs.getString("nome"));
                    c.setCpf(rs.getString("cpf"));
                    c.setTelefone(rs.getString("telefone"));
                    c.setEmail(rs.getString("email"));
                    lista.add(c);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar clientes: " + e.getMessage());
        }
        return lista;
    }
}