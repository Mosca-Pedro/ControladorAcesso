package repository;

import database.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import model.RegistroAcesso;



public class AcessoRepository {


   public void registrarAcesso(RegistroAcesso acesso) {

    String sql = """
            INSERT INTO registros_acesso
            (usuario_id, data_hora, tipo)
            VALUES (?, ?, ?)
            """;

    try (Connection conexao = Conexao.conectar();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {

        stmt.setInt(1, acesso.getUsuarioId());
        stmt.setObject(2, acesso.getDataHora());
        stmt.setString(3, acesso.getTipo());

        stmt.executeUpdate();

        System.out.println(acesso.getTipo() + " registrada com sucesso!");

    } catch (SQLException e) {

        System.out.println("Erro ao registrar acesso.");
        System.out.println(e.getMessage());
    }
}
    public ArrayList<RegistroAcesso> listarAcessos() {

    ArrayList<RegistroAcesso> lista = new ArrayList<>();

     String sql = """
        SELECT
            u.nome,
            r.data_hora,
            r.tipo
        FROM registros_acesso r
        INNER JOIN usuarios u
            ON r.usuario_id = u.id
        ORDER BY r.data_hora DESC
        """;

    try (Connection conexao = Conexao.conectar();
         PreparedStatement stmt = conexao.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {

            RegistroAcesso acesso = new RegistroAcesso(
                rs.getString("nome"),
                rs.getTimestamp("data_hora").toLocalDateTime(),
                rs.getString("tipo")
            );

            lista.add(acesso);
        }

    } catch (SQLException e) {

        System.out.println("Erro ao listar acessos.");
        System.out.println(e.getMessage());

    }

    return lista;
}

    public ArrayList<RegistroAcesso> listarAcessosPorUsuario(String nome) {

    ArrayList<RegistroAcesso> lista = new ArrayList<>();

    String sql = """
            SELECT
                u.nome,
                r.data_hora,
                r.tipo
            FROM registros_acesso r
            INNER JOIN usuarios u
                ON r.usuario_id = u.id
            WHERE u.nome LIKE ?
            ORDER BY r.data_hora DESC
            """;

    try (Connection conexao = Conexao.conectar();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {

        stmt.setString(1, "%" + nome + "%");

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            RegistroAcesso acesso = new RegistroAcesso(
                    rs.getString("nome"),
                    rs.getTimestamp("data_hora").toLocalDateTime(),
                    rs.getString("tipo")
            );

            lista.add(acesso);
        }

    } catch (SQLException e) {

        System.out.println("Erro ao buscar histórico por usuário.");
        System.out.println(e.getMessage());

    }

    return lista;
}

    public ArrayList<RegistroAcesso> listarAcessosPorTipo(String tipo) {

    ArrayList<RegistroAcesso> lista = new ArrayList<>();

    String sql = """
            SELECT
                u.nome,
                r.data_hora,
                r.tipo
            FROM registros_acesso r
            INNER JOIN usuarios u
                ON r.usuario_id = u.id
            WHERE r.tipo = ?
            ORDER BY r.data_hora DESC
            """;

    try (Connection conexao = Conexao.conectar();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {

        stmt.setString(1, tipo.toUpperCase());

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            RegistroAcesso acesso = new RegistroAcesso(
                    rs.getString("nome"),
                    rs.getTimestamp("data_hora").toLocalDateTime(),
                    rs.getString("tipo")
            );

            lista.add(acesso);
        }

    } catch (SQLException e) {

        System.out.println("Erro ao buscar histórico por tipo.");
        System.out.println(e.getMessage());

    }

    return lista;
}

    public ArrayList<RegistroAcesso> listarAcessosPorPeriodo(
        LocalDateTime inicio,
        LocalDateTime fim) {

    ArrayList<RegistroAcesso> lista = new ArrayList<>();

    String sql = """
            SELECT
                u.nome,
                r.data_hora,
                r.tipo
            FROM registros_acesso r
            INNER JOIN usuarios u
                ON r.usuario_id = u.id
            WHERE r.data_hora BETWEEN ? AND ?
            ORDER BY r.data_hora DESC
            """;

    try (Connection conexao = Conexao.conectar();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {

        stmt.setObject(1, inicio);
        stmt.setObject(2, fim);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            RegistroAcesso acesso = new RegistroAcesso(
                    rs.getString("nome"),
                    rs.getTimestamp("data_hora").toLocalDateTime(),
                    rs.getString("tipo")
            );

            lista.add(acesso);
        }

    } catch (SQLException e) {

        System.out.println("Erro ao buscar histórico por período.");
        System.out.println(e.getMessage());
    }

    return lista;
}

}