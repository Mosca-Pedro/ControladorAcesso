package ui;

import java.util.Scanner;
import model.Usuario;
import repository.BancoDados;
import repository.HistoricoRepository;
import service.ControladorAcesso;


public class Menu {

    Scanner scanner = new Scanner(System.in);
    BancoDados banco = new BancoDados();

    ControladorAcesso controlador = new ControladorAcesso();
    Usuario usuarioLogado = null;

    HistoricoRepository historico = new HistoricoRepository();

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
            System.out.println("4 - Registrar acesso");
            System.out.println("5 - Sair");
            System.out.println("6 - Ver histórico de acessos");
            System.out.println("7 - Editar usuário");
            System.out.println("8 - Excluir usuário");
            System.out.println("9 - Buscar usuário");
            System.out.print("Escolha uma opção: ");
           

            opcao = scanner.nextInt();

            switch (opcao) {

                case 1:

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

                    Usuario usuario = new Usuario(id, nome, cpf, senha, nivel);

                    banco.cadastrarUsuario(usuario);

                    break;


                    
                

                case 2:

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


                    }

                    if (tentativasLogin >= 3) {

                        System.out.println("\n=================================");
                        System.out.println("LOGIN BLOQUEADO!");
                        System.out.println("Você excedeu o número de tentativas.");
                        System.out.println("=================================");
}

                    break;

                case 3:

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

                    scanner.nextLine();

                    System.out.print("Digite a sala que deseja acessar: ");
                    String sala = scanner.nextLine();

                    boolean permitido = controlador.verificarAcesso(usuarioLogado, sala);

                    historico.salvarHistorico(usuarioLogado.getNome(), sala, permitido);

                if (permitido) {

                    System.out.println("\n=================================");
                    System.out.println("ACESSO LIBERADO!");
                    System.out.println("Usuário: " + usuarioLogado.getNome());
                    System.out.println("Sala: " + sala);
                    System.out.println("=================================");

                } else {

                    System.out.println("\n=================================");
                    System.out.println("ACESSO NEGADO!");
                    System.out.println("Usuário: " + usuarioLogado.getNome());
                    System.out.println("Sala: " + sala);
                    System.out.println("=================================");

            }

             break;
                case 5:
                    System.out.println("Sistema encerrado.");
                    break;

                default:
                    System.out.println("Opção inválida.");

                case 6:

                   historico.listarHistorico();

                    break;
                
                case 7:

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

                    System.out.print("Digite o ID do usuário que deseja excluir: ");
                    int idExcluir = scanner.nextInt();
                    scanner.nextLine();

                    boolean removido = banco.excluirUsuario(idExcluir);

                if (removido) {

                    System.out.println("Usuário excluído com sucesso!");

                } else {

                     System.out.println("Usuário não encontrado.");

            }

    break;

                case 9:

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
                
               

    }

        } while (opcao != 5);
    }

}
