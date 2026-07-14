package model;

public class Usuario {

    private int id;
    private String nome;
    private String cpf;
    private String senha;
    private String salt;
    private String nivelAcesso;


    public Usuario(int id, String nome, String cpf, String senha, String nivelAcesso) {

        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
        this.nivelAcesso = nivelAcesso;

    }


    public Usuario(int id, String nome, String cpf, String senhaHash, String salt, String nivelAcesso) {

        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senhaHash;
        this.salt = salt;
        this.nivelAcesso = nivelAcesso;

    }


    public int getId() {
        return id;
    }


    public String getNome() {
        return nome;
    }


    public String getCpf() {
        return cpf;
    }


    public String getSenha() {
        return senha;
    }


    public String getSalt() {
        return salt;
    }


    public String getNivelAcesso() {
        return nivelAcesso;
    }


    public void setNome(String nome) {

        this.nome = nome;

    }


    public void setCpf(String cpf) {

        this.cpf = cpf;

    }


    public void setSenha(String senha) {

        this.senha = senha;

    }


    public void setSalt(String salt) {

        this.salt = salt;

    }


    public void setNivelAcesso(String nivelAcesso) {

        this.nivelAcesso = nivelAcesso;

    }


    @Override
    public String toString() {

        return "ID: " + id +
                "\nNome: " + nome +
                "\nCPF: " + cpf +
                "\nNível de Acesso: " + nivelAcesso;

    }

}