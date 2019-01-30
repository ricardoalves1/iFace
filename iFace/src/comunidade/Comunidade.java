package comunidade;

import mensagem.Mensagem;
import mensagem.MensagemComunidade;
import sistema.Funcoes;
import sistema.Sistema;
import usuario.Perfil;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Comunidade implements Funcoes {

    private String comunidade;
    private String descricao;
    private Perfil admin;
    private ArrayList<Mensagem> mensagens = new ArrayList<>();
    private ArrayList<Perfil> membro = new ArrayList<>();

    private Comunidade(String comunidade, String descricao, Perfil admin) {
        this.comunidade = comunidade;
        this.descricao = descricao;
        this.admin = admin;
    }

    public Comunidade() {}

    Scanner input = new Scanner(System.in);

    public void menuComunidade(Perfil usuario) {

        while (true) {
            System.out.println("\n(1) - Minhas comunidades (" + usuario.getComunidades().size() + ")\n" +
                    "(2) - Mensagens\n" +
                    "(3) - Criar comunidade\n" +
                    "(4) - Entrar em uma comunidade\n" +
                    "(5) - Administrar comunidades\n" +
                    "(0) - Voltar");

            try {
                int opc = input.nextInt();
                input.nextLine();

                switch (opc) {
                    case 0:
                        return;
                    case 1:
                        verComunidade(usuario);
                        break;
                    case 2:
                        new MensagemComunidade().menuMensagem(usuario);
                        break;
                    case 3:
                        criar(usuario);
                        break;
                    case 4:
                        entrar(usuario);
                        break;
                    case 5:
                        adminComunidade(usuario);
                        break;
                    default:
                        System.out.println("Opção inválida");
                }

            } catch (InputMismatchException e) {
                input.nextLine();
                System.out.println("Opção inválida");
            }

        }

    }

    private void verComunidade(Perfil usuario) {

        if (usuario.getComunidades().size() == 0) {
            System.out.println("Você não faz parte de comunidades");
            return;
        }

        System.out.println(" - Comunidades - \n");

        for (Comunidade i: usuario.getComunidades()) {
            System.out.printf("%s: \"%s\"\n", i.getComunidade(), i.getDescricao());
        }

    }

    @Override
    public void criar(Perfil usuario) {

        System.out.print("Nome da Comunidade: ");
        String comunidade = input.nextLine();

        if (comunidade.length() < 3) {
            System.out.println("Nome da Comunidade muito pequeno\n" +
                    "O Nome precisa ter no mínimo 3 caracteres\n");
            criar(usuario);
            return;
        }

        for (Comunidade i: Sistema.getComunidades()) {
            if (i.getComunidade().equals(comunidade)) {
                System.out.println("Não foi possível criar a comunidade\nEsse nome já está sendo usado\n");
                return;
            }
        }

        System.out.print("Descrição da Comunidade: ");
        String descricao = input.nextLine();


        Comunidade novo = new Comunidade(comunidade, descricao, usuario);
        usuario.setComunidades(novo);
        Sistema.setComunidades(novo);
        System.out.printf("\nA Comunidade %s foi criada\n", comunidade);

    }

    @Override
    public void entrar(Perfil usuario) {

        System.out.print("\n - Entrar em uma comunidade - \n");
        System.out.print("Digite o nome da comunidade: ");
        String comunidade = input.nextLine();

        for (Comunidade i: Sistema.getComunidades()) {
            if (i.getComunidade().equals(comunidade)) {

                if (!usuario.getComunidades().contains(i) && !i.getMembro().contains(usuario) && i.getAdmin() != usuario) {
                    i.setMembro(usuario);
                    usuario.setComunidades(i);
                    System.out.printf("\nVocê entrou na comunidade %s\n", comunidade);
                }
                else {
                    System.out.printf("\nVocê já é membro da comunidade %s\n", comunidade);
                }

                return;
            }
        }
        System.out.printf("\nA Comunidade %s não foi encontrada\n", comunidade);

    }

    private void adminComunidade(Perfil usuario) {

        if (usuario.getComunidades().size() == 0) {
            System.out.println("Você não está em uma comunidade");
            return;
        }

        while (true) {
            int opc = 1;
            System.out.println("\nSelecione a comunidade que deseja administrar\n");
            for (Comunidade i: usuario.getComunidades()) {
                System.out.printf("(%d) %s\n", opc++, i.getComunidade());
            }
            System.out.println("(0) Voltar");

            try {
                opc = input.nextInt();
                if (opc == 0) {
                    return;
                }
                editar(usuario, usuario.getComunidades().get(opc - 1));

            } catch (InputMismatchException e) {
                input.nextLine();
                System.out.println("Opção inválida");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Opção inválida");
            }
        }

    }

    private void editar(Perfil usuario, Comunidade comunidade) {

        if (comunidade.getAdmin() != usuario) { // Usuário não é o adminstrador

            System.out.printf("\nAdministrar comunidade %s\n" +
                    "(1) Membros\n" +
                    "(2) Sair da comunidade\n" +
                    "(0) Voltar\n", comunidade.getComunidade());

            try {
                int opc = input.nextInt();

                switch (opc) {
                    case 0:
                        return;
                    case 1: // Membros

                        System.out.printf("\nAdministrador: %s\n", comunidade.getAdmin().getNome());
                        if (comunidade.getMembro().size() == 0){
                            System.out.println("Não há membros");
                            break;
                        }

                        System.out.println("Membros da comunidade:");
                        for (Perfil i : comunidade.getMembro()) {
                            System.out.printf("- %s\n", i.getNome());
                        }
                        break;
                    case 2:
                        comunidade.getMembro().remove(usuario);
                        usuario.getComunidades().remove(comunidade);
                        System.out.printf("Você saiu da comunidade %s\n", comunidade.getComunidade());
                        break;
                    default:
                        System.out.println("Opção inválida");
                }
            } catch (InputMismatchException e) {
                input.nextLine();
                System.out.println("Opção inválida");
            }
        }
        else {  // Usuário é o administrador

            System.out.printf("\nAdministrar comunidade %s\n" +
                    "(1) Alterar nome\n" +
                    "(2) Alterar descrição\n" +
                    "(3) Membros\n" +
                    "(4) Remover membro\n" +
                    "(5) Mudar administrador\n" +
                    "(6) Sair da comunidade\n" +
                    "(7) Apagar comunidade\n" +
                    "(0) Voltar\n", comunidade.getComunidade());

            try {
                int opc = input.nextInt();
                switch (opc) {
                    case 0:
                        break;
                    case 1: // Alterar nome
                        System.out.print("Novo nome da comunidade: ");
                        input.nextLine();
                        String nome = input.nextLine();
                        if (nome.length() < 3) {
                            System.out.println("Nome da Comunidade muito pequeno\n" +
                                    "O Nome precisa ter no mínimo 3 caracteres\n");
                            criar(usuario);
                            return;
                        }

                        for (Comunidade i: Sistema.getComunidades()) {
                            if (i.getComunidade().equals(nome)) {
                                System.out.println("Não foi possível alterar o nome da comunidade\nEsse nome já está sendo usado\n");
                                return;
                            }
                        }

                        comunidade.setComunidade(nome);
                        System.out.printf("Nome da comunidade alterado para: %s\n", nome);
                        break;

                    case 2: // Alterar descrição
                        System.out.print("Nova descrição da comunidade: ");
                        input.nextLine();
                        comunidade.setDescricao(input.nextLine());
                        System.out.println("Descrição alterada");
                        break;

                    case 3: // Membros
                        System.out.printf("Administrador: %s\n", comunidade.getAdmin().getNome());
                        if (comunidade.getMembro().size() == 0){
                            System.out.println("Não há membros");
                            break;
                        }

                        System.out.println("Membros da comunidade:");
                        for (Perfil i : comunidade.getMembro()) {
                            System.out.printf("- %s\n", i.getNome());
                        }
                        break;
                    case 4: // Remover membro

                        if (comunidade.getMembro().size() == 0) {
                            System.out.println("Ainda não há membros na comunidade");
                            return;
                        }

                        System.out.print("Remover membro\nNome do membro: ");
                        input.nextLine();
                        String membro = input.nextLine();

                        for (Perfil i : Sistema.getUsuarios()) {
                            if (i.getNome().equals(membro)) {
                                comunidade.getMembro().remove(i);
                                i.getComunidades().remove(comunidade);
                                System.out.println("Membro removido");
                                return;
                            }
                        }
                        System.out.println("Não foi encontrado membro com esse nome");
                        break;

                    case 5: // Mudar administrador
                        System.out.printf("Mudar administrador da comunidade %s\n" +
                                "Nome do usuário: ", comunidade.getComunidade());
                        input.nextLine();
                        String novoAdministrtador = input.nextLine();

                        if (usuario.getNome().equals(novoAdministrtador)) {
                            System.out.println("Nome inválido");
                        }
                        else {
                            for (Perfil i: Sistema.getUsuarios()) {
                                if (i.getNome().equals(novoAdministrtador)){
                                    comunidade.setAdmin(i);
                                    comunidade.getMembro().remove(i);
                                    comunidade.setMembro(usuario);
                                    System.out.println("Administrador atualizado");
                                    return;
                                }
                            }
                            System.out.println("Nome não encontrado");
                        }
                        break;
                    case 6: // Sair da Comunidade

                        if (comunidade.getMembro().size() == 0) {
                            System.out.println("Ao sair da comunidade ela será apagada\n" +
                                    "Deseja mesmo sair da comunidade?\n(1) Sim \t (Outro) Não");
                            if (input.nextInt() == 1) {
                                apagar(comunidade);
                                System.out.println("Você saiu da comunidade\nA comunidade foi apagada");
                            }
                        }
                        else {
                            System.out.println("Sair da comunidade\n" +
                                    "(1) Deixar administração para um membro\n" +
                                    "(2) Sair e apagar comunidade\n" +
                                    "(0) Voltar");
                            int esc = input.nextInt();

                            switch (esc) {
                                case 0:
                                    break;
                                case 1:
                                    System.out.print("Deixar a comunidade para o membro: ");
                                    input.nextLine();
                                    nome = input.nextLine();
                                    if (usuario.getNome().equals(nome)) {
                                        System.out.println("Nome incorreto\nVocê não saiu da comunidade");
                                        return;
                                    }

                                    for (Perfil i : Sistema.getUsuarios()) {
                                        if (i.getNome().equals(nome)) {
                                            comunidade.getMembro().remove(i);
                                            comunidade.setAdmin(i);
                                            System.out.println("Administrador alterado");
                                            return;
                                        }
                                    }
                                    System.out.println("Nome não encontrado");
                                    break;
                                case 2:
                                    apagar(comunidade);
                                    System.out.println("Você saiu da comunidade\nA comunidade foi apagada");
                                default:
                                    System.out.println("Opção inválida");
                            }
                        }
                        break;
                    case 7: // Apagar comunidade
                        apagar(comunidade);
                        System.out.printf("Comunidade %s apagada\n", comunidade.getComunidade());
                        break;
                    default:
                        System.out.println("Opção inválida");
                }
            } catch (InputMismatchException e) {
                input.nextLine();
                System.out.println("Opção inválida");
            }

        }

    }

    @Override
    public void apagar(Object x) {

        Comunidade comunidade = (Comunidade) x;

        for (Perfil i: comunidade.getMembro()) {
            i.getComunidades().remove(comunidade);
        }

        comunidade.getAdmin().getComunidades().remove(comunidade);
        Sistema.getComunidades().remove(comunidade);

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

    public void setMensagens(Mensagem mensagem) {
        this.mensagens.add(mensagem);
    }

    public ArrayList<Perfil> getMembro() {
        return membro;
    }

    public void setMembro(Perfil usuario) {
        this.membro.add(usuario);
    }
}
