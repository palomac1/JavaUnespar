package LFA.MaquinaTuring;

import java.util.*; 
import java.util.stream.Stream; 

// Classe ControlaMT que implementa a interface MT para controlar a Máquina de Turing
public class ControlaMT implements MT {

    // Tabela de cores ANSI
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String GREEN = "\033[0;92m";

    // Variáveis de instância para armazenar os dados da MT e a fita de entrada atual 
    private int numEstados; // Armazena o número de estados da MT
    private String[] alfabeto; // Cria um array de strings para armazenar o alfabeto da MT
    private String[] alfabetoAux; // Cria um array de strings para armazenar o alfabeto auxiliar da MT
    private int estadoInicial; // Armazena o estado inicial da MT
    private Set<Integer> aceitaFinal; // Cria um conjunto de estados finais para armazenar os estados finais da MT
    private Map<ParEstadoSimbolo, Transicao> transicoes; // Cria um mapa de transições para armazenar as transições da MT 
    private char[] fita; // Cria um array de caracteres para representar a fita da MT
    private int cabecote; // Armazena a posição do cabeçote na fita
    private Scanner scanner; // Cria um scanner para ler as entradas do usuário
    private char marcadorInicio; // Armazena o marcador de início da fita
    private char branco; // Armazena o símbolo branco da fita

    // Construtor da classe ControlaMT, utilizado para inicializar o scanner para leitura de entradas do usuário
    public ControlaMT() {
        scanner = new Scanner(System.in);
    }
    
    //**Essa classe interna vai armazenar o estado atual e o símbolo lido na fita, para identificar as transições da maquina
    // então os metodos equals(que vaai comparar e verificar se os objetos sao do mesmo valor e classe) e o hashCode(que vai gerar um mesmo codigo para os dois atributos
    // guarantindo que eles tem o mesmo codigo e valor) 
    // Classe interna para armazenar o estado atual e o símbolo lido na fita (usado para acessar as transições da MT)
    private class ParEstadoSimbolo {
        int estado; // Cria uma variável para armazenar o estado atual
        char simbolo; // Cria uma variável para armazenar o símbolo lido na fita

        // Construtor da classe ParEstadoSimbolo, utilizado para criar um novo par de estado e símbolo usado para acessar as transições da MT
        ParEstadoSimbolo(int estado, char simbolo) {
            this.estado = estado;
            this.simbolo = simbolo;
        }

        // Sobrescreve(Pois sem ele pode retornar um valor só pra cada instancia, baseado no endereço, isso faz com que valores iguais sejam identificados como coisas diferentes)
        // o método equals para comparar dois objetos ParEstadoSimbolo e verificar se são iguais 
        @Override
        public boolean equals(Object o) {
            if (this == o) return true; // Verifica se os objetos são iguais
            if (o == null || getClass() != o.getClass()) return false; // Verifica se a classe dos objetos é a mesma
            ParEstadoSimbolo that = (ParEstadoSimbolo) o; // Converte o objeto para a classe ParEstadoSimbolo
            return estado == that.estado && simbolo == that.simbolo; // Compara os estados e símbolos dos objetos
        }

        /* Sobrescreve o método hashCode para gerar um código hash para o objeto ParEstadoSimbolos, isso quer dizer que objetos 
        iguais terão o mesmo código hash para facilitar a comparação */
        @Override
        public int hashCode() {
            return Objects.hash(estado, simbolo); //Usa objects para calcuar o valor hash com base nos estados e símbolos
        }
    }

    // Classe interna para armazenar as transições da MT 
    private class Transicao {
        int novoEstado;
        char simboloAux;
        char direcao;

        // Construtor da classe Transicao, utilizado para criar uma nova transição e armazenar o novo estado, símbolo e direção
        Transicao(int novoEstado, char simboloAux, char direcao) {
            this.novoEstado = novoEstado;
            this.simboloAux = simboloAux;
            this.direcao = direcao;
        }
    }

