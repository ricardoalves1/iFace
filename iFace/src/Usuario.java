import java.util.Scanner;

public class Usuario {

    public static Scanner input = new Scanner(System.in);

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
                Menu.inicio(login);
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

    public static void editarPerfil(String login) {

        System.out.println("\n(1) Editar nome\n(2) Editar Senha\n(0) Cancelar");
        int opc = input.nextInt();

        if (opc == 1){
            input.nextLine();
            System.out.print("Novo nome: ");
            String nome = input.nextLine();

            for (Perfil i : Sistema.getUsuarios()) {
                if (i.getLogin().equals(login)) {
                    i.setNome(nome);
                    System.out.println("Nome alterado\n");
                }
            }
        }
        else if (opc == 2){
            input.nextLine();
            System.out.println("Nova senha: ");
            String senha = input.nextLine();

            for (Perfil i : Sistema.getUsuarios()) {
                if (i.getLogin().equals(login)) {
                    i.setSenha(senha);
                    System.out.println("Senha alterada\n");
                }
            }
        }

    }

    public static void pedidoAmizade(String login, String nome) {

        for (Perfil i : Sistema.getUsuarios()) {
            if (i.getNome().equals(nome) && !i.getLogin().equals(login)) {
                i.setPedido(login);
                System.out.println("\nConvite enviado\n");
                return;
            }
        }
        System.out.println("\nOcorreu um erro...\nAmigo não adicionado\n");
    }

}
