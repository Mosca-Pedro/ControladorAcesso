import java.util.Scanner;
import repository.BancoDados;
import ui.Menu;
public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        BancoDados banco = new BancoDados();

        Menu menu = new Menu();

        menu.exibirMenu();
    }

}