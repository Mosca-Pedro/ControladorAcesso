package service;

import model.Dashboard;
import repository.DashboardRepository;

public class DashboardService {

    private DashboardRepository repository;

    public DashboardService() {

        repository = new DashboardRepository();

    }

    public Dashboard obterDashboard() {

        return repository.obterDashboard();

    }

    public String usuarioComMaisAcessos() {
    return repository.usuarioComMaisAcessos();
}

}