package AOC.CicloDeInstrucao;

import java.util.*;

public class ControlaCicloInstrucao implements CicloInstrucao {

    private int PC = 0;
    private int IR = 0;
    private int MAR = 0;
    private int MBR = 0;
    private boolean flagZero = false;
    private boolean flagNegativa = false;
    private int[] memoria = new int[256];
    private Scanner scanner = new Scanner(System.in);
    public List<String> instrucoes = new ArrayList<>();

    public void entradaUsuario() {
        System.out.println("Digite as instruções do programa (ou '4' para sair):");
        while (true) {
            System.out.print("\nDigite o código da instrução: ");
            String instrucao = scanner.nextLine();
            if (instrucao.equals("4")) { 
                break;
            } 

            String op1 = "", op2 = "";
            if (!instrucao.equals("001010") && !instrucao.equals("001011") && !instrucao.equals("001100")) {
                System.out.print("Digite o primeiro operando: ");
                op1 = scanner.nextLine();
                if (!instrucao.equals("000001") && !instrucao.equals("000011") && !instrucao.equals("000100") &&
                    !instrucao.equals("000101") && !instrucao.equals("000110") && !instrucao.equals("000111") &&
                    !instrucao.equals("001000") && !instrucao.equals("001001") && !instrucao.equals("001111")) {
                    System.out.print("Digite o segundo operando: ");
                    op2 = scanner.nextLine();
                }
            }

            instrucoes.add(instrucao + " " + op1 + " " + op2);
        }
    }

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

        for (String instrucao : instrucoes) {
            System.out.println(instrucao);
        }
    }

    public void executarTodasInstrucoes() {
        System.out.println("==================================================================================");
        System.out.println("EXECUTANDO");
        for (String instrucao : instrucoes) {
            executaInstrucao(instrucao);
            exibeCiclo();
            System.out.println();
        }
    }

    public void executaInstrucao(String instrucao) {
        String[] componentesInst = instrucao.split(" ");
        String opcode = componentesInst[0];

        IR = Integer.parseInt(opcode); // Atualiza o registrador IR com o opcode atual
        MAR = 0; // Limpa o MAR antes de cada operação

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
        String op1 = "", op2 = ""; // Limpa os operandos antes de cada execução

        if (!opcode.equals("001010") && !opcode.equals("001011") && !opcode.equals("001100")) {
            op1 = componentesInst[1];
            if (!opcode.equals("000001") && !opcode.equals("000011") && !opcode.equals("000100") &&
                    !opcode.equals("000101") && !opcode.equals("000110") && !opcode.equals("000111") &&
                    !opcode.equals("001000") && !opcode.equals("001001") && !opcode.equals("001111")) {
                op2 = componentesInst[2];
            }
        }

        System.out.println("==================================================================================");
        System.out.println("CÁLCULO DO ENDEREÇO DA INSTRUÇÃO:");
        System.out.printf("PC: %06d\n", PC);
        System.out.println("\nBUSCANDO A INSTRUÇÃO:");
        System.out.println("RI <OPCODE>: " + opcode);
        System.out.println("RI <OP1>: " + op1);
        System.out.println("RI <OP2>: " + op2);
        System.out.println("\nDECODIFICANDO A INSTRUÇÃO:");
        switch (opcode) {
            case "000001":
                System.out.println("MBR <- #POS");
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
                System.out.println("MBR <- raiz_quadrada(MBR)");
                System.out.println("MBR <- raiz_quadrada(" + MBR + ")");
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
                System.out.println("NOP");
                break;
            default:
                System.out.println("OPCODE não reconhecido");
                break;
        }
        System.out.println("\nCÁLCULO DO ENDEREÇO DO OPERANDO: ");
        System.out.println("Endereço: " + MAR);
        System.out.println("\nBUSCANDO O OPERANDO NA POSIÇÃO:");
        System.out.println("MAR: " + MAR);
        System.out.println("\nCÁLCULO DO ENDEREÇO DO SEGUNDO OPERANDO: ");
        System.out.println("Endereço: " + MAR);
        System.out.println("\nBUSCANDO O SEGUNDO OPERANDO NA POSIÇÃO: ");
        System.out.println("MAR: " + MAR);
        System.out.println("\nOPERAÇÃO DE DADOS: ");
        // Implementar a lógica para a operação de dados, dependendo da instrução
        System.out.println("\nCALCULANDO ENDEREÇO DO OPERANDO:");
        System.out.println("Endereço: " + MAR);
        System.out.println("\nARMAZENANDO O OPERANDO: ");
        System.out.println("MAR: " + MAR);
        System.out.println("O VALOR FOI ARMAZENADO!");
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
        updateFlags();
    }

    public void inst000010(int pos, int dado) {
        memoria[pos] = dado;
    }

    public void inst000011(int pos) {
        MBR += pos;
        updateFlags();
    }

    public void inst000100(int pos) {
        MBR -= pos;
        updateFlags();
    }

    public void inst000101(int pos) {
        MBR *= pos;
        updateFlags();
    }

    public void inst000110(int pos) {
        MBR /= pos;
        updateFlags();
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
        updateFlags();
    }

    public void inst001011() {
        MBR = -MBR;
        updateFlags();
    }

    public void inst001111(int pos) {
        memoria[pos] = MBR;
    }

    public void inst001100() {
        // NOP
    }

    private void updateFlags() {
        flagZero = (MBR == 0);
        flagNegativa = (MBR < 0);
    }
}
