import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class SegurancaSenha {

    public static String gerarSalt() {

        byte[] salt = new byte[16];

        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);

        return Base64.getEncoder()
                .encodeToString(salt);
    }

    public static String gerarHash(
            String senha,
            String saltBase64
    ) {

        try {

            byte[] salt = Base64.getDecoder()
                    .decode(saltBase64);


            MessageDigest md = MessageDigest
                    .getInstance("SHA-256");


            md.update(salt);


            byte[] hash = md.digest(
                    senha.getBytes()
            );


            return Base64.getEncoder()
                    .encodeToString(hash);


        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }


    public static boolean verificarSenha(
            String senhaDigitada,
            String hashBanco,
            String saltBanco
    ) {


        String hashGerado = gerarHash(
                senhaDigitada,
                saltBanco
        );


        System.out.println("Hash gerado:");
        System.out.println(hashGerado);


        System.out.println("Hash banco:");
        System.out.println(hashBanco);


        return hashGerado.equals(hashBanco);
    }
}