    // Método que solicita ao usuário as entradas necessárias para configurar a máquina
    public void entradaUsuario() {
        System.out.println(PURPLE + " --- Linguagens Formais, Autômatos e Computabilidade ---\n  " + RESET);
        System.out.println("Trabalho de LFA - 2° Bimestre - C.C UNESPAR\n");
        System.out.println("Paloma de Castro Leite - 2ª Ano - 22.07.24");
        System.out.println(PURPLE + " --- SIMULADOR DE MÁQUINA DE TURING --- " + RESET);

        System.out.println("\nDigite o número de estados:"); // Solicita ao usuário o número de estados da MT
        numEstados = lerNumeroPositivo();

        System.out.println("\nDigite o estado inicial (entre 0 e " + (numEstados - 1) + "):"); // Solicita ao usuário o estado inicial da MT
        estadoInicial = lerEstadoInicial(numEstados);

        System.out.println("\nDigite os estados finais (Separe por espaços):"); // Solicita ao usuário os estados finais da MT
        aceitaFinal = new HashSet<>();
        for (String estado : scanner.nextLine().split(" ")) {
            aceitaFinal.add(Integer.parseInt(estado));
        }

        System.out.println("\nDigite o alfabeto (Separe por espaços):"); // Solicita ao usuário o alfabeto da MT
        alfabeto = scanner.nextLine().split(" ");

        System.out.println("\nDigite o alfabeto auxiliar (Sem marcador de ínicio e branco):"); // Solicita ao usuário o alfabeto auxiliar da MT
        alfabetoAux = scanner.nextLine().split(" ");

        System.out.println("\nDigite o marcador de início:"); // Solicita ao usuário o marcador de início da fita
        marcadorInicio = scanner.nextLine().charAt(0);

        System.out.println("\nDigite um símbolo branco:"); // Solicita ao usuário o símbolo branco da fita
        branco = scanner.nextLine().charAt(0);

        transicoes = new HashMap<>(); // Inicializa o mapa de transições da MT com os estados e símbolos lidos

        System.out.println(BLUE + "\n---TRANSIÇÕES --" + RESET);
        System.out.println(RED + "\nPreencha as transições a seguir:" + RESET);
        System.out.println(RED + "\nOBS: Caso não haja transição, insira X para anular o campo" + RESET);

        // Cria um array com todos os símbolos possíveis da MT (alfabeto, alfabeto auxiliar, marcador de início e branco) para facilitar a leitura das transições 
        String[] todosSimbolos = Stream.concat(Stream.concat(Arrays.stream(alfabeto), Arrays.stream(alfabetoAux)), Stream.of(String.valueOf(marcadorInicio), String.valueOf(branco))).toArray(String[]::new);

        // Para cada estado e símbolo, solicita ao usuário o novo estado, alfabeto futuro e direção da transição 
        for (int estado = 0; estado < numEstados; estado++) {
            for (String simbolo : todosSimbolos) {
                System.out.print("\nTransição para q" + estado + " lendo '" + simbolo + "': ");
                String transicaoInput = scanner.nextLine();

                // Se o usuário inserir 'X', a transição é anulada 
                if (transicaoInput.equalsIgnoreCase("X")) {
                    continue;
                }

                // Lê o novo estado, símbolo e direção da transição
                int novoEstado = Integer.parseInt(transicaoInput); // Converte o novo estado para inteiro e armazena na variável novoEstado
                System.out.print("Alfabeto futuro da transição: ");
                char simboloEscrito = scanner.nextLine().charAt(0); // Lê o símbolo a ser escrito na fita e armazena na variável simboloEscrito
                System.out.print("Direção futura da transição (E para esquerda/D para direita): ");
                char direcao = scanner.nextLine().charAt(0); // Lê a direção da transição (E para esquerda, D para direita)

                // Adiciona a transição ao mapa de transições
                transicoes.put(new ParEstadoSimbolo(estado, simbolo.charAt(0)), new Transicao(novoEstado, simboloEscrito, direcao));
            }
        }
    }

