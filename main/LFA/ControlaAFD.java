package LFA;
import java.util.*;
import java.util.regex.Pattern;

//Classe ControlaAFD implementa a interface AFD
public class ControlaAFD implements AFD {

     // Códigos ANSI para cores
     public static final String RESET = "\033[0m";
     public static final String RED = "\033[0;31m";
     public static final String BLUE = "\033[0;34m";
     public static final String PURPLE = "\033[0;35m";
     public static final String GREEN = "\033[0;92m";

 
     // Atributos da classe
     private int numEstados; // Número de estados
     private String[] alfabeto; // Alfabeto
     private int estadoInicial; // Estado inicial
     private Set<Integer> aceitaFinal; // Conjunto de estados finais
     private int[][] transicoes; // Matriz
     private Scanner scanner; //Lê entradas do usuário
 
     // Metodo construtor da classe
     public ControlaAFD() {
         scanner = new Scanner(System.in); 
     }
 
     // Metodo para ler as entradas do usuário
     public void leEntradas() {
        System.out.println(PURPLE + " --- Linguagens Formais, Autômatos e Computabilidade ---\n  " + RESET);
        System.out.println("Trabalho de LFA - 1° Bimestre - C.C UNESPAR\n");
        System.out.println(PURPLE + " --- SIMULADOR DE AUTÔMATO FINITO DETERMINÍSTICO (AFD) --- " + RESET);
    
        // Variável para o número de estados do AFD
        System.out.println("\nDigite o número de estados:");
        numEstados = lerInteiroPositivo();
    
        // Variável para o estado inicial do AFD
        System.out.println("\nDigite o estado inicial (entre 0 e " + (numEstados - 1) + "):");
        estadoInicial = lerEstadoInicial(numEstados);
    
        // Variável para o número de estados finais do AFD
        System.out.println("\nDigite os estados finais (Se houver mais de um, separe por espaços):");
        String[] estadoFinal = scanner.nextLine().split(" "); // Leitura e divisão da entrada através do split
        aceitaFinal = new HashSet<>(); // Armazena os estados finais.
        for (String estado : estadoFinal) {
            aceitaFinal.add(Integer.parseInt(estado)); // Converte e adiciona os estados finais ao conjunto
        }
    
        // Variável para o alfabeto(sigma) do AFD
        System.out.println("\nDigite o alfabeto (separados por espaços):");
        System.out.println("\nObs: são permitidos apenas letras minúsculas e números.");
    
        alfabeto = scanner.nextLine().split(" "); // Divide-as em símbolos
    
        // Valida o alfabeto inserido
        if (!validarAlfabeto(alfabeto)) {
            System.out.println(RED + "Erro: os símbolos permitidos são letras minúsculas e números." + RESET);
            leEntradas(); // Solicita novamente a entrada do alfabeto
            return;
        }
    
        // Cria a matriz da tabela de transições
        transicoes = new int[numEstados][alfabeto.length];
        // Inicializa as transições com (-1), indicando que é indefinida
        for (int[] linha : transicoes) {
            Arrays.fill(linha, -1);
        }
    
        // Preenchimento das transições - São validadas e definidas na matriz
        System.out.println(BLUE + "\n---TRANSIÇÕES --" + RESET);
        System.out.println(RED + "\nPreencha as transições a seguir (Apenas números inteiros):" + RESET);
    
        // Percorre todos os estados e símbolos do alfabeto
        for (int estado = 0; estado < numEstados; estado++) {
            for (int simboloIndex = 0; simboloIndex < alfabeto.length; simboloIndex++) {
                boolean entradaValida = false;
                while (!entradaValida) {
                    System.out.print("\nq" + estado + " para '" + alfabeto[simboloIndex] + "': ");
                    try {
                        int novoEstado = Integer.parseInt(scanner.nextLine()); // Tenta converter para inteiro
    
                        // Verifica se o estado inserido é válido
                        if (novoEstado < -1 || novoEstado >= numEstados) {
                            System.out.println(RED + "Estado " + novoEstado + " inválido." + RESET);
                        } else {
                            // Armazena a transição no array na posição correspondente ao estado atual e ao símbolo
                            transicoes[estado][simboloIndex] = novoEstado;
                            entradaValida = true;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println(RED + "Entrada inválida, insira um número inteiro." + RESET);
                    }
                }
            }
        }
    }

 
     // Metodo para a tabela de transições e descrição formal do AFD
     public void tabelaTransicaoDescricao() {
         System.out.println(BLUE + "\n --- TABELA DE TRANSIÇÕES --- \n" + RESET);
         System.out.print("    ");
         for (String simbolo : alfabeto) {
             System.out.print(" " + simbolo);
         }
 
         // For para exibição da tabela
         System.out.println();
         //Percorre todos os estados
         for (int estado = 0; estado < numEstados; estado++) {
             System.out.print("q" + estado + " ");
             //Percorre todos os símbolos do alfabeto
             for (int simboloIndex = 0; simboloIndex < alfabeto.length; simboloIndex++) {
                // Se transição = '-1', então exiba um 'x'
                 System.out.print(" " + (transicoes[estado][simboloIndex] == -1 ? RED + "x" + RESET : "q" + transicoes[estado][simboloIndex]));
             }
             System.out.println();
         }
 
         // Descição formal do AFD
         System.out.println(BLUE + "\n --- DESCRIÇÃO FORMAL --- \n" + RESET);
         System.out.println("E = {" + getEstados(numEstados) + "}"); // Conjunto de estados
         System.out.println("Sigma = {" + String.join(", ", alfabeto) + "}"); // Alfabeto
         System.out.println("i = q" + estadoInicial); //Estado final
         System.out.println("F = {" + getEstadoFinal(aceitaFinal) + "}"); // Conjunto de estados finais
     }
 
     // Testa as cadeias de string
public void testaCadeia() {
    System.out.println(BLUE + "\n --- TESTAR CADEIA --- " + RESET);

    // Loop para verificar várias cadeias, até o usuário sair ou testar um novo AFD
    while (true) {
        System.out.println("\n-Informe uma cadeia a ser testada (Digite 'sair' para fechar o programa ou 'novo' para testar um novo AFD):");
        String cadeia = scanner.nextLine();

        // Fecha o programa caso o usuário digite "sair"
        if (cadeia.equalsIgnoreCase("sair")) {
            break;
        // Inicializa o programa outra vez caso o usuário digite "novo"
        } else if (cadeia.equalsIgnoreCase("novo")) {
            leEntradas(); // Testa um novo AFD
            continue; // Volta para o início do loop para testar um novo AFD
        }

        // Verifica o conjunto de caracteres que pode ser aceito pelo AFD
        int estadoAtual = estadoInicial;
        boolean cadeiaRejeitada = false;

        // Executa até que todos os caracteres sejam lidos e acessa toda a cadeia
        for (int posicao = 0; posicao < cadeia.length(); posicao++) {
            char simbolo = cadeia.charAt(posicao);
            // Retorna o índice do item na lista, se não for encontrado retorna -1
            int simboloIndex = Arrays.asList(alfabeto).indexOf(String.valueOf(simbolo));

            // Se o símbolo for diferente do alfabeto informado, então o símbolo não pertence ao alfabeto
            if (simboloIndex == -1) {
                System.out.println("\nCadeia contém símbolo inválido no alfabeto.");
                cadeiaRejeitada = true;
                break;
            }

            // Não realiza a transição caso não tenha encontrado o próximo estado para aquele símbolo
            int proximoEstado = transicoes[estadoAtual][simboloIndex];
            if (proximoEstado == -1) {
                System.out.println(RED + "\nTransição indefinida para '" + simbolo + "' a partir do estado q" + estadoAtual + "." + RESET);
                cadeiaRejeitada = true;
                break;
            }

            // Mostra o estado atual e o valor indica para qual estado o AFD deverá prosseguir
            estadoAtual = proximoEstado;
        }

        // Verifica se o estadoAtual está nos estados de aceitaFinal
        if (cadeiaRejeitada) {
            System.out.println(RED + "\nCadeia rejeitada." + RESET); // Não aceita durante a transição
        } else if (aceitaFinal.contains(estadoAtual)) {
            System.out.println(GREEN + "\nResultado: Cadeia aceita pelo AFD." + RESET); // Se o estado atual está no conjunto de estados finais
        } else {
            System.out.println(RED + "\nResultado: Cadeia não aceita pelo AFD." + RESET);
        }
    }

    scanner.close(); 
}


       // Método para ler um inteiro positivo
       private int lerInteiroPositivo() {
        // Utiliza um while para que isso se repita até obter uma entrada válida
        while (true) {
            try {
                int valor = Integer.parseInt(scanner.nextLine());
                //Se o valor for menor ou igual a 0, retorna erro
                if (valor <= 0) { 
                    System.out.println(RED + "Erro: Digite um número inteiro positivo." + RESET);
                    continue; 
                }
                return valor; //Caso contrario armazena o valor
            } catch (NumberFormatException e) {
                //  Caso a conversão do valor para inteiro não seja possivel, aparecerá o seguinte erro
                System.out.println(RED + "Digite um número inteiro." + RESET);
            }
        }
       }

       // Método para validar se o alfabeto contém apenas letras minúsculas e números
       private boolean validarAlfabeto(String[] alfabeto) { //Recebe um array de string como parametro e retorna v ou f
        for (String simbolo : alfabeto) {
            if (!Pattern.matches("[a-z0-9]", simbolo)) { //Limita a letras minúsculas de a até z e números de 0 até 9, verificando se o simboo atual é correspondente
                return false; //Será verdadeiro se houver caracteres inválidos
            }
        }
        return true;
       }

        
        // Método para ler um estado inicial válido
        private int lerEstadoInicial(int numEstados) { //Recebe numero de estados como parametro
            while (true) {
                try {
                    int estado = Integer.parseInt(scanner.nextLine());
                    //Se o estado for menor do que 0 ou maior ou igual ao numero total de estados
                    if (estado < 0 || estado >= numEstados) {
                        System.out.println(RED + "O estado inicial deve estar entre 0 e " + (numEstados - 1) + "." + RESET);
                    } else {
                        return estado; //Se não, retorna o estado inserido
                    }
                } catch (NumberFormatException e) {
                    System.out.println(RED + "Digite um número inteiro válido." + RESET);
                }
            }
        }

 
        // Função para obter todos os estados
        private String getEstados(int numEstados) {
         StringBuilder estados = new StringBuilder(); // Faz a string com os estados
            for (int i = 0; i < numEstados; i++) {
                if (i > 0) {
                    //Adiciona uma virgula e um espaço antes de cada estado
                    estados.append(", ");
                }
                //Adiciona o estado atual + q e o número do mesmo
                estados.append("q").append(i);
            }
            return estados.toString(); //Converte para string
        }
    
        // Função para obeter os estados finais em string
        private String getEstadoFinal(Set<Integer> aceitaFinal) {
         List<String> estadosFinais = new ArrayList<>();
            for (int estado : aceitaFinal) {
                estadosFinais.add("q" + estado);
            }
            return String.join(", ", estadosFinais);
        }
 }



 