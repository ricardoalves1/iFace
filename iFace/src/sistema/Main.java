package sistema;

import usuario.Perfil;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        int opc;

        System.out.println("\n   - iFace -   ");

        while (true) {

            Perfil perfil = new Perfil();
            String login, nome, senha;

            try {
                System.out.println("\n-----------------\n" +
                        "(1) Fazer login \n" +
                        "(2) Criar Conta \n" +
                        "(0) Sair        \n" +
                        "-----------------");
                opc = input.nextInt();
                switch (opc) {
                    case 0:
                        return;
                    case 1:
                        // Entrar
                        input.nextLine();

                        System.out.print("\nLogin: ");
                        login = input.nextLine();

                        System.out.print("Senha: ");
                        senha = input.nextLine();

                        perfil.entrar(perfil.encontrarPerfil(login, senha));
                        break;
                    case 2:
                        // Criar usuário
                        input.nextLine();

                        System.out.print("\nLogin: ");
                        login = input.nextLine();
                        if (login.length() < 3) {
                            System.out.println("O Login precisa ter no mínimo 3 caracteres\nCadastro cancelado");
                            break;
                        } else if (perfil.checarLogin(login)) {
                            System.out.println("Esse Login já está cadastrado\nCadastro cancelado");
                            break;
                        }

                        System.out.print("Nome: ");
                        nome = input.nextLine();
                        if (nome.length() < 3) {
                            System.out.println("O Nome precisa ter no mínimo 3 caracteres\nCadastro cancelado");
                            break;
                        }

                        System.out.print("Senha: ");
                        senha = input.nextLine();
                        if (senha.length() < 3) {
                            System.out.println("A Senha precisa ter no mínimo 3 caracteres\nCadastro cancelado");
                            break;
                        }

                        perfil.criar(new Perfil(nome, login, senha));
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
}
