import security.Criptografia;

public class TesteLogin {

    public static void main(String[] args) throws Exception {


        String senhaDigitada = "3456";


        String hashBanco =
        "ohEQOMjaW2ZhS1SDun8H2wiaYCKkjslCQy7x7ZvYdjA=";


        String saltBanco =
        "CxQffUXy8CoZ6oy+Xr2OXA==";



        boolean resultado =
                Criptografia.verificarSenha(
                        senhaDigitada,
                        hashBanco,
                        saltBanco
                );


        System.out.println("Senha correta?");
        System.out.println(resultado);

    }
}