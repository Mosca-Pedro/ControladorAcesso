import java.sql.*;
import security.Criptografia;

public class MigrarSenhas {

    public static void main(String[] args) throws Exception {

        Connection conexao = DriverManager.getConnection(
                
                "jdbc:mysql://localhost:3306/controlador_acesso",
                "root",
                "271121"
);


        String buscar = """
                SELECT cpf, senha
                FROM usuarios
                WHERE senha_hash IS NULL
                OR salt IS NULL
                """;


        PreparedStatement ps = conexao.prepareStatement(buscar);

        ResultSet rs = ps.executeQuery();


        while (rs.next()) {

            String cpf = rs.getString("cpf");
            String senha = rs.getString("senha");


            String salt = Criptografia.gerarSalt();

            String hash = Criptografia.criptografarSenha(
                    senha,
                    salt
            );


            PreparedStatement atualizar =
                    conexao.prepareStatement("""
                    UPDATE usuarios
                    SET senha_hash = ?,
                        salt = ?
                    WHERE cpf = ?
                    """);


            atualizar.setString(1, hash);
            atualizar.setString(2, salt);
            atualizar.setString(3, cpf);


            atualizar.executeUpdate();


            System.out.println(
                    "Migrado CPF: " + cpf
            );
        }


        conexao.close();

        System.out.println("Migração concluída!");
    }
}