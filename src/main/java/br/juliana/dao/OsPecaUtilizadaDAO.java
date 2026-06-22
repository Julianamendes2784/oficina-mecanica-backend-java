


package br.juliana.dao;

import br.juliana.config.FabricaConexao;
import br.juliana.model.OsPecaUtilizada;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OsPecaUtilizadaDAO {

    private Connection conn;

    // ✅ CONSTRUTOR CORRIGIDO
    public OsPecaUtilizadaDAO() {
        try {
            this.conn = FabricaConexao.getConnection();
        } catch (Exception e) {
            System.out.println("❌ Erro ao conectar com o banco");
            e.printStackTrace();
        }
    }

    // ✅ INSERT
    public void inserir(OsPecaUtilizada os) {

        String sql = "INSERT INTO os_pecas_utilizadas " +
                "(ordem_servico_id, peca_id, quantidade, preco_aplicado) " +
                "VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, os.getOrdemServicoId());
            stmt.setInt(2, os.getPecaId());
            stmt.setInt(3, os.getQuantidade());
            stmt.setDouble(4, os.getPrecoAplicado());

            stmt.execute();
            stmt.close();

        } catch (Exception e) {
            System.out.println("❌ Erro ao inserir peça");
            e.printStackTrace();
        }
    }

    // ✅ LISTAR PEÇAS POR O.S.
    public List<OsPecaUtilizada> listarPorOrdem(int osId) {

        List<OsPecaUtilizada> lista = new ArrayList<>();

        String sql = "SELECT * FROM os_pecas_utilizadas WHERE ordem_servico_id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, osId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OsPecaUtilizada os = new OsPecaUtilizada();
                os.setOrdemServicoId(rs.getInt("ordem_servico_id"));
                os.setPecaId(rs.getInt("peca_id"));
                os.setQuantidade(rs.getInt("quantidade"));
                os.setPrecoAplicado(rs.getDouble("preco_aplicado"));

                lista.add(os);
            }

            rs.close();
            stmt.close();

        } catch (Exception e) {
            System.out.println("❌ Erro ao listar peças");
            e.printStackTrace();
        }

        return lista;
    }

    // ✅ REMOVER PEÇA
    public void remover(int osId, int pecaId) {

        String sql = "DELETE FROM os_pecas_utilizadas " +
                "WHERE ordem_servico_id = ? AND peca_id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, osId);
            stmt.setInt(2, pecaId);

            stmt.execute();
            stmt.close();

        } catch (Exception e) {
            System.out.println("❌ Erro ao remover peça");
            e.printStackTrace();
        }
    }

    // ✅ VERIFICAR SE JÁ EXISTE
    public boolean existe(int osId, int pecaId) {

        String sql = "SELECT 1 FROM os_pecas_utilizadas " +
                "WHERE ordem_servico_id = ? AND peca_id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, osId);
            stmt.setInt(2, pecaId);

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


