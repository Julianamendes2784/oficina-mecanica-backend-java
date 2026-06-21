package br.juliana.model;

public class Usuario {
    private int id;
    private String login;
    private String senha;
    private int colaboradorId;


    public Usuario() {}


    public Usuario(int id, String login, String senha, int colaboradorId) {
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.colaboradorId = colaboradorId;
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public int getColaboradorId() { return colaboradorId; }
    public void setColaboradorId(int colaboradorId) { this.colaboradorId = colaboradorId; }


    public static UsuarioBuilder builder() {
        return new UsuarioBuilder();
    }

    public static class UsuarioBuilder {
        private int id;
        private String login;
        private String senha;
        private int colaboradorId;

        public UsuarioBuilder id(int id) { this.id = id; return this; }
        public UsuarioBuilder login(String login) { this.login = login; return this; }
        public UsuarioBuilder senha(String senha) { this.senha = senha; return this; }
        public UsuarioBuilder colaboradorId(int colaboradorId) { this.colaboradorId = colaboradorId; return this; }

        public Usuario build() {
            return new Usuario(id, login, senha, colaboradorId);
        }
    }
}