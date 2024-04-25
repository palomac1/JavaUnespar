package LFA;

import java.util.*;
import java.util.Scanner;

public class AFD_Trab {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Variavel para o número de estados do AFD
        System.out.println("Digite o número de estados:");
        int numEstados = Integer.parseInt(scanner.nextLine());

        // Variavel para o alfabeto(sigma) do AFD
        System.out.println("Digite o alfabeto, separados por espaços:");
        String[] sigma = scanner.nextLine().split(" "); // Divide em matriz, por isso o espaço

        // Variavel para o estado inicial do AFD
        System.out.println("Digite o estado inicial:");
        int estadoInicial = Integer.parseInt(scanner.nextLine());

        // Variavel para o número de estados finais do AFD
        System.out.println("Digite os estados finais (separados por espaços):");
        String[] estadoFinalStr = scanner.nextLine().split(" ");
        // Armazenados em um Set(conjunto) para facilitar a busca e comparação
        Set<Integer> aceitacao = new HashSet<>(); 
        for (String estado : estadoFinalStr) {
            aceitacao.add(Integer.parseInt(estado));
        }

        // Cria a matriz da tabela de transições 
        int[][] transicoes = new int[numEstados][sigma.length];

        // Preenche as transições da tabela
        System.out.println("Digite as transições:");
        // Percorre cada estado e o preenche de acordo com o caractere que o usuário informa
        for (int estado = 0; estado < numEstados; estado++) {
            System.out.println("Para estado q" + estado + ", insira as transições para cada símbolo do alfabeto:");
            //Verifica o conjunto de caracteres que pode ser aceito atraves de um loop e armazena o próximo estado na matriz de transição
            for (int simboloIndex = 0; simboloIndex < sigma.length; simboloIndex++) {
                System.out.print("Para símbolo '" + sigma[simboloIndex] + "': ");
                int novoEstado = Integer.parseInt(scanner.nextLine());
                transicoes[estado][simboloIndex] = novoEstado;
            }
        }

        // Lê a cadeia de entrada 
        System.out.println("Informe uma cadeia para verificar:");
        String cadeia = scanner.nextLine();

        // Verifica o conjunto de caracteres que pode ser aceito pelo AFD
        int estadoAtual = estadoInicial;
        ///Executa até que todos os caracteres sejam lidos e acessa toda a cadeia
        for (int posicao = 0; posicao < cadeia.length(); posicao++) {
            char simbolo = cadeia.charAt(posicao);
            // Retorna o índice do item na lista, se não for encontrado retorna -1
            int simboloIndex = Arrays.asList(sigma).indexOf(String.valueOf(simbolo)); 

            // Se o índice do símbolo for -1, entao o símbolo não pertence ao alfabeto 
            if (simboloIndex == -1) {
                System.out.println("Cadeia contém símbolo inválido no alfabeto.");
                return;
            }

            // Mostra o estado atual e o valor indica para qual estado o AFD deverá prosseguir
            estadoAtual = transicoes[estadoAtual] /*estado atual*/ [simboloIndex] /*simbolo do alfabeto*/;
        }

        // Verifica se o estadoAtual está nos estados de aceitação
        if (aceitacao.contains(estadoAtual)) {
            System.out.println("Aceita");
        } else {
            System.out.println("Não aceita");
        }

        scanner.close();
    }
}


//Ver qual não muda o estado
//Exibir a tabela de transição
//Fazer mais de uma palavra