package LFA;

import java.util.*; // Importa todas as classes do pacote java.util
import java.util.regex.Pattern; // Importa a classe Pattern do pacote java.util.regex

// Classe ControlaAFD implementa a interface AFD
public class ControlaAFD implements AFD {

    // Códigos ANSI para cores
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String GREEN = "\033[0;92m";

    // Atributos da classe ControlaAFD
    private int numEstados; // Número de estados
    private String[] alfabeto; // Alfabeto
    private int estadoInicial; // Estado inicial
    private Set<Integer> aceitaFinal; // Conjunto de estados finais
    private int[][] transicoes; // Matriz de transições
    private Scanner scanner; // Lê entradas do usuário

    // Método construtor da classe
    public ControlaAFD() {
        scanner = new Scanner(System.in); // Inicializa o scanner
    }

    // Método para ler as entradas do usuário
    public void entradaUsuario() {
        System.out.println(PURPLE + " --- Linguagens Formais, Autômatos e Computabilidade ---\n  " + RESET);
        System.out.println("Trabalho de LFA - 1° Bimestre - C.C UNESPAR\n");
        System.out.println(PURPLE + " --- SIMULADOR DE AUTÔMATO FINITO DETERMINÍSTICO (AFD) --- " + RESET);

        // Número de estados do AFD
        System.out.println("\nDigite o número de estados:");
        while (true) {
            // Tenta converter a entrada do usuário em um número inteiro
            try {
                numEstados = Integer.parseInt(scanner.nextLine());
                // Verifica se o número de estados é positivo
                if (numEstados <= 0) {
                    System.out.println(RED + "Digite um número inteiro positivo." + RESET);
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println(RED + "Digite um número inteiro." + RESET);
            }
        }

        // Estado inicial do AFD
        System.out.println("\nDigite o estado inicial (entre 0 e " + (numEstados - 1) + "):");
        estadoInicial = lerEstadoInicial(numEstados);

        // Estados finais do AFD
        System.out.println("\nDigite os estados finais (Se houver mais de um, separe por espaços):");
        String[] estadoFinal = scanner.nextLine().split(" ");   
        // Cria um conjunto de estados finais
        aceitaFinal = new HashSet<>();
        // Adiciona os estados finais ao conjunto
        for (String estado : estadoFinal) {
            aceitaFinal.add(Integer.parseInt(estado)); 
        }

        // Alfabeto do AFD
        System.out.println("\nDigite o alfabeto (separados por espaços):");
        System.out.println("\nObs: apenas letras minúsculas ou números.");
        alfabeto = scanner.nextLine().split(" ");

        // Valida o alfabeto inserido
        if (!validarAlfabeto(alfabeto)) {
            System.out.println(RED + "Erro: Apenas letras minúsculas ou números." + RESET);
            entradaUsuario();
            return;
        }

        // Cria a matriz de transições
        transicoes = new int[numEstados][alfabeto.length];
        // Preenche a matriz de transições com -1
        for (int[] linha : transicoes) {
            Arrays.fill(linha, -1);
        }

        // Preenchimento das transições
        System.out.println(BLUE + "\n---TRANSIÇÕES --" + RESET);
        System.out.println(RED + "\nPreencha as transições a seguir (Apenas números inteiros):" + RESET);
        // Loop para preencher as transições
        for (int estado = 0; estado < numEstados; estado++) {
            for (int simboloIndex = 0; simboloIndex < alfabeto.length; simboloIndex++) {
                // Pede ao usuário para inserir o estado de transição
                boolean entradaValida = false;
                // Loop para ter um estado válido
                while (!entradaValida) {
                    System.out.print("\nq" + estado + " para '" + alfabeto[simboloIndex] + "': ");
                    try {
                        // Lê o estado de transição
                        int novoEstado = Integer.parseInt(scanner.nextLine());
                        // Verifica se o estado de transição é válido
                        if (novoEstado < -1 || novoEstado >= numEstados) {
                            System.out.println(RED + "Estado " + novoEstado + " inválido." + RESET);
                        } else {
                            // Define o estado de transição na matriz
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

    // Método para a tabela de transições e descrição formal do AFD
    public void tabelaTransicaoDescricao() {
        System.out.println(BLUE + "\n --- TABELA DE TRANSIÇÕES --- \n" + RESET);
        System.out.print("    ");
        // Mostra o alfabeto na tabela de transições
        for (String simbolo : alfabeto) {
            System.out.print(" " + simbolo);
        }

        // For para exibição da tabela
        System.out.println();
        // Mostra a tabela de transições do AFD
        for (int estado = 0; estado < numEstados; estado++) {
            // Mostra o estado atual e as transições para cada símbolo do alfabeto
            System.out.print("q" + estado + " ");
            // Mostra o estado para cada símbolo do alfabeto
            for (int simboloIndex = 0; simboloIndex < alfabeto.length; simboloIndex++) {
                // Mostra o estado de transição para o símbolo
                System.out.print(" " + (transicoes[estado][simboloIndex] == -1 ? RED + "x" + RESET : "q" + transicoes[estado][simboloIndex]));
            }
            System.out.println();
        }

        // Descrição formal do AFD
        System.out.println(BLUE + "\n --- DESCRIÇÃO FORMAL --- \n" + RESET);
        System.out.println("E = {" + conjuntoEstados(numEstados) + "}"); // Conjunto de estados do AFD
        System.out.println("Sigma = {" + String.join(", ", alfabeto) + "}"); // Alfabeto do AFD
        System.out.println("i = q" + estadoInicial); // Estado inicial do AFD
        System.out.println("F = {" + conjuntoEstadoFinal(aceitaFinal) + "}"); // Conjunto de estados finais do AFD
    }
        
    // Método para testar cadeias de entrada no AFD
    public void testarCadeias() {
        System.out.println(BLUE + "\n --- TESTAR CADEIA --- " + RESET);
        while (true) {
            System.out.println("\nInforme uma cadeia (ou 'sair' para encerrar, 'novo' para novo AFD):");
            String cadeia = scanner.nextLine().trim();

            // Verifica se o usuário deseja sair ou criar um novo AFD
            if ("sair".equalsIgnoreCase(cadeia)) {
                break;
            } else if ("novo".equalsIgnoreCase(cadeia)) {
                entradaUsuario();
                continue;
            }

            int estadoAtual = estadoInicial; // Estado atual do AFD
            boolean rejeitada = false; // Cadeia de caracteres rejeitada

            // Verifica cada símbolo da cadeia
            for (int i = 0; i < cadeia.length(); i++) {
                // Pega o símbolo atual
                char simbolo = cadeia.charAt(i);

                // Verifica se o símbolo está no alfabeto
                if (!Arrays.asList(alfabeto).contains(String.valueOf(simbolo))) {
                    System.out.println(RED + "\nErro: símbolo '" + simbolo + "' não pertence ao alfabeto." + RESET);
                    rejeitada = true; 
                    break;
                }

                // Transição para o próximo estado
                int simboloIndex = Arrays.asList(alfabeto).indexOf(String.valueOf(simbolo));
                // Próximo estado com base no símbolo atual
                int proximoEstado = transicoes[estadoAtual][simboloIndex];

                // Verifica se a transição é indefinida
                if (proximoEstado == -1) {
                    System.out.println(RED + "\nErro: Transição indefinida para símbolo '" + simbolo + "' no estado q" + estadoAtual + "." + RESET);
                    rejeitada = true;
                    break;
                }

                // Atualiza o estado atual
                estadoAtual = proximoEstado;
            }

            // Verifica se a cadeia foi rejeitada ou aceita
            if (rejeitada) {
                System.out.println(RED + "\nCadeia rejeitada." + RESET);
            } else if (aceitaFinal.contains(estadoAtual)) {
                System.out.println(GREEN + "\nResultado: Cadeia aceita pelo AFD." + RESET);
            } else {
                System.out.println(RED + "\nResultado: Cadeia não aceita pelo AFD." + RESET);
            }
        }

        scanner.close();
        }

        // Método para validar se o alfabeto contém apenas letras minúsculas e números
        private boolean validarAlfabeto(String[] alfabeto) {
            // Verifica cada símbolo do alfabeto
            for (String simbolo : alfabeto) {
                // Verifica se o símbolo é uma letra minúscula ou número
                if (!Pattern.matches("[a-z0-9]", simbolo)) {
                    // Retorna falso se o símbolo não for válido
                    return false;
                }
            }
            // Retorna verdadeiro se todos os símbolos forem válidos
            return true;
        }

        // Método para ler um estado inicial válido
        private int lerEstadoInicial(int numEstados) {
            // Loop para ler o estado inicial
            while (true) {
                // Tenta converter a entrada do usuário em um número inteiro
                try {
                    // Lê o estado inicial
                    int estado = Integer.parseInt(scanner.nextLine());
                    // Verifica se o estado está entre 0 e numEstados - 1
                    if (estado < 0 || estado >= numEstados) {
                        // Informa ao usuário que o estado inicial é inválido
                        System.out.println(RED + "O estado inicial deve estar entre 0 e " + (numEstados - 1) + "." + RESET);
                    } else {
                        // Retorna o estado inicial se for válido
                        return estado;
                    }
                    // Se a entrada não for um número inteiro aparece a mensagem de erro
                } catch (NumberFormatException e) {
                    System.out.println(RED + "Digite um número inteiro" + RESET);
                }
            }
        }

        // Função para obter todos os estados
        private String conjuntoEstados(int numEstados) {
            // Armazena os estados
            StringBuilder estados = new StringBuilder();
            // Loop para adicionar cada estado na string
            for (int i = 0; i < numEstados; i++) {
                // Adiciona o estado na string
                if (i > 0) {
                    estados.append(", "); // Adiciona vírgula entre os estados
                }
                estados.append("q").append(i); // Adiciona o estado
            }
            // Retorna a string com os estados
            return estados.toString();
        }

        // Função para obter os estados finais em string
        private String conjuntoEstadoFinal(Set<Integer> aceitaFinal) {
            // Armazena os estados finais
            List<String> estadosFinais = new ArrayList<>();
            // Loop para adicionar cada estado final na lista
            for (int estado : aceitaFinal) {
                estadosFinais.add("q" + estado);
            }
            // Retorna a lista de estados finais como string
            return String.join(", ", estadosFinais);
        }
    }
