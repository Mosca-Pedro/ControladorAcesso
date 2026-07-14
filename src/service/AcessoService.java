package service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import model.RegistroAcesso;
import repository.AcessoRepository;


public class AcessoService {

    private AcessoRepository repository;


    public AcessoService() {

        repository = new AcessoRepository();

    }


    public void registrarEntrada(int usuarioId) {

        RegistroAcesso acesso =
            new RegistroAcesso(
                    usuarioId,
                    LocalDateTime.now(),
                    "ENTRADA"
            );

      repository.registrarAcesso(acesso);
    }

    public void registrarSaida(int usuarioId) {


    RegistroAcesso acesso =
            new RegistroAcesso(
                    usuarioId,
                    LocalDateTime.now(),
                    "SAIDA"
            );


   repository.registrarAcesso(acesso);
}
    public ArrayList<RegistroAcesso> listarAcessos() {

    return repository.listarAcessos();

}

}