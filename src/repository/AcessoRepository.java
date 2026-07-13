package repository;

import database.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.RegistroAcesso;


public class AcessoRepository {


    public void registrarEntrada(RegistroAcesso acesso) {


        String sql = """
                INSERT INTO registros_acesso
                (usuario_id, data_hora, tipo)
                VALUES (?, ?, ?)
                """;


        try(Connection conexao = Conexao.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql)) {


            stmt.setInt(1, acesso.getUsuarioId());

            stmt.setObject(2, acesso.getDataHora());

            stmt.setString(3, acesso.getTipo());


            stmt.executeUpdate();


            System.out.println("Entrada registrada com sucesso!");


        } catch(SQLException e) {

            System.out.println("Erro ao registrar entrada.");
            System.out.println(e.getMessage());
        }
    }

    public void registrarSaida(RegistroAcesso acesso) {


    String sql = """
            INSERT INTO registros_acesso
            (usuario_id, data_hora, tipo)
            VALUES (?, ?, ?)
            """;


    try(Connection conexao = Conexao.conectar();
        PreparedStatement stmt = conexao.prepareStatement(sql)) {


        stmt.setInt(1, acesso.getUsuarioId());

        stmt.setObject(2, acesso.getDataHora());

        stmt.setString(3, acesso.getTipo());


        stmt.executeUpdate();


        System.out.println("Saída registrada com sucesso!");


    } catch(SQLException e) {

        System.out.println("Erro ao registrar saída.");
        System.out.println(e.getMessage());
    }
}
}