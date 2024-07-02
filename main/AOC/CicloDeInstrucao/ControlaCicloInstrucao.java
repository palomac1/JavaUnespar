package AOC.CicloDeInstrucao;

import java.util.*;

// Arrumar para não exibir a manipulação vazia de op2 se não houver
// Arrumar NOP para ir direto para a operação de dados após decodificar
// **Arrumar o valor de MBR para ser 0 na instrução 000001 - Verificar se está correto a partir do exemplo
// Talvez tenha que zerar o MBR para cada instrução (?)
// **Verificar se o loop tem q ser infinito

public class ControlaCicloInstrucao implements CicloInstrucao {

    private int PC = 0; // Contador de Programa para armazenar a posição da instrução atual
    private int MBR = 0; // Registrador de Memória de Buffer
    private boolean flagZero = false; // Flag para verificar se o valor de MBR é zero
    private boolean flagNegativa = false; // Flag para verificar se o valor de MBR é negativo, usa falso porque o valor inicial de MBR é 0
    private int[] memoria = new int[256]; // Cria uma memória de 256 posições para armazenar os dados
    private Scanner scanner = new Scanner(System.in); // Cria um scanner para receber as instruções do usuário
    public List<String> instrucoes = new ArrayList<>(); // Cria uma lista de instruções para armazenar as instruções do usuário

    // Receve as instruções do usuario e as armazena em uma lista
    public void entradaUsuario() {
        System.out.println("Digite as instruções do programa (ou '4' para sair da inserção de dados):");
        /* Loop para receber as instruções do usuário, se ele digitar '4' o loop é interrompido e o programa continua para 
        que possa voltar ao menu de opções */
        while (true) {
            System.out.print("\nDigite o código da instrução: ");
            String instrucao = scanner.nextLine();
            if (instrucao.equals("4")) {
                break;
            }

            // Inicializa os operandos como vazio para não exibir caso não haja nenhum valor
            String op1 = "", op2 = ""; 
            // Verifica se a instrução não precisa de um operando
            if (!instrucao.equals("001010") && !instrucao.equals("001011") && !instrucao.equals("001100")) {
                System.out.print("Digite o primeiro operando: ");
                op1 = scanner.nextLine();
                // Verifica se a instrução não precisa de um segundo operando
                if (!instrucao.equals("000001") && !instrucao.equals("000011") && !instrucao.equals("000100") &&
                    !instrucao.equals("000101") && !instrucao.equals("000110") && !instrucao.equals("000111") &&
                    !instrucao.equals("001000") && !instrucao.equals("001001") && !instrucao.equals("001111")) {
                    System.out.print("Digite o segundo operando: ");
                    op2 = scanner.nextLine();
                }
            }

            instrucoes.add(instrucao + " " + op1 + " " + op2); // Adiciona a instrução com os operandos na lista de instruções
        }
    }

    // Exibe a tabela de instruções 
    public void verInstrucoes() {
        System.out.println("==================================================================================");
        System.out.printf("= INSTRUÇÕES: =\n");
        System.out.println("==================================================================================");
        System.out.printf("%-10s %-10s %-10s %-25s\n", "COD", "OP1", "OP2", "RESULTADOS");
        System.out.printf("%-10s %-10s %-10s %-25s\n", "000001", "#POS", "-", "MBR <- #POS");
        System.out.printf("%-10s %-10s %-10s %-25s\n", "000010", "#POS", "#DADO", "POS <- #DADO");
        System.out.printf("%-10s %-10s %-10s %-25s\n", "000011", "#POS", "-", "MBR <- MBR + #POS");
        System.out.printf("%-10s %-10s %-10s %-25s\n", "000100", "#POS", "-", "MBR <- MBR - #POS");
        System.out.printf("%-10s %-10s %-10s %-25s\n", "000101", "#POS", "-", "MBR <- MBR * #POS");
        System.out.printf("%-10s %-10s %-10s %-25s\n", "000110", "#POS", "-", "MBR <- MBR / #POS");
        System.out.printf("%-10s %-10s %-10s %-25s\n", "000111", "#LIN", "-", "JUMP to #LIN");
        System.out.printf("%-10s %-10s %-10s %-25s\n", "001000", "#LIN", "-", "JUMP IF Z to #LIN");
        System.out.printf("%-10s %-10s %-10s %-25s\n", "001001", "#LIN", "-", "JUMP IF N to #LIN");
        System.out.printf("%-10s %-10s %-10s %-25s\n", "001010", "-", "-", "MBR <- raiz_quadrada(MBR)");
        System.out.printf("%-10s %-10s %-10s %-25s\n", "001011", "-", "-", "MBR <- -MBR");
        System.out.printf("%-10s %-10s %-10s %-25s\n", "001111", "#POS", "-", "#POS <- MBR");
        System.out.printf("%-10s %-10s %-10s %-25s\n", "001100", "-", "-", "NOP");
        System.out.println("==================================================================================");

        // Exibe as instruções que já foram inseridas pelo usuario e seus operandos
        for (String instrucao : instrucoes) {
            System.out.println("Instruções que já foram inseridas: ");
            System.out.println(instrucao);
        }
    }

