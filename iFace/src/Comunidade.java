import java.util.ArrayList;
import java.util.Scanner;

public class Comunidade {

    private String comunidade; // Nome da comunidade
    private String descricao;
    private Perfil admin;
    private ArrayList<Mensagem> mensagens = new ArrayList<>();
    private static ArrayList<Perfil> membro = new ArrayList<>();

    public Comunidade(String comunidade, String descricao, Perfil admin) {
        this.comunidade = comunidade;
        this.descricao = descricao;
        this.admin = admin;
    }

    public static void criarComunidade(Perfil usuario) {

        Scanner input = new Scanner(System.in);

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

        System.out.printf("\nCriar Comunidade %s?\n(1) Sim \t (0) Não\n", comunidade);
        if (input.nextInt() == 1) {
            Comunidade novo = new Comunidade(comunidade, descricao, usuario);
            usuario.setComunidades(novo);
            Sistema.setComunidades(novo);
            System.out.printf("\nComunidade %s criada\n", comunidade);
        }
        else {
            System.out.println("Criação de Comunidade interrompida\n");
        }

    }

    public static void entraComunidade(Perfil usuario) {

        Scanner input = new Scanner(System.in);

        System.out.print("\nEntrar em uma comunidade\nDigite o nome da comunidade: ");
        String comunidade = input.nextLine();

        for (Comunidade i: Sistema.getComunidades()) {
            if (i.getComunidade().equals(comunidade)) {
                if (!usuario.getComunidades().contains(i)) {
                    Comunidade.membro.add(usuario);
                    usuario.setComunidades(i);
                    System.out.printf("Você entrou na comunidade %s\n", comunidade);
                    return;
                }
                else {
                    System.out.printf("Você já é membro da comunidade %s\n", comunidade);
                    return;
                }
            }
        }
        System.out.printf("Comunidade %s não encontrada\nVocê não entrou na comunidade\n", comunidade);
    }

    public static void adminComunidade(Perfil usuario) {

        if (usuario.getComunidades().size() == 0) {
            System.out.println("Você não é administrador de uma comunidade");
            return;
        }

        Scanner input = new Scanner(System.in);
        int opc = 1;
        ArrayList<Integer> indice = new ArrayList<>();

        System.out.println("\nSelecione a comunidade que deseja administrar\n");
        for (Comunidade i: usuario.getComunidades()) {
            if (i.getAdmin() == usuario) {
                indice.add(usuario.getComunidades().indexOf(i));
                System.out.printf("(%d) %s\n", opc++, i.getComunidade());
            }
        }
        System.out.println("(0) Cancelar");

        opc = input.nextInt();
        if (opc == 0) {
            return;
        }
        Comunidade.editarComunidade(usuario.getComunidades().get(indice.get(opc - 1)));

    }

    private static void editarComunidade(Comunidade comunidade) {

        Scanner input = new Scanner(System.in);

        System.out.printf("\nAdministrar comunidade %s\n" +
                "(1) Alterar nome\n" +
                "(2) Alterar descrição\n" +
                "(3) Membros\n" +
                "(4) Remover membro\n" +
                "(5) Apagar comunidade\n" +
                "(0) Cancelar\n", comunidade.getComunidade());

        int opc = input.nextInt();

        if (opc == 1) {
            System.out.print("Novo nome da comunidade: ");
            String nome = input.nextLine();

            for (Comunidade i: Sistema.getComunidades()) {
                if (i.getComunidade().equals(nome)) {
                    System.out.println("Não foi possível alterar o nome da comunidade\nEsse nome já está sendo usado\n");
                    return;
                }
            }
            comunidade.setComunidade(nome);
            System.out.printf("Nome da comunidade alterado para: %s\n", nome);
        }
        else if (opc == 2) {
            System.out.print("Nova descrição da comunidade: ");
            String descricao = input.nextLine();
            comunidade.setDescricao(descricao);
            System.out.println("Descrição alterada");
        }
        else if (opc == 3) {
            if (comunidade.getMembro().size() == 0) {
                System.out.println("Ainda não há membros na comunidade");
                return;
            }

            System.out.printf("Membros da comunidade %s:\n", comunidade.getComunidade());
            for (Perfil i: comunidade.getMembro()) {
                System.out.printf("- %s\n", i.getNome());
            }
        }
        else if (opc == 4) {
            if (comunidade.getMembro().size() == 0) {
                System.out.println("Ainda não há membros na comunidade");
                return;
            }

            System.out.print("Remover membro\nNome do membro: ");
            input.nextLine();
            String nome = input.nextLine();
            System.out.printf("Deseja mesmo remover %s da comunidade?\n(1) Sim \t (2) Não\n", nome);
            if (input.nextInt() == 1) {
                for (Perfil i: Sistema.getUsuarios()) {
                    if (i.getNome().equals(nome)) {
                        comunidade.getMembro().remove(i);
                        System.out.println("Membro removido");
                        return;
                    }
                }
                System.out.println("Não foi encontrado membro com esse nome");
            }
        }
        else if (opc == 5) {
            Comunidade.apagarComunidade(comunidade);
            System.out.printf("Comunidade %s apagada\n", comunidade.getComunidade());
        }
    }

    public static void apagarComunidade(Comunidade comunidade) {

        for (Perfil i: comunidade.getMembro()) {
            i.getComunidades().remove(comunidade);
        }

        comunidade.getAdmin().getComunidades().remove(comunidade);
        Sistema.getComunidades().remove(comunidade);
    }

    public static void todasComunidades() {

        if (Sistema.getComunidades().size() == 0) {
            System.out.println("Ainda não há comunidades registradas");
        }

        for (Comunidade comunidade: Sistema.getComunidades()){
            System.out.printf("\nNome: %s\nDescrição: %s\n--------------------\n", comunidade.getComunidade(), comunidade.getDescricao());
        }
    }

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

    public void setMensagens(Perfil usuario, String mensagem) {
        this.mensagens.add(new Mensagem(usuario, mensagem));
    }

    public ArrayList<Perfil> getMembro() {
        return Comunidade.membro;
    }

}