package sistema;

import comunidade.Comunidade;
import mensagem.MensagemUsuario;
import usuario.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    public static void menuUsuario(Perfil usuario) {

        Scanner input = new Scanner(System.in);
        int opc;

        while (true) {

            System.out.println("\n(1) - Perfil\n" +
                    "(2) - Amigos\n" +
                    "(3) - Mensagens\n" +
                    "(4) - Comunidades\n" +
                    "(5) - Remover conta\n" +
                    "(0) - Sair");

            try {
                opc = input.nextInt();
                Perfil perfil = new Perfil();
                Amigo amigo = new Amigo();
                Comunidade comunidade = new Comunidade();

                switch (opc) {
                    case 0:
                        return;
                    case 1:
                        perfil.verPerfil(usuario);
                        break;
                    case 2:
                        amigo.menuAmigos(usuario);
                        break;
                    case 3:
                        new MensagemUsuario().menuMensagem(usuario);
                        break;
                    case 4:
                        comunidade.menuComunidade(usuario);
                        break;
                    case 5:
                        perfil.apagar(usuario);
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

}
