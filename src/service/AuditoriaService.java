package service;

import model.Auditoria;
import repository.AuditoriaRepository;

import java.time.LocalDateTime;

public class AuditoriaService {

    private AuditoriaRepository repository;

    public AuditoriaService() {

        repository = new AuditoriaRepository();
    }

    public void registrar(String usuario, String acao) {

        Auditoria auditoria = new Auditoria(
                usuario,
                acao,
                LocalDateTime.now()
        );

        repository.registrar(auditoria);
    }
}