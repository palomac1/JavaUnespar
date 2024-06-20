package ArqOrg;

import java.util.Arrays;
import java.util.Scanner;

public class Simulador_SSF_FCFS {

    // Função para calcular FCFS - Ordem de chegada
    static void FCFS(int[] vetorPedidos, char[] acessoCilindro, int tamanhoCilindro, int qntdPedidos) {
        System.out.println("\n==========================================================");
        System.out.println("INÍCIO DO ESCALONAMENTO (FIRST COME FIRST SERVED) ");
        System.out.println("==========================================================");

        for (int i = 0; i < qntdPedidos; i++) {
            acessoCilindro[vetorPedidos[i] - 1] = 'X';

            System.out.println("\nRepresentação do Cilindro:");
            System.out.println("----------------------------------------------------------");

            System.out.print("|| ");
            for (int j = 0; j < tamanhoCilindro; j++) {
                System.out.printf("%2d || ", j + 1);
            }

            System.out.print("\n|| ");
            for (int j = 0; j < tamanhoCilindro; j++) {
                System.out.printf("%2c || ", acessoCilindro[j]);
            }

            System.out.println("\n----------------------------------------------------------");
        }

        System.out.println("FINAL DO ESCALONAMENTO (FIRST COME FIRST SERVED) ");
        System.out.println("==========================================================");
    }

    // Função para calcular SSF - Cilindro mais próximo
    static void SSF(int[] vetorPedidos, char[] acessoCilindro, int tamanhoCilindro, int qntdPedidos) {
        System.out.println("\n==========================================================");
        System.out.println("INÍCIO DO ESCALONAMENTO (SHORTEST SEEK FIRST) ");
        System.out.println("==========================================================");

        boolean[] atendidos = new boolean[qntdPedidos];
        Arrays.fill(atendidos, false);

        int posicaoAtual = vetorPedidos[0];
        int cont = 0;

        while (cont < qntdPedidos) {
            int menorDistanciaPedido = -1;
            int menorDistancia = tamanhoCilindro;

            for (int i = 0; i < qntdPedidos; i++) {
                if (atendidos[i]) continue;

                int distancia = Math.abs(vetorPedidos[i] - posicaoAtual);
                if (distancia < menorDistancia) {
                    menorDistancia = distancia;
                    menorDistanciaPedido = i;
                }
            }

            if (menorDistanciaPedido != -1) {
                posicaoAtual = vetorPedidos[menorDistanciaPedido];
                acessoCilindro[posicaoAtual - 1] = 'X';
                atendidos[menorDistanciaPedido] = true;
                cont++;

                System.out.println("\nRepresentação do Cilindro:");
                System.out.println("----------------------------------------------------------");

                System.out.print("|| ");
                for (int j = 0; j < tamanhoCilindro; j++) {
                    System.out.printf("%2d || ", j + 1);
                }

                System.out.print("\n|| ");
                for (int j = 0; j < tamanhoCilindro; j++) {
                    System.out.printf("%2c || ", acessoCilindro[j]);
                }

                System.out.println("\n----------------------------------------------------------");
            }
        }

        System.out.println("FINAL DO ESCALONAMENTO (SHORTEST SEEK FIRST) ");
        System.out.println("==========================================================");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Informe o tamanho do cilindro: ");
        int tamanhoCilindro = scanner.nextInt();

        System.out.print("Informe a quantidade de pedidos de cilindros: ");
        int qntdPedidos = scanner.nextInt();

        int[] vetorPedidos = new int[qntdPedidos];
        for (int i = 0; i < qntdPedidos; i++) {
            System.out.print("Informe o pedido " + (i + 1) + ": ");
            vetorPedidos[i] = scanner.nextInt();
        }

        System.out.println("\n==========================================================");
        System.out.println("Representação inicial do Cilindro:");
        System.out.println("----------------------------------------------------------");
        System.out.print("|| ");
        for (int i = 0; i < tamanhoCilindro; i++) {
            System.out.printf("%2d || ", i + 1);
        }

        char[] acessoCilindro = new char[tamanhoCilindro];
        Arrays.fill(acessoCilindro, '-');

        System.out.print("\n|| ");
        for (int i = 0; i < tamanhoCilindro; i++) {
            System.out.printf("%2c || ", acessoCilindro[i]);
        }

        System.out.println("\n==========================================================");
        System.out.println("\nVetor de Pedidos");
        System.out.println("==========================================================");
        System.out.print("||");
        for (int i = 0; i < qntdPedidos; i++) {
            System.out.printf(" %2d ||", vetorPedidos[i]);
        }

        System.out.println();
        FCFS(vetorPedidos, acessoCilindro, tamanhoCilindro, qntdPedidos);

        // Reseta o vetor acessoCilindro para o algoritmo SSF
        Arrays.fill(acessoCilindro, '-');

        SSF(vetorPedidos, acessoCilindro, tamanhoCilindro, qntdPedidos);
        
        scanner.close();
    }
}