    // Executa todas as instruções armazenadas na lista de instruções e exibe o ciclo de instrução
    public void executarTodasInstrucoes() {
        System.out.println("==================================================================================");
        System.out.println("EXECUTANDO");
        for (String instrucao : instrucoes) { // Recebe a instrução da lista de instruções e executa
            executaInstrucao(instrucao); // Chama o método para executar a instrução
            exibeCiclo();
            System.out.println();
        }
    }

    // Executa a instrução passada como parâmetro e atualiza o IR com o opcode atual para exibir o ciclo de instrução e as suas operações 
    public void executaInstrucao(String instrucao) {
        String[] componentesInst = instrucao.split(" "); // Separa a instrução em componentes para pegar o opcode e os operandos e executar a instrução
        String opcode = componentesInst[0]; // Atualiza o opcode atual com o opcode da instrução futura para executar a instrução correta e exibir o ciclo de instrução

        switch (opcode) {
            case "000001":
                inst000001(Integer.parseInt(componentesInst[1])); // Converte o operando para inteiro e chama a instrução
                break;
            case "000010":
                inst000010(Integer.parseInt(componentesInst[1]), Integer.parseInt(componentesInst[2]));
                break;
            case "000011":
                inst000011(Integer.parseInt(componentesInst[1]));
                break;
            case "000100":
                inst000100(Integer.parseInt(componentesInst[1]));
                break;
            case "000101":
                inst000101(Integer.parseInt(componentesInst[1]));
                break;
            case "000110":
                inst000110(Integer.parseInt(componentesInst[1]));
                break;
            case "000111":
                inst000111(Integer.parseInt(componentesInst[1]));
                break;
            case "001000":
                inst001000(Integer.parseInt(componentesInst[1]));
                break;
            case "001001":
                inst001001(Integer.parseInt(componentesInst[1]));
                break;
            case "001010":
                inst001010();
                break;
            case "001011":
                inst001011();
                break;
            case "001111":
                inst001111(Integer.parseInt(componentesInst[1]));
                break;
            case "001100":
                inst001100();
                break;
            default:
                System.out.println("Instrução inválida");
                break;
        }
        PC++;
    }

