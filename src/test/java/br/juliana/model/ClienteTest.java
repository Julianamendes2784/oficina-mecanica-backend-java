package br.juliana.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ClienteTest {

    @Test
    @DisplayName("Deve higienizar o CPF removendo pontos e traços")
    void deveHigienizarCpf() {
        // Cenário
        Cliente cliente = Cliente.builder()
                .nome("Juliana Mendes")
                .cpf("123.456.789-00")
                .build();

        // Ação & Validação
        assertThat(cliente.getCpf()).isEqualTo("12345678900");
    }

    @Test
    @DisplayName("Deve retornar o CPF formatado com máscara visual corretamente")
    void deveRetornarCpfFormatado() {
        // Cenário
        Cliente cliente = Cliente.builder()
                .nome("Juliana Mendes")
                .cpf("98765432100")
                .build();

        // Ação & Validação
        assertThat(cliente.getCpfFormatado()).isEqualTo("987.654.321-00");
    }

    @Test
    @DisplayName("Deve higienizar o telefone removendo parênteses e traços")
    void deveHigienizarTelefone() {
        // Cenário
        Cliente cliente = Cliente.builder()
                .nome("Juliana Mendes")
                .telefone("(11) 99999-5555")
                .build();

        // Ação & Validação
        assertThat(cliente.getTelefone()).isEqualTo("11999995555");
    }
}