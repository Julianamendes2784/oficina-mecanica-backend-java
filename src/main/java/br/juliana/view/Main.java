package br.juliana.view;

import br.juliana.dao.ClienteDAO;
import br.juliana.dao.ColaboradorDAO;
import br.juliana.dao.PecaDAO;
import br.juliana.dao.ServicoDAO;
import br.juliana.dao.VeiculoDAO;
import br.juliana.model.Cliente;
import br.juliana.model.Colaborador;
import br.juliana.model.Peca;
import br.juliana.model.Servico;
import br.juliana.model.Veiculo;

import java.util.Scanner;
import java.util.List;

public class Main {
    private static ClienteDAO clienteDAO = new ClienteDAO();
    private static ServicoDAO servicoDAO = new ServicoDAO();
    private static ColaboradorDAO colaboradorDAO = new ColaboradorDAO();
    private static VeiculoDAO veiculoDAO = new VeiculoDAO();
    private static PecaDAO pecaDAO = new PecaDAO();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n--- SISTEMA OFICINA MECÂNICA ---");
            System.out.println("Escolha o módulo desejado:");
            System.out.println("1. Gestão de Clientes");
            System.out.println("2. Gestão de Serviços");
            System.out.println("3. Gestão de Colaboradores");
            System.out.println("4. Gestão de Veículos");
            System.out.println("5. Gestão de Peças");
            System.out.println("0. Sair do Sistema");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer do teclado

