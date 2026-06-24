package br.juliana.dao;

import br.juliana.config.FabricaConexao;
import br.juliana.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private Usuario mapear(ResultSet rs) throws SQLException {
        return Usuario.builder()
                .id(rs.getInt("id"))
                .login(rs.getString("login"))
                .senha(rs.getString("senha"))
                .colaboradorId(rs.getInt("colaborador_id"))
                .perfil(rs.getString("perfil"))
                .ativo(rs.getInt("ativo") == 1)
                .build();
    }

    public Usuario autenticar(String login, String senha) {
        String sql = "SELECT * FROM usuarios WHERE login = ? AND senha = ? AND ativo = 1";

        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);
            stmt.setString(2, senha);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao autenticar: " + e.getMessage());
        }
        return null;
    }

    public void cadastrar(Usuario u) {
        String sql = "INSERT INTO usuarios (login, senha, colaborador_id, perfil, ativo) VALUES (?, ?, ?, ?, 1)";

        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, u.getLogin());
            stmt.setString(2, u.getSenha());
            if (u.getColaboradorId() == 0) {
                stmt.setNull(3, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(3, u.getColaboradorId());
            }
            stmt.setString(4, u.getPerfil());

            stmt.executeUpdate();
            System.out.println("✅ Usuário cadastrado com sucesso!");

        } catch (SQLIntegrityConstraintViolationException e) {
            if (e.getMessage().contains("login")) {
                System.out.println("❌ Erro: Já existe um usuário com esse login!");
            } else {
                System.err.println("❌ Erro de constraint: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios ORDER BY id ASC";

        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar usuários: " + e.getMessage());
        }
        return lista;
    }

    public void desativar(int id) {
        String sql = "UPDATE usuarios SET ativo = 0 WHERE id = ?";

        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("✅ Usuário desativado com sucesso!");
            } else {
                System.out.println("❌ Nenhum usuário encontrado com esse ID.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao desativar usuário: " + e.getMessage());
        }
    }

    public void reativar(int id) {
        String sql = "UPDATE usuarios SET ativo = 1 WHERE id = ?";

        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("✅ Usuário reativado com sucesso!");
            } else {
                System.out.println("❌ Nenhum usuário encontrado com esse ID.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao reativar usuário: " + e.getMessage());
        }
    }

    public boolean alterarPerfil(int id, String novoPerfil) {
        String sql = "UPDATE usuarios SET perfil = ? WHERE id = ?";

        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, novoPerfil);
            stmt.setInt(2, id);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("✅ Perfil atualizado para " + novoPerfil + " com sucesso!");
                return true;
            } else {
                System.out.println("❌ Nenhum usuário encontrado com esse ID.");
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao alterar perfil: " + e.getMessage());
            return false;
        }
    }
}