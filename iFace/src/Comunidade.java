import java.util.ArrayList;
import java.util.Scanner;

public class Comunidade {

    private String comunidade; // Nome da comunidade
    private String descricao;
    private Perfil admin;
    private ArrayList<Mensagem> mensagens = new ArrayList<>();
    private ArrayList<Perfil> membro = new ArrayList<>();

    public Comunidade(String comunidade, String descricao, Perfil admin) {
        this.comunidade = comunidade;
        this.descricao = descricao;
        this.admin = admin;
    }

    public static void criarComunidade(Perfil usuario) {

        Scanner input = new Scanner(System.in);

        System.out.print("Nome da Comunidade: ");
        String comunidade = input.nextLine();

        if (!checarComunidade(comunidade)) {
            System.out.println("Não foi possível criar a comunidade\nEsse nome já está sendo usado\n");
            return;
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
                if (!usuario.getComunidades().contains(i) && !i.getMembro().contains(usuario) && i.getAdmin() != usuario) {
                    i.setMembro(usuario);
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
            System.out.println("Você não está em uma comunidade");
            return;
        }

        Scanner input = new Scanner(System.in);
        int opc = 1;

        System.out.println("\nSelecione a comunidade que deseja administrar\n");
        for (Comunidade i: usuario.getComunidades()) {
            System.out.printf("(%d) %s\n", opc++, i.getComunidade());
        }
        System.out.println("(0) Cancelar");

        opc = input.nextInt();
        if (opc == 0) {
            return;
        }
        editarComunidade(usuario, usuario.getComunidades().get(opc - 1));

    }

    private static void editarComunidade(Perfil usuario, Comunidade comunidade) {

        Scanner input = new Scanner(System.in);

        if (comunidade.getAdmin() != usuario) {
            System.out.printf("\nAdministrar comunidade %s\n" +
                    "(1) Membros\n" +
                    "(2) Sair da comunidade\n" +
                    "(0) Cancelar\n", comunidade.getComunidade());
            int opc = input.nextInt();

            if (opc == 1) {
                membrosComunidade(comunidade);
            }
            else if (opc == 2) {
                comunidade.getMembro().remove(usuario);
                usuario.getComunidades().remove(comunidade);
                System.out.printf("Você saiu da comunidade %s\n", comunidade.getComunidade());
            }
        }
        else {
            System.out.printf("\nAdministrar comunidade %s\n" +
                    "(1) Alterar nome\n" +
                    "(2) Alterar descrição\n" +
                    "(3) Membros\n" +
                    "(4) Remover membro\n" +
                    "(5) Mudar administrador\n" +
                    "(6) Sair da comunidade\n" +
                    "(7) Apagar comunidade\n" +
                    "(0) Cancelar\n", comunidade.getComunidade());

            int opc = input.nextInt();
            if (opc == 1) {

                System.out.print("Novo nome da comunidade: ");
                input.nextLine();
                String nome = input.nextLine();
                if(!checarComunidade(nome)) {
                    System.out.println("Não foi possível alterar o nome da comunidade\nEsse nome já está sendo usado\n");
                    return;
                }

                comunidade.setComunidade(nome);
                System.out.printf("Nome da comunidade alterado para: %s\n", nome);

            }
            else if (opc == 2) {

                System.out.print("Nova descrição da comunidade: ");
                input.nextLine();
                comunidade.setDescricao(input.nextLine());
                System.out.println("Descrição alterada");

            }
            else if (opc == 3) {
                membrosComunidade(comunidade);
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
                    for (Perfil i : Sistema.getUsuarios()) {
                        if (i.getNome().equals(nome)) {
                            comunidade.getMembro().remove(i);
                            i.getComunidades().remove(comunidade);
                            System.out.println("Membro removido");
                            return;
                        }
                    }
                    System.out.println("Não foi encontrado membro com esse nome");
                }

            }
            else if (opc == 5) {
                System.out.printf("Mudar administrador da comunidade %s\nNome do usuário: ", comunidade.getComunidade());
                input.nextLine();
                String nome = input.nextLine();
                if (usuario.getNome().equals(nome)){
                    System.out.println("Nome inválido");
                    return;
                }
                comunidade.setAdmin(nome);
                comunidade.setMembro(usuario);
            }
            else if (opc == 6) {
                if (comunidade.getMembro().size() == 0) {
                    System.out.println("Ao sair da comunidade ela será apagada\nDeseja mesmo sair da comunidade?\n(1) Sim \t (0) Não");
                    if (input.nextInt() == 1) {
                        apagarComunidade(comunidade);
                        System.out.println("Você saiu da comunidade\nA comunidade foi apagada");
                    }
                }
                else{
                    System.out.println("Sair da comunidade\n" +
                            "(1) Deixar administração para um membro\n" +
                            "(2) Sair e apagar comunidade\n" +
                            "(0) Cancelar");
                    int esc = input.nextInt();

                    if (esc == 1) {
                        System.out.print("Deixar a comunidade para o membro: ");
                        input.nextLine();
                        String nome = input.nextLine();
                        if (usuario.getNome().equals(nome)){
                            System.out.println("Nome incorreto\nSair da comunidade falhou");
                            return;
                        }
                        comunidade.setAdmin(nome);
                    }
                    else if (esc == 2) {
                        apagarComunidade(comunidade);
                        System.out.println("Você saiu da comunidade\nA comunidade foi apagada");
                    }
                }
            }
            else if (opc == 7) {
                apagarComunidade(comunidade);
                System.out.printf("Comunidade %s apagada\n", comunidade.getComunidade());
            }

        }
    }

    public static void apagarComunidade(Comunidade comunidade) {

        for (Perfil i: comunidade.getMembro()) {
            i.getComunidades().remove(comunidade);
        }

        comunidade.getAdmin().getComunidades().remove(comunidade);
        Sistema.getComunidades().remove(comunidade);
    }

    private static boolean checarComunidade(String nome) { // Verificar se existe cmunidade com o nome

        for (Comunidade i : Sistema.getComunidades()) {
            if (i.getComunidade().equals(nome)) {
                return false;
            }
        }
        return true;

    }

    private static void membrosComunidade(Comunidade comunidade) { // Listar membros da comunidade

        System.out.printf("Administrador: %s\n", comunidade.getAdmin().getNome());
        if (comunidade.getMembro().size() == 0){
            System.out.println("Não há membros");
            return;
        }
        System.out.printf("Membros da comunidade %s:\n", comunidade.getComunidade());
        for (Perfil i : comunidade.getMembro()) {
            System.out.printf("- %s\n", i.getNome());
        }

    }

    public static void todasComunidades() { // Listar todas as comunidades

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

    public void setAdmin(String nome) {
        for (Perfil i: Sistema.getUsuarios()) {
            if (i.getNome().equals(nome)){
                admin = i;
                membro.remove(i);
                System.out.println("Administrador atualizado");
                return;
            }
        }
        System.out.println("Nome não encontrado");
    }

    public ArrayList<Mensagem> getMensagens() {
        return mensagens;
    }

    public void setMensagens(Perfil usuario, String mensagem) {
        this.mensagens.add(new Mensagem(usuario, mensagem));
    }

    public ArrayList<Perfil> getMembro() {
        return membro;
    }

    public void setMembro(Perfil usuario) {
        membro.add(usuario);
    }

}