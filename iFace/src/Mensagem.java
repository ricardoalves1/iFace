import java.util.Scanner;

public class Mensagem {

    private Perfil usuario;
    private String mensagem;

    public Mensagem(Perfil usuario, String mensagem) {
        this.usuario = usuario;
        this.mensagem = mensagem;
    }

    public Perfil getUsuario() {
        return usuario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public static void enviarMensagemUsuario(Perfil usuario) {

        Scanner input = new Scanner(System.in);

        System.out.print("Enviar mensagem para pessoa: ");
        String nome = input.nextLine();
        System.out.print("Mensagem: ");
        String mensagem = input.nextLine();

        for (Perfil i: Sistema.getUsuarios()) {
            if (i.getNome().equals(nome)) {
                if (i == usuario) {
                    System.out.println("Não é permitido enviar mensagem para você mesmo");
                    return;
                }
                i.setMensagens(usuario, mensagem);
                System.out.println("\nMensagem enviada\n");
                return;
            }
        }
        System.out.println("Usuário não encontrado\nMensagem não enviada\n");

    }

    public static void enviarMensagemComunidade(Perfil usuario) {

        Scanner input = new Scanner(System.in);

        System.out.print("Enviar mensagem para a comunidade: ");
        String comunidade = input.nextLine();
        System.out.print("Mensagem: ");
        String mensagem = input.nextLine();

        for (Comunidade i: Sistema.getComunidades()) {
            if (i.getComunidade().equals(comunidade)) {
                i.setMensagens(usuario, mensagem);
                System.out.println("\nMensagem enviada\n");
                return;
            }
        }
        System.out.println("Ocorreu um erro...\nMensagem não enviada\n");

    }

    public static void lerMensagemUsuario(Perfil usuario) {

        // Mensagens de usuários
        for (Mensagem j : usuario.getMensagens()) {
                System.out.printf("--------------\nMensagem de %s:\t%s\n--------------\n", j.getUsuario().getNome(), j.getMensagem());
        }

    }

    public static void qtdMensagemUsuario(Perfil usuario) {

        Scanner input = new Scanner(System.in);
        System.out.printf("\nVocê tem %d mensagens\n", usuario.getMensagens().size());

        if (usuario.getMensagens().size() > 0) {
            System.out.println("(1) Ler \t(0) Ignorar\n\n");
            if (input.nextInt() == 1) {
                lerMensagemUsuario(usuario);
            }
        }


    }

    public static void lerMensagemComunidade(Comunidade comunidade) {

        // Mensagens de Comunidades
        System.out.printf("\nMensagens da comunidade %s:\n", comunidade.getComunidade());
        for (Mensagem k : comunidade.getMensagens()) {
            System.out.printf("Enviada por: %s\n\t%s\n------------------------------\n", k.getUsuario().getNome(), k.getMensagem());
        }

    }

    public static void qtdMensagemComunidade(Perfil usuario) {

        Scanner input = new Scanner(System.in);
        if (usuario.getComunidades().size() == 0) {
            System.out.println("Você não está em uma comunidade");
            return;
        }
        System.out.println("Mensagens das Comunidades");

        // Comunidades
        for (Comunidade j: Sistema.getComunidades()) {
            if (j.getAdmin() == usuario || j.getMembro().contains(usuario)) {
                System.out.printf("A comunidade %s tem %d mensagens no total\n", j.getComunidade(), j.getMensagens().size());

                if (j.getMensagens().size() > 0) {
                    System.out.println("(1) Ler Mensagens \t (0) Ignorar");
                    if (input.nextInt() == 1) {
                        lerMensagemComunidade(j);
                    }
                }

            }
        }

    }

}