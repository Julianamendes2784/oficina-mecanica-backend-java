package br.juliana.view;

import br.juliana.dao.ClienteDAO;
import br.juliana.model.Cliente;
import java.util.Scanner;
import java.util.List;

public class Main {
    private static ClienteDAO clienteDAO = new ClienteDAO();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n--- SISTEMA OFICINA MECÂNICA: GESTÃO DE CLIENTES ---");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("3. Atualizar Cliente");
            System.out.println("4. Excluir Cliente");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer do teclado

            switch (opcao) {
                case 1:
                    cadastrarCliente();
                    break;
                case 2:
                    listarClientes();
                    break;
                case 3:
                    atualizarCliente();
                    break;
                case 4:
                    excluirCliente();
                    break;
                case 0:
                    System.out.println("Encerrando o sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }
    }

    private static void cadastrarCliente() {
        System.out.println("\n--- NOVO CADASTRO ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("E-mail: ");
        String email = scanner.nextLine();

        // Usando construtor padrão vazio e adicionando com os setters para não dar erro
        Cliente novoCliente = new Cliente();
        novoCliente.setNome(nome);
        novoCliente.setCpf(cpf);
        novoCliente.setTelefone(telefone);
        novoCliente.setEmail(email);

        clienteDAO.cadastrar(novoCliente);
    }

    private static void listarClientes() {
        System.out.println("\n--- LISTA DE CLIENTES ---");
        List<Cliente> lista = clienteDAO.listarTodos();

        if (lista.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
        } else {
            for (Cliente c : lista) {
                System.out.printf("ID: %d | Nome: %s | CPF: %s | Tel: %s | Email: %s\n",
                        c.getId(), c.getNome(), c.getCpf(), c.getTelefone(), c.getEmail());
            }
        }
    }

    private static void atualizarCliente() {
        System.out.println("\n--- ATUALIZAR CADASTRO ---");
        System.out.print("Digite o ID do cliente que deseja alterar: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Limpar buffer

        System.out.print("Novo Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Novo CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Novo Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Novo E-mail: ");
        String email = scanner.nextLine();

        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setId(id);
        clienteAtualizado.setNome(nome);
        clienteAtualizado.setCpf(cpf);
        clienteAtualizado.setTelefone(telefone);
        clienteAtualizado.setEmail(email);

        clienteDAO.atualizar(clienteAtualizado);
    }

    private static void excluirCliente() {
        System.out.println("\n--- EXCLUIR CLIENTE ---");
        System.out.print("Digite o ID do cliente que deseja deletar: ");
        Long id = scanner.nextLong();

        clienteDAO.excluir(id);
    }
}