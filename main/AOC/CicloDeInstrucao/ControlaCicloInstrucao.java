package AOC.CicloDeInstrucao;

import java.util.*;

public class ControlaCicloInstrucao implements CicloInstrucao {

    private int PC = 0; // Contador de Programa para armazenar a posição da instrução atual
    private int MBR = 0; // Registrador de Memória de Buffer
    private boolean flagZero = false; // Flag para verificar se o valor de MBR é zero
    private boolean flagNegativa = false; // Flag para verificar se o valor de MBR é negativo
    private int[] memoria = new int[256]; // Cria uma memória de 256 posições para armazenar os dados
    private Scanner scanner = new Scanner(System.in); // Cria um scanner para receber as instruções do usuário
    public List<String> instrucoes = new ArrayList<>(); // Cria uma lista de instruções para armazenar as instruções do usuário

    // Método para receber a entrada do usuário por meio de um scanner e armazenar as instruções em uma lista de strings chamada instrucoes
    public void entradaUsuario() {
        System.out.println("Digite as instruções do programa (ou '4' para sair da inserção de dados):");
        while (true) {
            System.out.print("\nDigite o código da instrução (ou '4' para voltar ao menu): ");
            String instrucao = scanner.nextLine();
            if (instrucao.equals("4")) {
                break;
            }

            // Verifica se a instrução requer operando(s) e solicita ao usuário que insira o(s) operando(s) se necessário
            String op1 = "", op2 = ""; 
            if (!instrucao.equals("000001") && !instrucao.equals("001010") && !instrucao.equals("001011") && !instrucao.equals("001100")) {
                System.out.print("Digite o primeiro operando: ");
                op1 = scanner.nextLine();
                if (!instrucao.equals("000011") && !instrucao.equals("000100") &&
                    !instrucao.equals("000101") && !instrucao.equals("000110") && !instrucao.equals("000111") &&
                    !instrucao.equals("001000") && !instrucao.equals("001001") && !instrucao.equals("001111")) {
                    System.out.print("Digite o segundo operando: ");
                    op2 = scanner.nextLine();
                }
            }

            // Adiciona a instrução à lista de instruções com os operandos, se houver
            instrucoes.add(instrucao + " " + op1 + " " + op2);
        }
    }

    // Tabela para mostrar as intruções e depois para mostrar as instruções e os operandos inseridos pelo usuário
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
        System.out.printf("%-10s %-10s %-10s %-25s\n", "001010", "-", "-", "MBR <- sqrt(MBR)");
        System.out.printf("%-10s %-10s %-10s %-25s\n", "001011", "-", "-", "MBR <- -MBR");
        System.out.printf("%-10s %-10s %-10s %-25s\n", "001111", "#POS", "-", "#POS <- MBR");
        System.out.printf("%-10s %-10s %-10s %-25s\n", "001100", "-", "-", "NOP");
        System.out.println("==================================================================================");

