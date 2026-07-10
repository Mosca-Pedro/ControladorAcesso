import dao.UsuarioDAO;

public class TesteLoginBanco {


    public static void main(String[] args) {


        UsuarioDAO usuarioDAO =
                new UsuarioDAO();


        usuarioDAO.login(
                "2939948839",
                "3456"
        );

    }
}