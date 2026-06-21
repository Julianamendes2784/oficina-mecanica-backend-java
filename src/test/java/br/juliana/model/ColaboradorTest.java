package br.juliana.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ColaboradorTest {

    @Test
    @DisplayName("Deve construir um colaborador e normalizar o papel em maiúsculas")
    void deveConstruirColaboradorEPadronizarPapel() {
        // Cenário e Ação usando o método cargo correto
        Colaborador colaborador = Colaborador.builder()
                .id(1L)
                .nome("Ricardo Mecânico")
                .cargo("mecanico")
                .build();

        // Validações
        assertThat(colaborador.getId()).isEqualTo(1L);
        assertThat(colaborador.getNome()).isEqualTo("Ricardo Mecânico");
        assertThat(colaborador.getCargo()).isEqualTo("MECANICO");
    }
}