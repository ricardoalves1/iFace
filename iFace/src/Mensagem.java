import java.util.Scanner;

public class Mensagem {

    public Mensagem(String login, String mensagem) {
        this.login = login;
        this.mensagem = mensagem;
    }

    private String login;
    private String mensagem;
    static Scanner input = new Scanner(System.in);

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public static void enviarMensagemUsuario(String login) {

        input.nextLine();
        System.out.print("Enviar mensagem para pessoa: ");
        String nome = input.nextLine();
        System.out.print("Mensagem:");
        String mensagem = input.nextLine();

        for (Perfil i: Sistema.getUsuarios()) {
            if (i.getNome().equals(nome) && !i.getLogin().equals(login)) {
                i.setMensagens(login, mensagem);
                System.out.println("Mensagem enviada\n");
                return;
            }
        }
        System.out.println("Usuário não encontrado\nMensagem não enviada\n");

    }

    public static void enviarMensagemComunidade(String login) {

        input.nextLine();
        System.out.print("Enviar mensagem para a comunidade: ");
        String comunidade = input.nextLine();
        System.out.println("Mensagem:");
        String mensagem = input.nextLine();

        for (Comunidade i: Sistema.getComunidades()) {
            if (i.getComunidade().equals(comunidade)) {
                for (Perfil j: Sistema.getUsuarios()) {
                    if (j.getLogin().equals(login)) {
                        i.setMensagens(j.getNome(), mensagem);
                    }
                }
                System.out.println("Mensagem enviada\n");
                return;
            }
        }
        System.out.println("Ocorreu um erro...\nMensagem não enviada\n");

    }


    public static void lerMensagemUsuario(Perfil usuario) {

        // Mensagens de usuários
        for (Mensagem j : usuario.getMensagens()) {
            for (Perfil k : Sistema.getUsuarios()) {
                if (k.getLogin().equals(j.getLogin())) {
                    System.out.printf("--------------\nMensagem de %s:\t%s\n--------------\n", k.getNome(), j.getMensagem());
                    break;
                }
            }
        }

    }

    public static void qtdMensagemUsuario(Perfil usuario) {

        Scanner input = new Scanner(System.in);
        System.out.printf("\nVocê tem %d mensagens:\n", usuario.getMensagens().size());

        if (usuario.getMensagens().size() > 0) {
            System.out.println("(1) Ler \t(0) Ignorar\n\n");
            if (input.nextInt() == 1) {
                lerMensagemUsuario(usuario);
            }
        }


    }

    public static void lerMensagemComunidade(Comunidade comunidade) {

        // Mensagens de Comunidades
        System.out.printf("Mensagens da comunidade %s:\n", comunidade.getComunidade());
        for (Mensagem k : comunidade.getMensagens()) {
            // k.getLogin() é o nome do usuário
            System.out.printf("Enviada por: %s\n\t%s\n------------------------------\n", k.getLogin(), k.getMensagem());
        }

    }

    public static void qtdMensagemComunidade(String login) {

        Scanner input = new Scanner(System.in);

        for (Perfil i : Sistema.getUsuarios()) {
            if (i.getLogin().equals(login)) {
                // Comunidades
                for (Comunidade j: Sistema.getComunidades()) {
                    if ((j.getAdmin().getLogin().equals(i.getLogin()) || Comunidade.getMembro().contains(i))) {
                        System.out.printf("A comunidade %s tem %d mensagens no total\n", j.getComunidade(), j.getMensagens().size());

                        if (j.getMensagens().size() > 0) {
                            System.out.println("(1) Ler Mensagens \t (2) Ignorar");

                            if (input.nextInt() == 1) {
                                lerMensagemComunidade(j);
                            }
                        }
                    }
                }

            }
        }
    }


}
