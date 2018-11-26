import java.util.Scanner;

public class Menu {

    public static void inicio(Perfil usuario) {

        Scanner input = new Scanner(System.in);
        int opc;
        String login = usuario.getLogin(); // todo tirar isso aqui

        System.out.println("\n(1) Editar perfil\n" +
                "(2) Adicionar amigos\n" +
                "(3) Solicitações de amizade\n" +
                "(4) Mensagens recebidas\n" +
                "(5) Enviar mensagem para usuário\n" +
                "(6) Comunidades existentes\n" +
                "(7) Criar comunidade\n" +
                "(8) Entrar em uma comunidade\n" +
                "(9) Enviar mensagem para uma comunidade\n" +
                "(10) Mensagens das comunidades\n" +
                "(11) Informações da conta\n" +
                "(12) Remover conta\n" +
                "(0) Sair\n");

        opc = input.nextInt();

        switch (opc) {
            case 1: // Editar
                Usuario.editarPerfil(usuario);
                inicio(usuario);
                break;
            case 2: // Enviar pedido de amizade
                Amigo.enviarPedidoAmizade(usuario);
                inicio(usuario);
                break;
            case 3: // Solicitações de amizade
                Amigo.qtdPedido(usuario);
                inicio(usuario);
                break;
            case 4: // Mensagens recebidas
                Mensagem.qtdMensagemUsuario(usuario);
                inicio(usuario);
                break;
            case 5: // Enviar mensagem para outro usuário // Todo Fazer daqui pra baixo
                Mensagem.enviarMensagemUsuario(login);
                inicio(usuario);
                break;
            case 6: // Comunidades existentes
                Comunidade.todasComunidades();
                inicio(usuario);
                break;
            case 7: // Criar comunidade
                System.out.println("Comunidades\nDeseja criar nova comunidade?\n(1) Sim \t (0) Não");

                if (input.nextInt() == 1) {
                    Comunidade.criarComunidade(login);
                }

                inicio(usuario);
                break;
            case 8: // Entrar em uma comunidade
                System.out.println("Entrar em uma comunidade?\n(1) Sim \t (0) Não");
                if (input.nextInt() == 1) {
                    Comunidade.entraComunidade(login);
                }
                inicio(usuario);
                break;
            case 9: // Enviar mensagem para comunidades
                Mensagem.enviarMensagemComunidade(login);
                inicio(usuario);
                break;
            case 10: // Mensagens das comunidades
                Mensagem.qtdMensagemComunidade(login);
                inicio(usuario);
                break;
            case 11: // Informações da conta
                Usuario.informacoes(login);
                inicio(usuario);
                break;
            case 12: // Remover conta

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

                inicio(usuario);
        }
    }

}