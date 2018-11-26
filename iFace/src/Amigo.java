import java.util.Scanner;

public class Amigo {

    static Scanner input = new Scanner(System.in);

    public static void enviarPedidoAmizade(Perfil usuario) {

        System.out.print("Enviar solicitação de amizade para: ");
        input.nextLine();
        String nome = input.nextLine();

        for (Perfil i : Sistema.getUsuarios()) {
            if (i.getNome().equals(nome) && i != usuario) {
                i.setPedido(usuario);
                System.out.println("\nConvite enviado\n");
                return;
            }
        }
        System.out.println("\nNão foi encontrado alguém com esse nome\nSolicitação não enviada\n");
    }

    public static void pedidoAmizade(Perfil usuario) {

        for (Perfil j : usuario.getPedido()) {
            for (Perfil k : Sistema.getUsuarios()) {
                if (k == j) {
                    System.out.printf("Solicitação de amizade de: %s\n(1) Aceitar \t (2) Rejeitar \t (0) Ignorar", k.getNome());
                    int opc = input.nextInt();

                    if (opc == 1) {
                        usuario.setAmigo(k);
                        k.setAmigo(usuario);
                        usuario.getPedido().set(usuario.getPedido().indexOf(k), null);   // "removendo" o pedido
                        System.out.printf("Agora você e %s são amigos\n\n", k.getNome());
                    } else if (opc == 2) {
                        usuario.getPedido().set(usuario.getPedido().indexOf(k), null);   // "removendo" o pedido
                        System.out.printf("Solicitação de amizade de %s negada\n\n", k.getNome());
                    }

                }
            }
        }
        // Remover todos os pedidos que já foram aceitos ou rejeitados
        while (usuario.getPedido().contains(null)) {
            usuario.getPedido().remove(null);
        }

    }

    public static void qtdPedido(Perfil usuario) {

        System.out.printf("Você tem %d solicitações de amizade\n", usuario.getPedido().size());

        if (usuario.getPedido().size() > 0) {
            System.out.println("(1) Ver solicitações \t (0) Cancelar");

            if (input.nextInt() == 1) {
                pedidoAmizade(usuario);
            }
        }

    }

}
