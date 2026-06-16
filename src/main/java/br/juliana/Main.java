package br.juliana;

import br.juliana.dao.ClienteDAO;
import br.juliana.model.Cliente;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ClienteDAO clienteDAO = new ClienteDAO();
        int opcao = -1;

        System.out.println("=================================");
        System.out.println("   SISTEMA DE OFICINA MECÂNICA   ");
        System.out.println("=================================");

        while (opcao != 0) {
            System.out.println("\n--- MENU DE CLIENTES ---");
            System.out.println("1 - Cadastrar Novo Cliente");
            System.out.println("2 - Consultar Cliente por CPF");
            System.out.println("3 - Alterar Cadastro de Cliente");
            System.out.println("0 - Sair do Sistema");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer do teclado

            switch (opcao) {
                case 1:
                    System.out.println("\n[ NOVO CADASTRO ]");
                    Cliente novoCliente = new Cliente();

                    System.out.print("Nome: ");
                    novoCliente.setNome(scanner.nextLine());

                    System.out.print("CPF (apenas números): ");
                    novoCliente.setCpf(scanner.nextLine());

                    System.out.print("Telefone: ");
                    novoCliente.setTelefone(scanner.nextLine());

                    System.out.print("Email: ");
                    novoCliente.setEmail(scanner.nextLine());

                    clienteDAO.cadastrar(novoCliente);
                    break;

                case 2:
                    System.out.println("\n[ CONSULTAR CLIENTE ]");
                    System.out.print("Digite o CPF do cliente para buscar: ");
                    String cpfBusca = scanner.nextLine();

                    Cliente clienteEncontrado = clienteDAO.consultarPorCpf(cpfBusca);

                    if (clienteEncontrado != null) {
                        System.out.println("\nDados do Cliente Cadastrado:");
                        System.out.println("ID: " + clienteEncontrado.getId());
                        System.out.println("Nome: " + clienteEncontrado.getNome());
                        System.out.println("CPF: " + clienteEncontrado.getCpf());
                        System.out.println("Telefone: " + clienteEncontrado.getTelefone());
                        System.out.println("Email: " + clienteEncontrado.getEmail());
                    } else {
                        System.out.println("⚠️ Cliente não encontrado no sistema.");
                    }
                    break;

                case 3:
                    System.out.println("\n[ ALTERAR CADASTRO ]");
                    System.out.print("Digite o CPF do cliente que deseja alterar: ");
                    String cpfAlterar = scanner.nextLine();

                    // Primeiro consultamos se ele existe para não atualizar dados no vento
                    Cliente clienteAtualizar = clienteDAO.consultarPorCpf(cpfAlterar);

                    if (clienteAtualizar != null) {
                        System.out.println("Cliente encontrado: " + clienteAtualizar.getNome());
                        System.out.println("Digite os novos dados (deixe em branco para manter o antigo):");

                        System.out.print("Novo Nome: ");
                        String novoNome = scanner.nextLine();
                        if (!novoNome.trim().isEmpty()) clienteAtualizar.setNome(novoNome);

                        System.out.print("Novo Telefone: ");
                        String novoTelefone = scanner.nextLine();
                        if (!novoTelefone.trim().isEmpty()) clienteAtualizar.setTelefone(novoTelefone);

                        System.out.print("Novo Email: ");
                        String novoEmail = scanner.nextLine();
                        if (!novoEmail.trim().isEmpty()) clienteAtualizar.setEmail(novoEmail);

                        clienteDAO.alterar(clienteAtualizar);
                    } else {
                        System.out.println("⚠️ Cliente não encontrado para alteração.");
                    }
                    break;

                case 0:
                    System.out.println("Encerrando o sistema da oficina. Até logo!");
                    break;

                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }

        scanner.close();
    }
}