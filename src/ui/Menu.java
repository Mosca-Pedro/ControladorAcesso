package ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;
import model.Dashboard;
import model.RegistroAcesso;
import model.Usuario;
import repository.BancoDados;
import repository.HistoricoRepository;
import service.AcessoService;
import service.ControladorAcesso;
import service.DashboardService;


public class Menu {

    Scanner scanner = new Scanner(System.in);
    BancoDados banco = new BancoDados();

    ControladorAcesso controlador = new ControladorAcesso();
    Usuario usuarioLogado = null;

    HistoricoRepository historico = new HistoricoRepository();
    AcessoService acessoService = new AcessoService();

    DashboardService dashboardService = new DashboardService();

    int tentativasLogin = 0;

    public void exibirMenu() {

        int opcao;

        do {

            System.out.println("\n=================================");
            System.out.println(" CONTROLADOR DE ACESSO ");
            System.out.println("=================================");
            System.out.println("1 - Cadastrar usuário");
            System.out.println("2 - Login");
            System.out.println("3 - Listar usuários");
            System.out.println("4 - Registrar entrada");
            System.out.println("5 - Registrar saída");
            System.out.println("6 - Ver histórico de acessos");
            System.out.println("7 - Editar usuário");
            System.out.println("8 - Excluir usuário");
            System.out.println("9 - Buscar usuário");
            System.out.println("10 - Dashboard");
            System.out.println("11 - Recuperar senha");
            System.out.println("12 - Sair");
           

            opcao = scanner.nextInt();

            switch (opcao) {

                case 1:

                if (usuarioLogado != null &&
                !controlador.temPermissao(usuarioLogado, 1)) {

                    System.out.println("Você não possui permissão para cadastrar usuários.");
                    break;
                }

                    scanner.nextLine();

                    System.out.print("ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();

                    if (banco.existeId(id)) {

                        System.out.println("Já existe um usuário com esse ID.");
                    break;

}

                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();

                    System.out.print("CPF: ");
                    String cpf = scanner.nextLine();

                    if (banco.existeCpf(cpf)) {

                        System.out.println("Já existe um usuário com esse CPF.");
                    break;

}

                    System.out.print("Senha: ");
                    String senha = scanner.nextLine();

                    System.out.print("Nível de acesso (Administrador/Funcionário/Visitante): ");
                    String nivel = scanner.nextLine();

                    System.out.print("Pergunta secreta: ");
                    String perguntaSecreta = scanner.nextLine();

                    System.out.print("Resposta secreta: ");
                    String respostaSecreta = scanner.nextLine();

                    Usuario usuario = new Usuario(
                        id,
                        nome,
                        cpf,
                        senha,
                        nivel,
                        perguntaSecreta,
                        respostaSecreta
                    );

                    banco.cadastrarUsuario(usuario);

                    break;


                case 2:

                        if (tentativasLogin >= 3) {

                            System.out.println("\n=================================");
                            System.out.println("Sistema Bloqueado!");
                            System.out.println("Reinicie o programa para tentar novamente.");
                            System.out.println("=================================");
                            break;
                }

                    System.out.print("ID: ");
                    int idLogin = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Senha: ");
                    String senhaLogin = scanner.nextLine();

                    usuarioLogado = banco.fazerLogin(idLogin, senhaLogin);

                    if (usuarioLogado != null) {

                        tentativasLogin = 0;

                        System.out.println("\n=================================");
                        System.out.println("Login realizado com sucesso!");
                        System.out.println("Bem-vindo, " + usuarioLogado.getNome());
                        System.out.println("Nível: " + usuarioLogado.getNivelAcesso());
                        System.out.println("=================================");

                    } else {

                        tentativasLogin++;

                        System.out.println("\nID ou senha inválidos.");
                        System.out.println("Tentativa " + tentativasLogin + " de 3.");

                    if (tentativasLogin >= 3) {


                        System.out.println("\n=================================");
                        System.out.println("Sistema Bloqueado!");
                        System.out.println("Você excedeu o número de tentativas.");
                        System.out.println("=================================");

                    }

                }
                break;

                case 3:


                    if (!controlador.temPermissao(usuarioLogado, 3)) {

                        System.out.println("Você não possui permissão para listar usuários.");
                        break;

                    }

                        System.out.println("\n===== USUÁRIOS CADASTRADOS =====");

                        for (Usuario usuarioLista : banco.listarUsuarios()) {

                            System.out.println(usuarioLista);
                            System.out.println("---------------------------");
                        }

                        break;

                case 4:

                    if (usuarioLogado == null) {

                        System.out.println("Faça login primeiro!");
                    break;

                }


                acessoService.registrarEntrada(
                    usuarioLogado.getId()
                );


                break;
                case 5:

                    if (usuarioLogado == null) {

                        System.out.println("Faça login primeiro!");
                break;

                }


                    acessoService.registrarSaida(
                        usuarioLogado.getId()
                    );


                break;

              case 6:

                System.out.println("\n===== HISTÓRICO DE ACESSOS =====");
                System.out.println("1 - Mostrar todo o histórico");
                System.out.println("2 - Buscar por usuário");
                System.out.println("3 - Buscar por tipo");
                System.out.println("4 - Buscar por período");
                System.out.print("Escolha uma opção: ");

                int opcaoHistorico = scanner.nextInt();
                scanner.nextLine();

                switch (opcaoHistorico) {

                case 1:

                     if (banco.existeAdministrador()) {

                        if (usuarioLogado == null) {

                            System.out.println("Faça login como administrador.");
                            break;
                        }

                        if (!controlador.temPermissao(usuarioLogado, 1)) {

                            System.out.println("Você não possui permissão para cadastrar usuários.");
                        break;
                    }
                }

                case 2:

                    System.out.print("Digite o nome do usuário: ");
                    nome = scanner.nextLine();

                    for (RegistroAcesso acesso :
                    acessoService.listarAcessosPorUsuario(nome)) {

                    System.out.println("---------------------------");
                    System.out.println("Usuário  : " + acesso.getNomeUsuario());
                    System.out.println("Data/Hora: " + acesso.getDataHora());
                    System.out.println("Tipo     : " + acesso.getTipo());

                }

                break;

                   case 3:

                    System.out.print("Digite o tipo (ENTRADA ou SAIDA): ");
                    String tipo = scanner.nextLine();

                for (RegistroAcesso acesso :
                    acessoService.listarAcessosPorTipo(tipo)) {

                    System.out.println("---------------------------");
                    System.out.println("Usuário  : " + acesso.getNomeUsuario());
                    System.out.println("Data/Hora: " + acesso.getDataHora());
                    System.out.println("Tipo     : " + acesso.getTipo());

                }

                break; 

                    case 4:

                        System.out.print("Data inicial (AAAA-MM-DD): ");
                        LocalDate dataInicial = LocalDate.parse(scanner.nextLine());

                        System.out.print("Data final (AAAA-MM-DD): ");
                        LocalDate dataFinal = LocalDate.parse(scanner.nextLine());

                        LocalDateTime inicio = dataInicial.atStartOfDay();
                        LocalDateTime fim = dataFinal.atTime(LocalTime.MAX);

                    for (RegistroAcesso acesso :
                        acessoService.listarAcessosPorPeriodo(inicio, fim)) {

                        System.out.println("---------------------------");
                        System.out.println("Usuário  : " + acesso.getNomeUsuario());
                        System.out.println("Data/Hora: " + acesso.getDataHora());
                        System.out.println("Tipo     : " + acesso.getTipo());

                    }

                    break;

                default:

                    System.out.println("Opção inválida.");
                }

                break;
                
                case 7:


                    if (!controlador.temPermissao(usuarioLogado, 7)) {

                        System.out.println("Você não possui permissão para editar usuários.");
                        break;

                    }

                    System.out.print("Digite o ID do usuário: ");
                    int idEditar = scanner.nextInt();
                    scanner.nextLine();

                    Usuario usuarioEditar = banco.buscarUsuarioPorId(idEditar);

                    if (usuarioEditar == null) {

                        System.out.println("Usuário não encontrado.");

                    } else {

                        System.out.print("Novo nome: ");
                        usuarioEditar.setNome(scanner.nextLine());

                        System.out.print("Novo CPF: ");
                        usuarioEditar.setCpf(scanner.nextLine());

                        System.out.print("Nova senha: ");
                        usuarioEditar.setSenha(scanner.nextLine());

                        System.out.print("Novo nível de acesso: ");
                        usuarioEditar.setNivelAcesso(scanner.nextLine());

                        boolean atualizado = banco.editarUsuario(usuarioEditar);

                    if (atualizado) {

                        System.out.println("Usuário atualizado com sucesso!");

                    } else {

                        System.out.println("Erro ao atualizar usuário.");

                    }

                }

                break;

                case 8:

                    if (!controlador.temPermissao(usuarioLogado, 8)) {

                        System.out.println("Você não possui permissão para excluir usuários.");
                        break;

                    }

                    System.out.print("Digite o ID do usuário que deseja excluir: ");
                    int idExcluir = scanner.nextInt();
                    scanner.nextLine();

                    boolean removido = banco.excluirUsuario(idExcluir);

                    if (removido) {

                        System.out.println("Usuário excluído com sucesso!");

                    }else {

                        System.out.println("Usuário não encontrado.");

                    }

                    break;

                case 9:

                    if (!controlador.temPermissao(usuarioLogado, 9)) {

                        System.out.println("Você não possui permissão para buscar usuários.");
                        break;

                    }

                    System.out.print("Digite o ID do usuário: ");
                    int idBusca = scanner.nextInt();
                    scanner.nextLine();

                    Usuario usuarioEncontrado = banco.buscarUsuarioPorId(idBusca);

                if (usuarioEncontrado != null) {

                    System.out.println("\n===== USUÁRIO ENCONTRADO =====");
                    System.out.println(usuarioEncontrado);

                } else {

                    System.out.println("Usuário não encontrado.");

            }

            break;

            case 10:

                Dashboard dashboard = dashboardService.obterDashboard();

                System.out.println("\n=================================");
                System.out.println("          DASHBOARD");
                System.out.println("=================================");

                System.out.println("Total de usuários : " + dashboard.getTotalUsuarios());

                System.out.println();

                System.out.println("Administradores   : " + dashboard.getAdministradores());
                System.out.println("Funcionários      : " + dashboard.getFuncionarios());
                System.out.println("Visitantes        : " + dashboard.getVisitantes());
                System.out.println();
                System.out.println("Entradas          : " + dashboard.getTotalEntradas());
                System.out.println("Saídas            : " + dashboard.getTotalSaidas());
                System.out.println();
                System.out.println("Acessos hoje      : " + dashboard.getAcessosHoje());

                System.out.println("Entradas hoje     : "
                    + dashboardService.entradasHoje());

                System.out.println("Saídas hoje       : "
                    + dashboardService.saidasHoje());

                System.out.println("Usuário com mais acessos: "
                    + dashboardService.usuarioComMaisAcessos());

                System.out.println("Último acesso      : "
                    + dashboardService.ultimoAcesso());
                    
                System.out.println("=================================");

            break;

            case 11:

                scanner.nextLine();

                System.out.println("\n===== RECUPERAÇÃO DE SENHA =====");

                System.out.print("CPF: ");
                String cpfRecuperacao = scanner.nextLine();

                if (!banco.existeCpf(cpfRecuperacao)) {

                    System.out.println("CPF não encontrado.");
                    break;
                }

                Usuario usuarioRecuperacao = banco.buscarPerguntaSecreta(cpfRecuperacao);

                if (usuarioRecuperacao == null || usuarioRecuperacao.getPerguntaSecreta() == null) {

                    System.out.println("Este usuário não possui pergunta secreta cadastrada.");
                    break;
                }

                System.out.println("Pergunta secreta: " + usuarioRecuperacao.getPerguntaSecreta());
                System.out.print("Resposta: ");
                String respostaRecuperacao = scanner.nextLine();

                if (!banco.validarRespostaSecreta(cpfRecuperacao, respostaRecuperacao)) {

                    System.out.println("Resposta incorreta.");
                    break;
                }

                System.out.print("Nova senha: ");
                String novaSenha = scanner.nextLine();

                if (banco.redefinirSenha(cpfRecuperacao, novaSenha)) {

                    System.out.println("Senha redefinida com sucesso!");

                } else {

                    System.out.println("Erro ao redefinir senha.");

                }

                break;


        default:
            System.out.println("Opção inválida.");
            break;  
               

    }

        } while (opcao != 12);
    }

}
