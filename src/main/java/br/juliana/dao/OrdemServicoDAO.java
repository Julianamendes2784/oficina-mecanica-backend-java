package br.juliana.dao;

import br.juliana.config.FabricaConexao;
import br.juliana.model.OrdemServico;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdemServicoDAO {

        // 1. PROCESSAR CANCELAMENTO DE ORDEM DE SERVIÇO (Proibido Deletar)
    public void processarCancelamento(Long id, String motivoCancelamento) {
        String sqlBusca = "SELECT situacao, chk_observacoes_avarias FROM ordens_servico WHERE id = ?";

        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmtBusca = conn.prepareStatement(sqlBusca)) {

            stmtBusca.setLong(1, id);
            try (ResultSet rs = stmtBusca.executeQuery()) {
                if (rs.next()) {
                    String situacao = rs.getString("situacao");
                    String obsAntiga = rs.getString("chk_observacoes_avarias");

                    // Regra A: Concluída não pode ser alterada ou cancelada
                    if ("CONCLUIDA".equalsIgnoreCase(situacao)) {
                        System.out.println("\n❌ Erro de Segurança: Esta Ordem de Serviço já está CONCLUÍDA!");
                        System.out.println("💡 Motivo: Históricos de faturamento concluídos não podem ser removidos ou cancelados.");
                        return;
                    }

                    // Regra B: Já está cancelada
                    if ("CANCELADA".equalsIgnoreCase(situacao)) {
                        System.out.println("\n💡 Esta Ordem de Serviço já consta como CANCELADA no sistema.");
                        return;
                    }

                    // Regra C: Qualquer outro status (EM_ANDAMENTO, ABERTA, AGUARDANDO_PECA) vira cancelamento obrigatório
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

    // 2. LISTAR TODAS AS ORDENS
    public List<OrdemServico> listarTodas() {
        List<OrdemServico> lista = new ArrayList<>();
        String sql = "SELECT * FROM ordens_servico";
        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                OrdemServico os = new OrdemServico();
                os.setId(rs.getLong("id"));
                os.setNumeroOs(rs.getInt("numero_os"));
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
    // 3. CADASTRAR / ABRIR ORDEM DE SERVIÇO
    public void cadastrar(OrdemServico os) {
        String sql = "INSERT INTO ordens_servico (numero_os, situacao, data_abertura, chk_estepe, " +
                "chk_macaco_chave_roda, chk_triangulo, chk_radio, chk_nivel_combustivel, " +
                "chk_observacoes_avarias, cliente_id, veiculo_id, aberto_por_colaborador_id, " +
                "valor_total_servicos, valor_total_pecas, valor_total_geral) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, os.getNumeroOs());
            stmt.setString(2, os.getSituacao());
            stmt.setTimestamp(3, os.getDataAbertura());
            stmt.setInt(4, os.isChkEstepe() ? 1 : 0);
            stmt.setInt(5, os.isChkMacacoChaveRoda() ? 1 : 0);
            stmt.setInt(6, os.isChkTriangulo() ? 1 : 0);
            stmt.setInt(7, os.isChkRadio() ? 1 : 0);
            stmt.setString(8, os.getChkNivelCombustivel());
            stmt.setString(9, os.getChkObservacoesAvarias());
            stmt.setLong(10, os.getClienteId());
            stmt.setLong(11, os.getVeiculoId());
            stmt.setLong(12, os.getAbertoPorColaboradorId());
            stmt.setDouble(13, os.getValorTotalServicos());
            stmt.setDouble(14, os.getValorTotalPecas());
            stmt.setDouble(15, os.getValorTotalGeral());

            stmt.executeUpdate();
            System.out.println("✅ Ordem de Serviço aberta com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao abrir Ordem de Serviço: " + e.getMessage());
        }
    }
    // 4. BUSCA INTELIGENTE (Por Número da O.S., Situação ou Data de Abertura)
    public List<OrdemServico> buscarPorTermo(String termoBusca) {
        List<OrdemServico> lista = new ArrayList<>();

        // SQL que busca por parte da situação, número exato da OS, ou parte da data de abertura
        String sql = "SELECT * FROM ordens_servico WHERE LOWER(situacao) LIKE LOWER(?) " +
                "OR numero_os = ? " +
                "OR data_abertura LIKE ?";

        try (Connection conn = FabricaConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String curinga = "%" + termoBusca.trim() + "%";

            // 1º Parâmetro: Situação (LIKE)
            stmt.setString(1, curinga);

            // 2º Parâmetro: Número da O.S. (Tenta converter o texto para número)
            int numeroOsProcurado = -1;
            try {
                numeroOsProcurado = Integer.parseInt(termoBusca.trim());
            } catch (NumberFormatException e) {
                // Se não for um número válido (ex: o usuário digitou "ABERTA"), ignora
            }
            stmt.setInt(2, numeroOsProcurado);

            // 3º Parâmetro: Data de Abertura (LIKE para achar por ano, mês ou dia digitado como texto)
            stmt.setString(3, curinga);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrdemServico os = new OrdemServico();
                    os.setId(rs.getLong("id"));
                    os.setNumeroOs(rs.getInt("numero_os"));
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
}
