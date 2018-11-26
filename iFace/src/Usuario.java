import java.util.Scanner;

public class Usuario {

    static Scanner input = new Scanner(System.in);

    public static int novoUsuario(String nome, String login, String senha) {

        for (Perfil i : Sistema.getUsuarios()) {
            if (i.getLogin().equals(login) || i.getNome().equals(nome)) {
                System.out.println("\nUsuário já cadastrado\nDigite (1) para tentar novamente ou (0) para sair");
                int opc = input.nextInt();
                if (opc == 0) {
                    return 1;
                }
                return 0;
            }
        }

        Perfil novo = new Perfil(nome, login, senha);
        Sistema.setUsuarios(novo); // Adicionando o usuario a lista
        entrar(login, senha); // Entrando com o novo usuario

        return 1;

    }

    public static int entrar(String login, String senha) {

        for (Perfil i : Sistema.getUsuarios()) {
            if (i.getLogin().equals(login) && i.getSenha().equals(senha)) {
                System.out.println("Login efetuado\n");
                Menu.inicio(i);
                return 1;
            }
        }

        System.out.println("\nLogin ou senha incorretos\nDigite (1) para tentar novamente ou (0) para sair");
        int opc = input.nextInt();
        if (opc == 0) {
            return 1;
        }

        return 0;

    }

    public static void editarPerfil(Perfil usuario) {

        System.out.println("\n(1) Editar login\n(2) Editar nome\n(3) Editar Senha\n(0) Cancelar");
        int opc = input.nextInt();

        if (opc == 1) {
            input.nextLine();
            System.out.print("Novo login: ");
            String login = input.nextLine();

            usuario.setLogin(login);
            System.out.println("Login alterado\n");
        }
        else if (opc == 2){
            input.nextLine();
            System.out.print("Novo nome: ");
            String nome = input.nextLine();

            usuario.setNome(nome);
            System.out.println("Nome alterado\n");
        }
        else if (opc == 3){
            input.nextLine();
            System.out.println("Nova senha: ");
            String senha = input.nextLine();

            usuario.setSenha(senha);
            System.out.println("Senha alterada\n");

        }

    }

    public static void informacoes(String login) {

        System.out.println("Informações do usuário:\n(1) Perfil\n(2) Amigos\n(3) Comunidades\n(0) Cancelar");
        int opc = input.nextInt();
        Perfil usuario = new Perfil();

        for (Perfil i: Sistema.getUsuarios()) {
            if (i.getLogin().equals(login)) {
                usuario = i;
            }
        }

        if (opc == 1) {
            System.out.printf("Nome: %s\nLogin: %s\nSenha: %s\n", usuario.getNome(), usuario.getLogin(), usuario.getSenha());
        }
        else if (opc == 2) {

            System.out.printf("Você tem %d amigos\n", usuario.getAmigo().size());

            if (usuario.getAmigo().size() > 0) {
                System.out.println("Seus amigos");
                for (String j: usuario.getAmigo()) {
                    System.out.printf("%s\n", j);
                }
            }

        }
        else if (opc == 3) {

            System.out.printf("Você está em %d Comunidades\n", usuario.getComunidades().size());

            if (usuario.getComunidades().size() > 0) {
                System.out.println("Comunidades:");
                for (Comunidade j: usuario.getComunidades()) {
                    System.out.printf("Nome: %s\nDescrição: %s\n--------------------\n", j.getComunidade(), j.getDescricao());
                }
            }

        }

    }

}
