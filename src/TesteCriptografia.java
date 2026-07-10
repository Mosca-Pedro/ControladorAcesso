import security.Criptografia;

public class TesteCriptografia {

    public static void main(String[] args) throws Exception {


        String senha = "Pedro123";


        String salt = Criptografia.gerarSalt();


        String hash = Criptografia.criptografarSenha(
                senha,
                salt
        );


        System.out.println("Salt:");
        System.out.println(salt);


        System.out.println("\nHash:");
        System.out.println(hash);



        boolean correto =
                Criptografia.verificarSenha(
                        "Pedro123",
                        hash,
                        salt
                );


        System.out.println("\nSenha correta?");
        System.out.println(correto);

    }
}
