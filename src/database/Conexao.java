package database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexao {

    public static Connection conectar() {

        try {

            Properties props = new Properties();

            FileInputStream arquivo = new FileInputStream("config.properties");

            props.load(arquivo);


            String url = props.getProperty("db.url");
            String usuario = props.getProperty("db.usuario");
            String senha = props.getProperty("db.senha");


            Connection conexao = DriverManager.getConnection(
                    url,
                    usuario,
                    senha
            );


            System.out.println("Conexão realizada com sucesso!");

            return conexao;


        } catch (SQLException | IOException e) {

            System.out.println("Erro ao conectar ao banco.");
            System.out.println(e.getMessage());

            return null;
        }
    }
}