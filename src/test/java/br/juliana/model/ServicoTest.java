package br.juliana.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ServicoTest {

    @Test
    @DisplayName("Deve construir um serviço corretamente usando o Builder manual")
    void deveConstruirServicoComBuilder() {
        Servico servico = Servico.builder()
                .id(1L)
                .nome("Troca de Óleo")
                .valor(150.00)
                .build();

        assertThat(servico.getId()).isEqualTo(1L);
        assertThat(servico.getNome()).isEqualTo("Troca de Óleo");
        assertThat(servico.getValor()).isEqualTo(150.00);
    }
}
