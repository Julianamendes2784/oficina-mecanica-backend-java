package br.juliana.view;

import br.juliana.dao.*;
import br.juliana.model.*;

import java.util.Scanner;
import java.util.List;

public class Main {
    private static ClienteDAO clienteDAO = new ClienteDAO();
    private static ServicoDAO servicoDAO = new ServicoDAO();
    private static ColaboradorDAO colaboradorDAO = new ColaboradorDAO();
    private static VeiculoDAO veiculoDAO = new VeiculoDAO();
    private static OrdemServicoDAO ordemServicoDAO = new OrdemServicoDAO();
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
            System.out.println("5. Gestão de Ordens de Serviço (O.S.)");
            System.out.println("0. Sair do Sistema");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> menuClientes();
                case 2 -> menuServicos();
                case 3 -> menuColaboradores();
                case 4 -> menuVeiculos();
                case 5 -> menuOrdensServico();
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
            scanner.nextLine(); // Limpar buffer

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
        scanner.nextLine(); // Limpar buffer

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
            scanner.nextLine(); // Limpar buffer

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
            scanner.nextLine(); // Limpar buffer

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
        // Mensagem clara para o usuário saber que pode digitar qualquer um dos três!
        System.out.print("Digite a descrição, parte dela ou o preço exato: ");
        String termo = scanner.nextLine();

        // Chama o método do DAO que faz a busca dupla
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
            scanner.nextLine(); // Limpar buffer

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
        scanner.nextLine(); // Limpar buffer
        System.out.print("Cor: ");
        String cor = scanner.nextLine();
        System.out.print("Quilometragem: ");
        int quilometragem = scanner.nextInt();
        System.out.print("ID do Cliente Proprietário (Obrigatório): ");
        long clienteId = scanner.nextLong();
        scanner.nextLine(); // Limpar buffer

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
        scanner.nextLine(); // Limpar buffer

        System.out.print("Novo Modelo: ");
        String modelo = scanner.nextLine();
        System.out.print("Novo Ano: ");
        int ano = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer
        System.out.print("Nova Cor: ");
        String cor = scanner.nextLine();
        System.out.print("Nova Quilometragem: ");
        int quilometragem = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer

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
        scanner.nextLine(); // Limpar buffer

        veiculoDAO.excluir(id);
    }
    private static void menuOrdensServico() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- MÓDULO: GESTÃO DE ORDENS DE SERVIÇO ---");
            System.out.println("1. Abrir Nova O.S. (Checklist de Entrada)");
            System.out.println("2. Listar Todas as O.S.");
            System.out.println("3. Cancelar Ordem de Serviço");
            System.out.println("4. Buscar O.S. (Nº, Situação ou Data)"); // <-- Nova opção!
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1 -> abrirOrdemServico();
                case 2 -> listarOrdensServico();
                case 3 -> cancelarOrdemServico();
                case 4 -> buscarOrdemServico(); // <-- Nova chamada!
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private static void abrirOrdemServico() {
        System.out.println("\n--- ABERTURA DE ORDEM DE SERVIÇO ---");
        System.out.print("Número da O.S. (ex: 1011): ");
        int numeroOs = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer

        // Relacionamentos Obrigatórios
        System.out.print("ID do Cliente: ");
        long clienteId = scanner.nextLong();
        System.out.print("ID do Veículo: ");
        long veiculoId = scanner.nextLong();
        System.out.print("ID do Colaborador (Mecânico Responsável): ");
        long colaboradorId = scanner.nextLong();
        scanner.nextLine(); // Limpar buffer

        // --- CHECKLIST DE ENTRADA DO VEÍCULO ---
        System.out.println("\n--- CHECKLIST DE ENTRADA (Responda 1 para SIM, 0 para NÃO) ---");
        System.out.print("Possui Estepe? ");
        boolean chkEstepe = scanner.nextInt() == 1;
        System.out.print("Possui Macaco e Chave de Roda? ");
        boolean chkMacaco = scanner.nextInt() == 1;
        System.out.print("Possui Triângulo? ");
        boolean chkTriangulo = scanner.nextInt() == 1;
        System.out.print("Possui Rádio/Multimídia? ");
        boolean chkRadio = scanner.nextInt() == 1;
        scanner.nextLine(); // Limpar buffer

        System.out.print("Nível de Combustível (ex: CHEIO, MEIO_TANQUE, RESERVA): ");
        String combustivel = scanner.nextLine().toUpperCase();

        System.out.print("Observações / Avarias Visíveis: ");
        String avarias = scanner.nextLine();

        // Montando o Objeto O.S.
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

    private static void listarOrdensServico() {
        System.out.println("\n--- LISTA DE ORDENS DE SERVIÇO ---");
        List<OrdemServico> lista = ordemServicoDAO.listarTodas();

        if (lista.isEmpty()) {
            System.out.println("Nenhuma O.S. cadastrada.");
        } else {
            for (OrdemServico os : lista) {
                System.out.printf("ID: %d | Nº O.S.: %d | Situação: %s | Data Abertura: %s | Cliente ID: %d | Veículo ID: %d | Total: R$ %.2f\n",
                        os.getId(), os.getNumeroOs(), os.getSituacao(), os.getDataAbertura(), os.getClienteId(), os.getVeiculoId(), os.getValorTotalGeral());
            }
        }
    }

    private static void cancelarOrdemServico() {
        System.out.println("\n--- CANCELAR ORDEM DE SERVIÇO ---");
        System.out.print("Digite o ID da Ordem de Serviço que deseja cancelar: ");
        long id = scanner.nextLong();
        scanner.nextLine(); // Limpar buffer

        System.out.print("Digite o motivo do cancelamento (Obrigatório): ");
        String motivo = scanner.nextLine();

        ordemServicoDAO.processarCancelamento(id, motivo);
    }

    private static void buscarOrdemServico() {
        System.out.println("\n--- BUSCAR ORDEM DE SERVIÇO ---");
        System.out.print("Digite o número da O.S., a situação ou parte da data (AAAA-MM-DD): ");
        String termo = scanner.nextLine();

        List<OrdemServico> resultados = ordemServicoDAO.buscarPorTermo(termo);

        if (resultados.isEmpty()) {
            System.out.println("❌ Nenhuma Ordem de Serviço encontrada com esse termo.");
        } else {
            System.out.println("\n--- RESULTADOS ENCONTRADOS ---");
            for (OrdemServico os : resultados) {
                System.out.printf("ID: %d | Nº O.S.: %d | Situação: %s | Data Abertura: %s | Total: R$ %.2f\n",
                        os.getId(), os.getNumeroOs(), os.getSituacao(), os.getDataAbertura(), os.getValorTotalGeral());
            }
        }
    }
}