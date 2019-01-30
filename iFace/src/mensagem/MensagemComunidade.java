package mensagem;

import comunidade.Comunidade;
import sistema.Sistema;
import usuario.Perfil;

import java.util.InputMismatchException;

public class MensagemComunidade extends Mensagem implements Funcoes {

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
                    case 1:
                        enviarMensagem(usuario);
                        break;
                    case 2: // Visualizar mensagens
                        verificaMensagem(usuario);
                        break;
                    case 0:
                        return;
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

        System.out.print("\nEnviar mensagem para a comunidade: ");
        String comunidade = input.nextLine();
        System.out.print("Mensagem: ");
        String mensagem = input.nextLine();

        for (Comunidade i: Sistema.getComunidades()) {
            if (i.getComunidade().equals(comunidade)) {
                i.setMensagens(new Mensagem(usuario, mensagem));
                System.out.println("\nMensagem enviada\n");
                return;
            }
        }
        System.out.println("Comunidade não encontrada\nMensagem não enviada\n");

    }

    @Override
    public void verificaMensagem(Perfil usuario) {

        if (usuario.getComunidades().size() == 0) {
            System.out.println("Você não está em uma comunidade");
            return;
        }
        System.out.println(" - Mensagens das Comunidades - \n");

        // Comunidades
        for (Comunidade i: Sistema.getComunidades()) {
            if (i.getAdmin() == usuario || i.getMembro().contains(usuario)) {
                System.out.printf("A comunidade %s tem %d mensagens no total\n",
                        i.getComunidade(), i.getMensagens().size());

                if (i.getMensagens().size() > 0) {
                    System.out.println("(1) Ler Mensagens \t (Outro) Ignorar");
                    try {
                        if (input.nextInt() == 1) {
                            lerMensagem(i);
                        }
                    } catch (InputMismatchException e) {
                        input.nextLine();
                    }
                }

            }
        }

    }

    @Override
    public void lerMensagem(Object x) {

        Comunidade comunidade = (Comunidade) x;

        System.out.println("\n - " + comunidade.getComunidade() + " - \n");

        for (Mensagem i : comunidade.getMensagens()) {
            System.out.printf("%s: \"%s\"\n",
                    i.getUsuario().getNome(), i.getMensagem());
        }

        System.out.println("------------------------------\n");

    }

}
