package model;

import java.time.LocalDateTime;

public class Auditoria {

    private String usuario;
    private String acao;
    private LocalDateTime dataHora;

    public Auditoria(String usuario, String acao, LocalDateTime dataHora) {

        this.usuario = usuario;
        this.acao = acao;
        this.dataHora = dataHora;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getAcao() {
        return acao;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }
}