package br.juliana.dao;

import br.juliana.config.FabricaConexao;
import br.juliana.model.Peca;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PecaDAO {

    // 1. CADASTRAR (Create)
    public void cadastrar(Peca peca) {
        String sql = "INSERT INTO pecas (nome, preco_venda, estoque) VALUES (?, ?, ?)";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, peca.getNome());
            stmt.setDouble(2, peca.getPrecoVenda());
            stmt.setInt(3, peca.getEstoque());
            stmt.executeUpdate();
            System.out.println("Peça cadastrada com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar peça: " + e.getMessage());
        }
    }

    // 2. CONSULTAR TODOS (Read)
    public List<Peca> listarTodos() {
        List<Peca> pecas = new ArrayList<>();
        String sql = "SELECT * FROM pecas";

        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Peca p = new Peca(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("preco_venda"),
                        rs.getInt("estoque")
                );
                pecas.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar peças: " + e.getMessage());
        }
        return pecas;
    }

    // 3. BUSCAR POR ID
    public Peca buscarPorId(int id) {
        String sql = "SELECT * FROM pecas WHERE id = ?";

        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Peca(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getDouble("preco_venda"),
                            rs.getInt("estoque")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar peça: " + e.getMessage());
        }
        return null;
    }

    // 3.1 BUSCAR POR NOME, PARTE DO NOME OU ID
    public List<Peca> buscarPorNomeOuId(String termo) {
        List<Peca> resultados = new ArrayList<>();

        // Detecta se o termo digitado é um número (ID) ou texto (nome)
        boolean ehNumero = termo.matches("\\d+");

        String sql = ehNumero
                ? "SELECT * FROM pecas WHERE id = ?"
                : "SELECT * FROM pecas WHERE nome LIKE ?";

        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (ehNumero) {
                stmt.setInt(1, Integer.parseInt(termo));
            } else {
                stmt.setString(1, "%" + termo + "%");
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    resultados.add(new Peca(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getDouble("preco_venda"),
                            rs.getInt("estoque")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar peça: " + e.getMessage());
        }
        return resultados;
    }

    // 4. ATUALIZAR (Update)
    public void atualizar(Peca peca) {
        String sql = "UPDATE pecas SET nome=?, preco_venda=?, estoque=? WHERE id=?";

        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, peca.getNome());
            stmt.setDouble(2, peca.getPrecoVenda());
            stmt.setInt(3, peca.getEstoque());
            stmt.setInt(4, peca.getId());
            stmt.executeUpdate();
            System.out.println("Peça atualizada com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar peça: " + e.getMessage());
        }
    }

    // 5. EXCLUIR (Delete) — com tratamento de vínculo (FK)
    public void excluir(int id) {
        String sql = "DELETE FROM pecas WHERE id = ?";

        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Peça excluída com sucesso!");

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("❌ Não foi possível excluir: esta peça já está vinculada a uma Ordem de Serviço e não pode ser removida.");

        } catch (SQLException e) {
            System.err.println("Erro ao excluir peça: " + e.getMessage());
        }
    }
}