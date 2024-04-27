package LFA;

import java.util.*;

public class AFD_Trab {
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
    
            // Variavel para o número de estados do AFD
            System.out.println("Digite o número de estados:");
            int numEstados = Integer.parseInt(scanner.nextLine());
    
            // Variavel para o alfabeto(sigma) do AFD
            System.out.println("\nDigite o alfabeto, separados por espaços:");
            String[] sigma = scanner.nextLine().split(" "); // Divide em matriz, por isso o espaço
    
            // Variavel para o estado inicial do AFD
            System.out.println("\nDigite o estado inicial:");
            int estadoInicial = Integer.parseInt(scanner.nextLine());
    
            // Variavel para o número de estados finais do AFD
            System.out.println("\nDigite os estados finais (Se houver mais de um, separe por espaços):");
            String[] estadoFinalStr = scanner.nextLine().split(" ");
            // Armazenados em um Set(conjunto) para facilitar a busca e comparação
            Set<Integer> aceitacao = new HashSet<>();
            for (String estado : estadoFinalStr) {
                aceitacao.add(Integer.parseInt(estado));
            }
    
            // Cria a matriz da tabela de transições
            int[][] transicoes = new int[numEstados][sigma.length];
            // Inicializa as transições com (-1), indicando que é indefinida
            for (int[] linha : transicoes) {
                Arrays.fill(linha, -1);
            }
    
            // Preenche as transições da tabela
            System.out.println("\nTRANSIÇÕES");
            System.out.println("\nOBS: Caso não exista uma transição, insira -1");
            System.out.println("\nDigite as transições para cada simbolo");
             // Percorre cada estado e o preenche de acordo com o caractere que o usuário informa
            for (int estado = 0; estado < numEstados; estado++) {
                System.out.println("\nEstado q" + estado + ", digite a transição:");
                //Verifica o conjunto de caracteres que pode ser aceito atraves de um loop e armazena o próximo estado na matriz de transição
                for (int simboloIndex = 0; simboloIndex < sigma.length; simboloIndex++) {
                    System.out.print("Para símbolo '" + sigma[simboloIndex] + "': ");
                    int novoEstado = Integer.parseInt(scanner.nextLine());
                    transicoes[estado][simboloIndex] = novoEstado;
                }
            }
    
            // Exibe a tabela de transição 
            System.out.println("\nTabela de Transições");
            System.out.print("    ");
            for (String simbolo : sigma) {
                System.out.print(" " + simbolo);
            }

            System.out.println();
            for (int estado = 0; estado < numEstados; estado++) {
                System.out.print("q" + estado + " ");
                for (int simboloIndex = 0; simboloIndex < sigma.length; simboloIndex++) {
                    System.out.print(" " + (transicoes[estado][simboloIndex] == -1 ? "-" : "q" + transicoes[estado][simboloIndex]));
                }
                System.out.println();
            }

            // Exibe a definição formal do AFD
            System.out.println("\nDescrição Formal");
            System.out.println("E = {" + getEstados(numEstados) + "}");
            System.out.println("Sigma = {" + String.join(", ", sigma) + "}");
            System.out.println("i = q" + estadoInicial);
            System.out.println("F = {" + getEstadosFinal(aceitacao) + "}");

            // Loop para verificar várias cadeias, até o usuário sair
            while (true) {
                // Lê a cadeia de entrada 
                System.out.println("\nInforme uma cadeia para verificar (Digite 'sair' para fechar o programa):");
                String cadeia = scanner.nextLine();
                //Fecha o programa caso o usuário digite "sair"
                if (cadeia.equalsIgnoreCase("sair")) {
                    break;
                }
    
                // // Verifica o conjunto de caracteres que pode ser aceito pelo AFD
                int estadoAtual = estadoInicial;
                boolean rejeitado = false;
                // Executa até que todos os caracteres sejam lidos e acessa toda a cadeia
                for (int posicao = 0; posicao < cadeia.length(); posicao++) {
                    char simbolo = cadeia.charAt(posicao);
                    // Retorna o índice do item na lista, se não for encontrado retorna -1
                    int simboloIndex = Arrays.asList(sigma).indexOf(String.valueOf(simbolo));
                    
                    // Se o simbolo for diferente do alfabto informado, entao o símbolo não pertence ao alfabeto 
                    if (simboloIndex == -1) {
                        System.out.println("\nCadeia contém símbolo inválido no alfabeto.");
                        rejeitado = true;
                        break;
                    }
    
                    // Não realiza a transição caso não tenha encontrado o próximo estado para aquele símbolo
                    int proximoEstado = transicoes[estadoAtual][simboloIndex];
                    if (proximoEstado == -1) {
                        System.out.println("Transição indefinida para símbolo '" + simbolo + "' a partir do estado q" + estadoAtual);
                        rejeitado = true;
                        break;
                    }
    
                    // Mostra o estado atual e o valor indica para qual estado o AFD deverá prosseguir
                    estadoAtual = proximoEstado;
                }
    

                // Verifica se o estadoAtual está nos estados de aceitação
                if (rejeitado) {
                    System.out.println("Cadeia rejeitada.");
                } else if (aceitacao.contains(estadoAtual)) {
                    System.out.println("Aceita.");
                } else {
                    System.out.println("Não aceita.");
                }
            }
    
            scanner.close();
        }

        private static String getEstados(int numEstados) {
            StringBuilder estados = new StringBuilder();
            for (int i = 0; i < numEstados; i++) {
                if (i > 0) {
                    estados.append(", ");
                }
                estados.append("q").append(i);
            }
            return estados.toString();
        }
    
        private static String getEstadosFinal(Set<Integer> aceitacao) {
            List<String> estadosFinais = new ArrayList<>();
            for (int estado : aceitacao) {
                estadosFinais.add("q" + estado);
            }
            return String.join(", ", estadosFinais);
        }
}
    


