package AOC.CicloDeInstrucao;

import java.util.Scanner;

public class UsarCicloInstrucao {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("==================================================================================");
            System.out.printf("OPÇÕES:\n");
            System.out.printf("1. INSERIR\n");
            System.out.printf("2. VER INSTRUÇÕES\n");
            System.out.printf("3. EXECUTAR\n");
            System.out.printf("4. SAIR\n");
            System.out.println("==================================================================================");
            System.out.printf("Escolha uma opção: ");
            opcao = scanner.nextInt();
        
            switch (opcao) {
                case 1:
                    System.out.printf("Digite a operação:\n");
                    break;
                case 2:
                    System.out.println("==================================================================================");
                    System.out.printf("= INSTRUÇÕES: =\n");
                    System.out.println("==================================================================================");
                    System.out.printf("COD - OP1 - OP2 - RESULTADOS\n");
                    System.out.printf("000001 - #POS -    - MBR <- #POS\n");
                    System.out.printf("000010 - #POS - #DADO - POS <- #DADO\n");
                    System.out.printf("000011 - #POS -    - MBR <- MBR + #POS\n");
                    System.out.printf("000100 - #POS -    - MBR <- MBR - #POS\n");
                    System.out.printf("000101 - #POS -    - MBR <- MBR * #POS\n");
                    System.out.printf("000110 - #POS -    - MBR <- MBR / #POS\n");
                    System.out.printf("000111 - #LIN -    - JUMP to #LIN\n");
                    System.out.println("001000 - #LIN -  - JUMP IF Z to #LIN\n");
                    System.out.println("001001 - #LIN -  - JUMP IF N to #LIN\n");
                    System.out.println("001010 -  -  - MBR <- raiz_qiadrada(MBR)\n");
                    System.out.println("001011 -  -  - MBR <- - MBR\n");
                    System.out.println("001111 - #POS  -  - #POS <- MBR\n");
                    System.out.println("001100 -   - NOP\n");
                    System.out.println("==================================================================================");
                    break;
                case 3:
                    System.out.printf("EXECUTANDO\n");
                    break;
                case 4:
                    System.out.printf("Sair\n");
                    break;
                default:
                    System.out.printf("Opção inválida!\n");
            }
        } while (opcao != 4);

        CicloInstrucao ci = new ControlaCicloInstrucao();
        // Le entradas para configurar o AFD
        ci.entradaUsuario();

        scanner.close();

        
    }
}

