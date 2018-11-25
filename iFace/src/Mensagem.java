public class Mensagem {

    public Mensagem(String login, String mensagem) {
        this.login = login;
        this.mensagem = mensagem;
    }

    private String login;
    private String mensagem;

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

    public static void mensagemUsuario(String login, String nome, String mensagem) {

        for (Perfil i: Sistema.getUsuarios()) {
            if (i.getNome().equals(nome) && !i.getLogin().equals(login)) {
                i.setMensagens(login, mensagem);
                System.out.println("Mensagem enviada\n");
                return;
            }
        }
        System.out.println("Ocorreu um erro...\nMensagem não enviada\n");

    }

    public static void mensagemComunidade(String login, String comunidade, String mensagem) {

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

}
