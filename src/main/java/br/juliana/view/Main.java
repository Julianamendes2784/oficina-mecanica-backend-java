package br.juliana.view;

import br.juliana.dao.ClienteDAO;
import br.juliana.dao.ServicoDAO;
import br.juliana.model.Cliente;
import br.juliana.model.Servico;

import java.util.Scanner;
import java.util.List;

public class Main {
    private static ClienteDAO clienteDAO = new ClienteDAO();
    private static ServicoDAO servicoDAO = new ServicoDAO();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n--- SISTEMA OFICINA MECÂNICA ---");
            System.out.println("Escolha o módulo desejado:");
            System.out.println("1. Gestão de Clientes");
            System.out.println("2. Gestão de Serviços");
            System.out.println("0. Sair do Sistema");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer do teclado

            switch (opcao) {
                case 1 -> menuClientes();
                case 2 -> menuServicos();
                case 0 -> System.out.println("Encerrando o sistema... Até logo!");
                default -> System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    private static void cadastrarCliente() {
        System.out.println("\n--- NOVO CADASTRO ---");

        // 1. VALIDAÇÃO DO NOME
        String nome = "";
        while (nome.trim().isEmpty()) {
            System.out.print("Nome (Obrigatório): ");
            nome = scanner.nextLine();
            if (nome.trim().isEmpty()) {
                System.out.println("⚠️ Alerta: O campo Nome é de preenchimento obrigatório!");
            }
        }

        // 2. VALIDAÇÃO DO CPF
        String cpf = "";
        while (cpf.trim().isEmpty()) {
            System.out.print("CPF (Obrigatório): ");
            cpf = scanner.nextLine();
            if (cpf.trim().isEmpty()) {
                System.out.println("⚠️ Alerta: O campo CPF é de preenchimento obrigatório!");
            }
        }

        // 3. CAMPOS OPCIONAIS (Pode dar Enter em branco)
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("E-mail: ");
        String email = scanner.nextLine();

        // Criando e salvando o objeto se tudo estiver OK
        Cliente novoCliente = new Cliente();
        novoCliente.setNome(nome);
        novoCliente.setCpf(cpf);
        novoCliente.setTelefone(telefone);
        novoCliente.setEmail(email);

        clienteDAO.cadastrar(novoCliente);
    }

    private static void menuClientes() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- MÓDULO: GESTÃO DE CLIENTES ---");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("3. Atualizar Cliente");
            System.out.println("4. Excluir Cliente");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1 -> cadastrarCliente();
                case 2 -> listarClientes();
                case 3 -> atualizarCliente();
                case 4 -> excluirCliente();
                case 0 -> System.out.println("Voltando ao menu anterior...");
                default -> System.out.println("Opção inválida!");
            }
        }
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

        // 1. VALIDAÇÃO DO NOVO NOME
        String nome = "";
        while (nome.trim().isEmpty()) {
            System.out.print("Novo Nome (Obrigatório): ");
            nome = scanner.nextLine();
            if (nome.trim().isEmpty()) {
                System.out.println("⚠️ Alerta: O campo Nome é de preenchimento obrigatório!");
            }
        }

        // 2. VALIDAÇÃO DO NOVO CPF
        String cpf = "";
        while (cpf.trim().isEmpty()) {
            System.out.print("Novo CPF (Obrigatório): ");
            cpf = scanner.nextLine();
            if (cpf.trim().isEmpty()) {
                System.out.println("⚠️ Alerta: O campo CPF é de preenchimento obrigatório!");
            }
        }

        // 3. CAMPOS OPCIONAIS
        System.out.print("Novo Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Novo E-mail: ");
        String email = scanner.nextLine();

        // Montando o objeto atualizado
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
    private static void menuServicos() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- GESTÃO DE SERVIÇOS ---");
            System.out.println("1. Cadastrar Serviço");
            System.out.println("2. Listar Serviços");
            System.out.println("3. Atualizar Serviço");
            System.out.println("4. Excluir Serviço");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1 -> cadastrarServico();
                case 2 -> listarServicos();
                case 3 -> atualizarServico();
                case 4 -> excluirServico();
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private static void cadastrarServico() {
        System.out.println("\n--- NOVO SERVIÇO ---");

        String descricao = "";
        while (descricao.trim().isEmpty()) {
            System.out.print("Descrição (Obrigatório): ");
            descricao = scanner.nextLine();
            if (descricao.trim().isEmpty()) {
                System.out.println("⚠️ Alerta: O campo Descrição é de preenchimento obrigatório!");
            }
        }

        Double precoTabela = null;
        while (precoTabela == null) {
            System.out.print("Preço Tabela (Obrigatório): ");
            String entradaPreco = scanner.nextLine();
            if (entradaPreco.trim().isEmpty()) {
                System.out.println("⚠️ Alerta: O campo Preço Tabela é de preenchimento obrigatório!");
            } else {
                try {
                    precoTabela = Double.parseDouble(entradaPreco.replace(",", "."));
                } catch (NumberFormatException e) {
                    System.out.println("⚠️ Erro: Digite um preço numérico válido (Ex: 150.00)!");
                }
            }
        }

        Servico novoServico = new Servico();
        novoServico.setDescricao(descricao);
        novoServico.setPrecoTabela(precoTabela);

        servicoDAO.cadastrar(novoServico);
    }

    private static void listarServicos() {
        System.out.println("\n--- LISTA DE SERVIÇOS ---");
        List<Servico> lista = servicoDAO.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("Nenhum serviço cadastrado.");
        } else {
            for (Servico s : lista) {
                System.out.printf("ID: %d | Descrição: %s | Preço: R$ %.2f\n", s.getId(), s.getDescricao(), s.getPrecoTabela());
            }
        }
    }

    private static void atualizarServico() {
        System.out.println("\n--- ATUALIZAR SERVIÇO ---");
        System.out.print("Digite o ID do serviço que deseja alterar: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        String descricao = "";
        while (descricao.trim().isEmpty()) {
            System.out.print("Nova Descrição (Obrigatório): ");
            descricao = scanner.nextLine();
            if (descricao.trim().isEmpty()) {
                System.out.println("⚠️ Alerta: O campo Descrição é de preenchimento obrigatório!");
            }
        }

        Double precoTabela = null;
        while (precoTabela == null) {
            System.out.print("Novo Preço Tabela (Obrigatório): ");
            String entradaPreco = scanner.nextLine();
            if (entradaPreco.trim().isEmpty()) {
                System.out.println("⚠️ Alerta: O campo Preço Tabela é de preenchimento obrigatório!");
            } else {
                try {
                    precoTabela = Double.parseDouble(entradaPreco.replace(",", "."));
                } catch (NumberFormatException e) {
                    System.out.println("⚠️ Erro: Digite um preço numérico válido!");
                }
            }
        }

        Servico s = new Servico();
        s.setId(id);
        s.setDescricao(descricao);
        s.setPrecoTabela(precoTabela);

        servicoDAO.atualizar(s);
    }

    private static void excluirServico() {
        System.out.println("\n--- EXCLUIR SERVIÇO ---");
        System.out.print("Digite o ID do serviço que deseja remover: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        servicoDAO.excluir(id);
    }
}