        System.out.println("Instruções que já foram inseridas: ");
        for (String instrucao : instrucoes) {
            System.out.println(instrucao);
        }
    }

    // Método para executar todas as instruções inseridas pelo usuário
    public void executarTodasInstrucoes() {
        System.out.println("==================================================================================");
        System.out.println("EXECUTANDO");
        // Enquanto houver instruções a serem executadas na lista de instruções faça o loop para executar cada instrução
        while (PC < instrucoes.size()) { 
            String instrucao = instrucoes.get(PC); // Obtém a instrução atual
            executaInstrucao(instrucao);
            exibeCiclo();
            System.out.println();
        }
    }

    // Método para executar a instrução passada como parâmetro e atualizar o PC
    public void executaInstrucao(String instrucao) {
        String[] componentesInst = instrucao.split(" ");
        String opcode = componentesInst[0]; // Obtém o opcode da instrução

      try{
         switch (opcode) {
            case "000001":
                if (componentesInst.length > 1) {
                    int pos = Integer.parseInt(componentesInst[1]);
                    inst000001(pos);
                } else {
                    System.out.println("Instrução inválida: Falta o operando.");
                }
                break;
             case "000010":
                if (componentesInst.length > 2) {
                    int pos = Integer.parseInt(componentesInst[1]);
                    int dado = Integer.parseInt(componentesInst[2]);
                    inst000010(pos, dado);
                } else {
                    System.out.println("Instrução inválida: Faltam operandos.");
                }
            break;
            case "000011":
                if (componentesInst.length > 1) inst000011(Integer.parseInt(componentesInst[1]));
                break;
            case "000100":
                if (componentesInst.length > 1) inst000100(Integer.parseInt(componentesInst[1]));
                break;
            case "000101":
                if (componentesInst.length > 1) inst000101(Integer.parseInt(componentesInst[1]));
                break;
            case "000110":
                if (componentesInst.length > 1) inst000110(Integer.parseInt(componentesInst[1]));
                break;
            case "000111":
                if (componentesInst.length > 1) inst000111(Integer.parseInt(componentesInst[1]));
                break;
            case "001000":
                if (componentesInst.length > 1) inst001000(Integer.parseInt(componentesInst[1]));
                break;
            case "001001":
                if (componentesInst.length > 1) inst001001(Integer.parseInt(componentesInst[1]));
                break;
            case "001010":
                inst001010();
                break;
            case "001011":
                inst001011();
                break;
            case "001111":
                if (componentesInst.length > 1) inst001111(Integer.parseInt(componentesInst[1]));
                break;
            case "001100":
                inst001100();
                break;
            default:
                System.out.println("Instrução inválida");
                break;
        }
    } catch(NumberFormatException e){ 
        System.out.println("Erro: Formato inválido: " + e.getMessage());
    }
        PC++;
    }

    // Método para exibir o ciclo de instrução específico para cada opcode
    // Método para exibir o ciclo de instrução específico para cada opcode
