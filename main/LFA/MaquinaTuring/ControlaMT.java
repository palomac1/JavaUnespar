package LFA.MaquinaTuring;

import java.util.*; // Importa todas as classes do pacote java.util
import java.util.regex.Pattern; // Importa a classe Pattern do pacote java.util.regex

// Classe ControlaMT implementa a interface MT
public class ControlaMT implements MT {

    // Códigos ANSI para cores
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String GREEN = "\033[0;92m";

    // Atributos da classe ControlaMT e métodos da interface MT
    private int numEstados; // Número de estados
    private String[] alfabeto; // Alfabeto
    private String[] alfabetoAux; // Alfabeto auxiliar
    private int estadoInicial; // Estado inicial
    private Set<Integer> aceitaFinal; // Conjunto de estados finais
    private Map<String, Transition> transicoes; // Mapa de transições
    private char[] fita; // Fita
    private int cabecote; // Cabeçote de leitura/escrita
    private Scanner scanner; // Lê entradas do usuário
    private String marcadorInicio; // Marcador de início
    private String branco; // Símbolo para banco

    // Classe para representar uma transição
    private class Transition {
        int novoEstado;
        char simboloAux;
        char direcao;

        Transition(int novoEstado, char simboloAux, char direcao) {
            this.novoEstado = novoEstado;
            this.simboloAux = simboloAux;
            this.direcao = direcao;
        }
    }

    // Método construtor da classe
    public ControlaMT() {
        scanner = new Scanner(System.in); // Inicializa o scanner
    }

