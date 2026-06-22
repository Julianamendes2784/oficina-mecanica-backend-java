


package br.juliana.dao;

import br.juliana.config.FabricaConexao;
import br.juliana.model.OsServicoPrestado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OsServicoPrestadoDAO {

    private Connection conn;

    // ✅ CORRIGIDO AQUI
    public OsServicoPrestadoDAO() {
        try {
            this.conn = FabricaConexao.getConnection();
        } catch (Exception e) {
            System.out.println("❌ Erro ao conectar com o banco");
            e.printStackTrace();
        }
    }

    // ✅ INSERT
    public void inserir(OsServicoPrestado os) {

        String sql = "INSERT INTO os_servicos_prestados " +
                "(ordem_servico_id, servico_id, preco_aplicado) " +
                "VALUES (?, ?, ?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, os.getOrdemServicoId());
            stmt.setInt(2, os.getServicoId());
            stmt.setDouble(3, os.getPrecoAplicado());

            stmt.execute();
            stmt.close();

        } catch (Exception e) {
            System.out.println("❌ Erro ao inserir serviço prestado");
            e.printStackTrace();
        }
    }

    // ✅ LISTAR SERVIÇOS DA O.S.
    public List<OsServicoPrestado> listarPorOrdem(int osId) {

        List<OsServicoPrestado> lista = new ArrayList<>();

        String sql = "SELECT * FROM os_servicos_prestados WHERE ordem_servico_id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, osId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OsServicoPrestado os = new OsServicoPrestado();
                os.setOrdemServicoId(rs.getInt("ordem_servico_id"));
                os.setServicoId(rs.getInt("servico_id"));
                os.setPrecoAplicado(rs.getDouble("preco_aplicado"));

                lista.add(os);
            }

            rs.close();
            stmt.close();

        } catch (Exception e) {
            System.out.println("❌ Erro ao listar serviços");
            e.printStackTrace();
        }

        return lista;
    }

    // ✅ REMOVER SERVIÇO
    public void remover(int osId, int servicoId) {

        String sql = "DELETE FROM os_servicos_prestados " +
                "WHERE ordem_servico_id = ? AND servico_id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, osId);
            stmt.setInt(2, servicoId);

            stmt.execute();
            stmt.close();

        } catch (Exception e) {
            System.out.println("❌ Erro ao remover serviço");
            e.printStackTrace();
        }
    }

    // ✅ VERIFICAR EXISTÊNCIA
    public boolean existe(int osId, int servicoId) {

        String sql = "SELECT 1 FROM os_servicos_prestados " +
                "WHERE ordem_servico_id = ? AND servico_id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, osId);
            stmt.setInt(2, servicoId);

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

