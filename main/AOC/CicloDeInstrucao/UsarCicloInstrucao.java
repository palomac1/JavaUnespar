package AOC.CicloDeInstrucao;

import java.util.Scanner;

public class UsarCicloInstrucao {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        ControlaCicloInstrucao ci = new ControlaCicloInstrucao(); // Instancia um objeto da classe ControlaCicloInstrucao para controlar o ciclo de instrução

        // Menu de opções
        do {
            System.out.println("==================================================================================");
            System.out.printf("= OPÇÕES: =\n");
            System.out.println("==================================================================================");
            System.out.printf("1. INSERIR\n");
            System.out.printf("2. VER INSTRUÇÕES\n");
            System.out.printf("3. EXECUTAR\n");
            System.out.printf("4. SAIR DO PROGRAMA\n");
            System.out.println("==================================================================================");
            System.out.printf("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1:
                    ci.entradaUsuario();
                    break;
                case 2:
                    ci.verInstrucoes();
                    break;
                case 3:
                    ci.executarTodasInstrucoes();
                    break;
                case 4:
                    System.out.println("Encerrando!");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 4);

        scanner.close();
    }
}
