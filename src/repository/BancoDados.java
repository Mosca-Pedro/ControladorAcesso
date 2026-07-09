package repository;

import database.Conexao;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Usuario;

public class BancoDados {

    private ArrayList<Usuario> usuarios;

    public BancoDados() {
        usuarios = new ArrayList<>();
        carregarUsuariosDoArquivo();
    }

    private void carregarUsuariosDoArquivo() {

    try {

        BufferedReader reader = new BufferedReader(new FileReader("usuarios.txt"));

        String linha;

        while ((linha = reader.readLine()) != null) {

            String[] dados = linha.split(";");

            int id = Integer.parseInt(dados[0]);
            String nome = dados[1];
            String cpf = dados[2];
            String senha = dados[3];
            String nivel = dados[4];

            Usuario usuario = new Usuario(id, nome, cpf, senha, nivel);

            usuarios.add(usuario);
        }

        reader.close();

    } catch (Exception e) {
        System.out.println("Nenhum usuário para carregar.");
    }
}

    public void cadastrarUsuario(Usuario usuario) {

    String sql = "INSERT INTO usuarios (id, nome, cpf, senha, nivel_acesso) VALUES (?, ?, ?, ?, ?)";

    try (Connection conexao = Conexao.conectar();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {

        stmt.setInt(1, usuario.getId());
        stmt.setString(2, usuario.getNome());
        stmt.setString(3, usuario.getCpf());
        stmt.setString(4, usuario.getSenha());
        stmt.setString(5, usuario.getNivelAcesso());

        stmt.executeUpdate();

        usuarios.add(usuario);

        System.out.println("Usuário cadastrado com sucesso no MySQL!");

    } catch (SQLException e) {

        System.out.println("Erro ao cadastrar usuário.");
        System.out.println(e.getMessage());

    }
}    

    public ArrayList<Usuario> listarUsuarios() {

    ArrayList<Usuario> listaUsuarios = new ArrayList<>();

    String sql = "SELECT * FROM usuarios";

    try (Connection conexao = Conexao.conectar();
         PreparedStatement stmt = conexao.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {

            Usuario usuario = new Usuario(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("cpf"),
                    rs.getString("senha"),
                    rs.getString("nivel_acesso"));

            listaUsuarios.add(usuario);

        }

    } catch (SQLException e) {

        System.out.println("Erro ao listar usuários.");
        System.out.println(e.getMessage());

    }

    return listaUsuarios;
}

    
    public Usuario fazerLogin(int id, String senha) {

    String sql = "SELECT * FROM usuarios WHERE id = ? AND senha = ?";

    try (Connection conexao = Conexao.conectar();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {

        stmt.setInt(1, id);
        stmt.setString(2, senha);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {

            Usuario usuario = new Usuario(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("cpf"),
                    rs.getString("senha"),
                    rs.getString("nivel_acesso"));

            return usuario;
        }

    } catch (SQLException e) {

        System.out.println("Erro ao realizar login.");
        System.out.println(e.getMessage());

    }

    return null;
}
private void salvarUsuarioNoArquivo(Usuario usuario) {

    try {

        BufferedWriter writer = new BufferedWriter(
                new FileWriter("usuarios.txt", true));

        writer.write(
                usuario.getId() + ";" +
                usuario.getNome() + ";" +
                usuario.getCpf() + ";" +
                usuario.getSenha() + ";" +
                usuario.getNivelAcesso());

        writer.newLine();

        writer.close();

    } catch (IOException e) {

        System.out.println("Erro ao salvar usuário.");

    }

}

public Usuario buscarUsuarioPorId(int id) {

    String sql = "SELECT * FROM usuarios WHERE id = ?";

    try (Connection conexao = Conexao.conectar();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {

        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {

            return new Usuario(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("cpf"),
                    rs.getString("senha"),
                    rs.getString("nivel_acesso"));

        }

    } catch (SQLException e) {

        System.out.println("Erro ao buscar usuário.");
        System.out.println(e.getMessage());

    }

    return null;
}

public void salvarTodosUsuarios() {

    try {

        BufferedWriter writer = new BufferedWriter(new FileWriter("usuarios.txt"));

        for (Usuario usuario : usuarios) {

            writer.write(
                usuario.getId() + ";" +
                usuario.getNome() + ";" +
                usuario.getCpf() + ";" +
                usuario.getSenha() + ";" +
                usuario.getNivelAcesso()
            );

            writer.newLine();
        }

        writer.close();

    } catch (IOException e) {

        System.out.println("Erro ao salvar usuários.");

    }
}

public boolean editarUsuario(Usuario usuario) {

    String sql = "UPDATE usuarios SET nome = ?, cpf = ?, senha = ?, nivel_acesso = ? WHERE id = ?";

    try (Connection conexao = Conexao.conectar();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {

        stmt.setString(1, usuario.getNome());
        stmt.setString(2, usuario.getCpf());
        stmt.setString(3, usuario.getSenha());
        stmt.setString(4, usuario.getNivelAcesso());
        stmt.setInt(5, usuario.getId());

        int linhasAfetadas = stmt.executeUpdate();

        return linhasAfetadas > 0;

    } catch (SQLException e) {

        System.out.println("Erro ao editar usuário.");
        System.out.println(e.getMessage());

    }

    return false;
}

public boolean excluirUsuario(int id) {

    Usuario usuario = buscarUsuarioPorId(id);

    if (usuario != null) {

        usuarios.remove(usuario);

        salvarTodosUsuarios();

        return true;
    }

    return false;
}

public boolean existeId(int id) {

    for (Usuario usuario : usuarios) {

        if (usuario.getId() == id) {
            return true;
        }

    }

    return false;
}

public boolean existeCpf(String cpf) {

    for (Usuario usuario : usuarios) {

        if (usuario.getCpf().equals(cpf)) {
            return true;
        }

    }

    return false;
}

}