            switch (opcao) {
                case 1 -> menuClientes();
                case 2 -> menuServicos();
                case 3 -> menuColaboradores();
                case 4 -> menuVeiculos();
                case 5 -> menuPecas();
                case 0 -> System.out.println("Encerrando o sistema... Até logo!");
                default -> System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    private static void cadastrarCliente() {
        System.out.println("\n--- NOVO CADASTRO ---");

        String nome = "";
        while (nome.trim().isEmpty()) {
            System.out.print("Nome (Obrigatório): ");
            nome = scanner.nextLine();
            if (nome.trim().isEmpty()) {
                System.out.println("⚠️ Alerta: O campo Nome é de preenchimento obrigatório!");
            }
        }

        String cpf = "";
        while (cpf.trim().isEmpty()) {
            System.out.print("CPF (Obrigatório): ");
            cpf = scanner.nextLine();
            if (cpf.trim().isEmpty()) {
                System.out.println("⚠️ Alerta: O campo CPF é de preenchimento obrigatório!");
            }
        }

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("E-mail: ");
        String email = scanner.nextLine();

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
            System.out.println("3. Buscar Cliente por Nome");
            System.out.println("4. Atualizar Cliente");
            System.out.println("5. Excluir Cliente");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> cadastrarCliente();
                case 2 -> listarClientes();
                case 3 -> buscarCliente();
                case 4 -> atualizarCliente();
                case 5 -> excluirCliente();
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
        scanner.nextLine();

        String nome = "";
        while (nome.trim().isEmpty()) {
            System.out.print("Novo Nome (Obrigatório): ");
            nome = scanner.nextLine();
            if (nome.trim().isEmpty()) {
                System.out.println("⚠️ Alerta: O campo Nome é de preenchimento obrigatório!");
            }
        }

        String cpf = "";
        while (cpf.trim().isEmpty()) {
            System.out.print("Novo CPF (Obrigatório): ");
            cpf = scanner.nextLine();
            if (cpf.trim().isEmpty()) {
                System.out.println("⚠️ Alerta: O campo CPF é de preenchimento obrigatório!");
            }
        }

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
    private static void menuServicos() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- GESTÃO DE SERVIÇOS ---");
            System.out.println("1. Cadastrar Serviço");
            System.out.println("2. Listar Serviços");
            System.out.println("3. Buscar Serviço");
            System.out.println("4. Atualizar Serviço");
            System.out.println("5. Excluir Serviço");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> cadastrarServico();
                case 2 -> listarServicos();
                case 3 -> buscarServico();
                case 4 -> atualizarServico();
                case 5 -> excluirServico();
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
    private static void menuColaboradores() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- MÓDULO: GESTÃO DE COLABORADORES ---");
            System.out.println("1. Cadastrar Colaborador");
            System.out.println("2. Listar Colaboradores");
            System.out.println("3. Buscar Colaborador por Nome");
            System.out.println("4. Atualizar Colaborador");
            System.out.println("5. Excluir Colaborador");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> cadastrarColaborador();
                case 2 -> listarColaboradores();
                case 3 -> buscarColaborador();
                case 4 -> atualizarColaborador();
                case 5 -> excluirColaborador();
                case 0 -> System.out.println("Voltando ao menu anterior...");
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private static void cadastrarColaborador() {
        System.out.println("\n--- NOVO COLABORADOR ---");

        String nome = "";
        while (nome.trim().isEmpty()) {
            System.out.print("Nome (Obrigatório): ");
            nome = scanner.nextLine();
            if (nome.trim().isEmpty()) {
                System.out.println("⚠️ Alerta: O campo Nome é de preenchimento obrigatório!");
            }
        }

        String cargo = "";
        while (cargo.trim().isEmpty()) {
            System.out.print("Cargo (Obrigatório - ex: Mecânico, Alinhador): ");
            cargo = scanner.nextLine();
            if (cargo.trim().isEmpty()) {
                System.out.println("⚠️ Alerta: O campo Cargo é de preenchimento obrigatório!");
            }
        }

        System.out.print("Especialidade (Opcional - ex: Injeção Eletrônica, Suspensão): ");
        String especialidade = scanner.nextLine();

        Colaborador c = new Colaborador();
        c.setNome(nome);
        c.setCargo(cargo);
        c.setEspecialidade(especialidade);

        colaboradorDAO.cadastrar(c);
    }

    private static void listarColaboradores() {
        System.out.println("\n--- LISTA DE COLABORADORES ---");
        List<Colaborador> lista = colaboradorDAO.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("Nenhum colaborador cadastrado.");
        } else {
            for (Colaborador c : lista) {
                System.out.printf("ID: %d | Nome: %s | Cargo: %s | Especialidade: %s\n",
                        c.getId(), c.getNome(), c.getCargo(), c.getEspecialidade());
            }
        }
    }

    private static void atualizarColaborador() {
        System.out.println("\n--- ATUALIZAR COLABORADOR ---");
        System.out.print("Digite o ID do colaborador que deseja alterar: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        String nome = "";
        while (nome.trim().isEmpty()) {
            System.out.print("Novo Nome (Obrigatório): ");
            nome = scanner.nextLine();
            if (nome.trim().isEmpty()) {
                System.out.println("⚠️ Alerta: O campo Nome é de preenchimento obrigatório!");
            }
        }

        String cargo = "";
        while (cargo.trim().isEmpty()) {
            System.out.print("Novo Cargo (Obrigatório): ");
            cargo = scanner.nextLine();
            if (cargo.trim().isEmpty()) {
                System.out.println("⚠️ Alerta: O campo Cargo é de preenchimento obrigatório!");
            }
        }

        System.out.print("Nova Especialidade (Opcional): ");
        String especialidade = scanner.nextLine();

        Colaborador c = new Colaborador();
        c.setId(id);
        c.setNome(nome);
        c.setCargo(cargo);
        c.setEspecialidade(especialidade);

        colaboradorDAO.atualizar(c);
    }

    private static void excluirColaborador() {
        System.out.println("\n--- EXCLUIR COLABORADOR ---");
        System.out.print("Digite o ID do colaborador que deseja remover: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        colaboradorDAO.excluir(id);
    }
    private static void buscarColaborador() {
        System.out.println("\n--- BUSCAR COLABORADOR ---");
        System.out.print("Digite o nome ou parte do nome: ");
        String termo = scanner.nextLine();

        List<Colaborador> resultados = colaboradorDAO.buscarPorNome(termo);

        if (resultados.isEmpty()) {
            System.out.println("❌ Nenhum colaborador encontrado com esse termo.");
        } else {
            System.out.println("\n--- RESULTADOS ENCONTRADOS ---");
            for (Colaborador c : resultados) {
                System.out.printf("ID: %d | Nome: %s | Cargo: %s | Especialidade: %s\n",
                        c.getId(), c.getNome(), c.getCargo(), c.getEspecialidade());
            }
        }
    }
    private static void buscarCliente() {
        System.out.println("\n--- BUSCAR CLIENTE ---");
        System.out.print("Digite o nome ou parte do nome do cliente: ");
        String termo = scanner.nextLine();

        List<Cliente> resultados = clienteDAO.buscarPorNome(termo);

        if (resultados.isEmpty()) {
            System.out.println("❌ Nenhum cliente encontrado com esse termo.");
        } else {
            System.out.println("\n--- RESULTADOS ENCONTRADOS ---");
            for (Cliente c : resultados) {
                System.out.printf("ID: %d | Nome: %s | CPF: %s | Telefone: %s\n",
                        c.getId(), c.getNome(), c.getCpf(), c.getTelefone());
            }
        }
    }

    private static void buscarServico() {
        System.out.println("\n--- BUSCAR SERVIÇO ---");
        System.out.print("Digite a descrição, parte dela ou o preço exato: ");
        String termo = scanner.nextLine();

        List<Servico> resultados = servicoDAO.buscarPorDescricao(termo);

        if (resultados.isEmpty()) {
            System.out.println("❌ Nenhum serviço encontrado com esse termo ou preço.");
        } else {
            System.out.println("\n--- RESULTADOS ENCONTRADOS ---");
            for (Servico s : resultados) {
                System.out.printf("ID: %d | Descrição: %s | Preço: R$ %.2f\n",
                        s.getId(), s.getDescricao(), s.getPrecoTabela());
            }
        }
    }
    private static void menuVeiculos() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- MÓDULO: GESTÃO DE VEÍCULOS ---");
            System.out.println("1. Cadastrar Veículo");
            System.out.println("2. Listar Veículos");
            System.out.println("3. Buscar Veículo por Placa ou Modelo");
            System.out.println("4. Atualizar Veículo");
            System.out.println("5. Excluir Veículo");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> cadastrarVeiculo();
                case 2 -> listarVeiculos();
                case 3 -> buscarVeiculo();
                case 4 -> atualizarVeiculo();
                case 5 -> excluirVeiculo();
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private static void cadastrarVeiculo() {
        System.out.println("\n--- NOVO VEÍCULO ---");
        System.out.print("Placa (Obrigatório): ");
        String placa = scanner.nextLine();
        System.out.print("Modelo (Obrigatório): ");
        String modelo = scanner.nextLine();
        System.out.print("Ano: ");
        int ano = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Cor: ");
        String cor = scanner.nextLine();
        System.out.print("Quilometragem: ");
        int quilometragem = scanner.nextInt();
        System.out.print("ID do Cliente Proprietário (Obrigatório): ");
        long clienteId = scanner.nextLong();
        scanner.nextLine();

        Veiculo novoVeiculo = new Veiculo();
        novoVeiculo.setPlaca(placa);
        novoVeiculo.setModelo(modelo);
        novoVeiculo.setAno(ano);
        novoVeiculo.setCor(cor);
        novoVeiculo.setQuilometragem(quilometragem);
        novoVeiculo.setClienteId(clienteId);

        veiculoDAO.cadastrar(novoVeiculo);
    }

    private static void listarVeiculos() {
        System.out.println("\n--- LISTA DE VEÍCULOS ---");
        List<Veiculo> lista = veiculoDAO.listarTodos();

        if (lista.isEmpty()) {
            System.out.println("Nenhum veículo cadastrado.");
        } else {
            for (Veiculo v : lista) {
                System.out.printf("ID: %d | Placa: %s | Modelo: %s | Ano: %d | Cor: %s | KM: %d | Cliente ID: %d\n",
                        v.getId(), v.getPlaca(), v.getModelo(), v.getAno(), v.getCor(), v.getQuilometragem(), v.getClienteId());
            }
        }
    }

    private static void buscarVeiculo() {
        System.out.println("\n--- BUSCAR VEÍCULO ---");
        System.out.print("Digite a placa ou o modelo do veículo: ");
        String termo = scanner.nextLine();

        List<Veiculo> resultados = veiculoDAO.buscarPorTermo(termo);

        if (resultados.isEmpty()) {
            System.out.println("❌ Nenhum veículo encontrado com esse termo.");
        } else {
            System.out.println("\n--- RESULTADOS ENCONTRADOS ---");
            for (Veiculo v : resultados) {
                System.out.printf("ID: %d | Placa: %s | Modelo: %s | Ano: %d | Cor: %s | Cliente ID: %d\n",
                        v.getId(), v.getPlaca(), v.getModelo(), v.getAno(), v.getCor(), v.getClienteId());
            }
        }
    }

    private static void atualizarVeiculo() {
        System.out.println("\n--- ATUALIZAR VEÍCULO ---");
        System.out.print("Digite o ID do veículo que deseja atualizar: ");
        long id = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Novo Modelo: ");
        String modelo = scanner.nextLine();
        System.out.print("Novo Ano: ");
        int ano = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nova Cor: ");
        String cor = scanner.nextLine();
        System.out.print("Nova Quilometragem: ");
        int quilometragem = scanner.nextInt();
        scanner.nextLine();

        Veiculo veiculoAtualizado = new Veiculo();
        veiculoAtualizado.setId(id);
        veiculoAtualizado.setModelo(modelo);
        veiculoAtualizado.setAno(ano);
        veiculoAtualizado.setCor(cor);
        veiculoAtualizado.setQuilometragem(quilometragem);

        veiculoDAO.atualizar(veiculoAtualizado);
    }

    private static void excluirVeiculo() {
        System.out.println("\n--- EXCLUIR VEÍCULO ---");
        System.out.print("Digite o ID do veículo que deseja deletar: ");
        long id = scanner.nextLong();
        scanner.nextLine();

        veiculoDAO.excluir(id);
    }

    // ================= GESTÃO DE PEÇAS =================
    private static void menuPecas() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- MÓDULO: GESTÃO DE PEÇAS ---");
            System.out.println("1. Cadastrar Peça");
            System.out.println("2. Listar Peças");
            System.out.println("3. Buscar Peça por Nome ou ID");
            System.out.println("4. Atualizar Peça");
            System.out.println("5. Excluir Peça");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> cadastrarPeca();
                case 2 -> listarPecas();
                case 3 -> buscarPeca();
                case 4 -> atualizarPeca();
                case 5 -> excluirPeca();
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private static void cadastrarPeca() {
        System.out.println("\n--- NOVA PEÇA ---");

        String nome = "";
        while (nome.trim().isEmpty()) {
            System.out.print("Nome (Obrigatório): ");
            nome = scanner.nextLine();
            if (nome.trim().isEmpty()) {
                System.out.println("⚠️ Alerta: O campo Nome é de preenchimento obrigatório!");
            }
        }

        Double precoVenda = null;
        while (precoVenda == null) {
            System.out.print("Preço de Venda (Obrigatório): ");
            String entradaPreco = scanner.nextLine();
            if (entradaPreco.trim().isEmpty()) {
                System.out.println("⚠️ Alerta: O campo Preço é de preenchimento obrigatório!");
            } else {
                try {
                    precoVenda = Double.parseDouble(entradaPreco.replace(",", "."));
                } catch (NumberFormatException e) {
                    System.out.println("⚠️ Erro: Digite um preço numérico válido (Ex: 150.00)!");
                }
            }
        }

        System.out.print("Estoque: ");
        int estoque = scanner.nextInt();
        scanner.nextLine();

        Peca novaPeca = new Peca(nome, precoVenda, estoque);
        pecaDAO.cadastrar(novaPeca);
    }

    private static void listarPecas() {
        System.out.println("\n--- LISTA DE PEÇAS ---");
        List<Peca> lista = pecaDAO.listarTodos();

        if (lista.isEmpty()) {
            System.out.println("Nenhuma peça cadastrada.");
        } else {
            for (Peca p : lista) {
                System.out.printf("ID: %d | Nome: %s | Preço: R$ %.2f | Estoque: %d\n",
                        p.getId(), p.getNome(), p.getPrecoVenda(), p.getEstoque());
            }
        }
    }

    private static void buscarPeca() {
        System.out.println("\n--- BUSCAR PEÇA ---");
        System.out.print("Digite o nome, parte do nome ou o ID da peça: ");
        String termo = scanner.nextLine();

        List<Peca> resultados = pecaDAO.buscarPorNomeOuId(termo);

        if (resultados.isEmpty()) {
            System.out.println("❌ Nenhuma peça encontrada com esse termo.");
        } else {
            System.out.println("\n--- RESULTADOS ENCONTRADOS ---");
            for (Peca p : resultados) {
                System.out.printf("ID: %d | Nome: %s | Preço: R$ %.2f | Estoque: %d\n",
                        p.getId(), p.getNome(), p.getPrecoVenda(), p.getEstoque());
            }
        }
    }

    private static void atualizarPeca() {
        System.out.println("\n--- ATUALIZAR PEÇA ---");
        System.out.print("Digite o ID da peça que deseja alterar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        String nome = "";
        while (nome.trim().isEmpty()) {
            System.out.print("Novo Nome (Obrigatório): ");
            nome = scanner.nextLine();
            if (nome.trim().isEmpty()) {
                System.out.println("⚠️ Alerta: O campo Nome é de preenchimento obrigatório!");
            }
        }

        Double precoVenda = null;
        while (precoVenda == null) {
            System.out.print("Novo Preço de Venda (Obrigatório): ");
            String entradaPreco = scanner.nextLine();
            if (entradaPreco.trim().isEmpty()) {
                System.out.println("⚠️ Alerta: O campo Preço é de preenchimento obrigatório!");
            } else {
                try {
                    precoVenda = Double.parseDouble(entradaPreco.replace(",", "."));
                } catch (NumberFormatException e) {
                    System.out.println("⚠️ Erro: Digite um preço numérico válido!");
                }
            }
        }

        System.out.print("Nova Quantidade em Estoque: ");
        int estoque = scanner.nextInt();
        scanner.nextLine();

        Peca pecaAtualizada = new Peca(id, nome, precoVenda, estoque);
        pecaDAO.atualizar(pecaAtualizada);
    }

    private static void excluirPeca() {
        System.out.println("\n--- EXCLUIR PEÇA ---");
        System.out.print("Digite o ID da peça que deseja remover: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        pecaDAO.excluir(id);
    }
}