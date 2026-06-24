package br.juliana.view;

import br.juliana.dao.*;
import br.juliana.model.*;
import br.juliana.model.LogAuditoria;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
    private static LogAuditoriaDAO logAuditoriaDAO = new LogAuditoriaDAO();
    private static Scanner scanner = new Scanner(System.in);
    private static Usuario usuarioLogado = null;

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("    MECÂNICA TOPLÍDER - AUTENTICAÇÃO   ");
        System.out.println("========================================");

        while (usuarioLogado == null) {
            System.out.print("Digite o Usuário: ");
            String login = scanner.nextLine();
            System.out.print("Digite a Senha: ");
            String senha = scanner.nextLine();
            usuarioLogado = usuarioDAO.autenticar(login, senha);
            if (usuarioLogado == null)
                System.out.println("\n❌ Usuário ou senha incorretos, ou usuário inativo! Tente novamente.\n");
        }

        System.out.println("\n✅ Login realizado com sucesso! Bem-vindo(a), " + usuarioLogado.getLogin() + ".");
        System.out.println("   Perfil: " + usuarioLogado.getPerfil() + "\n");

        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- MecControl - Sistema de Gerenciamento de Oficina Mecânica ---");
            System.out.println("1. Gestão de Clientes");
            System.out.println("2. Gestão de Serviços");
            System.out.println("3. Gestão de Colaboradores");
            System.out.println("4. Gestão de Veículos");
            System.out.println("5. Gestão de Ordens de Serviço (O.S.)");
            System.out.println("6. Gestão de Peças");
            if (usuarioLogado.isAdmin()) {
                System.out.println("7. Gestão de Usuários  [ADMIN]");
                System.out.println("8. Logs de Auditoria   [ADMIN]");
            }
            System.out.println("0. Sair do Sistema");
            System.out.print("Escolha uma opção: ");

            try { opcao = scanner.nextInt(); scanner.nextLine(); }
            catch (Exception e) { System.out.println("❌ Opção inválida!"); scanner.nextLine(); opcao = -1; continue; }

            switch (opcao) {
                case 1: menuClientes(); break;
                case 2: menuServicos(); break;
                case 3: menuColaboradores(); break;
                case 4: menuVeiculos(); break;
                case 5: menuOrdensServicos(); break;
                case 6: menuPecas(); break;
                case 7:
                    if (usuarioLogado.isAdmin()) menuUsuarios();
                    else System.out.println("🚫 Acesso negado!");
                    break;
                case 8:
                    if (usuarioLogado.isAdmin()) menuAuditoria();
                    else System.out.println("🚫 Acesso negado!");
                    break;
                case 0: System.out.println("Encerrando o sistema... Até logo!"); break;
                default: System.out.println("Opção inválida! Tente novamente."); break;
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
            try { opcao = scanner.nextInt(); scanner.nextLine(); }
            catch (Exception e) { scanner.nextLine(); opcao = -1; continue; }
            switch (opcao) {
                case 1: cadastrarCliente(); break;
                case 2: listarClientes(); break;
                case 3: buscarCliente(); break;
                case 4: atualizarCliente(); break;
                case 5: excluirCliente(); break;
                case 0: System.out.println("Voltando ao menu anterior..."); break;
                default: System.out.println("Opção inválida!"); break;
            }
        }
    }

    private static void cadastrarCliente() {
        System.out.println("\n--- NOVO CADASTRO DE CLIENTE (ou digite 0 para voltar) ---");
        String nome = "";
        while (nome.trim().isEmpty()) {
            System.out.print("Nome (Obrigatório): "); nome = scanner.nextLine();
            if (nome.trim().equals("0")) { System.out.println("↩️ Operação cancelada."); return; }
            if (nome.trim().isEmpty()) System.out.println("⚠️ O campo Nome é obrigatório!");
        }
        String cpf = "";
        while (cpf.trim().isEmpty()) {
            System.out.print("CPF (Obrigatório): "); cpf = scanner.nextLine();
            if (cpf.trim().equals("0")) { System.out.println("↩️ Operação cancelada."); return; }
            if (cpf.trim().isEmpty()) System.out.println("⚠️ O campo CPF é obrigatório!");
        }
        System.out.print("Telefone: "); String telephone = scanner.nextLine();
        if (telephone.trim().equals("0")) { System.out.println("↩️ Operação cancelada."); return; }
        System.out.print("E-mail: "); String email = scanner.nextLine();
        if (email.trim().equals("0")) { System.out.println("↩️ Operação cancelada."); return; }
        Cliente novoCliente = new Cliente();
        novoCliente.setNome(nome); novoCliente.setCpf(cpf);
        novoCliente.setTelefone(telephone); novoCliente.setEmail(email);
        clienteDAO.cadastrar(novoCliente, usuarioLogado.getId());
        System.out.println("✅ Cliente cadastrado com sucesso!");
    }

    private static void listarClientes() {
        System.out.println("\n--- LISTA DE CLIENTES ---");
        List<Cliente> lista = clienteDAO.listarTodos();
        if (lista.isEmpty()) { System.out.println("Nenhum cliente cadastrado."); }
        else { for (Cliente c : lista) System.out.printf("ID: %d | Nome: %s | CPF: %s | Tel: %s | Email: %s\n", c.getId(), c.getNome(), c.getCpf(), c.getTelefone(), c.getEmail()); }
    }

    private static void buscarCliente() {
        System.out.println("\n--- BUSCAR CLIENTE ---");
        System.out.print("Digite o nome ou parte do nome: "); String termo = scanner.nextLine();
        List<Cliente> resultados = clienteDAO.buscarPorNome(termo);
        if (resultados.isEmpty()) { System.out.println("❌ Nenhum cliente encontrado."); }
        else { for (Cliente c : resultados) System.out.printf("ID: %d | Nome: %s | CPF: %s | Tel: %s\n", c.getId(), c.getNome(), c.getCpf(), c.getTelefone()); }
    }

    private static void atualizarCliente() {
        System.out.println("\n--- ATUALIZAR CADASTRO ---");
        System.out.print("Digite o ID do cliente: "); Long id = scanner.nextLong(); scanner.nextLine();
        String nome = "";
        while (nome.trim().isEmpty()) {
            System.out.print("Novo Nome (Obrigatório): "); nome = scanner.nextLine();
            if (nome.trim().isEmpty()) System.out.println("⚠️ O campo Nome é obrigatório!");
        }
        String cpf = "";
        while (cpf.trim().isEmpty()) {
            System.out.print("Novo CPF (Obrigatório): "); cpf = scanner.nextLine();
            if (cpf.trim().isEmpty()) System.out.println("⚠️ O campo CPF é obrigatório!");
        }
        System.out.print("Novo Telefone: "); String telephone = scanner.nextLine();
        System.out.print("Novo E-mail: "); String email = scanner.nextLine();
        Cliente c = new Cliente(); c.setId(id); c.setNome(nome); c.setCpf(cpf); c.setTelefone(telephone); c.setEmail(email);
        clienteDAO.atualizar(c, usuarioLogado.getId());
    }

    private static void excluirCliente() {
        System.out.println("\n--- EXCLUIR CLIENTE ---");
        System.out.print("Digite o ID do cliente: "); Long id = scanner.nextLong(); scanner.nextLine();
        clienteDAO.excluir(id, usuarioLogado.getId());
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
            try { opcao = scanner.nextInt(); scanner.nextLine(); }
            catch (Exception e) { scanner.nextLine(); opcao = -1; continue; }
            switch (opcao) {
                case 1: cadastrarServico(); break;
                case 2: listarServicos(); break;
                case 3: buscarServico(); break;
                case 4: atualizarServico(); break;
                case 5: excluirServico(); break;
                case 0: System.out.println("Voltando ao menu principal..."); break;
                default: System.out.println("Opção inválida!"); break;
            }
        }
    }

    private static void cadastrarServico() {
        System.out.println("\n--- NOVO SERVIÇO (ou digite 0 para voltar) ---");
        String descricao = "";
        while (descricao.trim().isEmpty()) {
            System.out.print("Descrição (Obrigatório): "); descricao = scanner.nextLine();
            if (descricao.trim().equals("0")) { System.out.println("↩️ Operação cancelada."); return; }
            if (descricao.trim().isEmpty()) System.out.println("⚠️ O campo Descrição é obrigatório!");
        }
        Double preco = null;
        while (preco == null) {
            System.out.print("Preço Tabela (Obrigatório): "); String entrada = scanner.nextLine();
            if (entrada.trim().equals("0")) { System.out.println("↩️ Operação cancelada."); return; }
            if (entrada.trim().isEmpty()) { System.out.println("⚠️ O campo Preço é obrigatório!"); }
            else { try { preco = Double.parseDouble(entrada.trim().replace(",", ".")); } catch (NumberFormatException e) { System.out.println("⚠️ Preço inválido!"); } }
        }
        Servico s = new Servico(); s.setDescricao(descricao); s.setPrecoTabela(preco);
        servicoDAO.cadastrar(s, usuarioLogado.getId());
    }

    private static void listarServicos() {
        System.out.println("\n--- LISTA DE SERVIÇOS ---");
        List<Servico> lista = servicoDAO.listarTodos();
        if (lista.isEmpty()) { System.out.println("Nenhum serviço cadastrado."); }
        else { for (Servico s : lista) System.out.printf("ID: %d | Descrição: %s | Preço: R$ %.2f\n", s.getId(), s.getDescricao(), s.getPrecoTabela()); }
    }

    private static void buscarServico() {
        System.out.println("\n--- BUSCAR SERVIÇO ---");
        System.out.print("Descrição ou parte dela: "); String termo = scanner.nextLine();
        List<Servico> resultados = servicoDAO.buscarPorDescricao(termo);
        if (resultados.isEmpty()) { System.out.println("❌ Nenhum serviço encontrado."); }
        else { for (Servico s : resultados) System.out.printf("ID: %d | Descrição: %s | Preço: R$ %.2f\n", s.getId(), s.getDescricao(), s.getPrecoTabela()); }
    }

    private static void atualizarServico() {
        System.out.println("\n--- ATUALIZAR SERVIÇO ---");
        System.out.print("ID do serviço: "); Long id = scanner.nextLong(); scanner.nextLine();
        String descricao = "";
        while (descricao.trim().isEmpty()) {
            System.out.print("Nova Descrição (Obrigatório): "); descricao = scanner.nextLine();
            if (descricao.trim().isEmpty()) System.out.println("⚠️ O campo Descrição é obrigatório!");
        }
        Double preco = null;
        while (preco == null) {
            System.out.print("Novo Preço Tabela (Obrigatório): "); String entrada = scanner.nextLine();
            if (entrada.trim().isEmpty()) { System.out.println("⚠️ O campo Preço é obrigatório!"); }
            else { try { preco = Double.parseDouble(entrada.trim().replace(",", ".")); } catch (NumberFormatException e) { System.out.println("⚠️ Preço inválido!"); } }
        }
        Servico s = new Servico(); s.setId(id); s.setDescricao(descricao); s.setPrecoTabela(preco);
        servicoDAO.atualizar(s, usuarioLogado.getId());
    }

    private static void excluirServico() {
        System.out.println("\n--- EXCLUIR SERVIÇO ---");
        System.out.print("ID do serviço: "); Long id = scanner.nextLong(); scanner.nextLine();
        servicoDAO.excluir(id, usuarioLogado.getId());
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
            try { opcao = scanner.nextInt(); scanner.nextLine(); }
            catch (Exception e) { scanner.nextLine(); opcao = -1; continue; }
            switch (opcao) {
                case 1: cadastrarColaborador(); break;
                case 2: listarLaboradores(); break;
                case 3: buscarColaborador(); break;
                case 4: atualizarColaborador(); break;
                case 5: excluirColaborador(); break;
                case 0: System.out.println("Voltando ao menu anterior..."); break;
                default: System.out.println("Opção inválida!"); break;
            }
        }
    }

    private static void cadastrarColaborador() {
        System.out.println("\n--- NOVO COLABORADOR (ou digite 0 para voltar) ---");
        String nome = "";
        while (nome.trim().isEmpty()) {
            System.out.print("Nome (Obrigatório): "); nome = scanner.nextLine();
            if (nome.trim().equals("0")) { System.out.println("↩️ Operação cancelada."); return; }
            if (nome.trim().isEmpty()) System.out.println("⚠️ O campo Nome é obrigatório!");
        }
        String cargo = "";
        while (cargo.trim().isEmpty()) {
            System.out.print("Cargo (Obrigatório): "); cargo = scanner.nextLine();
            if (cargo.trim().equals("0")) { System.out.println("↩️ Operação cancelada."); return; }
            if (cargo.trim().isEmpty()) System.out.println("⚠️ O campo Cargo é obrigatório!");
        }
        System.out.print("Especialidade (Opcional): "); String especialidade = scanner.nextLine();
        if (especialidade.trim().equals("0")) { System.out.println("↩️ Operação cancelada."); return; }
        Colaborador c = new Colaborador(); c.setNome(nome); c.setCargo(cargo); c.setEspecialidade(especialidade);
        colaboradorDAO.cadastrar(c, usuarioLogado.getId());
    }

    private static void listarLaboradores() {
        System.out.println("\n--- LISTA DE COLABORADORES ---");
        List<Colaborador> lista = colaboradorDAO.listarTodos();
        if (lista.isEmpty()) { System.out.println("Nenhum colaborador cadastrado."); }
        else { for (Colaborador c : lista) System.out.printf("ID: %d | Nome: %s | Cargo: %s | Especialidade: %s\n", c.getId(), c.getNome(), c.getCargo(), c.getEspecialidade()); }
    }

    private static void buscarColaborador() {
        System.out.println("\n--- BUSCAR COLABORADOR ---");
        System.out.print("Nome ou parte do nome: "); String termo = scanner.nextLine();
        List<Colaborador> resultados = colaboradorDAO.buscarPorNome(termo);
        if (resultados.isEmpty()) { System.out.println("❌ Nenhum colaborador encontrado."); }
        else { for (Colaborador c : resultados) System.out.printf("ID: %d | Nome: %s | Cargo: %s | Especialidade: %s\n", c.getId(), c.getNome(), c.getCargo(), c.getEspecialidade()); }
    }

    private static void atualizarColaborador() {
        System.out.println("\n--- ATUALIZAR COLABORADOR ---");
        System.out.print("ID do colaborador: "); Long id = scanner.nextLong(); scanner.nextLine();
        String nome = "";
        while (nome.trim().isEmpty()) {
            System.out.print("Novo Nome (Obrigatório): "); nome = scanner.nextLine();
            if (nome.trim().isEmpty()) System.out.println("⚠️ O campo Nome é obrigatório!");
        }
        String cargo = "";
        while (cargo.trim().isEmpty()) {
            System.out.print("Novo Cargo (Obrigatório): "); cargo = scanner.nextLine();
            if (cargo.trim().isEmpty()) System.out.println("⚠️ O campo Cargo é obrigatório!");
        }
        System.out.print("Nova Especialidade (Opcional): "); String especialidade = scanner.nextLine();
        Colaborador c = new Colaborador(); c.setId(id); c.setNome(nome); c.setCargo(cargo); c.setEspecialidade(especialidade);
        colaboradorDAO.atualizar(c, usuarioLogado.getId());
    }

    private static void excluirColaborador() {
        System.out.println("\n--- EXCLUIR COLABORADOR ---");
        System.out.print("ID do colaborador: "); Long id = scanner.nextLong(); scanner.nextLine();
        colaboradorDAO.excluir(id, usuarioLogado.getId());
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
            try { opcao = scanner.nextInt(); scanner.nextLine(); }
            catch (Exception e) { scanner.nextLine(); opcao = -1; continue; }
            switch (opcao) {
                case 1: cadastrarVeiculo(); break;
                case 2: listarVeiculo(); break;
                case 3: buscarVeiculo(); break;
                case 4: atualizarVeiculo(); break;
                case 5: excluirVeiculo(); break;
                case 0: System.out.println("Voltando ao menu principal..."); break;
                default: System.out.println("Opção inválida!"); break;
            }
        }
    }

    private static void cadastrarVeiculo() {
        System.out.println("\n--- NOVO VEÍCULO (ou digite 0 para voltar) ---");
        System.out.print("Placa (Obrigatório): "); String placa = scanner.nextLine();
        if (placa.trim().equals("0")) { System.out.println("↩️ Operação cancelada."); return; }
        System.out.print("Modelo (Obrigatório): "); String modelo = scanner.nextLine();
        if (modelo.trim().equals("0")) { System.out.println("↩️ Operação cancelada."); return; }
        System.out.print("Ano: "); String entradaAno = scanner.nextLine();
        if (entradaAno.trim().equals("0")) { System.out.println("↩️ Operação cancelada."); return; }
        int ano = entradaAno.trim().isEmpty() ? 0 : Integer.parseInt(entradaAno);
        System.out.print("Cor: "); String cor = scanner.nextLine();
        if (cor.trim().equals("0")) { System.out.println("↩️ Operação cancelada."); return; }
        System.out.print("Quilometragem: "); String entradaKm = scanner.nextLine();
        if (entradaKm.trim().equals("0")) { System.out.println("↩️ Operação cancelada."); return; }
        int quilometragem = entradaKm.trim().isEmpty() ? 0 : Integer.parseInt(entradaKm);
        System.out.print("ID do Cliente Proprietário (Obrigatório): "); String entradaClienteId = scanner.nextLine();
        if (entradaClienteId.trim().equals("0") || entradaClienteId.trim().isEmpty()) { System.out.println("↩️ Cancelado."); return; }
        long clienteId = Long.parseLong(entradaClienteId);
        Veiculo v = new Veiculo(); v.setPlaca(placa); v.setModelo(modelo); v.setAno(ano);
        v.setCor(cor); v.setQuilometragem(quilometragem); v.setClienteId(clienteId);
        veiculoDAO.cadastrar(v, usuarioLogado.getId());
    }

    private static void listarVeiculo() {
        System.out.println("\n--- LISTA DE VEÍCULOS ---");
        List<Veiculo> lista = veiculoDAO.listarTodos();
        if (lista.isEmpty()) { System.out.println("Nenhum veículo cadastrado."); }
        else { for (Veiculo v : lista) System.out.printf("ID: %d | Placa: %s | Modelo: %s | Ano: %d | Cor: %s | KM: %d | Cliente ID: %d\n", v.getId(), v.getPlaca(), v.getModelo(), v.getAno(), v.getCor(), v.getQuilometragem(), v.getClienteId()); }
    }

    private static void buscarVeiculo() {
        System.out.println("\n--- BUSCAR VEÍCULO ---");
        System.out.print("Placa ou modelo: "); String termo = scanner.nextLine();
        List<Veiculo> resultados = veiculoDAO.buscarPorTermo(termo);
        if (resultados.isEmpty()) { System.out.println("❌ Nenhum veículo encontrado."); }
        else { for (Veiculo v : resultados) System.out.printf("ID: %d | Placa: %s | Modelo: %s | Ano: %d | Cor: %s | Cliente ID: %d\n", v.getId(), v.getPlaca(), v.getModelo(), v.getAno(), v.getCor(), v.getClienteId()); }
    }

    private static void atualizarVeiculo() {
        System.out.println("\n--- ATUALIZAR VEÍCULO ---");
        System.out.print("ID do veículo: "); long id = scanner.nextLong(); scanner.nextLine();
        System.out.print("Novo Modelo: "); String modelo = scanner.nextLine();
        System.out.print("Novo Ano: "); int ano = scanner.nextInt(); scanner.nextLine();
        System.out.print("Nova Cor: "); String cor = scanner.nextLine();
        System.out.print("Nova Quilometragem: "); int quilometragem = scanner.nextInt(); scanner.nextLine();
        Veiculo v = new Veiculo(); v.setId(id); v.setModelo(modelo); v.setAno(ano); v.setCor(cor); v.setQuilometragem(quilometragem);
        veiculoDAO.atualizar(v, usuarioLogado.getId());
    }

    private static void excluirVeiculo() {
        System.out.println("\n--- EXCLUIR VEÍCULO ---");
        System.out.print("ID do veículo: "); long id = scanner.nextLong(); scanner.nextLine();
        veiculoDAO.excluir(id, usuarioLogado.getId());
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
            System.out.println("4. Buscar O.S. (Situação ou Data)");
            System.out.println("5. Adicionar Mecânico à O.S.");
            System.out.println("6. Adicionar Serviço à O.S.");
            System.out.println("7. Adicionar Peça à O.S.");
            System.out.println("8. Listar Peças da O.S.");
            System.out.println("9. Listar Mecânicos da O.S.");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            try { opcao = scanner.nextInt(); scanner.nextLine(); }
            catch (Exception e) { System.out.println("❌ Opção inválida!"); scanner.nextLine(); opcao = -1; continue; }
            switch (opcao) {
                case 1: abrirOrdemServicos(); break;
                case 2: listarOrdensServicos(); break;
                case 3: cancelarOrdemServicos(); break;
                case 4: buscarOrdemServicos(); break;
                case 5: adicionarMecanicoOS(); break;
                case 6: adicionarServicoOS(); break;
                case 7: adicionarPecaOS(); break;
                case 8: listarPecasOS(); break;
                case 9: listarMecanicosOS(); break;
                case 0: System.out.println("Voltando ao menu principal..."); break;
                default: System.out.println("Opção inválida!"); break;
            }
        }
    }

    private static void abrirOrdemServicos() {
        System.out.println("\n--- ABERTURA DE ORDEM DE SERVIÇO ---");

        long clienteId = 0;
        while (clienteId == 0) {
            System.out.println("\n👤 CLIENTE (Obrigatório)");
            System.out.println("  1. Buscar pelo nome  2. Informar ID  3. Cadastrar novo  0. Cancelar");
            System.out.print("Escolha: "); String opc = scanner.nextLine().trim();
            if (opc.equals("0")) { System.out.println("↩️ Cancelado."); return; }
            if (opc.equals("1")) {
                System.out.print("Nome ou parte do nome: "); String termo = scanner.nextLine();
                if (termo.trim().isEmpty()) { System.out.println("⚠️ Digite ao menos um caractere."); continue; }
                List<Cliente> encontrados = clienteDAO.buscarPorNome(termo);
                if (encontrados.isEmpty()) { System.out.println("❌ Nenhum cliente encontrado."); }
                else {
                    for (Cliente c : encontrados) System.out.printf("  ID: %d | Nome: %s | CPF: %s | Tel: %s\n", c.getId(), c.getNome(), c.getCpf(), c.getTelefone());
                    System.out.print("ID do cliente (ou 0 para voltar): "); String entId = scanner.nextLine().trim();
                    if (!entId.equals("0") && !entId.isEmpty()) { try { clienteId = Long.parseLong(entId); } catch (NumberFormatException e) { System.out.println("⚠️ ID inválido."); } }
                }
            } else if (opc.equals("2")) {
                System.out.print("ID do Cliente: "); String entId = scanner.nextLine().trim();
                if (entId.equals("0") || entId.isEmpty()) { System.out.println("↩️ Cancelado."); return; }
                try { clienteId = Long.parseLong(entId); } catch (NumberFormatException e) { System.out.println("⚠️ ID inválido."); }
            } else if (opc.equals("3")) {
                String nome = ""; while (nome.trim().isEmpty()) { System.out.print("Nome: "); nome = scanner.nextLine(); if (nome.trim().isEmpty()) System.out.println("⚠️ Obrigatório!"); }
                String cpf = ""; while (cpf.trim().isEmpty()) { System.out.print("CPF: "); cpf = scanner.nextLine(); if (cpf.trim().isEmpty()) System.out.println("⚠️ Obrigatório!"); }
                System.out.print("Telefone: "); String telefone = scanner.nextLine();
                System.out.print("E-mail: "); String email = scanner.nextLine();
                Cliente novoCliente = new Cliente(); novoCliente.setNome(nome); novoCliente.setCpf(cpf); novoCliente.setTelefone(telefone); novoCliente.setEmail(email);
                clienteDAO.cadastrar(novoCliente, usuarioLogado.getId());
                List<Cliente> recemCadastrado = clienteDAO.buscarPorNome(nome);
                if (!recemCadastrado.isEmpty()) { clienteId = recemCadastrado.get(0).getId(); System.out.printf("✅ Cliente cadastrado! ID: %d\n", clienteId); }
                else { System.out.print("⚠️ Informe o ID manualmente: "); try { clienteId = Long.parseLong(scanner.nextLine().trim()); } catch (NumberFormatException e) { return; } }
            } else { System.out.println("⚠️ Opção inválida."); }
        }

        long veiculoId = 0;
        while (veiculoId == 0) {
            System.out.println("\n🚗 VEÍCULO");
            System.out.println("  1. Buscar por placa/modelo  2. Informar ID  3. Cadastrar novo  0. Cancelar");
            System.out.print("Escolha: "); String opc = scanner.nextLine().trim();
            if (opc.equals("0")) { System.out.println("↩️ Cancelado."); return; }
            if (opc.equals("1")) {
                System.out.print("Placa ou modelo: "); String termo = scanner.nextLine();
                if (termo.trim().isEmpty()) { System.out.println("⚠️ Digite ao menos um caractere."); continue; }
                List<Veiculo> encontrados = veiculoDAO.buscarPorTermo(termo);
                if (encontrados.isEmpty()) { System.out.println("❌ Nenhum veículo encontrado."); }
                else {
                    for (Veiculo v : encontrados) System.out.printf("  ID: %d | Placa: %s | Modelo: %s | Ano: %d | Cliente ID: %d\n", v.getId(), v.getPlaca(), v.getModelo(), v.getAno(), v.getClienteId());
                    System.out.print("ID do veículo (ou 0 para voltar): "); String entId = scanner.nextLine().trim();
                    if (!entId.equals("0") && !entId.isEmpty()) { try { veiculoId = Long.parseLong(entId); } catch (NumberFormatException e) { System.out.println("⚠️ ID inválido."); } }
                }
            } else if (opc.equals("2")) {
                System.out.print("ID do Veículo: "); String entId = scanner.nextLine().trim();
                if (entId.equals("0") || entId.isEmpty()) { System.out.println("↩️ Cancelado."); return; }
                try { veiculoId = Long.parseLong(entId); } catch (NumberFormatException e) { System.out.println("⚠️ ID inválido."); }
            } else if (opc.equals("3")) {
                System.out.print("Placa: "); String placa = scanner.nextLine(); if (placa.trim().isEmpty()) { System.out.println("⚠️ Obrigatório."); continue; }
                System.out.print("Modelo: "); String modelo = scanner.nextLine(); if (modelo.trim().isEmpty()) { System.out.println("⚠️ Obrigatório."); continue; }
                System.out.print("Ano: "); String entAno = scanner.nextLine(); int ano = entAno.trim().isEmpty() ? 0 : Integer.parseInt(entAno.trim());
                System.out.print("Cor: "); String cor = scanner.nextLine();
                System.out.print("KM: "); String entKm = scanner.nextLine(); int km = entKm.trim().isEmpty() ? 0 : Integer.parseInt(entKm.trim());
                Veiculo novoVeiculo = new Veiculo(); novoVeiculo.setPlaca(placa); novoVeiculo.setModelo(modelo); novoVeiculo.setAno(ano); novoVeiculo.setCor(cor); novoVeiculo.setQuilometragem(km); novoVeiculo.setClienteId(clienteId);
                veiculoDAO.cadastrar(novoVeiculo, usuarioLogado.getId());
                List<Veiculo> recemCadastrado = veiculoDAO.buscarPorTermo(placa);
                if (!recemCadastrado.isEmpty()) { veiculoId = recemCadastrado.get(0).getId(); System.out.printf("✅ Veículo cadastrado! ID: %d\n", veiculoId); }
                else { System.out.print("⚠️ Informe o ID manualmente: "); try { veiculoId = Long.parseLong(scanner.nextLine().trim()); } catch (NumberFormatException e) { return; } }
            } else { System.out.println("⚠️ Opção inválida."); }
        }

        long colaboradorIdResponsavel = 0;
        while (colaboradorIdResponsavel == 0) {
            System.out.println("\n👔 MECÂNICO RESPONSÁVEL (obrigatório)");
            System.out.println("  1. Buscar pelo nome  2. Informar ID  0. Cancelar");
            System.out.print("Escolha: "); String opc = scanner.nextLine().trim();
            if (opc.equals("0")) { System.out.println("↩️ Cancelado."); return; }
            if (opc.equals("1")) {
                System.out.print("Nome ou parte do nome: "); String termo = scanner.nextLine();
                if (termo.trim().isEmpty()) { System.out.println("⚠️ Digite ao menos um caractere."); continue; }
                List<Colaborador> encontrados = colaboradorDAO.buscarPorNome(termo);
                if (encontrados.isEmpty()) { System.out.println("❌ Nenhum colaborador encontrado."); }
                else {
                    for (Colaborador c : encontrados) System.out.printf("  ID: %d | Nome: %s | Cargo: %s\n", c.getId(), c.getNome(), c.getCargo());
                    System.out.print("ID do colaborador (ou 0 para voltar): "); String entId = scanner.nextLine().trim();
                    if (!entId.equals("0") && !entId.isEmpty()) { try { colaboradorIdResponsavel = Long.parseLong(entId); } catch (NumberFormatException e) { System.out.println("⚠️ ID inválido."); } }
                }
            } else if (opc.equals("2")) {
                System.out.print("ID do Colaborador: "); String entId = scanner.nextLine().trim();
                if (entId.equals("0") || entId.isEmpty()) { System.out.println("↩️ Cancelado."); return; }
                try { colaboradorIdResponsavel = Long.parseLong(entId); } catch (NumberFormatException e) { System.out.println("⚠️ ID inválido."); }
            } else { System.out.println("⚠️ Opção inválida."); }
        }

        System.out.println("\n--- CHECKLIST DE ENTRADA (1 = SIM, 0 = NÃO) ---");
        System.out.print("Possui Estepe? "); boolean chkEstepe = scanner.nextLine().trim().equals("1");
        System.out.print("Possui Macaco e Chave de Roda? "); boolean chkMacaco = scanner.nextLine().trim().equals("1");
        System.out.print("Possui Triângulo? "); boolean chkTriangulo = scanner.nextLine().trim().equals("1");
        System.out.print("Possui Rádio/Multimídia? "); boolean chkRadio = scanner.nextLine().trim().equals("1");
        System.out.print("Nível de Combustível (CHEIO, MEIO_TANQUE, RESERVA): "); String combustivel = scanner.nextLine().toUpperCase();
        System.out.print("Observações / Avarias Visíveis: "); String avarias = scanner.nextLine();

        OrdemServico novaOs = new OrdemServico();
        novaOs.setSituacao("ABERTA");
        novaOs.setDataAbertura(new java.sql.Timestamp(System.currentTimeMillis()));
        novaOs.setClienteId(clienteId); novaOs.setVeiculoId(veiculoId); novaOs.setAbertoPorColaboradorId(colaboradorIdResponsavel);
        novaOs.setChkEstepe(chkEstepe); novaOs.setChkMacacoChaveRoda(chkMacaco); novaOs.setChkTriangulo(chkTriangulo);
        novaOs.setChkRadio(chkRadio); novaOs.setChkNivelCombustivel(combustivel); novaOs.setChkObservacoesAvarias(avarias);
        novaOs.setValorTotalServicos(0.0); novaOs.setValorTotalPecas(0.0); novaOs.setValorTotalGeral(0.0);

        ordemServicoDAO.cadastrar(novaOs, usuarioLogado.getId());

        List<OrdemServico> todasOs = ordemServicoDAO.listarTodas();
        if (!todasOs.isEmpty()) {
            long osId = todasOs.get(todasOs.size() - 1).getId();
            osMecanicoDAO.inserir(new OsMecanico((int) osId, (int) colaboradorIdResponsavel));
            System.out.println("\n➕ Deseja adicionar mais mecânicos? (1 = Sim | 0 = Não)");
            System.out.print("Escolha: ");
            if (scanner.nextLine().trim().equals("1")) adicionarMecanicosExtras((int) osId);
        }
        System.out.println("✅ Ordem de Serviço aberta com sucesso!");
    }

    private static void adicionarMecanicosExtras(int osId) {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n  1. Buscar pelo nome  2. Informar ID  0. Finalizar");
            System.out.print("Escolha: "); String opc = scanner.nextLine().trim();
            if (opc.equals("0")) { continuar = false; }
            else if (opc.equals("1")) {
                System.out.print("Nome: "); String termo = scanner.nextLine();
                List<Colaborador> encontrados = colaboradorDAO.buscarPorNome(termo);
                if (encontrados.isEmpty()) { System.out.println("❌ Nenhum colaborador encontrado."); }
                else {
                    for (Colaborador c : encontrados) System.out.printf("  ID: %d | Nome: %s | Cargo: %s\n", c.getId(), c.getNome(), c.getCargo());
                    System.out.print("ID (ou 0 para voltar): "); String entId = scanner.nextLine().trim();
                    if (!entId.equals("0") && !entId.isEmpty()) {
                        try { osMecanicoDAO.inserir(new OsMecanico(osId, Integer.parseInt(entId))); System.out.println("✅ Mecânico adicionado!"); System.out.print("Adicionar mais? (1/0): "); if (scanner.nextLine().trim().equals("0")) continuar = false; }
                        catch (NumberFormatException e) { System.out.println("⚠️ ID inválido."); }
                    }
                }
            } else if (opc.equals("2")) {
                System.out.print("ID: "); String entId = scanner.nextLine().trim();
                if (!entId.equals("0") && !entId.isEmpty()) {
                    try { osMecanicoDAO.inserir(new OsMecanico(osId, Integer.parseInt(entId))); System.out.println("✅ Mecânico adicionado!"); System.out.print("Adicionar mais? (1/0): "); if (scanner.nextLine().trim().equals("0")) continuar = false; }
                    catch (NumberFormatException e) { System.out.println("⚠️ ID inválido."); }
                }
            } else { System.out.println("⚠️ Opção inválida."); }
        }
    }

    private static void listarOrdensServicos() {
        System.out.println("\n--- LISTA DE ORDENS DE SERVIÇO ---");
        List<OrdemServico> lista = ordemServicoDAO.listarTodas();
        if (lista.isEmpty()) { System.out.println("Nenhuma O.S. cadastrada."); }
        else { for (OrdemServico os : lista) System.out.printf("ID: %d | Situação: %s | Data: %s | Cliente ID: %d | Veículo ID: %d | Total: R$ %.2f\n", os.getId(), os.getSituacao(), os.getDataAbertura(), os.getClienteId(), os.getVeiculoId(), os.getValorTotalGeral()); }
    }

    private static void cancelarOrdemServicos() {
        System.out.println("\n--- CANCELAR ORDEM DE SERVIÇO ---");
        System.out.print("ID da O.S.: "); long id = scanner.nextLong(); scanner.nextLine();
        System.out.print("Motivo do cancelamento: "); String motivo = scanner.nextLine();
        ordemServicoDAO.processarCancelamento(id, motivo, usuarioLogado.getId());
    }

    private static void buscarOrdemServicos() {
        System.out.println("\n--- BUSCAR O.S. ---");
        System.out.println("1. Por Situação  2. Por Período");
        System.out.print("Escolha: ");
        int tipo = 0;
        try { tipo = scanner.nextInt(); scanner.nextLine(); } catch (Exception e) { scanner.nextLine(); return; }
        List<OrdemServico> resultados = new ArrayList<>();
        if (tipo == 1) { System.out.print("Situação (ABERTA, CANCELADA...): "); resultados = ordemServicoDAO.buscarPorTermo(scanner.nextLine()); }
        else if (tipo == 2) { System.out.print("Data Inicial (AAAA-MM-DD): "); String di = scanner.nextLine(); System.out.print("Data Final (AAAA-MM-DD): "); resultados = ordemServicoDAO.buscarPorPeriodo(di, scanner.nextLine()); }
        if (resultados.isEmpty()) { System.out.println("❌ Nenhuma O.S. encontrada."); }
        else { for (OrdemServico os : resultados) System.out.printf("ID: %d | Situação: %s | Data: %s | Total: R$ %.2f\n", os.getId(), os.getSituacao(), os.getDataAbertura(), os.getValorTotalGeral()); }
    }

    private static void adicionarMecanicoOS() {
        System.out.println("\n--- ADICIONAR MECÂNICO À O.S. ---");
        System.out.print("ID da O.S.: ");
        int osId; try { osId = scanner.nextInt(); scanner.nextLine(); } catch (Exception e) { System.out.println("❌ ID inválido."); scanner.nextLine(); return; }
        adicionarMecanicosExtras(osId);
        System.out.println("✅ Mecânicos registrados na O.S. " + osId + ".");
    }

    private static void listarMecanicosOS() {
        System.out.println("\n--- MECÂNICOS DA O.S. ---");
        System.out.print("ID da O.S.: ");
        int osId; try { osId = scanner.nextInt(); scanner.nextLine(); } catch (Exception e) { System.out.println("❌ ID inválido."); scanner.nextLine(); return; }
        List<OsMecanico> lista = osMecanicoDAO.listarPorOrdem(osId);
        if (lista.isEmpty()) { System.out.println("❌ Nenhum mecânico registrado."); }
        else {
            List<Colaborador> todos = colaboradorDAO.listarTodos();
            System.out.println("\n--- MECÂNICOS REGISTRADOS ---");
            for (OsMecanico m : lista) {
                String info = "—";
                for (Colaborador c : todos) { if (c.getId() == m.getColaboradorId()) { info = c.getNome() + " | Cargo: " + c.getCargo(); break; } }
                System.out.printf("  Colaborador ID: %d | %s\n", m.getColaboradorId(), info);
            }
        }
    }

    private static void adicionarServicoOS() {
        try {
            System.out.print("ID da O.S.: "); int osId = scanner.nextInt();
            System.out.print("ID do Serviço: "); int servicoId = scanner.nextInt(); scanner.nextLine();
            System.out.print("Preço aplicado: "); double preco = Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
            osServicoPrestadoDAO.inserir(new OsServicoPrestado(osId, servicoId, preco));
            logAuditoriaDAO.registrar(new LogAuditoria(usuarioLogado.getId(), "os_servicos_prestados", osId, "INSERT",
                    String.format("Serviço ID %d adicionado à O.S. %d. Preço: R$ %.2f", servicoId, osId, preco)));
            System.out.println("✅ Serviço adicionado!");
        } catch (NumberFormatException e) { System.out.println("❌ Preço inválido."); }
        catch (Exception e) { e.printStackTrace(); }
    }

    private static void adicionarPecaOS() {
        try {
            System.out.print("ID da O.S.: "); int osId = scanner.nextInt();
            System.out.print("ID da Peça: "); int pecaId = scanner.nextInt();
            System.out.print("Quantidade: "); int quantidade = scanner.nextInt(); scanner.nextLine();
            System.out.print("Preço aplicado: "); double preco = Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
            osPecaUtilizadaDAO.inserir(new OsPecaUtilizada(osId, pecaId, quantidade, preco));
            logAuditoriaDAO.registrar(new LogAuditoria(usuarioLogado.getId(), "os_pecas_utilizadas", osId, "INSERT",
                    String.format("Peça ID %d adicionada à O.S. %d. Qtde: %d | Preço: R$ %.2f", pecaId, osId, quantidade, preco)));
            System.out.println("✅ Peça adicionada!");
        } catch (NumberFormatException e) { System.out.println("❌ Preço inválido."); }
        catch (Exception e) { e.printStackTrace(); }
    }

    private static void listarPecasOS() {
        try {
            System.out.print("ID da O.S.: "); int osId = scanner.nextInt(); scanner.nextLine();
            List<OsPecaUtilizada> lista = osPecaUtilizadaDAO.listarPorOrdem(osId);
            if (lista.isEmpty()) { System.out.println("❌ Nenhuma peça encontrada."); }
            else { System.out.println("\n--- PEÇAS DA O.S. ---"); for (OsPecaUtilizada p : lista) System.out.println("Peça ID: " + p.getPecaId() + " | Qtde: " + p.getQuantidade() + " | Preço: R$ " + p.getPrecoAplicado()); }
        } catch (Exception e) { e.printStackTrace(); }
    }

    // ==========================================
    // 📦 MÓDULO: PEÇAS
    // ==========================================
    private static void menuPecas() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- GESTÃO DE PEÇAS ---");
            System.out.println("1. Cadastrar Peça  2. Listar Peças  3. Buscar Peça  4. Excluir Peça  0. Voltar");
            System.out.print("Escolha: ");
            try { opcao = scanner.nextInt(); scanner.nextLine(); }
            catch (Exception e) { scanner.nextLine(); opcao = -1; continue; }
            switch (opcao) {
                case 1: cadastrarPeca(); break;
                case 2: listarPecas(); break;
                case 3: buscarPeca(); break;
                case 4: excluirPeca(); break;
                case 0: System.out.println("Voltando..."); break;
                default: System.out.println("Opção inválida!"); break;
            }
        }
    }

    private static void cadastrarPeca() {
        System.out.println("\n--- NOVA PEÇA ---");
        System.out.print("Nome: "); String nome = scanner.nextLine();
        if (nome.trim().equals("0")) { System.out.println("↩️ Cancelado."); return; }
        System.out.print("Preço Unitário: "); String entradaPreco = scanner.nextLine();
        if (entradaPreco.trim().equals("0")) { System.out.println("↩️ Cancelado."); return; }
        try {
            double preco = Double.parseDouble(entradaPreco.trim().replace(",", "."));
            Peca p = new Peca(); p.setNome(nome); p.setPrecoUnitario(preco);
            pecaDAO.cadastrar(p, usuarioLogado.getId());
        } catch (NumberFormatException e) { System.out.println("❌ Preço inválido!"); }
    }

    private static void listarPecas() {
        System.out.println("\n--- LISTA DE PEÇAS ---");
        List<Peca> lista = pecaDAO.listarTodas();
        if (lista.isEmpty()) { System.out.println("Nenhuma peça cadastrada."); }
        else { for (Peca p : lista) System.out.printf("ID: %d | Nome: %s | Preço: R$ %.2f\n", p.getId(), p.getNome(), p.getPrecoUnitario()); }
    }

    private static void buscarPeca() {
        System.out.println("\n--- BUSCAR PEÇA ---");
        System.out.print("Nome ou ID: "); String termo = scanner.nextLine();
        List<Peca> resultados = pecaDAO.buscarPorTermo(termo);
        if (resultados.isEmpty()) { System.out.println("❌ Nenhuma peça encontrada."); }
        else { for (Peca p : resultados) System.out.printf("ID: %d | Nome: %s | Preço: R$ %.2f\n", p.getId(), p.getNome(), p.getPrecoUnitario()); }
    }

    private static void excluirPeca() {
        System.out.println("\n--- EXCLUIR PEÇA ---"); listarPecas();
        System.out.print("ID da peça: "); Long id = scanner.nextLong(); scanner.nextLine();
        pecaDAO.excluir(id, usuarioLogado.getId());
    }

    // ==========================================
    // 🔐 MÓDULO: USUÁRIOS  [somente ADMIN]
    // ==========================================
    private static void menuUsuarios() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- MÓDULO: GESTÃO DE USUÁRIOS [ADMIN] ---");
            System.out.println("1. Cadastrar Usuário  2. Listar Usuários  3. Desativar Usuário  4. Reativar Usuário  5. Alterar Perfil  0. Voltar");
            System.out.print("Escolha: ");
            try { opcao = scanner.nextInt(); scanner.nextLine(); }
            catch (Exception e) { scanner.nextLine(); opcao = -1; continue; }
            switch (opcao) {
                case 1: cadastrarUsuario(); break;
                case 2: listarUsuarios(); break;
                case 3: desativarUsuario(); break;
                case 4: reativarUsuario(); break;
                case 5: alterarPerfilUsuario(); break;
                case 0: System.out.println("Voltando..."); break;
                default: System.out.println("Opção inválida!"); break;
            }
        }
    }

    private static void cadastrarUsuario() {
        System.out.println("\n--- NOVO USUÁRIO ---");
        String login = "";
        while (login.trim().isEmpty()) {
            System.out.print("Login (Obrigatório): "); login = scanner.nextLine();
            if (login.trim().equals("0")) { System.out.println("↩️ Cancelado."); return; }
            if (login.trim().isEmpty()) System.out.println("⚠️ O campo Login é obrigatório!");
        }
        String senha = "";
        while (senha.trim().isEmpty()) {
            System.out.print("Senha (Obrigatório): "); senha = scanner.nextLine();
            if (senha.trim().equals("0")) { System.out.println("↩️ Cancelado."); return; }
            if (senha.trim().isEmpty()) System.out.println("⚠️ O campo Senha é obrigatório!");
        }
        System.out.println("Perfil:  1. PADRAO  2. ADMIN"); System.out.print("Escolha: ");
        String perfil = scanner.nextLine().trim().equals("2") ? "ADMIN" : "PADRAO";
        System.out.print("ID do Colaborador vinculado (deixe em branco se não houver): ");
        String entColabId = scanner.nextLine().trim();
        int colaboradorId = 0;
        if (!entColabId.isEmpty()) { try { colaboradorId = Integer.parseInt(entColabId); } catch (NumberFormatException e) { System.out.println("⚠️ ID inválido, colaborador não vinculado."); } }
        Usuario novoUsuario = new Usuario();
        novoUsuario.setLogin(login); novoUsuario.setSenha(senha); novoUsuario.setPerfil(perfil); novoUsuario.setColaboradorId(colaboradorId);
        usuarioDAO.cadastrar(novoUsuario);
        logAuditoriaDAO.registrar(new LogAuditoria(usuarioLogado.getId(), "usuarios", 0, "INSERT",
                "Novo usuário cadastrado. Login: " + login + " | Perfil: " + perfil));
    }

    private static void listarUsuarios() {
        System.out.println("\n--- LISTA DE USUÁRIOS ---");
        List<Usuario> lista = usuarioDAO.listarTodos();
        if (lista.isEmpty()) { System.out.println("Nenhum usuário cadastrado."); }
        else { for (Usuario u : lista) System.out.printf("ID: %d | Login: %-20s | Perfil: %-6s | Colaborador ID: %d | Status: %s\n", u.getId(), u.getLogin(), u.getPerfil(), u.getColaboradorId(), u.isAtivo() ? "✅ Ativo" : "🔴 Inativo"); }
    }

    private static void desativarUsuario() {
        System.out.println("\n--- DESATIVAR USUÁRIO ---"); listarUsuarios();
        System.out.print("\nID do usuário a desativar: ");
        int id; try { id = scanner.nextInt(); scanner.nextLine(); } catch (Exception e) { System.out.println("❌ ID inválido."); scanner.nextLine(); return; }
        if (id == usuarioLogado.getId()) { System.out.println("🚫 Você não pode desativar o seu próprio usuário!"); return; }
        System.out.printf("Confirma desativação do usuário ID %d? (1 = Sim / 0 = Não): ", id);
        if (scanner.nextLine().trim().equals("1")) {
            usuarioDAO.desativar(id);
            logAuditoriaDAO.registrar(new LogAuditoria(usuarioLogado.getId(), "usuarios", id, "UPDATE", "Usuário ID " + id + " desativado."));
        } else { System.out.println("↩️ Cancelado."); }
    }

    private static void reativarUsuario() {
        System.out.println("\n--- REATIVAR USUÁRIO ---"); listarUsuarios();
        System.out.print("\nID do usuário a reativar: ");
        int id; try { id = scanner.nextInt(); scanner.nextLine(); } catch (Exception e) { System.out.println("❌ ID inválido."); scanner.nextLine(); return; }
        System.out.printf("Confirma reativação do usuário ID %d? (1 = Sim / 0 = Não): ", id);
        if (scanner.nextLine().trim().equals("1")) {
            usuarioDAO.reativar(id);
            logAuditoriaDAO.registrar(new LogAuditoria(usuarioLogado.getId(), "usuarios", id, "UPDATE", "Usuário ID " + id + " reativado."));
        } else { System.out.println("↩️ Cancelado."); }
    }

    private static void alterarPerfilUsuario() {
        System.out.println("\n--- ALTERAR PERFIL DE USUÁRIO ---");
        listarUsuarios();

        System.out.print("\nID do usuário a alterar: ");
        int id;
        try { id = scanner.nextInt(); scanner.nextLine(); }
        catch (Exception e) { System.out.println("❌ ID inválido."); scanner.nextLine(); return; }

        if (id == usuarioLogado.getId()) {
            System.out.println("🚫 Você não pode alterar o seu próprio perfil!");
            return;
        }

        System.out.println("Novo perfil:  1. PADRAO  2. ADMIN");
        System.out.print("Escolha: ");
        String escolha = scanner.nextLine().trim();

        if (!escolha.equals("1") && !escolha.equals("2")) {
            System.out.println("❌ Opção inválida. Operação cancelada.");
            return;
        }

        String novoPerfil = escolha.equals("2") ? "ADMIN" : "PADRAO";

        System.out.printf("Confirma alteração do perfil do usuário ID %d para %s? (1 = Sim / 0 = Não): ", id, novoPerfil);
        if (scanner.nextLine().trim().equals("1")) {
            boolean alterado = usuarioDAO.alterarPerfil(id, novoPerfil);
            if (alterado) {
                logAuditoriaDAO.registrar(new LogAuditoria(
                        usuarioLogado.getId(), "usuarios", id, "UPDATE",
                        "Perfil do usuário ID " + id + " alterado para: " + novoPerfil
                ));
            }
        } else {
            System.out.println("↩️ Operação cancelada.");
        }
    }

    // ==========================================
    // 📊 MÓDULO: AUDITORIA  [somente ADMIN]
    // ==========================================
    private static void menuAuditoria() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- LOGS DE AUDITORIA [ADMIN] ---");
            System.out.println("1. Listar todos os logs");
            System.out.println("2. Filtrar por Entidade (ex: clientes, veiculos, ordens_servico)");
            System.out.println("3. Filtrar por Tipo de Operação (INSERT, UPDATE, DELETE)");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha: ");
            try { opcao = scanner.nextInt(); scanner.nextLine(); }
            catch (Exception e) { scanner.nextLine(); opcao = -1; continue; }
            switch (opcao) {
                case 1: listarTodosLogs(); break;
                case 2: filtrarLogsPorEntidade(); break;
                case 3: filtrarLogsPorOperacao(); break;
                case 0: System.out.println("Voltando..."); break;
                default: System.out.println("Opção inválida!"); break;
            }
        }
    }

    private static void imprimirLog(LogAuditoria log) {
        System.out.printf("ID: %d | Data: %s | Usuário ID: %d | Entidade: %-20s | Registro ID: %d | Operação: %-6s\n",
                log.getId(), log.getDataAlteracao(), log.getUsuarioId(),
                log.getEntidadeAfetada(), log.getRegistroId(), log.getTipoOperacao());
        System.out.println("   Descrição: " + log.getDescricaoMudanca());
        System.out.println("   " + "-".repeat(80));
    }

    private static void listarTodosLogs() {
        System.out.println("\n--- TODOS OS LOGS DE AUDITORIA ---");
        List<LogAuditoria> lista = logAuditoriaDAO.listarTodos();
        if (lista.isEmpty()) { System.out.println("Nenhum log registrado."); }
        else { for (LogAuditoria log : lista) imprimirLog(log); }
    }

    private static void filtrarLogsPorEntidade() {
        System.out.println("\n--- FILTRAR POR ENTIDADE ---");
        System.out.println("Entidades disponíveis: clientes, colaboradores, servicos, veiculos, ordens_servico, pecas, usuarios, os_servicos_prestados, os_pecas_utilizadas");
        System.out.print("Digite a entidade: "); String entidade = scanner.nextLine().trim();
        List<LogAuditoria> lista = logAuditoriaDAO.listarPorEntidade(entidade);
        if (lista.isEmpty()) { System.out.println("❌ Nenhum log encontrado para a entidade: " + entidade); }
        else { System.out.println("\n--- LOGS ENCONTRADOS ---"); for (LogAuditoria log : lista) imprimirLog(log); }
    }

    private static void filtrarLogsPorOperacao() {
        System.out.println("\n--- FILTRAR POR TIPO DE OPERAÇÃO ---");
        System.out.println("Tipos: INSERT, UPDATE, DELETE");
        System.out.print("Digite o tipo: "); String tipo = scanner.nextLine().trim().toUpperCase();
        List<LogAuditoria> lista = logAuditoriaDAO.listarPorTipoOperacao(tipo);
        if (lista.isEmpty()) { System.out.println("❌ Nenhum log encontrado para o tipo: " + tipo); }
        else { System.out.println("\n--- LOGS ENCONTRADOS ---"); for (LogAuditoria log : lista) imprimirLog(log); }
    }
}