package usuario;

import comunidade.Comunidade;
import mensagem.Mensagem;
import sistema.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

public class Perfil implements Funcoes {

    private String nome;
    private String login;
    private String senha;
    private ArrayList<Perfil> amigo = new ArrayList<>();
    private ArrayList<Perfil> pedido = new ArrayList<>();
    private ArrayList<Mensagem> mensagens = new ArrayList<>();
    private ArrayList<Comunidade> comunidades = new ArrayList<>();

    public Perfil(String nome, String login, String senha) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
    }

    public Perfil() {}

    Scanner input = new Scanner(System.in);

    @Override
    public void criar(Perfil perfil) {

        Sistema.setUsuarios(perfil);
        System.out.println("\nCadastro realizado\nEntrando...");
        entrar(perfil);

    }

    @Override
    public void entrar(Perfil usuario) {

        if (Sistema.getUsuarios().contains(usuario)) {
            Menu.menuUsuario(usuario);
        }

    }

    private void editar(Perfil perfil) {

        while (true) {
            System.out.println("\n(1) - Editar login\n" +
                    "(2) - Editar nome\n" +
                    "(3) - Editar senha\n" +
                    "(0) - Voltar");

            try {
                int opc = input.nextInt();
                input.nextLine();

                switch (opc) {
                    case 0:
                        return;
                    case 1: // Editar login
                        System.out.print("Novo login: ");
                        String login = input.nextLine();
                        if (login.length() < 3) {
                            System.out.println("O Login precisa ter no mínimo 3 caracteres\nOperação cancelada");
                        }
                        else if (checarLogin(login)) {
                            System.out.println("O login já está cadastrado\nOperação cancelada");
                        }
                        else {
                            perfil.setLogin(login);
                            System.out.println("Login alterado");
                        }
                        break;
                    case 2: // Editar nome
                        System.out.print("Novo nome: ");
                        String nome = input.nextLine();
                        if (nome.length() < 3) {
                            System.out.println("O Nome precisa ter no mínimo 3 caracteres\nOperação cancelada");
                        }
                        else {
                            perfil.setNome(nome);
                            System.out.println("Nome alterado");
                        }
                        break;
                    case 3: // Editar senha
                        System.out.print("Nova senha: ");
                        String senha = input.nextLine();
                        if (senha.length() < 3) {
                            System.out.println("A Senha precisa ter no mínimo 3 caracteres\nOperação cancelada");
                        }
                        else {
                            perfil.setSenha(senha);
                            System.out.println("Senha alterado");
                        }
                        break;
                    default:
                        System.out.println("Opção inválida");
                }

            } catch(InputMismatchException e){
                input.nextLine();
                System.out.println("Opção inválida");
            }
        }

    }

    @Override
    public void apagar(Object perfil) {

        Perfil usuario = (Perfil) perfil;

        System.out.println("Deseja mesmo remover sua conta?\n" +
                "Comunidades administradas por você serão apagadas\n" +
                "(1) Sim \t (Outro) Não");

        try {

            if (input.nextInt() == 1) {

                // Remover usuário dos perfis dos amigos
                for (Perfil i : Sistema.getUsuarios()) {
                    i.getAmigo().remove(usuario);

                    // Apagar mensagens para outros usuários
                    Iterator<Mensagem> itMensagem = i.getMensagens().iterator();
                    while (itMensagem.hasNext()) {
                        Mensagem x = itMensagem.next();
                        if (x.getUsuario() == usuario) {
                            itMensagem.remove();
                        }
                    }
                }

                // Remover pedidos de amizade feito pelo usuário
                for (Perfil i : Sistema.getUsuarios()) {
                    i.getPedido().remove(usuario);
                }

                // Remover usuário das comunidades
                Iterator<Comunidade> itComunidade = Sistema.getComunidades().iterator();
                while (itComunidade.hasNext()) {
                    Comunidade i = itComunidade.next();

                    // Mensagens enviadas para comunidades
                    Iterator<Mensagem> itMsgComunidade = i.getMensagens().iterator();
                    while (itMsgComunidade.hasNext()) {
                        Mensagem j = itMsgComunidade.next();
                        if (j.getUsuario() == usuario) {
                            itMsgComunidade.remove();
                        }
                    }

                    // Se for admin de alguma comunidade
                    if (i.getAdmin() == usuario) {
                        //Apagar comunidade
                        for (Perfil j : i.getMembro()) {
                            j.getComunidades().remove(i);
                        }
                        itComunidade.remove();
                    } else {
                        // Se for um membro
                        i.getMembro().remove(usuario);
                    }

                }
                // Remover da lista de usuarios:
                Sistema.getUsuarios().remove(usuario);

                System.out.println("Conta removida");
            }

        } catch (InputMismatchException e) {
            input.nextLine();
            System.out.println("Operação cancelada\nA conta não foi removida");
        }

    }

    public Perfil encontrarPerfil(String login, String senha) {

        for (Perfil i: Sistema.getUsuarios()) {
            if (i.getLogin().equals(login) && i.getSenha().equals(senha)) {
                return i;
            }
        }

        System.out.println("\nUsuário não encontrado\nLogin ou senha incorretos");
        return null;
    }

    public boolean checarLogin(String login) {

        for (Perfil i: Sistema.getUsuarios()) {
            if (i.getLogin().equals(login)) {
                return true;
            }
        }

        return false;
    }

    public void verPerfil(Perfil perfil) {

        System.out.println("\n---------------------" +
                "\n Nome: " + perfil.getNome() +
                "\n Login: " + perfil.getLogin());

        System.out.println("---------------------\n" +
                "(1) - Editar Perfil\n" +
                "(0) - Voltar");

        try {
            int opc = input.nextInt();

            switch (opc){
                case 0:
                    return;
                case 1:
                    editar(perfil);
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        } catch (InputMismatchException e) {
            System.out.println("Opção inválida");
        }

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public ArrayList<Perfil> getAmigo() {
        return amigo;
    }

    public void setAmigo(Perfil usuario) {
        amigo.add(usuario);
    }

    public ArrayList<Perfil> getPedido() {
        return pedido;
    }

    public void setPedido(Perfil usuario) {
        pedido.add(usuario);
    }

    public ArrayList<Mensagem> getMensagens() {
        return mensagens;
    }

    public void setMensagens(Mensagem mensagem) {
        mensagens.add(mensagem);
    }

    public ArrayList<Comunidade> getComunidades() {
        return comunidades;
    }

    public void setComunidades(Comunidade comunidade) {
        comunidades.add(comunidade);
    }
}