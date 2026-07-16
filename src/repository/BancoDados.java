package repository;

import database.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Usuario;
import security.Criptografia;

public class BancoDados {

    public BancoDados() {
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
                    rs.getString("senha_hash"),
                    rs.getString("salt"));

            listaUsuarios.add(usuario);
        }

    } catch (SQLException e) {

        System.out.println("Erro ao listar usuários.");
        System.out.println(e.getMessage());

    }

    return listaUsuarios;
}
    
    public Usuario fazerLogin(int id, String senha) {

    String sql = "SELECT * FROM usuarios WHERE id = ?";

    try (Connection conexao = Conexao.conectar();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {

        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {

            String senhaHashBanco = rs.getString("senha_hash");
            String salt = rs.getString("salt");

            boolean senhaCorreta =
                    Criptografia.verificarSenha(
                            senha,
                            senhaHashBanco,
                            salt);


            if (senhaCorreta) {

                return new Usuario(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("cpf"),
                    rs.getString("senha_hash"),
                    rs.getString("salt"),
                    rs.getString("nivel_acesso")
                ); 
              
            }  
    }

    } catch (SQLException e) {

        System.out.println("Erro ao realizar login.");
        System.out.println(e.getMessage());

    }

    return null;
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
                    rs.getString("senha_hash"),
                    rs.getString("salt"));
        }

    } catch (SQLException e) {

        System.out.println("Erro ao buscar usuário.");
        System.out.println(e.getMessage());

    }

    return null;
}

    public void cadastrarUsuario(Usuario usuario) {

    String sql = """
        INSERT INTO usuarios 
        (id, nome, cpf, senha_hash, salt, nivel_acesso, pergunta_secreta, resposta_secreta)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

    String salt = Criptografia.gerarSalt();

    String senhaCriptografada = Criptografia.criptografarSenha(
            usuario.getSenha(),
            salt);

    try (Connection conexao = Conexao.conectar();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {

        stmt.setInt(1, usuario.getId());
        stmt.setString(2, usuario.getNome());
        stmt.setString(3, usuario.getCpf());
        stmt.setString(4, senhaCriptografada);
        stmt.setString(5, salt);
        stmt.setString(6, usuario.getNivelAcesso());
        stmt.setString(7, usuario.getPerguntaSecreta());
        stmt.setString(8, usuario.getRespostaSecreta());

        stmt.executeUpdate();

        System.out.println("Usuário cadastrado com sucesso no MySQL!");

    } catch (SQLException e) {

        System.out.println("Erro ao cadastrar usuário.");
        System.out.println(e.getMessage());

    }
}

public boolean editarUsuario(Usuario usuario) {

    String sql = "UPDATE usuarios SET nome = ?, cpf = ?, senha_hash = ?, salt = ?, nivel_acesso = ? WHERE id = ?";

    String salt = Criptografia.gerarSalt();

    String senhaCriptografada = Criptografia.criptografarSenha(
        usuario.getSenha(),
        salt);

    try (Connection conexao = Conexao.conectar();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {

        stmt.setString(1, usuario.getNome());
        stmt.setString(2, usuario.getCpf());
        stmt.setString(3, senhaCriptografada);
        stmt.setString(4, salt);
        stmt.setString(5, usuario.getNivelAcesso());
        stmt.setInt(6, usuario.getId());
        
        int linhasAfetadas = stmt.executeUpdate();

        return linhasAfetadas > 0;

    } catch (SQLException e) {

        System.out.println("Erro ao editar usuário.");
        System.out.println(e.getMessage());

    }

    return false;
}

public boolean excluirUsuario(int id) {

    String sql = "DELETE FROM usuarios WHERE id = ?";

    try (Connection conexao = Conexao.conectar();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {

        stmt.setInt(1, id);

        int linhasAfetadas = stmt.executeUpdate();

        return linhasAfetadas > 0;

    } catch (SQLException e) {

        System.out.println("Erro ao excluir usuário.");
        System.out.println(e.getMessage());

    }

    return false;
}

public boolean existeId(int id) {

    String sql = "SELECT id FROM usuarios WHERE id = ?";

    try (Connection conexao = Conexao.conectar();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {

        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();

        return rs.next();

    } catch (SQLException e) {

        System.out.println("Erro ao verificar ID.");
        System.out.println(e.getMessage());

    }

    return false;
}

public boolean existeAdministrador() {

    String sql = """
            SELECT COUNT(*)
            FROM usuarios
            WHERE nivel_acesso = 'Administrador'
            """;

    try (Connection conexao = Conexao.conectar();
         PreparedStatement stmt = conexao.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        if (rs.next()) {
            return rs.getInt(1) > 0;
        }

    } catch (SQLException e) {

        System.out.println("Erro ao verificar administradores.");
        System.out.println(e.getMessage());
    }

    return false;
}

public boolean existeCpf(String cpf) {

    String sql = "SELECT cpf FROM usuarios WHERE cpf = ?";

    try (Connection conexao = Conexao.conectar();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {

        stmt.setString(1, cpf);

        ResultSet rs = stmt.executeQuery();

        return rs.next();

    } catch (SQLException e) {

        System.out.println("Erro ao verificar CPF.");
        System.out.println(e.getMessage());

    }

    return false;
}

    public boolean redefinirSenha(String cpf, String novaSenha) {

    String novoSalt = Criptografia.gerarSalt();
    String senhaHash = Criptografia.criptografarSenha(novaSenha, novoSalt);

    String sql = """
            UPDATE usuarios
            SET senha_hash = ?, salt = ?
            WHERE cpf = ?
            """;

    try (Connection conexao = Conexao.conectar();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {

        stmt.setString(1, senhaHash);
        stmt.setString(2, novoSalt);
        stmt.setString(3, cpf);

        return stmt.executeUpdate() > 0;

    } catch (SQLException e) {

        System.out.println("Erro ao redefinir senha.");
        System.out.println(e.getMessage());
    }

    return false;
}

}

