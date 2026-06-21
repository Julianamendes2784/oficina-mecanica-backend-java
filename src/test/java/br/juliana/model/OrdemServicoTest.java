package br.juliana.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class OrdemServicoTest {

    @Test
    @DisplayName("Deve calcular o valor total geral automaticamente ao criar a O.S.")
    void deveCalcularTotalGeralAoConstruir() {
        // Cenário e Ação: Criando uma O.S. com R$ 150 de serviços e R$ 350 de peças
        OrdemServico os = OrdemServico.builder()
                .numeroOs(1001)
                .valorTotalServicos(150.00)
                .valorTotalPecas(350.00)
                .build();

        // Validação: O total geral deve somar e dar exatamente 500.00
        assertThat(os.getValorTotalGeral()).isEqualTo(500.00);
    }

    @Test
    @DisplayName("Deve recalcular o valor total geral quando os valores mudarem via setters")
    void deveRecalcularTotalGeralNosSetters() {
        // Cenário: Começa zerada
        OrdemServico os = new OrdemServico();

        // Ação: Mudando valores individuais
        os.setValorTotalServicos(200.00);
        os.setValorTotalPecas(120.50);

        // Validação: Deve somar dinamicamente e bater 320.50
        assertThat(os.getValorTotalGeral()).isEqualTo(320.50);
    }

    @Test
    @DisplayName("Deve salvar a situação padronizada em letras maiúsculas")
    void deveNormalizarSituacao() {
        // Cenário: Inserindo situação em caixa baixa
        OrdemServico os = OrdemServico.builder()
                .situacao("em andamento")
                .build();

        // Validação
        assertThat(os.getSituacao()).isEqualTo("EM ANDAMENTO");
    }
}
