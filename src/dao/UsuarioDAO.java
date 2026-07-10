package dao;

import database.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import security.Criptografia;

public class UsuarioDAO {


    public boolean login(String cpf, String senhaDigitada) {


        String sql = """
                SELECT nome, nivel_acesso, senha_hash, salt
                FROM usuarios
                WHERE cpf = ?
                """;


        try {


            Connection conexao = Conexao.conectar();


            PreparedStatement ps =
                    conexao.prepareStatement(sql);


            ps.setString(1, cpf);


            ResultSet rs = ps.executeQuery();



            if (rs.next()) {


                String nome =
                        rs.getString("nome");


                String nivel =
                        rs.getString("nivel_acesso");


                String hash =
                        rs.getString("senha_hash");


                String salt =
                        rs.getString("salt");



                boolean correto =
                        Criptografia.verificarSenha(
                                senhaDigitada,
                                hash,
                                salt
                        );



                if (correto) {

                    System.out.println(
                            "Login autorizado!"
                    );

                    System.out.println(
                            "Usuário: " + nome
                    );

                    System.out.println(
                            "Nível: " + nivel
                    );

                    return true;
                }

            }


            System.out.println(
                    "CPF ou senha incorretos!"
            );


        } catch(Exception e) {

            e.printStackTrace();

        }


        return false;
    }
}