    // Método para testar as fitas da máquina
    public void testarCadeias() {
        System.out.println(BLUE + "\n --- TESTAR FITA --- " + RESET);
        while (true) {
            System.out.println("\nInforme uma fita a ser testada (Ou '1' para encerrar, '2' para novo MT, '3' para testar outra fita):");
            String entrada = scanner.nextLine().trim();
    
            if ("1".equalsIgnoreCase(entrada)) { // Encerra o programa
                break;
            } else if ("2".equalsIgnoreCase(entrada)) { // Retorna ao início do loop para configurar uma nova MT
                entradaUsuario();
                continue;
            } else if ("3".equalsIgnoreCase(entrada)) {
                continue;  // Volta ao início do loop para testar outra fita
            }
    
            if (!entrada.startsWith(String.valueOf(marcadorInicio))) { //Se a entrada não começar com o marcador de início, adiciona o marcador de início
                entrada = marcadorInicio + entrada;
            }
    
            fita = entrada.toCharArray(); // Converte a fita em um array de caracteres para facilitar a manipulação dos símbolos e estados
            cabecote = 0; // Inicializa o cabeçote da MT na posição 0 da fita
            int estadoAtual = estadoInicial; // Inicializa o estado atual com o estado inicial da MT
            boolean rejeitada = false; // Variável para verificar se a fita foi rejeitada pela MT
            boolean palavraAceita = false; // Variável para verificar se a palavra foi aceita pela MT 
    
            // Enquanto a fita não for rejeitada e a palavra não for aceita, executa as transições da MT
            while (true) {
                if (cabecote < 0 || cabecote >= fita.length) {
                    rejeitada = true;
                    break;
                }
    
                // Pega o símbolo atual da fita e a transição correspondente ao estado e símbolo atuais para a próxima execução 
                char simboloAtual = fita[cabecote]; 
                Transicao transicao = transicoes.get(new ParEstadoSimbolo(estadoAtual, simboloAtual));
    
                // Se não houver transição para o estado e símbolo atual, a fita é rejeitada
                if (transicao == null) {
                    rejeitada = true;
                    break;
                }
    
                System.out.println("\nEstado atual: q" + estadoAtual);
                System.out.println("Símbolo atual: " + simboloAtual);
                System.out.println("Fita: " + new String(fita).trim());
                System.out.println("Próxima transição: (q" + estadoAtual + "," + simboloAtual + ") -> (q" + transicao.novoEstado + "," + transicao.simboloAux + "," + transicao.direcao + ")");
                System.out.println("--------------------------------");
    
                fita[cabecote] = transicao.simboloAux; // Escreve o novo símbolo na fita
                estadoAtual = transicao.novoEstado; // Atualiza o estado atual com o novo estado
                cabecote += (transicao.direcao == 'D' ? 1 : -1); // Move o cabeçote para a direita ou esquerda, dependendo da direção da transição
    
                if (cabecote < 0) { // Se o cabeçote estiver antes do início da fita, expande a fita para a esquerda
                    fita = adicionarBranco(fita, true);
                    cabecote = 0;
                } else if (cabecote >= fita.length) { // Se o cabeçote estiver após o final da fita, expande a fita para a direita
                    fita = adicionarBranco(fita, false);
                }
     
                // Se o estado atual for um estado final, a palavra é aceita pela MT
                if (aceitaFinal.contains(estadoAtual)) {
                    palavraAceita = true;
                    break;
                }
            }
    
            /* Imprime o resultado da execução da MT na fita, usando o replaceAll para remover os espaços em branco da fita e o
             replace para adicionar o marcador de início e branco*/
            System.out.println("Fita final: " + new String(fita).replaceAll("_", "") + new String(new char[5]).replace("\0", "<"));
    
            if (rejeitada) {
                System.out.println(RED + "\nFita rejeitada." + RESET);
            } else if (palavraAceita) {
                System.out.println(GREEN + "\nResultado: Fita aceita pela MT." + RESET);
            } else {
                System.out.println(RED + "\nResultado: Fita não aceita pela MT." + RESET);
            }
        }
        scanner.close();
    }
    
    // Método para ampliar a fita para a esquerda ou direita, adicionando um novo símbolo em branco 
    private char[] adicionarBranco(char[] fita, boolean adicionarBrancoEsquerda) {
        char[] novaFita = new char[fita.length + 1]; // Cria um novo array de caracteres com o tamanho da fita mais um símbolo em branco 
        if (adicionarBrancoEsquerda) {
            System.arraycopy(fita, 0, novaFita, 1, fita.length); // Copia os símbolos da fita para o novo array, começando da posição 1 porque a fita foi expandida para a esquerda
            novaFita[0] = branco;
        } else {
            System.arraycopy(fita, 0, novaFita, 0, fita.length); // Copia os símbolos da fita para o novo array, começando da posição 0 porque a fita foi expandida para a direita
            novaFita[fita.length] = branco;
        }
        return novaFita;
    }

    // Método para ler um número positivo da entrada do usuário
    private int lerNumeroPositivo() {
        while (true) {
            try {
                int num = Integer.parseInt(scanner.nextLine());
                if (num > 0) {
                    return num;
                }
                System.out.println(RED + "Número inválido. Digite novamente:" + RESET);
            } catch (NumberFormatException e) {
                System.out.println(RED + "Entrada inválida. Digite novamente:" + RESET);
            }
        }
    }

    // Método para ler o estado inicial da MT
    private int lerEstadoInicial(int maxEstados) {
        while (true) {
            try {
                int estado = Integer.parseInt(scanner.nextLine());
                if (estado >= 0 && estado < maxEstados) {
                    return estado;
                }
                System.out.println(RED + "Estado inválido. Digite novamente:" + RESET);
            } catch (NumberFormatException e) {
                System.out.println(RED + "Entrada inválida. Digite novamente:" + RESET);
            }
        }
    }
}
