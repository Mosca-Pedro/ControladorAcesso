package repository;

import database.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Auditoria;

public class AuditoriaRepository {

    public void registrar(Auditoria auditoria) {

        String sql = """
                INSERT INTO auditoria
                (usuario, acao, data_hora)
                VALUES (?, ?, ?)
                """;

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, auditoria.getUsuario());
            stmt.setString(2, auditoria.getAcao());
            stmt.setObject(3, auditoria.getDataHora());

            stmt.executeUpdate();

        } catch (SQLException e) {

            System.out.println("Erro ao registrar auditoria.");
            System.out.println(e.getMessage());
        }
    }

    public void listarAuditoria() {

    String sql = """
            SELECT usuario, acao, data_hora
            FROM auditoria
            ORDER BY data_hora DESC
            """;

    try (Connection conexao = Conexao.conectar();
         PreparedStatement stmt = conexao.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        System.out.println("\n=================================");
        System.out.println("         AUDITORIA");
        System.out.println("=================================");

        while (rs.next()) {

            System.out.println("Usuário : " + rs.getString("usuario"));
            System.out.println("Ação    : " + rs.getString("acao"));
            System.out.println("Data    : " + rs.getTimestamp("data_hora"));
            System.out.println("---------------------------------");
        }

    } catch (SQLException e) {

        System.out.println("Erro ao listar auditoria.");
        System.out.println(e.getMessage());
    }
}

}