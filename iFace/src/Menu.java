import java.util.Scanner;

public class Menu {

    static int lerMsg = 0;

    public static void inicio(String login) {

        Scanner input = new Scanner(System.in);
        int opc;

        // Solicitações de amizade
        for (Perfil i: Sistema.getUsuarios()) {
            if (i.getLogin().equals(login) && i.getPedido().size() > 0) {
                System.out.println("\nVocê tem pedidos de amizade:\n(1) Aceitar\n(2) Rejeitar\n(0) Ignorar\n");

                for (String j : i.getPedido()) {
                    for (Perfil k : Sistema.getUsuarios()) {
                        if (k.getLogin().equals(j)) {
                            System.out.printf("Solicitação de amizade de: %s\n", k.getNome());
                            opc = input.nextInt();

                            if (opc == 1) {
                                i.setAmigo(k.getLogin());
                                k.setAmigo(i.getLogin());
                                i.getPedido().set(i.getPedido().indexOf(k.getLogin()), "-1");   // "removendo" o pedido
                                System.out.printf("Agora você e %s são amigos\n\n", k.getNome());
                            }
                            else if (opc == 2) {
                                i.getPedido().set(i.getPedido().indexOf(k.getLogin()), "-1");   // "removendo" o pedido
                                System.out.printf("Solicitação de amizade de %s negada\n\n", k.getNome());
                            }

                        }
                    }
                }
                // Remover todos os pedidos que já foram aceitos ou rejeitados
                while (i.getPedido().contains("-1")) {
                    i.getPedido().remove("-1");
                }

                break;
            }
        }

        if (lerMsg == 0) {

            // Mensagens de usuários
            for (Perfil i : Sistema.getUsuarios()) {
                if (i.getLogin().equals(login) && i.getMensagens().size() > 0) {
                    System.out.println("\nVocê tem mensagens:\n(1) Ler \t(0) Ignorar\n");

                    if (input.nextInt() == 1) {
                        for (Mensagem j : i.getMensagens()) {
                            for (Perfil k : Sistema.getUsuarios()) {
                                if (k.getLogin().equals(j.getLogin())) {
                                    System.out.printf("--------------\nMensagem de %s:\t%s\n--------------\n", k.getNome(), j.getMensagem());
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            // Mensagens de Comunidades
            for (Perfil i : Sistema.getUsuarios()) {
                if (i.getLogin().equals(login) && i.getComunidades().size() > 0) {
                    for (Comunidade j : Sistema.getComunidades()) {
                        if ((j.getAdmin().getLogin().equals(i.getLogin()) || Comunidade.getMembro().contains(i)) && j.getMensagens().size() > 0) {
                            System.out.printf("Mensagens novas da comunidade %s:\n(1) ler \t (0) Ignorar\n", j.getComunidade());

                            if (input.nextInt() == 1) {
                                for (Mensagem k : j.getMensagens()) {
                                    System.out.printf("%s:\t %s\n------------------------------\n", k.getLogin(), k.getMensagem());
                                }
                            }
                        }
                    }
                }
            }
            lerMsg = 1;

        }

        System.out.println("\n(1) Editar Perfil\n(2) Adicionar Amigos\n(3) Enviar Mensagem\n(4) Criar Comunidade\n(5) Entrar Em Comunidade\n(6) Informações\n(7) Remover Conta\n(0) Sair\n");

        opc = input.nextInt();

        switch (opc) {
            case 1: // Editar
                Usuario.editarPerfil(login);
                inicio(login);
                break;
            case 2: // Enviar pedido de amizade
                System.out.print("Adicionar: ");
                input.nextLine();
                String nome = input.nextLine();
                Usuario.pedidoAmizade(login, nome);
                inicio(login);
                break;
            case 3: // Mensagem
                System.out.println("Enviar mensagem para:\n(1) Usuário\n(2) Comunidade\n(0) Cancelar");
                int escolha = input.nextInt();
                String msg;

                if (escolha == 1) {
                    input.nextLine();
                    System.out.print("Enviar mensagem para pessoa: ");
                    nome = input.nextLine();
                    System.out.println("Mensagem:");
                    msg = input.nextLine();

                    Mensagem.mensagemUsuario(login, nome, msg);

                }
                else if (escolha == 2) {
                    input.nextLine();
                    System.out.print("Enviar mensagem para a comunidade (nome): ");
                    nome = input.nextLine();
                    System.out.println("Mensagem:");
                    msg = input.nextLine();

                    Mensagem.mensagemComunidade(login, nome, msg);

                }

                inicio(login);
                break;
            case 4: // Comunidade
                System.out.println("Comunidades\nDeseja criar nova comunidade?\n(1) Sim \t (0) Não");

                if (input.nextInt() == 1) {
                    input.nextLine();
                    System.out.print("Nome da Comunidade: ");
                    String comunidade = input.nextLine();

                    System.out.print("Descrição da Comunidade: ");
                    String descricao = input.nextLine();

                    System.out.printf("Criar Comunidade %s?\n(1) Sim \t (0) Não\n", comunidade);
                    if (input.nextInt() == 1) {
                        Comunidade.criarComunidade(comunidade, descricao, login);
                    }
                    else {
                        System.out.println("Criação de Comunidade interrompida\n");
                    }
                }

                inicio(login);
                break;
            case 5: // Entrar em comunidades
                System.out.println("Entrar em uma comunidade?\n(1) Sim \t (0) Não");
                if (input.nextInt() == 1) {
                    input.nextLine();
                    System.out.print("Digite o nome da comunidade: ");
                    String comunidade = input.nextLine();
                    Comunidade.entraComunidade(comunidade, login);
                }
                inicio(login);
                break;
            case 6: // Informacoes
                System.out.println("Informações do usuário:\n(1) Perfil\n(2) Comunidades\n(3) Amigos\n(4) Mensagens\n(0) Cancelar");
                opc = input.nextInt();

                if (opc == 1) {
                    for (Perfil i: Sistema.getUsuarios()) {
                        if (i.getLogin().equals(login)) {
                            System.out.printf("Nome: %s\nLogin: %s\nSenha: %s\n", i.getNome(), i.getLogin(), i.getSenha());
                            break;
                        }
                    }
                }
                else if (opc == 2) {
                    for (Perfil i: Sistema.getUsuarios()) {
                        if (i.getLogin().equals(login) && i.getComunidades().size() > 0) {
                            System.out.println("Você está nas comunidades:");
                            for (Comunidade j: i.getComunidades()) {
                                System.out.printf("#%s: %s\n", j.getComunidade(), j.getDescricao());
                            }
                            break;
                        }
                    }
                }
                else if (opc == 3) {
                    for (Perfil i: Sistema.getUsuarios()) {
                        if (i.getLogin().equals(login) && i.getAmigo().size() > 0) {
                            System.out.println("Seus amigos");
                            for (String j: i.getAmigo()) {
                                System.out.printf("%s\n", j);
                            }
                        }
                    }
                }
                else if (opc == 4) {
                    lerMsg = 0;
                }

                inicio(login);
                break;
            case 7: // Remover conta
                System.out.println("Deseja mesmo remover sua conta?\n(1) Sim \t (2) Não");

                if (input.nextInt() == 1) {
                    for (Perfil i: Sistema.getUsuarios()) {
                        if (i.getLogin().equals(login)) {
                            Sistema.getUsuarios().remove(i);

                            for (Perfil j: Sistema.getUsuarios()) {
                                j.getAmigo().remove(i.getNome());
                            }
                            for (Comunidade j: Sistema.getComunidades()) {
                                if (j.getAdmin() == i) {
                                    for (Perfil k: Sistema.getUsuarios()) {
                                        k.getComunidades().remove(j);
                                    }
                                    Sistema.getComunidades().set(Sistema.getComunidades().indexOf(j), null);
                                }
                            }

                            while (Sistema.getComunidades().contains(null)) {
                                Sistema.getComunidades().remove(null);
                            }

                            System.out.println("Conta Removida");
                            return;
                        }
                    }
                }

                inicio(login);
                break;
            case 0:
                lerMsg = 0;
        }
    }

}