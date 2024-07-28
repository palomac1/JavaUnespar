package AOC.CicloDeInstrucao;

/* Interface para representar o ciclo de instrução e suas operações, onde cada operação é uma instrução e cada instrução
é um método, assim podemos apenas chamar o método para executar a instrução desejada e exibir o ciclo de instrução */

public interface CicloInstrucao {
     
    void entradaUsuario(); // Reccebe a entrada do usuário por meio de um scanner e armazena as instruções em uma lista de strings chamada instrucoes
    void inst000001(int pos); // MBR <- #POS
    void inst000010(int pos, int dado); // POS <- #DADO
    void inst000011(int pos); // MBR <- MBR + #POS
    void inst000100(int pos); // MBR <- MBR - #POS
    void inst000101(int pos); // MBR <- MBR * #POS
    void inst000110(int pos); // MBR <- MBR / #POS
    void inst000111(int lin); // JUMP to #LIN
    void inst001000(int lin); // JUMP IF Z to #LIN
    void inst001001(int lin); // JUMP IF N to #LIN
    void inst001010(); // MBR <- raiz_qiadrada(MBR)
    void inst001011(); // MBR <- - MBR
    void inst001111(int pos); // #POS <- MBR
    void inst001100(); // NOP
    void executaInstrucao(String instrucao); // Executa a instrução passada como parâmetro 
    void exibeCiclo(); // Exibe o ciclo de instrução
}



 