public void exibeCiclo() {
    // Verifica se o PC está dentro do intervalo de instruções
    if (PC - 1 < 0 || PC - 1 >= instrucoes.size()) {
        System.out.println("Instrução fora do intervalo!");
        return;
    }

    String instrucao = instrucoes.get(PC - 1); // Obtém a instrução atual
    String[] componentesInst = instrucao.split(" "); // Divide a instrução em partes
    String opcode = componentesInst.length > 0 ? componentesInst[0] : ""; // Obtém o opcode da instrução
    String op1 = componentesInst.length > 1 ? componentesInst[1] : ""; // Verifica se há o primeiro operando
    String op2 = componentesInst.length > 2 ? componentesInst[2] : ""; // Verifica se há o segundo operando

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
            if (!op1.isEmpty()) {
                try {
                    int endereco = Integer.parseInt(op1);
                    if (endereco >= 0 && endereco < memoria.length) {
                        MBR = memoria[endereco];
                        System.out.println("MBR <- MEMÓRIA[" + endereco + "]");
                        System.out.println("MBR <- " + MBR);
                    } else {
                        System.out.println("Endereço fora do intervalo da memória!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Formato do endereço inválido!");
                }
            } else {
                System.out.println("Instrução inválida");
            }
            break;
        case "000010":
            if (!op1.isEmpty() && !op2.isEmpty()) {
                try {
                    int pos = Integer.parseInt(op1);
                    int dado = Integer.parseInt(op2);
                    if (pos >= 0 && pos < memoria.length) {
                        memoria[pos] = dado;
                        System.out.println("#POS <- #DADO");
                        System.out.println("memoria[" + pos + "] <- " + dado);
                    } else {
                        System.out.println("Endereço fora do intervalo da memória!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Formato do endereço ou dado inválido!");
                }
            } else {
                System.out.println("Instrução inválida");
            }
            break;
        // Repete o mesmo padrão para outras instruções
        case "000011":
            if (!op1.isEmpty()) {
                int endereco = Integer.parseInt(op1);
                if (endereco >= 0 && endereco < memoria.length) {
                    MBR += memoria[endereco];
                    System.out.println("MBR <- MBR + MEMÓRIA[" + endereco + "]");
                    System.out.println("MBR <- " + MBR);
                } else {
                    System.out.println("Endereço fora do intervalo da memória!");
                }
            } else {
                System.out.println("Instrução inválida");
            }
            break;
        case "000100":
            if (!op1.isEmpty()) {
                int endereco = Integer.parseInt(op1);
                if (endereco >= 0 && endereco < memoria.length) {
                    MBR -= memoria[endereco];
                    System.out.println("MBR <- MBR - MEMÓRIA[" + endereco + "]");
                    System.out.println("MBR <- " + MBR);
                } else {
                    System.out.println("Endereço fora do intervalo da memória!");
                }
            } else {
                System.out.println("Instrução inválida");
            }
            break;
        case "000101":
            if (!op1.isEmpty()) {
                int endereco = Integer.parseInt(op1);
                if (endereco >= 0 && endereco < memoria.length) {
                    MBR *= memoria[endereco];
                    System.out.println("MBR <- MBR * MEMÓRIA[" + endereco + "]");
                    System.out.println("MBR <- " + MBR);
                } else {
                    System.out.println("Endereço fora do intervalo da memória!");
                }
            } else {
                System.out.println("Instrução inválida");
            }
            break;
        case "000110":
            if (!op1.isEmpty()) {
                int endereco = Integer.parseInt(op1);
                if (endereco >= 0 && endereco < memoria.length) {
                    if (memoria[endereco] != 0) {
                        MBR /= memoria[endereco];
                        System.out.println("MBR <- MBR / MEMÓRIA[" + endereco + "]");
                        System.out.println("MBR <- " + MBR);
                    } else {
                        System.out.println("Divisão por zero!");
                    }
                } else {
                    System.out.println("Endereço fora do intervalo da memória!");
                }
            } else {
                System.out.println("Instrução inválida");
            }
            break;
        case "000111":
            if (!op1.isEmpty()) {
                int linha = Integer.parseInt(op1);
                System.out.println("JUMP to " + linha);
                // Ajuste do PC para a linha especificada
                PC = linha;
                if (PC < 0 || PC >= instrucoes.size()) {
                    System.out.println("Endereço fora do intervalo das instruções!");
                }
            } else {
                System.out.println("Instrução inválida");
            }
            break;
        case "001000":
            if (!op1.isEmpty()) {
                int linha = Integer.parseInt(op1);
                if (flagZero) {
                    System.out.println("JUMP IF Z to " + linha);
                    PC = linha;
                    if (PC < 0 || PC >= instrucoes.size()) {
                        System.out.println("Endereço fora do intervalo das instruções!");
                    }
                } else {
                    System.out.println("A condição Z não foi satisfeita!");
                }
            } else {
                System.out.println("Instrução inválida");
            }
            break;
        case "001001":
            if (!op1.isEmpty()) {
                int linha = Integer.parseInt(op1);
                if (flagNegativa) {
                    System.out.println("JUMP IF N to " + linha);
                    PC = linha;
                    if (PC < 0 || PC >= instrucoes.size()) {
                        System.out.println("Endereço fora do intervalo das instruções!");
                    }
                } else {
                    System.out.println("A condição N não foi satisfeita!");
                }
            } else {
                System.out.println("Instrução inválida");
            }
            break;
        case "001010":
            MBR = (int) Math.sqrt(MBR);
            System.out.println("MBR <- sqrt(" + MBR + ")");
            break;
        case "001011":
            MBR = -MBR;
            System.out.println("MBR <- -" + MBR);
            break;
        case "001111":
            if (!op1.isEmpty()) {
                int pos = Integer.parseInt(op1);
                if (pos >= 0 && pos < memoria.length) {
                    memoria[pos] = MBR;
                    System.out.println("#POS <- MBR");
                    System.out.println("memoria[" + pos + "] <- " + MBR);
                } else {
                    System.out.println("Endereço fora do intervalo da memória!");
                }
            } else {
                System.out.println("Instrução inválida");
            }
            break;
        case "001100":
            System.out.println("NOP");
            System.out.println("ENCERRANDO OPERAÇÃO DE DADOS");
            System.out.println("OPERAÇÃO FINALIZADA!");
            System.exit(0);
            break;
        default:
            System.out.println("OPCODE não reconhecido");
            break;
    }

    System.out.println("\nCÁLCULO DO ENDEREÇO DO OPERANDO:");
    System.out.println("Endereço: " + op1);
    System.out.println("\nBUSCANDO O OPERANDO NA POSIÇÃO:");
    System.out.println("MAR: " + op1);
    if (!op2.isEmpty()) {
        System.out.println("\nCÁLCULO DO ENDEREÇO DO SEGUNDO OPERANDO:");
        System.out.println("Endereço: " + op2);
        System.out.println("\nBUSCANDO O SEGUNDO OPERANDO NA POSIÇÃO:");
        System.out.println("MAR: " + op2);
    }
    System.out.println("\nOPERAÇÃO DE DADOS:");

    // Adiciona operações para outros códigos de operação conforme necessário
    switch (opcode) {
        case "000001":
            if (!op1.isEmpty()) {
                System.out.println("VALOR DO MBR: " + MBR);
                System.out.println("VALOR NA MEMÓRIA: " + memoria[Integer.parseInt(op1)]);
            }
            break;
        case "000011":
            // Atualiza a flag de zero
            flagZero = MBR == 0;
            System.out.println("Flag Z (Zero): " + flagZero);
            // Atualiza a flag negativa
            flagNegativa = MBR < 0;
            System.out.println("Flag N (Negativa): " + flagNegativa);
            break;
        case "000100":
            // Atualiza a flag de zero
            flagZero = MBR == 0;
            System.out.println("Flag Z (Zero): " + flagZero);
            // Atualiza a flag negativa
            flagNegativa = MBR < 0;
            System.out.println("Flag N (Negativa): " + flagNegativa);
            break;
        case "000101":
            // Atualiza a flag de zero
            flagZero = MBR == 0;
            System.out.println("Flag Z (Zero): " + flagZero);
            // Atualiza a flag negativa
            flagNegativa = MBR < 0;
            System.out.println("Flag N (Negativa): " + flagNegativa);
            break;
        case "000110":
            // Atualiza a flag de zero
            flagZero = MBR == 0;
            System.out.println("Flag Z (Zero): " + flagZero);
            // Atualiza a flag negativa
            flagNegativa = MBR < 0;
            System.out.println("Flag N (Negativa): " + flagNegativa);
            break;
        default:
            break;
    }

    System.out.println("IR <ENDEREÇO DO OPERANDO>: " + op1);
    System.out.println("MEMÓRIA[" + op1 + "]: " + (op1.isEmpty() ? "N/A" : memoria[Integer.parseInt(op1)]));
    System.out.println("PC <- PC + 1");
    System.out.println("PC: " + (PC + 1));
    System.out.println("==================================================================================");
}

    
    // Implementação dos métodos das instruções
    public void inst000001(int pos) {
        if (pos >= 0 && pos < memoria.length) { // Verifica se o endereço está dentro do intervalo da memória
            MBR = memoria[pos];
        } else {
            System.out.println("Erro: Endereço fora do intervalo da memória.");
        }
    }
    

    public void inst000010(int pos, int dado) {
        memoria[pos] = dado;
    }

    public void inst000011(int pos) {
        MBR += memoria[pos];
        atualizaFlags();
    }

    public void inst000100(int pos) {
        MBR -= memoria[pos];
        atualizaFlags();
    }

    public void inst000101(int pos) {
        MBR *= memoria[pos];
        atualizaFlags();
    }

    public void inst000110(int pos) {
        if (memoria[pos] != 0) {
            MBR /= memoria[pos];
        } else {
            System.out.println("Erro: Divisão por zero.");
        }
        atualizaFlags();
    }

    public void inst000111(int lin) {
        PC = lin - 1; // Subtrai 1 para compensar o incremento no método executaInstrucao
    }

    public void inst001000(int lin) {
        if (flagZero) {
            PC = lin - 1;
        }
    }

    public void inst001001(int lin) {
        if (flagNegativa) {
            PC = lin - 1;
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
        // No Operation (NOP)
    }

    // Método para atualizar as flags de Zero e Negativa
    private void atualizaFlags() {
        flagZero = (MBR == 0);
        flagNegativa = (MBR < 0);
    }
}