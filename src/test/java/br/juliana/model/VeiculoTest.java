package br.juliana.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class VeiculoTest {

    @Test
    @DisplayName("Deve construir um veículo corretamente usando o Builder manual")
    void deveConstruirVeiculoComBuilder() {
        Veiculo veiculo = Veiculo.builder()
                .id(1L)
                .marca("Chevrolet")
                .modelo("Onix")
                .ano(2022)
                .placa("abc1d23")
                .build();

        assertThat(veiculo.getId()).isEqualTo(1L);
        assertThat(veiculo.getModelo()).isEqualTo("Onix");
        assertThat(veiculo.getAno()).isEqualTo(2022);
    }

    @Test
    @DisplayName("Deve garantir que a placa seja convertida para letras maiúsculas")
    void deveColocarPlacaEmMaiuscula() {
        Veiculo veiculo = Veiculo.builder()
                .placa("abc1d23")
                .build();

        assertThat(veiculo.getPlaca()).isEqualTo("ABC1D23");
    }
}