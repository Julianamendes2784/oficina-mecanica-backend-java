package br.juliana.dao;

import br.juliana.config.FabricaConexao;
import br.juliana.model.LogAuditoria;
import br.juliana.model.OrdemServico;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdemServicoDAO {

    private LogAuditoriaDAO logDAO = new LogAuditoriaDAO();

    public OrdemServico buscarPorId(Long id) {
        String sql = "SELECT * FROM ordens_servico WHERE id = ?";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    OrdemServico os = new OrdemServico();
                    os.setId(rs.getLong("id"));
                    os.setSituacao(rs.getString("situacao"));
                    os.setDataAbertura(rs.getTimestamp("data_abertura"));
                    os.setClienteId(rs.getLong("cliente_id"));
                    os.setVeiculoId(rs.getLong("veiculo_id"));
                    os.setValorTotalGeral(rs.getDouble("valor_total_geral"));
                    return os;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar O.S. por ID: " + e.getMessage());
        }
        return null;
    }

    public void processarCancelamento(Long id, String motivoCancelamento) {
        processarCancelamento(id, motivoCancelamento, 0);
    }

    public void processarCancelamento(Long id, String motivoCancelamento, int usuarioId) {
        String sqlBusca = "SELECT situacao, chk_observacoes_avarias FROM ordens_servico WHERE id = ?";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmtBusca = conn.prepareStatement(sqlBusca)) {
            stmtBusca.setLong(1, id);
            try (ResultSet rs = stmtBusca.executeQuery()) {
                if (rs.next()) {
                    String situacao = rs.getString("situacao");
                    String obsAntiga = rs.getString("chk_observacoes_avarias");

                    if ("CONCLUIDA".equalsIgnoreCase(situacao)) {
                        System.out.println("\n❌ Erro de Segurança: Esta Ordem de Serviço já está CONCLUÍDA!");
                        System.out.println("💡 Motivo: Históricos de faturamento concluídos não podem ser removidos ou cancelados.");
                        return;
                    }
                    if ("CANCELADA".equalsIgnoreCase(situacao)) {
                        System.out.println("\n💡 Esta Ordem de Serviço já consta como CANCELADA no sistema.");
                        return;
                    }
                    if (motivoCancelamento == null || motivoCancelamento.trim().isEmpty()) {
                        System.out.println("\n❌ Erro: É obrigatório informar o motivo para cancelar esta O.S.!");
                        return;
                    }

                    String novaObs = (obsAntiga == null || obsAntiga.trim().isEmpty() ? "" : obsAntiga + " | ")
                            + "OS CANCELADA. Motivo: " + motivoCancelamento.trim();

                    String sqlCancelamento = "UPDATE ordens_servico SET situacao = 'CANCELADA', chk_observacoes_avarias = ? WHERE id = ?";
                    try (PreparedStatement stmtCancel = conn.prepareStatement(sqlCancelamento)) {
                        stmtCancel.setString(1, novaObs);
                        stmtCancel.setLong(2, id);
                        stmtCancel.executeUpdate();

                        logDAO.registrar(new LogAuditoria(usuarioId, "ordens_servico", (int) (long) id, "UPDATE",
                                "ANTES: situacao=" + situacao + " || DEPOIS: situacao=CANCELADA | Motivo: " + motivoCancelamento));

                        System.out.println("✅ Ordem de Serviço alterada para CANCELADA com sucesso!");
                    }
                } else {
                    System.out.println("❌ Nenhuma Ordem de Serviço encontrada com o ID informado.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao processar o cancelamento da O.S.: " + e.getMessage());
        }
    }

    public void cadastrar(OrdemServico os) { cadastrar(os, 0); }

    public void cadastrar(OrdemServico os, int usuarioId) {
        String sql = "INSERT INTO ordens_servico (situacao, data_abertura, chk_estepe, " +
                "chk_macaco_chave_roda, chk_triangulo, chk_radio, chk_nivel_combustivel, " +
                "chk_observacoes_avarias, cliente_id, veiculo_id, aberto_por_colaborador_id, " +
                "valor_total_servicos, valor_total_pecas, valor_total_geral) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, os.getSituacao());
            stmt.setTimestamp(2, os.getDataAbertura());
            stmt.setInt(3, os.isChkEstepe() ? 1 : 0);
            stmt.setInt(4, os.isChkMacacoChaveRoda() ? 1 : 0);
            stmt.setInt(5, os.isChkTriangulo() ? 1 : 0);
            stmt.setInt(6, os.isChkRadio() ? 1 : 0);
            stmt.setString(7, os.getChkNivelCombustivel());
            stmt.setString(8, os.getChkObservacoesAvarias());
            stmt.setLong(9, os.getClienteId());
            stmt.setLong(10, os.getVeiculoId());
            stmt.setLong(11, os.getAbertoPorColaboradorId());
            stmt.setDouble(12, os.getValorTotalServicos());
            stmt.setDouble(13, os.getValorTotalPecas());
            stmt.setDouble(14, os.getValorTotalGeral());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    long novoId = rs.getLong(1);
                    os.setId(novoId);
                    logDAO.registrar(new LogAuditoria(usuarioId, "ordens_servico", (int) novoId, "INSERT",
                            String.format("O.S. aberta. Cliente ID: %d | Veículo ID: %d | Situação: %s",
                                    os.getClienteId(), os.getVeiculoId(), os.getSituacao())));
                }
            }
            System.out.println("✅ Ordem de Serviço aberta com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao abrir Ordem de Serviço: " + e.getMessage());
        }
    }

    public List<OrdemServico> listarTodas() {
        List<OrdemServico> lista = new ArrayList<>();
        String sql = "SELECT * FROM ordens_servico";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                OrdemServico os = new OrdemServico();
                os.setId(rs.getLong("id"));
                os.setSituacao(rs.getString("situacao"));
                os.setDataAbertura(rs.getTimestamp("data_abertura"));
                os.setDataEncerramento(rs.getTimestamp("data_encerramento"));
                os.setClienteId(rs.getLong("cliente_id"));
                os.setVeiculoId(rs.getLong("veiculo_id"));
                os.setValorTotalGeral(rs.getDouble("valor_total_geral"));
                lista.add(os);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar Ordens de Serviço: " + e.getMessage());
        }
        return lista;
    }

    public List<OrdemServico> buscarPorTermo(String termoBusca) {
        List<OrdemServico> lista = new ArrayList<>();
        String sql = "SELECT * FROM ordens_servico WHERE LOWER(situacao) LIKE LOWER(?) OR data_abertura LIKE ?";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String curinga = "%" + termoBusca.trim() + "%";
            stmt.setString(1, curinga);
            stmt.setString(2, curinga);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrdemServico os = new OrdemServico();
                    os.setId(rs.getLong("id"));
                    os.setSituacao(rs.getString("situacao"));
                    os.setDataAbertura(rs.getTimestamp("data_abertura"));
                    os.setClienteId(rs.getLong("cliente_id"));
                    os.setVeiculoId(rs.getLong("veiculo_id"));
                    os.setValorTotalGeral(rs.getDouble("valor_total_geral"));
                    lista.add(os);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar Ordens de Serviço: " + e.getMessage());
        }
        return lista;
    }

    public List<OrdemServico> buscarPorPeriodo(String dataInicio, String dataFim) {
        List<OrdemServico> lista = new ArrayList<>();
        String sql = "SELECT * FROM ordens_servico WHERE data_abertura BETWEEN ? AND ?";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dataInicio.trim() + " 00:00:00");
            stmt.setString(2, dataFim.trim() + " 23:59:59");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrdemServico os = new OrdemServico();
                    os.setId(rs.getLong("id"));
                    os.setSituacao(rs.getString("situacao"));
                    os.setDataAbertura(rs.getTimestamp("data_abertura"));
                    os.setClienteId(rs.getLong("cliente_id"));
                    os.setVeiculoId(rs.getLong("veiculo_id"));
                    os.setValorTotalGeral(rs.getDouble("valor_total_geral"));
                    lista.add(os);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar Ordens de Serviço por período: " + e.getMessage());
        }
        return lista;
    }
}