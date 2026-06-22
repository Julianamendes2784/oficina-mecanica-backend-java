package br.juliana;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Peca {

    // ================= ATRIBUTOS =================
    private int id;
    private String nome;
    private double precoVenda;
    private int estoque;

    // ================= CONSTRUTORES =================
    public Peca(String nome, double precoVenda, int estoque) {
        this.nome = nome;
        this.precoVenda = precoVenda;
        this.estoque = estoque;
    }

    public Peca(int id, String nome, double precoVenda, int estoque) {
        this.id = id;
        this.nome = nome;
        this.precoVenda = precoVenda;
        this.estoque = estoque;
    }

    // ================= GETTERS E SETTERS =================
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public double getPrecoVenda() { return precoVenda; }
    public void setPrecoVenda(double precoVenda) { this.precoVenda = precoVenda; }

    public int getEstoque() { return estoque; }
    public void setEstoque(int estoque) { this.estoque = estoque; }

    // ================= TOSTRING =================
    @Override
    public String toString() {
        return "Peça [id=" + id + ", nome=" + nome +
                ", preço=R$" + precoVenda + ", estoque=" + estoque + "]";
    }

    // ================= DAO =================
    public static void cadastrar(Connection connection, Peca peca) throws SQLException {
        String sql = "INSERT INTO pecas (nome, preco_venda, estoque) VALUES (?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, peca.getNome());
        stmt.setDouble(2, peca.getPrecoVenda());
        stmt.setInt(3, peca.getEstoque());
        stmt.executeUpdate();
        stmt.close();
    }

    public static List<Peca> listar(Connection connection) throws SQLException {
        List<Peca> lista = new ArrayList<>();
        String sql = "SELECT * FROM pecas";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            lista.add(new Peca(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getDouble("preco_venda"),
                    rs.getInt("estoque")
            ));
        }
        stmt.close();
        return lista;
    }

    public static Peca buscarPorId(Connection connection, int id) throws SQLException {
        String sql = "SELECT * FROM pecas WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Peca p = new Peca(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getDouble("preco_venda"),
                    rs.getInt("estoque")
            );
            stmt.close();
            return p;
        }
        stmt.close();
        return null;
    }

    public static void atualizar(Connection connection, Peca peca) throws SQLException {
        String sql = "UPDATE pecas SET nome=?, preco_venda=?, estoque=? WHERE id=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, peca.getNome());
        stmt.setDouble(2, peca.getPrecoVenda());
        stmt.setInt(3, peca.getEstoque());
        stmt.setInt(4, peca.getId());
        stmt.executeUpdate();
        stmt.close();
    }

    public static void excluir(Connection connection, int id) throws SQLException {
        String sql = "DELETE FROM pecas WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
        stmt.close();
    }

    // ================= MENU =================
    public static void menuPecas(Scanner sc, Connection connection) {
        int opcao;
        do {
            System.out.println("\n--- MENU PEÇAS ---");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar");
            System.out.println("3 - Buscar");
            System.out.println("4 - Atualizar");
            System.out.println("5 - Excluir");
            System.out.println("0 - Voltar");
            opcao = sc.nextInt();
            sc.nextLine();

            try {
                switch (opcao) {
                    case 1:
                        System.out.print("Nome da peça: ");
                        String nome = sc.nextLine();
                        System.out.print("Preço de venda: ");
                        double preco = sc.nextDouble();
                        System.out.print("Estoque: ");
                        int estoque = sc.nextInt();
                        sc.nextLine();
                        cadastrar(connection, new Peca(nome, preco, estoque));
                        System.out.println("Peça cadastrada com sucesso!");
                        break;

                    case 2:
                        List<Peca> lista = listar(connection);
                        if (lista.isEmpty()) {
                            System.out.println("Nenhuma peça cadastrada.");
                        } else {
                            lista.forEach(System.out::println);
                        }
                        break;

                    case 3:
                        System.out.print("ID da peça: ");
                        int idBusca = sc.nextInt();
                        sc.nextLine();
                        Peca encontrada = buscarPorId(connection, idBusca);
                        if (encontrada != null) {
                            System.out.println(encontrada);
                        } else {
                            System.out.println("Peça não encontrada.");
                        }
                        break;

                    case 4:
                        System.out.print("ID da peça a atualizar: ");
                        int idAtualizar = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Novo nome: ");
                        String novoNome = sc.nextLine();
                        System.out.print("Novo preço: ");
                        double novoPreco = sc.nextDouble();
                        System.out.print("Nova quantidade: ");
                        int novaQtd = sc.nextInt();
                        sc.nextLine();
                        atualizar(connection, new Peca(idAtualizar, novoNome, novoPreco, novaQtd));
                        System.out.println("Peça atualizada com sucesso!");
                        break;

                    case 5:
                        System.out.print("ID da peça a excluir: ");
                        int idExcluir = sc.nextInt();
                        sc.nextLine();
                        excluir(connection, idExcluir);
                        System.out.println("Peça excluída com sucesso!");
                        break;

                    case 0:
                        System.out.println("Voltando...");
                        break;

                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (SQLException e) {
                System.out.println("Erro no banco de dados: " + e.getMessage());
            }
        } while (opcao != 0);
    }
}