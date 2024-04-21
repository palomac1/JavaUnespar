package main.LFA;

import java.util.*;

public class AFD_Trab {

public class AFDSimplesGenerico {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Entradas para o autômato
        System.out.println("Informe o número de estados:");
        int numEstados = Integer.parseInt(scanner.nextLine());

        System.out.println("Informe o alfabeto, separados por espaços:");
        String[] alfabeto = scanner.nextLine().split(" ");

        // Criando a matriz de transições com tamanho dinâmico
        int[][] transicoes = new int[numEstados][alfabeto.length];

        // Preenchendo as transições
        System.out.println("Informe as transições:");
        for (int estado = 0; estado < numEstados; estado++) {
            System.out.println("Para estado q" + estado + ", insira as transições para cada símbolo do alfabeto:");
            for (int simboloIndex = 0; simboloIndex < alfabeto.length; simboloIndex++) {
                System.out.print("Para símbolo '" + alfabeto[simboloIndex] + "': ");
                int novoEstado = Integer.parseInt(scanner.nextLine());
                transicoes[estado][simboloIndex] = novoEstado;
            }
        }

        System.out.println("Informe o estado inicial:");
        int estadoInicial = Integer.parseInt(scanner.nextLine());

        System.out.println("Informe os estados de aceitação (separados por espaços):");
        String[] aceitacaoStr = scanner.nextLine().split(" ");
        Set<Integer> aceitacao = new HashSet<>();
        for (String estado : aceitacaoStr) {
            aceitacao.add(Integer.parseInt(estado));
        }

        // Ler a cadeia de entrada para validação
        System.out.println("Informe uma cadeia para verificar:");
        String cadeia = scanner.nextLine();

        // Verificar se a cadeia é aceita pelo autômato
        int estadoAtual = estadoInicial;
        for (int posicao = 0; posicao < cadeia.length(); posicao++) {
            char simbolo = cadeia.charAt(posicao);
            int simboloIndex = Arrays.asList(alfabeto).indexOf(String.valueOf(simbolo));

            if (simboloIndex == -1) {
                System.out.println("Cadeia contém símbolo inválido no alfabeto.");
                return;
            }

            estadoAtual = transicoes[estadoAtual][simboloIndex];
        }

        if (aceitacao.contains(estadoAtual)) {
            System.out.println("Aceita");
        } else {
            System.out.println("Não aceita");
        }
    }
}

}
