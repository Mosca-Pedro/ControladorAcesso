package repository;

import database.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Dashboard;

public class DashboardRepository {

    public Dashboard obterDashboard() {

        String sqlTotalUsuarios =
                "SELECT COUNT(*) FROM usuarios";

        String sqlAdministradores =
                "SELECT COUNT(*) FROM usuarios WHERE nivel_acesso = 'Administrador'";

        String sqlFuncionarios =
                "SELECT COUNT(*) FROM usuarios WHERE nivel_acesso = 'Funcionario'";

        String sqlVisitantes =
                "SELECT COUNT(*) FROM usuarios WHERE nivel_acesso = 'Visitante'";

        String sqlEntradas =
                "SELECT COUNT(*) FROM registros_acesso WHERE tipo = 'ENTRADA'";

        String sqlSaidas =
                "SELECT COUNT(*) FROM registros_acesso WHERE tipo = 'SAIDA'";

        String sqlHoje =
                """
                SELECT COUNT(*)
                FROM registros_acesso
                WHERE DATE(data_hora) = CURDATE()
                """;

        try (Connection conexao = Conexao.conectar()) {

            int totalUsuarios = executarContagem(conexao, sqlTotalUsuarios);
            int administradores = executarContagem(conexao, sqlAdministradores);
            int funcionarios = executarContagem(conexao, sqlFuncionarios);
            int visitantes = executarContagem(conexao, sqlVisitantes);
            int entradas = executarContagem(conexao, sqlEntradas);
            int saidas = executarContagem(conexao, sqlSaidas);
            int acessosHoje = executarContagem(conexao, sqlHoje);

            return new Dashboard(
                    totalUsuarios,
                    administradores,
                    funcionarios,
                    visitantes,
                    entradas,
                    saidas,
                    acessosHoje
            );

        } catch (SQLException e) {

            System.out.println("Erro ao carregar dashboard.");
            System.out.println(e.getMessage());

        }

        return null;
    }

    private int executarContagem(Connection conexao, String sql) {

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

        return 0;
    }

    public String usuarioComMaisAcessos() {

    String sql = """
            SELECT u.nome, COUNT(*) AS total
            FROM registros_acesso r
            INNER JOIN usuarios u
                ON r.usuario_id = u.id
            GROUP BY u.nome
            ORDER BY total DESC
            LIMIT 1
            """;

    try (Connection conexao = Conexao.conectar();
         PreparedStatement stmt = conexao.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        if (rs.next()) {
            return rs.getString("nome") + " (" + rs.getInt("total") + " acessos)";
        }

    } catch (SQLException e) {

        System.out.println("Erro ao buscar usuário com mais acessos.");
        System.out.println(e.getMessage());
    }

    return "Nenhum acesso registrado";
}

public String ultimoAcesso() {

    String sql = """
            SELECT u.nome, r.data_hora, r.tipo
            FROM registros_acesso r
            INNER JOIN usuarios u
                ON r.usuario_id = u.id
            ORDER BY r.data_hora DESC
            LIMIT 1
            """;

    try (Connection conexao = Conexao.conectar();
         PreparedStatement stmt = conexao.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        if (rs.next()) {

            return rs.getString("nome") +
                   " - " +
                   rs.getString("tipo") +
                   " - " +
                   rs.getTimestamp("data_hora").toLocalDateTime();
        }

    } catch (SQLException e) {

        System.out.println("Erro ao buscar último acesso.");
        System.out.println(e.getMessage());
    }

    return "Nenhum acesso registrado";
}

}