    public void exibeCiclo() {
        String[] componentesInst = instrucoes.get(PC - 1).split(" "); // Pega a instrução atual e separa os componentes para exibir
        String opcode = componentesInst[0]; // Atualiza o opcode atual com o opcode da instrução atual
        String op1 = componentesInst.length > 1 ? componentesInst[1] : ""; // Atualiza o operando 1 atual com o operando 1 da instrução atual 
        String op2 = componentesInst.length > 2 ? componentesInst[2] : ""; // Atualiza o operando 2 atual com o operando 2 da instrução atual
    
        System.out.println("==================================================================================");
        System.out.println("CÁLCULO DO ENDEREÇO DA INSTRUÇÃO:");
        System.out.printf("PC: %06d\n", PC);
        System.out.println("\nBUSCANDO A INSTRUÇÃO:");
        System.out.println("IR <OPCODE>: " + opcode);
        System.out.println("IR <OP1>: " + op1);
        System.out.println("IR <OP2>: " + op2);
        System.out.println("\nDECODIFICANDO A INSTRUÇÃO:");
        switch (opcode) {
            case "000001":
                System.out.println("MBR <- #POS"); //MBR teria que ser 0, mas está mostrando o msm valor do op1 (?)
                System.out.println(MBR + " <- " + op1);
                break;
            case "000010":
                System.out.println("#POS <- #DADO");
                System.out.println(op1 + " <- " + op2);
                break;
            case "000011":
                System.out.println("MBR <- MBR + #POS");
                System.out.println(MBR + " <- " + MBR + " + " + op1);
                break;
            case "000100":
                System.out.println("MBR <- MBR - #POS");
                System.out.println(MBR + " <- " + MBR + " - " + op1);
                break;
            case "000101":
                System.out.println("MBR <- MBR * #POS");
                System.out.println(MBR + " <- " + MBR + " * " + op1);
                break;
            case "000110":
                System.out.println("MBR <- MBR / #POS");
                System.out.println(MBR + " <- " + MBR + " / " + op1);
                break;
            case "000111":
                System.out.println("JUMP to #LIN");
                System.out.println("JUMP to " + op1);
                break;
            case "001000":
                System.out.println("JUMP IF Z to #LIN");
                System.out.println("JUMP IF Z to " + op1);
                break;
            case "001001":
                System.out.println("JUMP IF N to #LIN");
                System.out.println("JUMP IF N to " + op1);
                break;
            case "001010":
                System.out.println("MBR <- sqrt(MBR)");
                System.out.println("MBR <- sqrt(" + MBR + ")");
                break;
            case "001011":
                System.out.println("MBR <- -MBR");
                System.out.println("MBR <- -" + MBR);
                break;
            case "001111":
                System.out.println("#POS <- MBR");
                System.out.println(op1 + " <- " + MBR);
                break;
            case "001100":
                System.out.println("NOP"); // Fazer com a partir daqui vá direto para a operação de dados
                break;
            default:
                System.out.println("OPCODE não reconhecido");
                break;
        }
        System.out.println("\nCÁLCULO DO ENDEREÇO DO OPERANDO:");
        System.out.println("Endereço: " + op1);
        System.out.println("\nBUSCANDO O OPERANDO NA POSIÇÃO:");
        System.out.println("MAR: " + op1);
        System.out.println("\nCÁLCULO DO ENDEREÇO DO SEGUNDO OPERANDO:");
        System.out.println("Endereço: " + op2);
        System.out.println("\nBUSCANDO O SEGUNDO OPERANDO NA POSIÇÃO:");
        System.out.println("MAR: " + op2);
        System.out.println("\nOPERAÇÃO DE DADOS:");
    
        switch (opcode) {
            case "000001":
                System.out.println("VALOR DO MBR: " + MBR);
                System.out.println("VALOR NA MEMÓRIA: " + memoria[Integer.parseInt(op1)]);
                System.out.println("VALOR DO MBR APÓS A OPERAÇÃO: " + MBR + " + " + memoria[Integer.parseInt(op1)] + " = " + (MBR + memoria[Integer.parseInt(op1)])); 
                System.out.println("O VALOR FOI ARMAZENADO!");
                break;
            case "001111":
                System.out.println("VALOR DO MBR: " + MBR);
                System.out.println("VALOR DO ENDEREÇO APÓS A OPERAÇÃO: " + op1);
                System.out.println("O VALOR FOI ARMAZENADO!");
                break;
            case "000011":
                System.out.println("VALOR DO MBR: " + MBR);
                // Arrumar op2 para não ser uma string
                System.out.println("VALOR DO CONTEÚDO NA POSIÇÃO: " + memoria[Integer.parseInt(op1)]);
                System.out.println("VALOR DO MBR APÓS A OPERAÇÃO: " + MBR + " + " + memoria[Integer.parseInt(op1)] + " = " + (MBR + memoria[Integer.parseInt(op1)])); // Passa para inteiro e soma
                System.out.println("O VALOR FOI ARMAZENADO!");
                break;
            case "001100":
                System.out.println("ENCERRANDO OPERAÇÃO DE DADOS");
                System.out.println("OPERAÇÃO FINALIZADA!");
                System.exit(0);
            case "000010":
            case "000100":
            case "000101":
            case "000110":
            case "000111":
            case "001000":
            case "001001":
            case "001010":
            case "001011":
                System.out.println("ARMAZENANDO: " + op2);
                System.out.println("NA POSIÇÃO: " + op1);
                System.out.println("\nCALCULANDO ENDEREÇO DO OPERANDO:");
                System.out.println("ENDEREÇO: " + op1);
                System.out.println("\nARMAZENANDO O OPERANDO:");
                System.out.println("MAR: " + op1);
                System.out.println("O VALOR FOI ARMAZENADO!");
                break;
            default:
                break;
        }
    
        System.out.println("==================================================================================");
    }
    
    // Método principal para executar o ciclo de instrução e suas operações
    public static void main(String[] args) {
        ControlaCicloInstrucao controlador = new ControlaCicloInstrucao();
        controlador.entradaUsuario();
        controlador.verInstrucoes();
        controlador.executarTodasInstrucoes();
    }

    // Métodos de instrução específicos para cada operação
    public void inst000001(int pos) {
        MBR = pos;
        atualizaFlags();
    }

    public void inst000010(int pos, int dado) {
        memoria[pos] = dado;
    }

    public void inst000011(int pos) { 
        MBR += memoria[pos];
        atualizaFlags();
    }

    public void inst000100(int pos) {
        MBR -= pos;
        atualizaFlags();
    }

    public void inst000101(int pos) {
        MBR *= pos;
        atualizaFlags();
    }

    public void inst000110(int pos) {
        MBR /= pos;
        atualizaFlags();
    }

    public void inst000111(int lin) {
        PC = lin;
    }

    public void inst001000(int lin) {
        if (flagZero) {
            PC = lin;
        }
    }

    public void inst001001(int lin) {
        if (flagNegativa) {
            PC = lin;
        }
    }

    public void inst001010() {
        MBR = (int) Math.sqrt(MBR);
        atualizaFlags();
    }

    public void inst001011() {
        MBR = -MBR;
        atualizaFlags();
    }

    public void inst001111(int pos) {
        memoria[pos] = MBR;
    }

    public void inst001100() {
        // NOP
    }

    private void atualizaFlags() {
        flagZero = (MBR == 0);
        flagNegativa = (MBR < 0);
    }
}
