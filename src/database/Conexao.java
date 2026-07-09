package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    public static Connection conectar() {

        try {

            Connection conexao = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/controlador_acesso",
                    "root",
                    "271121");

            System.out.println("Conexão realizada com sucesso!");

            return conexao;

        } catch (SQLException e) {

            System.out.println("Erro ao conectar ao banco.");
            System.out.println(e.getMessage());

            return null;
        }
    }
}