package model;

public class Usuario {

    private int id;
    private String nome;
    private String cpf;
    private String senha;
    private String salt;
    private String nivelAcesso;
    private String perguntaSecreta;
    private String respostaSecreta;

    public Usuario(int id,
                   String nome,
                   String cpf,
                   String senha,
                   String salt,
                   String nivelAcesso,
                   String perguntaSecreta,
                   String respostaSecreta) {

        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
        this.salt = salt;
        this.nivelAcesso = nivelAcesso;
        this.perguntaSecreta = perguntaSecreta;
        this.respostaSecreta = respostaSecreta;
    }

    public Usuario(int id, String nome, String cpf, String senhaHash, String salt, String nivelAcesso) {
        this(id, nome, cpf, senhaHash, salt, nivelAcesso, null, null);
    }

    public Usuario(int id, String nome, String cpf, String senhaHash, String salt) {
        this(id, nome, cpf, senhaHash, salt, null, null, null);
    }

    public Usuario(int id, String nome, String cpf, String senha, String nivelAcesso, String perguntaSecreta, String respostaSecreta) {
        this(id, nome, cpf, senha, null, nivelAcesso, perguntaSecreta, respostaSecreta);
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

    public String getPerguntaSecreta() {
        return perguntaSecreta;
    }

    public String getRespostaSecreta() {
        return respostaSecreta;
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

    public void setPerguntaSecreta(String perguntaSecreta) {
        this.perguntaSecreta = perguntaSecreta;
    }

    public void setRespostaSecreta(String respostaSecreta) {
        this.respostaSecreta = respostaSecreta;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                "\nNome: " + nome +
                "\nCPF: " + cpf +
                "\nNível de Acesso: " + nivelAcesso;
    }
}