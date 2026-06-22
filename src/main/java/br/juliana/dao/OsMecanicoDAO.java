



package br.juliana.dao;

import br.juliana.config.FabricaConexao;
import br.juliana.model.OsMecanico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OsMecanicoDAO {

    private Connection conn;

    // ✅ CONSTRUTOR CORRIGIDO
    public OsMecanicoDAO() {
        try {
            this.conn = FabricaConexao.getConnection();
        } catch (Exception e) {
            System.out.println("❌ Erro ao conectar com o banco");
            e.printStackTrace();
        }
    }

    // ✅ INSERT
    public void inserir(OsMecanico os) {

        String sql = "INSERT INTO os_mecanicos (ordem_servico_id, colaborador_id) VALUES (?, ?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, os.getOrdemServicoId());
            stmt.setInt(2, os.getColaboradorId());

            stmt.execute();
            stmt.close();

        } catch (Exception e) {
            System.out.println("❌ Erro ao inserir mecânico");
            e.printStackTrace();
        }
    }

    // ✅ LISTAR MECÂNICOS POR O.S.
    public List<OsMecanico> listarPorOrdem(int osId) {

        List<OsMecanico> lista = new ArrayList<>();

        String sql = "SELECT * FROM os_mecanicos WHERE ordem_servico_id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, osId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OsMecanico os = new OsMecanico();
                os.setOrdemServicoId(rs.getInt("ordem_servico_id"));
                os.setColaboradorId(rs.getInt("colaborador_id"));

                lista.add(os);
            }

            rs.close();
            stmt.close();

        } catch (Exception e) {
            System.out.println("❌ Erro ao listar mecânicos");
            e.printStackTrace();
        }

        return lista;
    }

    // ✅ REMOVER MECÂNICO DA O.S.
    public void remover(int osId, int colaboradorId) {

        String sql = "DELETE FROM os_mecanicos WHERE ordem_servico_id = ? AND colaborador_id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, osId);
            stmt.setInt(2, colaboradorId);

            stmt.execute();
            stmt.close();

        } catch (Exception e) {
            System.out.println("❌ Erro ao remover mecânico");
            e.printStackTrace();
        }
    }

    // ✅ VERIFICAR SE JÁ EXISTE
    public boolean existe(int osId, int colaboradorId) {

        String sql = "SELECT 1 FROM os_mecanicos WHERE ordem_servico_id = ? AND colaborador_id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, osId);
            stmt.setInt(2, colaboradorId);

            ResultSet rs = stmt.executeQuery();

            boolean existe = rs.next();

            rs.close();
            stmt.close();

            return existe;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

