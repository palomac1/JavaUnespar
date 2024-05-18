package LFA;
import java.util.*;
import java.util.regex.Pattern;

public class ControlaAFD implements AFD {

     // Códigos ANSI para cores
     public static final String RESET = "\033[0m";
     public static final String RED = "\033[0;31m";
     public static final String BLUE = "\033[0;34m";
     public static final String PURPLE = "\033[0;35m";
     public static final String GREEN = "\033[0;92m";
 
     // Atributos da classe
     private int numEstados;
     private String[] sigma;
     private int estadoInicial;
     private Set<Integer> aceitaFinal;
     private int[][] transicoes;
     private Scanner scanner;
 
     // Metodo construtor
     public ControlaAFD() {
         scanner = new Scanner(System.in);
     }
 
     // Metodo para ler as entradas do usuário
     public void leEntradas() {
         System.out.println(PURPLE + " --- AUTOMATO FINITO DETERMINISTICO (AFD) --- " + RESET);
 
         // Variável para o número de estados do AFD
         System.out.println("\nDigite o número de estados:");
         numEstados = lerInteiroPositivo();
 
         // Variavel para o alfabeto(sigma) do AFD
         System.out.println("\nDigite o alfabeto (separados por espaços):");
         sigma = scanner.nextLine().split(" ");

        // Validação do alfabeto
        if (!validarAlfabeto(sigma)) {
            System.out.println(RED + "Erro: Alfabeto inválido. Símbolos permitidos: letras minúsculas e números." + RESET);
            leEntradas(); // Solicita novamente a entrada do alfabeto
            return;
        }
 
         // Variavel para o estado inicial do AFD
         System.out.println("\nDigite o estado inicial:");
         estadoInicial = Integer.parseInt(scanner.nextLine());
 
         // Variavel para o número de estados finais do AFD
         System.out.println("\nDigite os estados finais (Se houver mais de um, separe por espaços):");
         String[] estadoFinal = scanner.nextLine().split(" "); // Leitura e divisão da entrada atravês do split
         aceitaFinal = new HashSet<>(); //Armazena os estados finais.
         for (String estado : estadoFinal) {
             aceitaFinal.add(Integer.parseInt(estado)); // Converte as substrings para inteiro e adiciona ao conjunto de estados aceitos
         }
 
         // Cria a matriz da tabela de transições
         transicoes = new int[numEstados][sigma.length];
         // Inicializa as transições com (-1), indicando que é indefinida
         for (int[] linha : transicoes) {
             Arrays.fill(linha, -1);
         }
 
        // Preenchimento das transições
        System.out.println(BLUE + "\n---TRANSIÇÕES --" + RESET);
        System.out.println(RED + "\nOBS: Caso não exista uma transição, insira -1" + RESET);
        for (int estado = 0; estado < numEstados; estado++) {
            for (int simboloIndex = 0; simboloIndex < sigma.length; simboloIndex++) {
                while (true) {
                    System.out.print("\nEstado q" + estado + " para símbolo '" + sigma[simboloIndex] + "': ");
                    try {
                        int novoEstado = Integer.parseInt(scanner.nextLine());
                        if (novoEstado < -1 || novoEstado >= numEstados) {
                            System.out.println(RED + "Estado " + novoEstado + " inválido." + RESET);
                            continue;
                        }
                        transicoes[estado][simboloIndex] = novoEstado;
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println(RED + "Entrada inválida. Por favor, insira um número inteiro." + RESET);
                    }
                }
            }
        }
}
 
     // Metodo para a tabela de transições e descrição formal do AFD
     public void tabelaTransicaoDescricao() {
         System.out.println(BLUE + "\n --- TABELA DE TRANSIÇÕES --- \n" + RESET);
         System.out.print("    ");
         for (String simbolo : sigma) {
             System.out.print(" " + simbolo);
         }
 
         System.out.println();
         for (int estado = 0; estado < numEstados; estado++) {
             System.out.print("q" + estado + " ");
             for (int simboloIndex = 0; simboloIndex < sigma.length; simboloIndex++) {
                 System.out.print(" " + (transicoes[estado][simboloIndex] == -1 ? RED + "x" + RESET : "q" + transicoes[estado][simboloIndex]));
             }
             System.out.println();
         }
 
         // Descição formal do AFD
         System.out.println(BLUE + "\n --- DESCRIÇÃO FORMAL --- \n" + RESET);
         System.out.println("E = {" + getEstados(numEstados) + "}");
         System.out.println("Sigma = {" + String.join(", ", sigma) + "}");
         System.out.println("i = q" + estadoInicial);
         System.out.println("F = {" + getEstadoFinal(aceitaFinal) + "}");
     }
 
