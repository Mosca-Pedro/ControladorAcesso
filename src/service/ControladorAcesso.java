package service;

import model.Usuario;



public class ControladorAcesso {

    public boolean verificarAcesso(Usuario usuario, String sala) {

        String nivel = usuario.getNivelAcesso();

        if (nivel.equalsIgnoreCase("Administrador")) {
            return true;
        }

        if (nivel.equalsIgnoreCase("Funcionario")) {

            return sala.equalsIgnoreCase("Escritorio")
                    || sala.equalsIgnoreCase("Laboratorio");

        }

        if (nivel.equalsIgnoreCase("Visitante")) {

            return sala.equalsIgnoreCase("Recepcao");

        }

        return false;
    }
}