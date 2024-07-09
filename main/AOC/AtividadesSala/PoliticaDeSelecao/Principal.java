package AOC.AtividadesSala.PoliticaDeSelecao;

import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\tTRABALHO POLITICAS DE SELECAO\t");
        System.out.println("--------------------------------------------------------------------------");

        System.out.print("Insira a quantidade de KB para a memória: ");
        int quantKB = scanner.nextInt();
        Memoria memoria = new Memoria(quantKB);

        System.out.println("Escolha a política de alocação:");
        System.out.println("1 - Primeiro ajuste");
        System.out.println("M - Melhor ajuste");
        System.out.println("P - Pior ajuste");
        char politica = scanner.next().charAt(0);

        char opcao;
        do {
            System.out.println("Escolha uma opção:");
            System.out.println("I - Inserir objeto");
            System.out.println("L - Liberar objeto");
            System.out.println("X - Sair");
            opcao = scanner.next().charAt(0);

            switch (opcao) {
                case 'I':
                    System.out.print("Digite a quantidade de KB do objeto: ");
                    int tamanho = scanner.nextInt();
                    Instrucoes objeto = new Instrucoes(tamanho);

                    boolean alocado = false;
                    switch (politica) {
                        case '1':
                            alocado = memoria.alocarPrimeiroAjuste(objeto);
                            break;
                        case 'M':
                            alocado = memoria.alocarMelhorAjuste(objeto);
                            break;
                        case 'P':
                            alocado = memoria.alocarPiorAjuste(objeto);
                            break;
                        default:
                            System.out.println("Política de alocação inválida.");
                    }

                    if (!alocado) {
                        System.out.println("Não foi possível alocar o objeto.");
                    }
                    break;

                case 'L':
                    System.out.print("Digite a quantidade de KB do objeto a ser liberado: ");
                    tamanho = scanner.nextInt();
                    boolean desalocado = memoria.desalocarPorTamanho(tamanho);
                    if (!desalocado) {
                        System.out.println("Não foi possível desalocar o objeto.");
                    }
                    break;

                case 'X':
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }

            System.out.println("Estado atual da memória:");
            memoria.mostrarMemoria();
            System.out.println("--------------------------------------------------------------------------");

        } while (opcao != 'X');

        scanner.close();
    }
}
