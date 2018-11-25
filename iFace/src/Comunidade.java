import java.util.ArrayList;

public class Comunidade {

    public Comunidade(String comunidade, String descricao, Perfil admin) {
        this.comunidade = comunidade;
        this.descricao = descricao;
        this.admin = admin;
    }
    public Comunidade() {}

    private String comunidade;
    private String descricao;
    private Perfil admin;
    private ArrayList<Mensagem> mensagens = new ArrayList<>();
    private static ArrayList<Perfil> membro = new ArrayList<>();

    public String getComunidade() {
        return comunidade;
    }

    public void setComunidade(String comunidade) {
        this.comunidade = comunidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Perfil getAdmin() {
        return admin;
    }

    public void setAdmin(Perfil admin) {
        this.admin = admin;
    }

    public ArrayList<Mensagem> getMensagens() {
        return mensagens;
    }

    public void setMensagens(String nome, String mensagem) {
        this.mensagens.add(new Mensagem(nome, mensagem));
    }

    public static ArrayList<Perfil> getMembro() {
        return Comunidade.membro;
    }

    public static void criarComunidade(String comunidade, String descricao, String login) {
        for (Comunidade i: Sistema.getComunidades()) {
            if (i.getComunidade().equals(comunidade)) {
                System.out.println("Não foi possível criar a comunidade\nEsse nome já está sendo usado\n");
                return;
            }
        }
        Perfil admin = new Perfil();

        for (Perfil i: Sistema.getUsuarios()) {
            if (i.getLogin().equals(login)) {
                admin = i;
            }
        }

        Comunidade novo = new Comunidade(comunidade, descricao, admin);
        admin.setComunidades(novo);
        Sistema.setComunidades(novo);
        System.out.println("Comunidade criada\n");
    }

    public static void entraComunidade(String comunidade, String login) {
        for (Comunidade i: Sistema.getComunidades()) {
            if (i.getComunidade().equals(comunidade)) {
                for (Perfil j: Sistema.getUsuarios()) {
                    if (j.getLogin().equals(login) && !j.getComunidades().contains(i)) {
                        Comunidade.membro.add(j);
                        j.setComunidades(i);
                        System.out.printf("Você entrou na comunidade %s\n", comunidade);
                        return;
                    }
                }
            }
        }
        System.out.println("Ocorreu um erro...\nVocê não entrou na comunidade");
    }

}
