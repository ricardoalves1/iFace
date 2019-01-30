package mensagem;

import sistema.Sistema;
import usuario.Perfil;

import java.util.InputMismatchException;

public class MensagemUsuario extends Mensagem implements Funcoes {

    @Override
    public void menuMensagem(Perfil usuario) {

        while (true) {
            System.out.println("\n(1) - Enviar mensagem\n" +
                    "(2) - Visualizar mensagens\n" +
                    "(0) - Voltar");

            try {

                int opc = input.nextInt();
                input.nextLine();

                switch (opc) {
                    case 0:
                        return;
                    case 1:
                        enviarMensagem(usuario);
                        break;
                    case 2: // Visualizar mensagens
                        verificaMensagem(usuario);
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

    @Override
    public void enviarMensagem(Perfil usuario) {

        System.out.print("\nEnviar mensagem para pessoa: ");
        String nome = input.nextLine();
        System.out.print("Mensagem: ");
        String mensagem = input.nextLine();

        for (Perfil i: Sistema.getUsuarios()) {
            if (i.getNome().equals(nome)) {
                if (i == usuario) {
                    System.out.println("Não é permitido enviar mensagem para você mesmo");
                    return;
                }
                i.setMensagens(new Mensagem(usuario, mensagem));
                System.out.println("\nMensagem enviada\n");
                return;
            }
        }
        System.out.println("Usuário não encontrado\nMensagem não enviada\n");

    }

    @Override
    public void verificaMensagem(Perfil usuario) {

        System.out.printf("\nVocê tem %d mensagens\n", usuario.getMensagens().size());

        if (usuario.getMensagens().size() > 0) {
            System.out.println("(1) Ler \t(0) Ignorar\n\n");

            try {
                switch (input.nextInt()) {
                    case 0:
                        break;
                    case 1:
                        lerMensagem(usuario);
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

    @Override
    public void lerMensagem(Object x) {

        Perfil usuario = (Perfil) x;

        System.out.println(" - Mensagens - ");

        // Mensagens de usuários
        for (Mensagem i : usuario.getMensagens()) {
            System.out.println("De: " + i.getUsuario().getNome());
            System.out.println("Mensagem: \"" + i.getMensagem() + "\"\n");
        }

    }

}
