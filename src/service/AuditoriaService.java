package service;

import java.time.LocalDateTime;
import model.Auditoria;
import repository.AuditoriaRepository;

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

    public void listarAuditoria() {

        repository.listarAuditoria();

    }

}