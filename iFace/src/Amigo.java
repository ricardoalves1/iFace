import java.util.Iterator;
import java.util.Scanner;

public class Amigo {


    public static void enviarPedidoAmizade(Perfil usuario) {

        System.out.print("Enviar solicitação de amizade para: ");

        Scanner input = new Scanner(System.in);
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
                    System.out.printf("Agora você e %s são amigos\n\n", nome);
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

    public static void pedidoAmizade(Perfil usuario) {

        Scanner input = new Scanner(System.in);

        Iterator<Perfil> itPerfil = usuario.getPedido().iterator();

        while (itPerfil.hasNext()) {
            Perfil i = itPerfil.next();

            for (Perfil j : Sistema.getUsuarios()) {
                if (j == i) {
                    System.out.printf("\nSolicitação de amizade de: %s\n(1) Aceitar \t (2) Rejeitar \t (0) Ignorar\n", j.getNome());
                    int opc = input.nextInt();

                    if (opc == 1) {
                        usuario.setAmigo(j);
                        j.setAmigo(usuario);
                        itPerfil.remove();
                        System.out.printf("Agora você e %s são amigos\n\n", j.getNome());
                    } else if (opc == 2) {
                        itPerfil.remove();
                        System.out.printf("Solicitação de amizade de %s negada\n\n", j.getNome());
                    }

                }
            }
        }
        

    }

    public static void qtdPedido(Perfil usuario) {

        Scanner input = new Scanner(System.in);

        System.out.printf("Você tem %d solicitações de amizade\n", usuario.getPedido().size());

        if (usuario.getPedido().size() > 0) {
            System.out.println("(1) Ver solicitações \t (0) Cancelar");

            if (input.nextInt() == 1) {
                pedidoAmizade(usuario);
            }
        }

    }

}