package usuario;

import sistema.Sistema;

import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

public class Amigo {

    Scanner input = new Scanner(System.in);

    public void menuAmigos(Perfil usuario) {

        while (true) {
            System.out.println("\n(1) - Listar amigos\n" +
                    "(2) - Adicionar amigos\n" +
                    "(3) - Solicitações de amizade\n" +
                    "(0) - Voltar");

            try {

                int opc = input.nextInt();

                switch (opc) {
                    case 0:
                        return;
                    case 1:
                        listarAmigos(usuario);
                        break;
                    case 2: // Enviar solicitações de amizade
                        adicionarAmigo(usuario);
                        break;
                    case 3: // Visualizar solicitações de amizade
                        verPedido(usuario);
                        break;
                    default:
                        System.out.println("Opção inválida");
                }

            } catch (InputMismatchException e) {
                input.nextLine();
                System.out.println("Opção inválida");
            }

        }

    }

    private void listarAmigos(Perfil usuario) {

        System.out.println(" - Amigos - ");

        if (usuario.getAmigo().size() == 0) {
            System.out.println("Nenhum usuário na lista de amigos");
            return;
        }

        for (int i = 0; i < usuario.getAmigo().size(); i++) {
            System.out.println(" " + (i+1) + "_ " + usuario.getAmigo().get(i).getNome());
        }

    }

    private void adicionarAmigo(Perfil usuario) {

        input.nextLine();
        System.out.print("Enviar solicitação de amizade para: ");
        String nome = input.nextLine();

        for (Perfil i : Sistema.getUsuarios()) {
            if (i.getNome().equals(nome)) {
                if (i == usuario) {
                    System.out.println("Não é permitido enviar pedido de amizade para você mesmo");
                }
                else if (i.getPedido().contains(usuario)) {
                    System.out.printf("Você já enviou um pedido para %s\n", nome);
                }
                else if(usuario.getPedido().contains(i)) {
                    usuario.setAmigo(i);
                    i.setAmigo(usuario);
                    usuario.getPedido().remove(i);
                    System.out.printf("Solicitação de amizade aceita\nAgora você e %s são amigos\n\n", nome);
                }
                else if (i.getAmigo().contains(usuario)) {
                    System.out.println("Vocês já são amigos");
                }
                else {
                    i.setPedido(usuario);
                    System.out.println("Convite enviado\n");
                }
                return;
            }
        }
        System.out.println("Não foi encontrado alguém com esse nome\nSolicitação não enviada\n");

    }

    private void verPedido(Perfil usuario) {

        System.out.printf("Você tem %d solicitações de amizade\n", usuario.getPedido().size());

        try {
            if (usuario.getPedido().size() > 0) {
                System.out.println("(1) Ver solicitações \t (0) Voltar");

                switch (input.nextInt()) {
                    case 0:
                        return;
                    case 1:
                        pedidoAmizade(usuario);
                        break;
                    default:
                        System.out.println("Opção inválida");
                }
            }
        } catch (InputMismatchException e) {
            input.nextLine();
            System.out.println("Opção inválida");
        }

    }

    private void pedidoAmizade(Perfil usuario) {

        Iterator<Perfil> itPerfil = usuario.getPedido().iterator();

        while (itPerfil.hasNext()) {
            Perfil i = itPerfil.next();

            int opc = -1;

            while (opc != 0 && opc != 1 && opc != 2) {
                System.out.println("\nSolicitação de amizade de: " + i.getNome() +
                        "\n(1) Aceitar \t (2) Rejeitar \t (0) Ignorar");

                try {
                    opc = input.nextInt();

                    switch (opc) {
                        case 0:
                            break;
                        case 1:
                            usuario.setAmigo(i);
                            i.setAmigo(usuario);
                            itPerfil.remove();
                            System.out.printf("Agora você e %s são amigos\n\n", i.getNome());
                            break;
                        case 2:
                            itPerfil.remove();
                            System.out.printf("Solicitação de amizade de %s negada\n\n", i.getNome());
                            break;
                        default:
                            System.out.println("Opção inválida");
                    }
                } catch (InputMismatchException e) {
                    input.nextLine();
                    System.out.println("Opção inválida");
                }

            }
        }

    }

}