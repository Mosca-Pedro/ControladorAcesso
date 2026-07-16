package model;

public class Dashboard {

    private int totalUsuarios;
    private int administradores;
    private int funcionarios;
    private int visitantes;
    private int totalEntradas;
    private int totalSaidas;
    private int acessosHoje;

    public Dashboard(int totalUsuarios,
                     int administradores,
                     int funcionarios,
                     int visitantes,
                     int totalEntradas,
                     int totalSaidas,
                     int acessosHoje) {

        this.totalUsuarios = totalUsuarios;
        this.administradores = administradores;
        this.funcionarios = funcionarios;
        this.visitantes = visitantes;
        this.totalEntradas = totalEntradas;
        this.totalSaidas = totalSaidas;
        this.acessosHoje = acessosHoje;
    }

    public int getTotalUsuarios() {
        return totalUsuarios;
    }

    public int getAdministradores() {
        return administradores;
    }

    public int getFuncionarios() {
        return funcionarios;
    }

    public int getVisitantes() {
        return visitantes;
    }

    public int getTotalEntradas() {
        return totalEntradas;
    }

    public int getTotalSaidas() {
        return totalSaidas;
    }

    public int getAcessosHoje() {
        return acessosHoje;
    }
}