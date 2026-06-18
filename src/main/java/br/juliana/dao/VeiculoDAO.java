package br.juliana.dao;

import br.juliana.config.FabricaConexao;
import br.juliana.model.Veiculo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO {

    // Método auxiliar para garantir que não existam duas placas iguais
    public boolean existePlaca(String placa) {
        String sql = "SELECT COUNT(*) FROM veiculos WHERE LOWER(placa) = LOWER(?)";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, placa.trim());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao validar duplicidade de placa: " + e.getMessage());
        }
        return false;
    }

    // 1. CADASTRAR (Garante validações obrigatórias)
    public void cadastrar(Veiculo veiculo) {
        if (veiculo.getPlaca() == null || veiculo.getPlaca().trim().isEmpty() ||
                veiculo.getModelo() == null || veiculo.getModelo().trim().isEmpty()) {
            System.out.println("❌ Erro: Placa e Modelo são de preenchimento obrigatório!");
            return;
        }

        if (existePlaca(veiculo.getPlaca())) {
            System.out.println("❌ Erro: Já existe um veículo cadastrado com esta placa!");
            return;
        }

        String sql = "INSERT INTO veiculos (placa, modelo, ano, cor, quilometragem, cliente_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, veiculo.getPlaca().trim().toUpperCase()); // Placa sempre em maiúsculo
            stmt.setString(2, veiculo.getModelo().trim());
            stmt.setInt(3, veiculo.getAno());
            stmt.setString(4, veiculo.getCor().trim());
            stmt.setInt(5, veiculo.getQuilometragem());
            stmt.setLong(6, veiculo.getClienteId());

            stmt.executeUpdate();
            System.out.println("Veículo cadastrado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar veículo: " + e.getMessage());
        }
    }

    // 2. LISTAR TODOS
    public List<Veiculo> listarTodos() {
        List<Veiculo> lista = new ArrayList<>();
        String sql = "SELECT * FROM veiculos";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Veiculo v = new Veiculo();
                v.setId(rs.getLong("id"));
                v.setPlaca(rs.getString("placa"));
                v.setModelo(rs.getString("modelo"));
                v.setAno(rs.getInt("ano"));
                v.setCor(rs.getString("cor"));
                v.setQuilometragem(rs.getInt("quilometragem"));
                v.setClienteId(rs.getLong("cliente_id"));
                lista.add(v);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar veículos: " + e.getMessage());
        }
        return lista;
    }

    // 3. ATUALIZAR
    public void atualizar(Veiculo veiculo) {
        String sql = "UPDATE veiculos SET modelo = ?, ano = ?, cor = ?, quilometragem = ? WHERE id = ?";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, veiculo.getModelo().trim());
            stmt.setInt(2, veiculo.getAno());
            stmt.setString(3, veiculo.getCor().trim());
            stmt.setInt(4, veiculo.getQuilometragem());
            stmt.setLong(5, veiculo.getId());

            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println("Veículo atualizado com sucesso!");
            } else {
                System.out.println("Nenhum veículo encontrado com o ID informado.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar veículo: " + e.getMessage());
        }
    }

    // 4. EXCLUIR
    public void excluir(Long id) {
        String sql = "DELETE FROM veiculos WHERE id = ?";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println("Veículo excluído com sucesso!");
            } else {
                System.out.println("Nenhum veículo encontrado com o ID informado.");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("\n❌ Erro de Segurança: Não é possível excluir este veículo!");
            System.out.println("💡 Motivo: Este veículo possui histórico de Ordens de Serviço vinculadas.");
        } catch (SQLException e) {
            System.err.println("Erro ao excluir veículo: " + e.getMessage());
        }
    }

    // 5. BUSCA INTELIGENTE (Por Placa ou Modelo)
    public List<Veiculo> buscarPorTermo(String termoBusca) {
        List<Veiculo> lista = new ArrayList<>();
        String sql = "SELECT * FROM veiculos WHERE LOWER(placa) LIKE LOWER(?) OR LOWER(modelo) LIKE LOWER(?)";

        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String curinga = "%" + termoBusca.trim() + "%";
            stmt.setString(1, curinga);
            stmt.setString(2, curinga);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Veiculo v = new Veiculo();
                    v.setId(rs.getLong("id"));
                    v.setPlaca(rs.getString("placa"));
                    v.setModelo(rs.getString("modelo"));
                    v.setAno(rs.getInt("ano"));
                    v.setCor(rs.getString("cor"));
                    v.setQuilometragem(rs.getInt("quilometragem"));
                    v.setClienteId(rs.getLong("cliente_id"));
                    lista.add(v);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar veículos: " + e.getMessage());
        }
        return lista;
    }
}