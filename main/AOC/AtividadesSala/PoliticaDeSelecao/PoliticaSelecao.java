package AOC.AtividadesSala.PoliticaDeSelecao;

import java.util.Scanner;

public class PoliticaSelecao {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println(" ---------------------------------------------------------------------");
        System.out.println("Atividade sobre Politica de Seleção");
        System.out.println(" Atividade 4 - AOC - Prof Nakahata");
        System.out.println(" ---------------------------------------------------------------------");

        System.out.print("Digite a quantidade total de KB: ");
        int tamanhoInicial = sc.nextInt();

        ControlaMemoria gerenciador = new ControlaMemoria(tamanhoInicial);

        char politica;

        do {
            System.out.println(" ---------------------------------------------------------------------");
            System.out.println("\nEscolha a politica de alocacao:");
            System.out.println("1 - Primeiro ajuste");
            System.out.println("M - Melhor ajuste");
            System.out.println("P - Pior ajuste");
            System.out.println(" ---------------------------------------------------------------------");

            politica = Character.toUpperCase(sc.next().charAt(0)); 

            if (politica != '1' && politica != 'M' && politica != 'P') {
                System.out.println("Politica de Seleção não encontrado!");
            }
        } while (politica != '1' && politica != 'M' && politica != 'P');

        char instrucao;

        do {
            System.out.println("\nEscolha uma opcao:");
            System.out.println("I - Inserir memoria");
            System.out.println("L - Liberar memoria");
            System.out.println("X - Sair");
            instrucao = Character.toUpperCase(sc.next().charAt(0)); 

            if (instrucao == 'I') { 
                System.out.print("\nDigite a quantidade de KB: ");
                int tamanho = sc.nextInt();

                switch (politica) {
                    case '1':
                        gerenciador.primeiroAjuste(tamanho);
                        break;
                    case 'M':
                        gerenciador.melhorAjuste(tamanho);
                        break;
                    case 'P':
                        gerenciador.piorAjuste(tamanho);
                        break;
                }

            } else if (instrucao == 'L') { 
                System.out.print("\nDigite a quantidade de KB: ");
                int tamanho = sc.nextInt();
                gerenciador.liberarMemoria(tamanho);

            } else if (instrucao != 'X') { 
                System.out.println("Instrucao invalida!");
            }

            if (instrucao != 'X') {
                System.out.println("======================================================");
                System.out.println("Memoria Atual: ");
                gerenciador.exibirMemoria();
                System.out.println("======================================================");
            }
        } while (instrucao != 'X');

        sc.close();
    }
}
