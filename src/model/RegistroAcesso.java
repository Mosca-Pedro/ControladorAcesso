package model;

import java.time.LocalDateTime;

public class RegistroAcesso {

    private int usuarioId;
    private LocalDateTime dataHora;
    private String tipo;


    public RegistroAcesso(int usuarioId, LocalDateTime dataHora, String tipo) {

        this.usuarioId = usuarioId;
        this.dataHora = dataHora;
        this.tipo = tipo;
    }


    public int getUsuarioId() {
        return usuarioId;
    }


    public LocalDateTime getDataHora() {
        return dataHora;
    }


    public String getTipo() {
        return tipo;
    }
}