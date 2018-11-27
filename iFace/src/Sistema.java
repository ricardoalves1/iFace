import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Sistema {

    private static ArrayList<Perfil> usuarios = new ArrayList<>();
    private static ArrayList<Comunidade> comunidades = new ArrayList<>();

    public static ArrayList<Perfil> getUsuarios() {
        return usuarios;
    }

    public static void setUsuarios(Perfil usuario) {
        Sistema.usuarios.add(usuario);
    }

    public static ArrayList<Comunidade> getComunidades() {
        return comunidades;
    }

    public static void setComunidades(Comunidade comunidade) {
        Sistema.comunidades.add(comunidade);
    }

    public static void removerConta(Perfil usuario) {

        System.out.println("Deseja mesmo remover sua conta?\nComunidades que você é o administrador serão apagadas\n" +
                "(1) Sim \t (2) Não");
        Scanner input = new Scanner(System.in);

        if (input.nextInt() == 1) {

            // Remover usuário dos perfis dos amigos
            for (Perfil i: Sistema.getUsuarios()) {
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
            for (Perfil i: Sistema.getUsuarios()) {
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
                    for (Perfil j: i.getMembro()) {
                        j.getComunidades().remove(i);
                    }
                    itComunidade.remove();
                }
                else {
                    // Se for um membro
                    i.getMembro().remove(usuario);
                }

            }
            // Remover da lista de usuarios:
            Sistema.getUsuarios().remove(usuario);

            System.out.println("Conta reovida");
        }
        else {
            Menu.inicio(usuario);
        }
    }

}