package repository;

import database.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class HistoricoRepository {


    public void salvarHistorico(String usuario, String sala, boolean permitido) {

        String sql = "INSERT INTO historico_acessos (usuario, sala, status, data_hora) VALUES (?, ?, ?, ?)";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {


            String status = permitido ? "LIBERADO" : "NEGADO";


            stmt.setString(1, usuario);
            stmt.setString(2, sala);
            stmt.setString(3, status);
            stmt.setObject(4, LocalDateTime.now());


            stmt.executeUpdate();


            System.out.println("Histórico salvo no MySQL!");


        } catch (SQLException e) {

            System.out.println("Erro ao salvar histórico.");
            System.out.println(e.getMessage());

        }
    }


    public void listarHistorico() {

        String sql = "SELECT * FROM historico_acessos";


        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {


            System.out.println("\n===== HISTÓRICO DE ACESSOS =====");


            while (rs.next()) {

                System.out.println(
                    "ID: " + rs.getInt("id") +
                    " | Usuário: " + rs.getString("usuario") +
                    " | Sala: " + rs.getString("sala") +
                    " | Status: " + rs.getString("status") +
                    " | Data: " + rs.getTimestamp("data_hora")
                );

            }


            System.out.println("================================");


        } catch (SQLException e) {

            System.out.println("Erro ao buscar histórico.");
            System.out.println(e.getMessage());

        }
    }

}