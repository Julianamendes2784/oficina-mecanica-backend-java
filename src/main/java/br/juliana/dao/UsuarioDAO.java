package br.juliana.dao;

import br.juliana.config.FabricaConexao;
import br.juliana.model.Usuario;
import java.sql.*;

public class UsuarioDAO {

    public Usuario autenticar(String login, String senha) {
        String sql = "SELECT * FROM usuarios WHERE login = ? AND senha = ?";

        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);
            stmt.setString(2, senha);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Usuario.builder()
                            .id(rs.getInt("id"))
                            .login(rs.getString("login"))
                            .senha(rs.getString("senha"))
                            .colaboradorId(rs.getInt("colaborador_id"))
                            .build();
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao tentar autenticar no banco: " + e.getMessage());
        }
        return null;
    }
}