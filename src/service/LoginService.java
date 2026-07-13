package service;

import model.Usuario;
import repository.BancoDados;

public class LoginService {

    private BancoDados bancoDados;

    public LoginService() {
        bancoDados = new BancoDados();
    }


    public Usuario login(int id, String senha) {

        Usuario usuario = bancoDados.fazerLogin(id, senha);

        if (usuario != null) {

            System.out.println("Login autorizado!");

            return usuario;

        } else {

            System.out.println("Usuário ou senha incorretos.");

            return null;
        }
    }
}