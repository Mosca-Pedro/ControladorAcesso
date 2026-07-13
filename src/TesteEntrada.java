import service.AcessoService;

public class TesteEntrada {

    public static void main(String[] args) {

        AcessoService acessoService = new AcessoService();

        acessoService.registrarEntrada(1);
        acessoService.registrarEntrada(1);

    }
}