package LFA.MaquinaTuring;

import java.util.*;
import java.util.stream.Stream; // Manipula fluxo de dados de entrada e saída

// Arrumar transições (-1 para X)
// Arrumar tabela de transições (alinhamento) 
// Arrumar fita para validar as palavras e exibir a fita final

public class ControlaMT implements MT {

    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String GREEN = "\033[0;92m";

    private int numEstados;
    private String[] alfabeto;
    private String[] alfabetoAux;
    private int estadoInicial;
    private Set<Integer> aceitaFinal;
    private Map<String, Transicao> transicoes; // Função da transição
    private char[] fita; // Array de caracteres
    private int cabecote;
    private Scanner scanner;
    private String marcadorInicio;
    private String branco;

    private class Transicao {
        int novoEstado;
        char simboloAux;
        char direcao;

    // Representa uma transição da maquina de turing
    Transicao(int novoEstado, char simboloAux, char direcao) {
            this.novoEstado = novoEstado;
            this.simboloAux = simboloAux;
            this.direcao = direcao;
        }
    }

    // Lê a entrada do usuário
    public ControlaMT() {
        scanner = new Scanner(System.in);
    }

    public void entradaUsuario() {
        System.out.println(PURPLE + " --- Linguagens Formais, Autômatos e Computabilidade ---\n  " + RESET);
        System.out.println("Trabalho de MT - 2° Bimestre - C.C UNESPAR\n");
        System.out.println(PURPLE + " --- SIMULADOR DE MÁQUINA DE TURING --- " + RESET);

        System.out.println("\nDigite o número de estados:");
        numEstados = lerNumeroPositivo();

        System.out.println("\nDigite o estado inicial (entre 0 e " + (numEstados - 1) + "):");
        estadoInicial = lerEstadoInicial(numEstados);

        System.out.println("\nDigite os estados finais (Separe por espaços):");
        aceitaFinal = new HashSet<>(); 
        for (String estado : scanner.nextLine().split(" ")) {
            aceitaFinal.add(Integer.parseInt(estado));
        }

        System.out.println("\nDigite o alfabeto (Separe por espaços):");
        alfabeto = scanner.nextLine().split(" ");

        System.out.println("\nDigite o alfabeto auxiliar (Sem marcador de ínicio e branco):");
        alfabetoAux = scanner.nextLine().split(" ");

        System.out.println("\nDigite o marcador de ínicio:");
        marcadorInicio = scanner.nextLine();

        System.out.println("\nDigite um símbolo branco:");
        branco = scanner.nextLine();

        transicoes = new HashMap<>(); // Cria uma nova tabela de transições com os estados e símbolos lidos

        System.out.println(BLUE + "\n---TRANSIÇÕES --" + RESET);
        System.out.println(RED + "\nPreencha as transições a seguir:" + RESET);
        System.out.println(RED + "\nOBS: Caso não haja transição, insira X para anular o campo" + RESET);

        String[] todosSimbolos = Stream.concat(Stream.concat(Arrays.stream(alfabeto), Arrays.stream(alfabetoAux)), Stream.of(marcadorInicio, branco)).toArray(String[]::new);

        for (int estado = 0; estado < numEstados; estado++) {
            for (String simbolo : todosSimbolos) {
                System.out.print("\nTransição para q" + estado + " lendo '" + simbolo + "': ");
                System.out.print("\nEstado futuro da transição: ");
                int novoEstado = Integer.parseInt(scanner.nextLine());
                System.out.print("Alfabeto futuro da transição: ");
                char simboloEscrito = scanner.nextLine().charAt(0);
                System.out.print("Direção futura da transição (E para esquerda/D para direita): ");
                char direcao = scanner.nextLine().charAt(0);

                transicoes.put(estado + simbolo, new Transicao(novoEstado, simboloEscrito, direcao));
            } 
        }
    }

    public void tabelaTransicaoDescricao() {
        System.out.println(BLUE + "\n --- TABELA DE TRANSIÇÕES --- \n" + RESET);
        System.out.print("    ");

        String[] todosSimbolos = Stream.concat(Stream.concat(Arrays.stream(alfabeto), Arrays.stream(alfabetoAux)), Stream.of(marcadorInicio, branco)).toArray(String[]::new);

        for (String simbolo : todosSimbolos) {
            System.out.print(" " + simbolo);
        } 

        System.out.println();
        for (int estado = 0; estado < numEstados; estado++) {
            System.out.print("q" + estado + " ");
            for (String simbolo : todosSimbolos) {
                Transicao transicao = transicoes.get(estado + simbolo);
                if (transicao != null) {
                    System.out.print(" q" + transicao.novoEstado + "/" + transicao.simboloAux + "/" + transicao.direcao);
                } else {
                    System.out.print(" " + RED + "x" + RESET);
                }
            }
            System.out.println();
        }
    }

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
                Transicao transicao = transicoes.get(estadoAtual + String.valueOf(simboloAtual));

                if (transicao == null) {
                    rejeitada = true;
                    break;
                }

                fita[cabecote] = transicao.simboloAux;
                estadoAtual = transicao.novoEstado;
                cabecote += (transicao.direcao == 'D' ? 1 : -1);

                if (cabecote < 0 || cabecote >= fita.length) {
                    rejeitada = true;
                    break;
                }
            }

            System.out.println("Fita final: " + Arrays.toString(fita));

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

    private int lerNumeroPositivo() {
        while (true) {
            try {
                int numero = Integer.parseInt(scanner.nextLine());
                if (numero <= 0) {
                    System.out.println(RED + "Digite um número inteiro positivo." + RESET);
                } else {
                    return numero;
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "Digite um número inteiro." + RESET);
            }
        }
    }

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
