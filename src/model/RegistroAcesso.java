package model;

import java.time.LocalDateTime;

public class RegistroAcesso {

    private int usuarioId;
    private String nomeUsuario;
    private LocalDateTime dataHora;
    private String tipo;


    public RegistroAcesso(int usuarioId, LocalDateTime dataHora, String tipo) {

        this.usuarioId = usuarioId;
        this.dataHora = dataHora;
        this.tipo = tipo;
    }

    public RegistroAcesso(String nomeUsuario, LocalDateTime dataHora, String tipo) {

    this.nomeUsuario = nomeUsuario;
    this.dataHora = dataHora;
    this.tipo = tipo;
}


    public int getUsuarioId() {
        return usuarioId;
    }

    public String getNomeUsuario() {
    return nomeUsuario;
    }


    public LocalDateTime getDataHora() {
        return dataHora;
    }


    public String getTipo() {
        return tipo;
    }
}