    // Método para ler as entradas do usuário
    public void entradaUsuario() {
        System.out.println(PURPLE + " --- Linguagens Formais, Autômatos e Computabilidade ---\n  " + RESET);
        System.out.println("Trabalho de MT - 2° Bimestre - C.C UNESPAR\n");
        System.out.println(PURPLE + " --- SIMULADOR DE MÁQUINA DE TURING --- " + RESET);

        // Número de estados do MT
        System.out.println("\nDigite o número de estados:");
        while (true) {
            try {
                numEstados = Integer.parseInt(scanner.nextLine()); // Lê o número de estados e converte para inteiro
                if (numEstados <= 0) {
                    System.out.println(RED + "Digite um número inteiro positivo." + RESET);
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println(RED + "Digite um número inteiro." + RESET);
            }
        }

        // Estado inicial do MT
        System.out.println("\nDigite o estado inicial (entre 0 e " + (numEstados - 1) + "):");
        estadoInicial = lerEstadoInicial(numEstados); // Lê o estado inicial e verifica se é válido

        // Estados finais do MT
        System.out.println("\nDigite os estados finais (Separe por espaços):");
        String[] estadoFinal = scanner.nextLine().split(" ");   // Lê os estados finais e separa por espaços
        aceitaFinal = new HashSet<>(); // Cria um conjunto vazio
        for (String estado : estadoFinal) { // Loop para adicionar cada estado final ao conjunto
            aceitaFinal.add(Integer.parseInt(estado));  // Adiciona o estado final ao conjunto
        }

        // Alfabeto do MT
        System.out.println("\nDigite o alfabeto:");
        System.out.println("\nObs: apenas letras minúsculas ou números.");
        alfabeto = scanner.nextLine().split(" "); // Lê o alfabeto e separa por espaços

        // Valida o alfabeto inserido
        if (!validarAlfabeto(alfabeto)) {
            System.out.println(RED + "Erro: Apenas letras minúsculas ou números." + RESET);
            entradaUsuario();
            return; // Retorna para o início do método
        }

        // Alfabeto auxiliar do MT
        System.out.println("\nDigite o alfabeto auxiliar (Sem marcador de ínicio e branco):");
        alfabetoAux = scanner.nextLine().split(" "); // Lê o alfabeto aux. e separa por espaços

        System.out.println("\nDigite o marcador de ínicio:");
        marcadorInicio = scanner.nextLine(); // Lê o marcador de início 

        System.out.println("\nDigite um símbolo para banco:");
        branco = scanner.nextLine(); // Lê o símbolo para banco 

        // Cria o mapa de transições
        transicoes = new HashMap<>();

        // Preenchimento das transições
        System.out.println(BLUE + "\n---TRANSIÇÕES --" + RESET);
        System.out.println(RED + "\nPreencha as transições a seguir:" + RESET);
        for (int estado = 0; estado < numEstados; estado++) {
            for (String simbolo : alfabeto) {
                System.out.print("\nTransição para q" + estado + " lendo '" + simbolo + "': ");
                System.out.print("\nEstado futuro da transição: ");
                int novoEstado = Integer.parseInt(scanner.nextLine());
                System.out.print("Alfabeto futuro da transição: ");
                char simboloEscrito = scanner.nextLine().charAt(0);
                System.out.print("Direção futura da transição (E para esquerda/D para direita): ");
                char direcao = scanner.nextLine().charAt(0);

                transicoes.put(estado + simbolo, new Transition(novoEstado, simboloEscrito, direcao));
            }
        }

        // Leitura da fita inicial
        System.out.print("\nDigite a fita inicial: ");
        fita = scanner.nextLine().toCharArray();
        cabecote = 0; // Inicializando o cabeçote na posição inicial
    }

    // Método para a tabela de transições e descrição formal do MT
    public void tabelaTransicaoDescricao() {
        System.out.println(BLUE + "\n --- TABELA DE TRANSIÇÕES --- \n" + RESET);
        System.out.print("    ");
        for (String simbolo : alfabeto) {
            System.out.print(" " + simbolo);
        }

        System.out.println();
        for (int estado = 0; estado < numEstados; estado++) {
            System.out.print("q" + estado + " ");
            for (String simbolo : alfabeto) {
                Transition transicao = transicoes.get(estado + simbolo);
                if (transicao != null) {
                    System.out.print(" q" + transicao.novoEstado + "/" + transicao.simboloAux + "/" + transicao.direcao);
                } else {
                    System.out.print(" " + RED + "x" + RESET);
                }
            }
            System.out.println();
        }
    }

    // Método para testar cadeias de entrada no MT
    public void testarCadeias() {
        System.out.println(BLUE + "\n --- TESTAR FITA --- " + RESET);
        while (true) {
            System.out.println("\nInforme uma fita a ser testada (Ou '1' para encerrar, '2' para novo MT):");
            String entrada = scanner.nextLine().trim();

            if ("1".equalsIgnoreCase(entrada)) {
                break;
            } else if ("2".equalsIgnoreCase(entrada)) {
                entradaUsuario();
                continue;
            }

            fita = entrada.toCharArray();
            cabecote = 0;
            int estadoAtual = estadoInicial;
            boolean rejeitada = false;

            while (cabecote < fita.length && cabecote >= 0) {
                char simboloAtual = fita[cabecote];
                Transition transicao = transicoes.get(estadoAtual + String.valueOf(simboloAtual));

                if (transicao == null) {
                    rejeitada = true;
                    break;
                }

                fita[cabecote] = transicao.simboloAux;
                estadoAtual = transicao.novoEstado;
                cabecote += (transicao.direcao == 'R' ? 1 : -1);

                if (cabecote < 0 || cabecote >= fita.length) {
                    rejeitada = true;
                    break;
                }
            }

            if (rejeitada) {
                System.out.println(RED + "\nFita rejeitada." + RESET);
            } else if (aceitaFinal.contains(estadoAtual)) {
                System.out.println(GREEN + "\nResultado: Fita aceita pela MT." + RESET);
            } else {
                System.out.println(RED + "\nResultado: Fita não aceita pela MT." + RESET);
            }
        }
        scanner.close();
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

    // Método para ler um estado inicial válido
    private int lerEstadoInicial(int numEstados) {
        while (true) {
            try {
                int estadoInicial = Integer.parseInt(scanner.nextLine());
                if (estadoInicial < 0 || estadoInicial >= numEstados) {
                    System.out.println(RED + "Digite um estado inicial entre 0 e " + (numEstados - 1) + "." + RESET);
                } else {
                    return estadoInicial;
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "Digite um número inteiro." + RESET);
            }
        }
    }
}