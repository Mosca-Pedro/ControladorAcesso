package repository;

import database.Conexao;
import model.Auditoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}