     // Testa as cadeias de string
     public void testaCadeia() {
         System.out.println(BLUE + "\n --- TESTAR CADEIA --- " + RESET);
 
         // Loop para verificar várias cadeias, até o usuário sair ou testar um novo AFD
        while (true) {
            System.out.println("\n-Informe uma cadeia (Digite 'sair' para fechar o programa ou 'novo' para testar um novo AFD):");
            String cadeia = scanner.nextLine();

            // Fecha o programa caso o usuário digite "sair"
            if (cadeia.equalsIgnoreCase("sair")) {
                break;
            } else if (cadeia.equalsIgnoreCase("novo")) {
                leEntradas(); // Testa um novo AFD
                continue; // Volta para o início do loop para testar uma cadeia no novo AFD
            }

            // Verifica o conjunto de caracteres que pode ser aceito pelo AFD
            int estadoAtual = estadoInicial;
            boolean cadeiaRejeitada = false;

            // Executa até que todos os caracteres sejam lidos e acessa toda a cadeia
            for (int posicao = 0; posicao < cadeia.length(); posicao++) {
                char simbolo = cadeia.charAt(posicao);
                // Retorna o índice do item na lista, se não for encontrado retorna -1
                int simboloIndex = Arrays.asList(sigma).indexOf(String.valueOf(simbolo));

                // Se o símbolo for diferente do alfabeto informado, então o símbolo não pertence ao alfabeto
                if (simboloIndex == -1) {
                    System.out.println("\nCadeia contém símbolo inválido no alfabeto.");
                    cadeiaRejeitada = true;
                    break;
                }

                // Não realiza a transição caso não tenha encontrado o próximo estado para aquele símbolo
                int proximoEstado = transicoes[estadoAtual][simboloIndex];
                if (proximoEstado == -1) {
                    System.out.println(RED + "\nTransição indefinida para símbolo '" + simbolo + "' a partir do estado q" + estadoAtual + "." + RESET);
                    cadeiaRejeitada = true;
                    break;
                }

                // Mostra o estado atual e o valor indica para qual estado o AFD deverá prosseguir
                estadoAtual = proximoEstado;
            }

            // Verifica se o estadoAtual está nos estados de aceitaFinal
            if (cadeiaRejeitada) {
                System.out.println(RED + "\nCadeia rejeitada." + RESET);
            } else if (aceitaFinal.contains(estadoAtual)) {
                System.out.println(GREEN + "\nResultado: Cadeia aceita." + RESET);
            } else {
                System.out.println(RED + "\nResultado: Cadeia não aceita." + RESET);
            }
        }

        scanner.close();
     }

        // Método para ler um inteiro positivo

       private int lerInteiroPositivo() {
        while (true) {
            try {
                int valor = Integer.parseInt(scanner.nextLine());
                if (valor <= 0) {
                    System.out.println(RED + "Erro: Digite um número inteiro positivo." + RESET);
                    continue;
                }
                return valor;
            } catch (NumberFormatException e) {
                System.out.println(RED + "Entrada inválida, digite um número inteiro." + RESET);
            }
        }
    }
 
     // Função para obter todos os estados
     private String getEstados(int numEstados) {
         StringBuilder estados = new StringBuilder();
         for (int i = 0; i < numEstados; i++) {
             if (i > 0) {
                 estados.append(", ");
             }
             estados.append("q").append(i);
         }
         return estados.toString();
     }
 
     // Função para obeter os estados finais
     private String getEstadoFinal(Set<Integer> aceitaFinal) {
         List<String> estadosFinais = new ArrayList<>();
         for (int estado : aceitaFinal) {
             estadosFinais.add("q" + estado);
         }
         return String.join(", ", estadosFinais);
     }

     // Método para validar se o alfabeto contém apenas letras minúsculas e números
    private boolean validarAlfabeto(String[] alfabeto) {
        for (String simbolo : alfabeto) {
            if (!Pattern.matches("[a-z0-9]", simbolo)) {
                return false;
            }
        }
        return true;
    }

 }



 