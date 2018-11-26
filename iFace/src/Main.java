import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        int opc;

        while (true) {

            System.out.println("\n(1) Entrar \n(2) Criar Conta \n(0) Sair");
            opc = input.nextInt();

            if (opc == 1) { // Entrar

                input.nextLine();

                System.out.print("\nLogin: ");
                String login = input.nextLine();

                System.out.print("Senha: ");
                String senha = input.nextLine();

                Usuario.entrar(login, senha);

            }
            else if (opc == 2) { // Novo usuario

                input.nextLine();

                System.out.print("\nLogin: ");
                String login = input.nextLine();

                System.out.print("Nome: ");
                String nome = input.nextLine();

                System.out.print("Senha: ");
                String senha = input.nextLine();

                Usuario.novoUsuario(nome, login, senha);

            }
            else{
                return;
            }

        }

    }

}