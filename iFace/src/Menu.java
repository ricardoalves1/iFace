import java.util.Scanner;

public class Menu {

    public static void inicio(Perfil usuario) {

        Scanner input = new Scanner(System.in);
        int opc;

        System.out.println("\n(1) Editar perfil\n" +
                "(2) Adicionar amigos\n" +
                "(3) Solicitações de amizade\n" +
                "(4) Mensagens recebidas\n" +
                "(5) Enviar mensagem para usuário\n" +
                "(6) Comunidades existentes\n" +
                "(7) Criar comunidade\n" +
                "(8) Entrar em uma comunidade\n" +
                "(9) Administrar comunidade\n" +
                "(10) Enviar mensagem para uma comunidade\n" +
                "(11) Mensagens das comunidades\n" +
                "(12) Informações da conta\n" +
                "(13) Remover conta\n" +
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
            case 5: // Enviar mensagem para outro usuário
                Mensagem.enviarMensagemUsuario(usuario);
                inicio(usuario);
                break;
            case 6: // Comunidades existentes
                Comunidade.todasComunidades();
                inicio(usuario);
                break;
            case 7: // Criar comunidade
                Comunidade.criarComunidade(usuario);
                inicio(usuario);
                break;
            case 8: // Entrar em uma comunidade
                Comunidade.entraComunidade(usuario);
                inicio(usuario);
                break;
            case 9: // Administração da comunidade
                Comunidade.adminComunidade(usuario);
                inicio(usuario);
                break;
            case 10: // Enviar mensagem para comunidades
                Mensagem.enviarMensagemComunidade(usuario);
                inicio(usuario);
                break;
            case 11: // Mensagens das comunidades
                Mensagem.qtdMensagemComunidade(usuario);
                inicio(usuario);
                break;
            case 12: // Informações da conta
                Usuario.informacoes(usuario);
                inicio(usuario);
                break;
            case 13: // Remover conta
                Sistema.removerConta(usuario);
        }
    }

}