package security;

import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Criptografia {

    private static final int ITERACOES = 65536;
    private static final int TAMANHO_HASH = 256;
    private static final int TAMANHO_SALT = 16;


    public static String gerarSalt() {

        byte[] salt = new byte[TAMANHO_SALT];

        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);

        return Base64.getEncoder().encodeToString(salt);
    }


    public static String criptografarSenha(String senha, String salt) throws Exception {

        byte[] saltBytes = Base64.getDecoder().decode(salt);


        PBEKeySpec spec = new PBEKeySpec(
                senha.toCharArray(),
                saltBytes,
                ITERACOES,
                TAMANHO_HASH
        );


        SecretKeyFactory factory =
                SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");


        byte[] hash = factory
                .generateSecret(spec)
                .getEncoded();


        return Base64.getEncoder().encodeToString(hash);
    }


    public static boolean verificarSenha(
            String senhaDigitada,
            String senhaBanco,
            String salt
    ) throws Exception {


        String novoHash = criptografarSenha(
                senhaDigitada,
                salt
        );


        return novoHash.equals(senhaBanco);
    }
}