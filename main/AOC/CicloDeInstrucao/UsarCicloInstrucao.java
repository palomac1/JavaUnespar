package AOC.CicloDeInstrucao;
import java.util.Scanner; // Importa a classe Scanner para receber a entrada do usuário

// Classe para usar o ciclo de instrução e realizar as operações de inserir, ver instruções e executar
public class UsarCicloInstrucao {

    // Tabela de cores ANSI
    public static final String RESET = "\033[0m";
    public static final String PURPLE = "\033[0;35m";
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        ControlaCicloInstrucao ci = new ControlaCicloInstrucao(); // Instancia um objeto da classe ControlaCicloInstrucao para controlar o ciclo de instrução

        System.out.println(PURPLE + "--- Arquitetura e Organização de Computadores ---\n  " + RESET);
        System.out.println("Trabalho de AOC  - 2° Bimestre - C.C UNESPAR");
        System.out.println("Paloma de Castro Leite - 2ª Ano - 29.07.24");
        System.out.println(PURPLE + "\n --- SIMULADOR DE CICLO DE INSTRUÇÕES --- " + RESET);

        // Menu de opções
        do {
            System.out.println("\n==================================================================================");
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


