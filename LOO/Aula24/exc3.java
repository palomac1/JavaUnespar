package LOO.Aula24;

import java.util.Scanner;

public class exc3 {

    public static void main(String[] args) {
        String loginOriginal = "java8";
        String senhaOriginal = "java8";
        int tentativas = 3;

        Scanner scanner = new Scanner(System.in);

        while (tentativas > 0) {
            System.out.println("Digite seu login:");
            String login = scanner.nextLine();

            System.out.println("Digite sua senha:");
            String senha = scanner.nextLine();

            if (login.equals(loginOriginal) && senha.equals(senhaOriginal)) {
                System.out.println("Acesso liberado!");
                break;
            } else {
                tentativas--;
                System.out.println("Dados incorretos. Você tem mais " + tentativas + " tentativas.");
            }
        }

        if (tentativas == 0) {
            System.out.println("Tentativas esgotadas. Acesso negado.");
        }
        scanner.close();
    }
}