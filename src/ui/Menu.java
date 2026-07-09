package ui;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;
import model.Usuario;
import repository.BancoDados;
import service.ControladorAcesso;


public class Menu {

    Scanner scanner = new Scanner(System.in);
    BancoDados banco = new BancoDados();

    ControladorAcesso controlador = new ControladorAcesso();
    Usuario usuarioLogado = null;

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

                    salvarHistorico(usuarioLogado.getNome(), sala, permitido);

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

                    mostrarHistorico();

                    break;
                
                case 7:

                System.out.print("Digite o ID do usuário: ");
                int idEditar = scanner.nextInt();
                scanner.nextLine();

                Usuario usuarioEditar = banco.buscarUsuarioPorId(idEditar);

            if (usuarioEditar == null) {

                System.out.println("Usuário não encontrado.");

                banco.salvarTodosUsuarios();

            } else {

                System.out.print("Novo nome: ");
                usuarioEditar.setNome(scanner.nextLine());

                System.out.print("Novo CPF: ");
                usuarioEditar.setCpf(scanner.nextLine());

                System.out.print("Nova senha: ");
                usuarioEditar.setSenha(scanner.nextLine());

                System.out.print("Novo nível de acesso: ");
                usuarioEditar.setNivelAcesso(scanner.nextLine());

                System.out.println("Usuário atualizado com sucesso!");

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

    private void salvarHistorico(String nome, String sala, boolean permitido) {

    try {

        BufferedWriter writer = new BufferedWriter(
                new FileWriter("historico.txt", true));

        String status = permitido ? "LIBERADO" : "NEGADO";

        writer.write(LocalDateTime.now() + " - " +
                nome + " - " +
                sala + " - " +
                status);

        writer.newLine();

        writer.close();

    } catch (IOException e) {
        System.out.println("Erro ao salvar histórico.");
    }
}

private void mostrarHistorico() {

    try {

        BufferedReader reader = new BufferedReader(new FileReader("historico.txt"));

        String linha;

        System.out.println("\n===== HISTÓRICO DE ACESSOS =====");

        while ((linha = reader.readLine()) != null) {
            System.out.println(linha);
        }

        reader.close();

        System.out.println("================================");

    } catch (Exception e) {
        System.out.println("Nenhum histórico encontrado.");
    }
}

}
