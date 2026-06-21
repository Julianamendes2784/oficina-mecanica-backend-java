package br.juliana.view;

import br.juliana.dao.*;
import br.juliana.model.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class Main {
    private static ClienteDAO clienteDAO = new ClienteDAO();
    private static ServicoDAO servicoDAO = new ServicoDAO();
    private static ColaboradorDAO colaboradorDAO = new ColaboradorDAO();
    private static VeiculoDAO veiculoDAO = new VeiculoDAO();
    private static OrdemServicoDAO ordemServicoDAO = new OrdemServicoDAO();
    private static UsuarioDAO usuarioDAO = new UsuarioDAO();
    private static PecaDAO pecaDAO = new PecaDAO();
    private static OsMecanicoDAO osMecanicoDAO = new OsMecanicoDAO();
    private static OsPecaUtilizadaDAO osPecaUtilizadaDAO = new OsPecaUtilizadaDAO();
    private static OsServicoPrestadoDAO osServicoPrestadoDAO = new OsServicoPrestadoDAO();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Usuario usuarioLogado = null;

        System.out.println("========================================");
        System.out.println("    MECÂNICA TOPLÍDER - AUTENTICAÇÃO   ");
        System.out.println("========================================");

        while (usuarioLogado == null) {
            System.out.print("Digite o Usuário: ");
            String login = scanner.nextLine();

            System.out.print("Digite a Senha: ");
            String senha = scanner.nextLine();

            usuarioLogado = usuarioDAO.autenticar(login, senha);

            if (usuarioLogado == null) {
                System.out.println("\n❌ Usuário ou senha incorretos! Tente novamente.\n");
            }
        }

        System.out.println("\n✅ Login realizado com sucesso! Bem-vindo(a), " + usuarioLogado.getLogin() + ".\n");

        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n--- MecControl - Sistema de Gerenciamento de Oficina Mecânica ---");
            System.out.println("Escolha o módulo desejado:");
            System.out.println("1. Gestão de Clientes");
            System.out.println("2. Gestão de Serviços");
            System.out.println("3. Gestão de Colaboradores");
            System.out.println("4. Gestão de Veículos");
            System.out.println("5. Gestão de Ordens de Serviço (O.S.)");
            System.out.println("6. Gestão de Peças");
            System.out.println("0. Sair do Sistema");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    menuClientes();
                    break;
                case 2:
                    menuServicos();
                    break;
                case 3:
                    menuColaboradores();
                    break;
                case 4:
                    menuVeiculos();
                    break;
                case 5:
                    menuOrdensServicos();
                    break;
                case 6:
                    menuPecas();
                    break;
                case 0:
                    System.out.println("Encerrando o sistema... Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    break;
            }
        }
    }

    // ==========================================
    // 👥 MÓDULO: CLIENTES
    // ==========================================
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
                case 1:
                    cadastrarCliente();
                    break;
                case 2:
                    listarClientes();
                    break;
                case 3:
                    buscarCliente();
                    break;
                case 4:
                    atualizarCliente();
                    break;
                case 5:
                    excluirCliente();
                    break;
                case 0:
                    System.out.println("Voltando ao menu anterior...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
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
        scanner.nextLine();

        clienteDAO.excluir(id);
    }

    // ==========================================
    // 🛠️ MÓDULO: SERVIÇOS
    // ==========================================
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
                case 1:
                    cadastrarServico();
                    break;
                case 2:
                    listarServicos();
                    break;
                case 3:
                    buscarServico();
                    break;
                case 4:
                    atualizarServico();
                    break;
                case 5:
                    excluirServico();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
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
                    System.out.println("⚠️ Erro: Digite um preço numérico válido!");
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

    // ==========================================
    // 👔 MÓDULO: COLABORADORES
    // ==========================================
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
                case 1:
                    cadastrarColaborador();
                    break;
                case 2:
                    listarLaboradores();
                    break;
                case 3:
                    buscarColaborador();
                    break;
                case 4:
                    atualizarColaborador();
                    break;
                case 5:
                    excluirColaborador();
                    break;
                case 0:
                    System.out.println("Voltando ao menu anterior...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
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

    private static void listarLaboradores() {
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

    // ==========================================
    // 🚗 MÓDULO: VEÍCULOS
    // ==========================================
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
                case 1:
                    cadastrarVeiculo();
                    break;
                case 2:
                    listarVeiculo();
                    break;
                case 3:
                    buscarVeiculo();
                    break;
                case 4:
                    atualizarVeiculo();
                    break;
                case 5:
                    excluirVeiculo();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
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

    private static void listarVeiculo() {
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

    // ==========================================
    // 📋 MÓDULO: ORDENS DE SERVIÇO (O.S.)
    // ==========================================
    private static void menuOrdensServicos() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- MÓDULO: GESTÃO DE ORDENS DE SERVIÇO ---");
            System.out.println("1. Abrir Nova O.S. (Checklist de Entrada)");
            System.out.println("2. Listar Todas as O.S.");
            System.out.println("3. Cancelar Ordem de Serviço");
            System.out.println("4. Buscar O.S. (Nº, Situação ou Data)");
            System.out.println("5. Adicionar Mecânico à O.S.");
            System.out.println("6. Adicionar Serviço à O.S.");
            System.out.println("7. Adicionar Peça à O.S.");
            System.out.println("8. Listar Peças da O.S.");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("❌ Opção inválida! Digite um número do menu.");
                scanner.nextLine();
                opcao = -1;
                continue;
            }

            switch (opcao) {
                case 1:
                    abrirOrdemServicos();
                    break;
                case 2:
                    listarOrdensServicos();
                    break;
                case 3:
                    cancelarOrdemServicos();
                    break;
                case 4:
                    buscarOrdemServicos();
                    break;

                case 5:
                    adicionarMecanicoOS();
                    break;

                case 6:
                    adicionarServicoOS();
                    break;

                case 7:
                    adicionarPecaOS();
                    break;

               case 8:
                listarPecasOS();
                break;


                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }
    }


    private static void abrirOrdemServicos() {
        System.out.println("\n--- ABERTURA DE ORDEM DE SERVIÇO ---");
        System.out.print("Número da O.S. (ex: 1011): ");
        int numeroOs = scanner.nextInt();
        scanner.nextLine();

        System.out.print("ID do Cliente: ");
        long clienteId = scanner.nextLong();
        System.out.print("ID do Veículo: ");
        long veiculoId = scanner.nextLong();
        System.out.print("ID do Colaborador (Mecânico Responsável): ");
        long colaboradorId = scanner.nextLong();
        scanner.nextLine();

        System.out.println("\n--- CHECKLIST DE ENTRADA (Responda 1 para SIM, 0 para NÃO) ---");
        System.out.print("Possui Estepe? ");
        boolean chkEstepe = scanner.nextInt() == 1;
        System.out.print("Possui Macaco e Chave de Roda? ");
        boolean chkMacaco = scanner.nextInt() == 1;
        System.out.print("Possui Triângulo? ");
        boolean chkTriangulo = scanner.nextInt() == 1;
        System.out.print("Possui Rádio/Multimídia? ");
        boolean chkRadio = scanner.nextInt() == 1;
        scanner.nextLine();

        System.out.print("Nível de Combustível (ex: CHEIO, MEIO_TANQUE, RESERVA): ");
        String combustivel = scanner.nextLine().toUpperCase();

        System.out.print("Observações / Avarias Visíveis: ");
        String avarias = scanner.nextLine();

        OrdemServico novaOs = new OrdemServico();
        novaOs.setNumeroOs(numeroOs);
        novaOs.setSituacao("ABERTA");
        novaOs.setDataAbertura(new java.sql.Timestamp(System.currentTimeMillis()));

        novaOs.setClienteId(clienteId);
        novaOs.setVeiculoId(veiculoId);
        novaOs.setAbertoPorColaboradorId(colaboradorId);

        novaOs.setChkEstepe(chkEstepe);
        novaOs.setChkMacacoChaveRoda(chkMacaco);
        novaOs.setChkTriangulo(chkTriangulo);
        novaOs.setChkRadio(chkRadio);
        novaOs.setChkNivelCombustivel(combustivel);
        novaOs.setChkObservacoesAvarias(avarias);

        novaOs.setValorTotalServicos(0.0);
        novaOs.setValorTotalPecas(0.0);
        novaOs.setValorTotalGeral(0.0);

        ordemServicoDAO.cadastrar(novaOs);
    }

    private static void listarOrdensServicos() {
        System.out.println("\n--- LISTA DE ORDENS DE SERVIÇO ---");
        List<OrdemServico> lista = ordemServicoDAO.listarTodas();

        if (lista.isEmpty()) {
            System.out.println("Nenhum O.S. cadastrada.");
        } else {
            for (OrdemServico os : lista) {
                System.out.printf("ID: %d | Nº O.S.: %d | Situação: %s | Data Abertura: %s | Cliente ID: %d | Veículo ID: %d | Total: R$ %.2f\n",
                        os.getId(), os.getNumeroOs(), os.getSituacao(), os.getDataAbertura(), os.getClienteId(), os.getVeiculoId(), os.getValorTotalGeral());
            }
        }
    }

    private static void cancelarOrdemServicos() {
        System.out.println("\n--- CANCELAR ORDEM DE SERVIÇO ---");
        System.out.print("Digite o ID da Ordem de Serviço que deseja cancelar: ");
        long id = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Digite o motivo do cancelamento (Obrigatório): ");
        String motivo = scanner.nextLine();

        ordemServicoDAO.processarCancelamento(id, motivo);
    }

    private static void buscarOrdemServicos() {
        System.out.println("\n--- BUSCAR ORDEM DE SERVIÇO ---");
        System.out.println("1. Buscar por Número da O.S. ou Situação");
        System.out.println("2. Filtrar por Período de Datas");
        System.out.print("Escolha o tipo de busca: ");

        int tipoSubBusca = 0;
        try {
            tipoSubBusca = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("❌ Erro: Digite apenas o número da opção desejada!");
            scanner.nextLine();
            return;
        }

        List<OrdemServico> resultados = new ArrayList<>();

        if (tipoSubBusca == 1) {
            System.out.print("Digite o número da O.S. ou a situação: ");
            String termo = scanner.nextLine();
            resultados = ordemServicoDAO.buscarPorTermo(termo);
        } else if (tipoSubBusca == 2) {
            System.out.print("Digite a Data Inicial (Formato: AAAA-MM-DD): ");
            String dataInicio = scanner.nextLine();
            System.out.print("Digite a Data Final (Formato: AAAA-MM-DD): ");
            String dataFim = scanner.nextLine();

            resultados = ordemServicoDAO.buscarPorPeriodo(dataInicio, dataFim);
        } else {
            System.out.println("Opção inválida!");
            return;
        }

        if (resultados.isEmpty()) {
            System.out.println("❌ Nenhuma Ordem de Serviço encontrada para os critérios informados.");
        } else {
            System.out.println("\n--- RESULTADOS ENCONTRADOS ---");
            for (OrdemServico os : resultados) {
                System.out.printf("ID: %d | Nº O.S.: %d | Situação: %s | Data Abertura: %s | Total: R$ %.2f\n",
                        os.getId(), os.getNumeroOs(), os.getSituacao(), os.getDataAbertura(), os.getValorTotalGeral());
            }
        }
    }

    // ==========================================
    // 📦 MÓDULO: PEÇAS
    // ==========================================
    private static void menuPecas() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- GESTÃO DE PEÇAS ---");
            System.out.println("1. Cadastrar Peça");
            System.out.println("2. Listar Peças");
            System.out.println("3. Buscar Peça");
            System.out.println("4. Excluir Peça");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarPeca();
                    break;
                case 2:
                    listarPecas();
                    break;
                case 3:
                    buscarPeca();
                    break;
                case 4:
                    excluirPeca();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }
    }

    private static void cadastrarPeca() {
        System.out.println("\n--- NOVA PEÇA ---");
        System.out.print("Nome do Componente: ");
        String nome = scanner.nextLine();
        System.out.print("Preço Unitário: ");
        double preco = scanner.nextDouble();
        scanner.nextLine();

        Peca novaPeca = new Peca();
        novaPeca.setNomeComponente(nome);
        novaPeca.setPrecoUnitario(preco);

        pecaDAO.cadastrar(novaPeca);
    }

    private static void listarPecas() {
        System.out.println("\n--- LISTA DE PEÇAS ---");
        List<Peca> lista = pecaDAO.listarTodas();
        if (lista.isEmpty()) {
            System.out.println("Nenhuma peça cadastrada.");
        } else {
            for (Peca p : lista) {
                System.out.printf("ID: %d | Componente: %s | Preço: R$ %.2f\n", p.getId(), p.getNomeComponente(), p.getPrecoUnitario());
            }
        }
    }

    private static void buscarPeca() {
        System.out.println("\n--- BUSCAR PEÇA ---");
        System.out.print("Digite o nome ou ID da peça: ");
        String termo = scanner.nextLine();

        List<Peca> resultados = pecaDAO.buscarPorTermo(termo);
        if (resultados.isEmpty()) {
            System.out.println("❌ Nenhuma peça encontrada.");
        } else {
            for (Peca p : resultados) {
                System.out.printf("ID: %d | Componente: %s | Preço: R$ %.2f\n", p.getId(), p.getNomeComponente(), p.getPrecoUnitario());
            }
        }
    }
        private static void excluirPeca() {
            System.out.println("\n--- EXCLUIR PEÇA ---");
            listarPecas();
            System.out.print("Digite o ID da peça que deseja excluir: ");
            Long id = scanner.nextLong();
            scanner.nextLine();

            pecaDAO.excluir(id);
        }


    private static void adicionarMecanicoOS() {
        try {
            System.out.print("ID da Ordem de Serviço: ");
            int osId = scanner.nextInt();

            System.out.print("ID do Colaborador: ");
            int colaboradorId = scanner.nextInt();
            scanner.nextLine();

            OsMecanico m = new OsMecanico(osId, colaboradorId);
            osMecanicoDAO.inserir(m);

            System.out.println("✅ Mecânico adicionado!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void adicionarServicoOS() {
        try {
            System.out.print("ID da Ordem de Serviço: ");
            int osId = scanner.nextInt();

            System.out.print("ID do Serviço: ");
            int servicoId = scanner.nextInt();

            System.out.print("Preço aplicado: ");
            double preco = scanner.nextDouble();
            scanner.nextLine();

            OsServicoPrestado s = new OsServicoPrestado(osId, servicoId, preco);
            osServicoPrestadoDAO.inserir(s);

            System.out.println("✅ Serviço adicionado!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void adicionarPecaOS() {
        try {
            System.out.print("ID da Ordem de Serviço: ");
            int osId = scanner.nextInt();

            System.out.print("ID da Peça: ");
            int pecaId = scanner.nextInt();

            System.out.print("Quantidade: ");
            int quantidade = scanner.nextInt();

            System.out.print("Preço aplicado: ");
            double preco = scanner.nextDouble();
            scanner.nextLine();

            OsPecaUtilizada p = new OsPecaUtilizada(osId, pecaId, quantidade, preco);
            osPecaUtilizadaDAO.inserir(p);

            System.out.println("✅ Peça adicionada!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void listarPecasOS() {
        try {
            System.out.print("ID da Ordem de Serviço: ");
            int osId = scanner.nextInt();
            scanner.nextLine();

            List<OsPecaUtilizada> lista = osPecaUtilizadaDAO.listarPorOrdem(osId);

            if (lista.isEmpty()) {
                System.out.println("❌ Nenhuma peça encontrada.");
            } else {
                System.out.println("\n--- PEÇAS DA O.S. ---");

                for (OsPecaUtilizada p : lista) {
                    System.out.println(
                            "Peça ID: " + p.getPecaId() +
                                    " | Qtde: " + p.getQuantidade() +
                                    " | Preço: R$ " + p.getPrecoAplicado()
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
