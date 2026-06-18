package br.juliana.dao;

import br.juliana.config.FabricaConexao;
import br.juliana.model.Servico;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicoDAO {

    // Método auxiliar para verificar se a descrição já existe
    public boolean existeDescricao(String descricao) {
        String sql = "SELECT COUNT(*) FROM servicos WHERE LOWER(descricao) = LOWER(?)";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, descricao.trim());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao validar duplicidade de descrição: " + e.getMessage());
        }
        return false;
    }

    // 1. CADASTRAR
    public void cadastrar(Servico servico) {
        if (existeDescricao(servico.getDescricao())) {
            System.out.println("❌ Erro: Já existe um serviço cadastrado com esta descrição!");
            return;
        }

        String sql = "INSERT INTO servicos (descricao, preco_tabela) VALUES (?, ?)";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, servico.getDescricao());
            stmt.setDouble(2, servico.getPrecoTabela());
            stmt.executeUpdate();
            System.out.println("Serviço cadastrado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar serviço: " + e.getMessage());
        }
    }

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
    public void atualizar(Servico servico) {
        String sql = "UPDATE servicos SET descricao = ?, preco_tabela = ? WHERE id = ?";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, servico.getDescricao());
            stmt.setDouble(2, servico.getPrecoTabela());
            stmt.setLong(3, servico.getId());

            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println("Serviço atualizado com sucesso!");
            } else {
                System.out.println("Nenhum serviço encontrado com o ID informado.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar serviço: " + e.getMessage());
        }
    }

    // 4. EXCLUIR (Tratando o vínculo com chave estrangeira)
    public void excluir(Long id) {
        String sql = "DELETE FROM servicos WHERE id = ?";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println("Serviço excluído com sucesso!");
            } else {
                System.out.println("Nenhum serviço encontrado com o ID informado.");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            // Captura específica para quando há vínculo na tabela filha (ex: os_servicos_prestados)
            System.out.println("\n❌ Erro de Segurança: Não é possível excluir este serviço!");
            System.out.println("💡 Motivo: Este serviço está atrelado a uma Ordem de Serviço ativa.");
            System.out.println("Para prosseguir, desfaça a operação removendo este serviço da respectiva tabela Pai.");
        } catch (SQLException e) {
            System.err.println("Erro ao excluir serviço: " + e.getMessage());
        }
    }
    // 5. BUSCAR SERVIÇO POR DESCRIÇÃO (OU PARTE DELA)
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
