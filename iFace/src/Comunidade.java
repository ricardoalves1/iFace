import java.util.ArrayList;
import java.util.Scanner;

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

    static Scanner input = new Scanner(System.in);

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

    public static void criarComunidade(String login) {

        input.nextLine();
        System.out.print("Nome da Comunidade: ");
        String comunidade = input.nextLine();

        for (Comunidade i: Sistema.getComunidades()) {
            if (i.getComunidade().equals(comunidade)) {
                System.out.println("Não foi possível criar a comunidade\nEsse nome já está sendo usado\n");
                return;
            }
        }

        System.out.print("Descrição da Comunidade: ");
        String descricao = input.nextLine();

        System.out.printf("Criar Comunidade %s?\n(1) Sim \t (0) Não\n", comunidade);
        if (input.nextInt() == 1) {
            Perfil admin = new Perfil();

            for (Perfil i: Sistema.getUsuarios()) {
                if (i.getLogin().equals(login)) {
                    admin = i;
                    break;
                }
            }

            Comunidade novo = new Comunidade(comunidade, descricao, admin);
            admin.setComunidades(novo);
            Sistema.setComunidades(novo);
            System.out.println("Comunidade criada\n");
        }
        else {
            System.out.println("Criação de Comunidade interrompida\n");
        }

    }

    public static void entraComunidade(String login) {

        input.nextLine();
        System.out.print("Digite o nome da comunidade: ");
        String comunidade = input.nextLine();

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
        System.out.println("Comunidade não encontrada\nVocê não entrou na comunidade");
    }

    public static void todasComunidades() {
        for (Comunidade comunidade: Sistema.getComunidades()){
            System.out.printf("Nome: %s\nDescrição: %s\n--------------------", comunidade.getComunidade(), comunidade.getDescricao());
        }
    }
}
