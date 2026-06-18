package br.juliana.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import br.juliana.config.FabricaConexao;
import br.juliana.model.Colaborador;
import java.util.ArrayList;
import java.util.List;

public class ColaboradorDAO {

    // Método para impedir dois colaboradores com o nome idêntico
    public boolean existeNome(String nome) {
        String sql = "SELECT COUNT(*) FROM colaboradores WHERE LOWER(nome) = LOWER(?)";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome.trim());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao validar duplicidade de nome: " + e.getMessage());
        }
        return false;
    }

    // 1. CADASTRAR
    public void cadastrar(Colaborador colaborador) {
        if (existeNome(colaborador.getNome())) {
            System.out.println("❌ Erro: Já existe um colaborador cadastrado com este nome!");
            return;
        }

        String sql = "INSERT INTO colaboradores (nome, cargo, especialidade) VALUES (?, ?, ?)";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, colaborador.getNome().trim());
            stmt.setString(2, colaborador.getCargo().trim());
            stmt.setString(3, colaborador.getEspecialidade() != null ? colaborador.getEspecialidade().trim() : "");

            stmt.executeUpdate();
            System.out.println("Colaborador cadastrado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar colaborador: " + e.getMessage());
        }
    }

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
    public void atualizar(Colaborador colaborador) {
        String sql = "UPDATE colaboradores SET nome = ?, cargo = ?, especialidade = ? WHERE id = ?";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, colaborador.getNome().trim());
            stmt.setString(2, colaborador.getCargo().trim());
            stmt.setString(3, colaborador.getEspecialidade().trim());
            stmt.setLong(4, colaborador.getId());

            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println("Colaborador updated successfully!");
            } else {
                System.out.println("Nenhum colaborador encontrado com o ID informado.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar colaborador: " + e.getMessage());
        }
    }

    // 4. EXCLUIR
    public void excluir(Long id) {
        String sql = "DELETE FROM colaboradores WHERE id = ?";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println("Colaborador excluído com sucesso!");
            } else {
                System.out.println("Nenhum colaborador encontrado com o ID informado.");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("\n❌ Erro: Não é possível excluir este colaborador pois ele possui registros vinculados em Ordens de Serviço!");
        } catch (SQLException e) {
            System.err.println("Erro ao excluir colaborador: " + e.getMessage());
        }
    } // <-- ESSA CHAVE ESTAVA FALTANDO AQUI PAI!

    // 5. BUSCAR POR NOME (OU PARTE DO NOME)
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