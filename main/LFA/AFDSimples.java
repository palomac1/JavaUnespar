package main.LFA;

import java.util.Scanner;

public class AFDSimples {
    //E = {q0, q1}...
    //Sigma = {0, 1}
    static int [][] transição = {{0, 1}, {1, 0}}; //Variavel para a tabela
    static int estadoInicial = 0; //Variavel para o estado inicial
    static int [] aceitacao = {1}; //Variavel com os estados aceitos
    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        String cadeia;
        int posicao = 0, estado = estadoInicial;
        char simbolo;

        System.out.println("Informe uma cadeia: ");
        cadeia = leitor.nextLine();

        //Executa o while até que a cadeia seja totalmente lida
        while (posicao<cadeia.length()) {
            imprimeCI(cadeia, estado, posicao);

            //Comparação da tabela de transição
            simbolo = cadeia.charAt(posicao); 
            int simboloInt = Integer.parseInt(simbolo+"");//Passa de string para inteiro (Concatenar com string vazia  para melhor funcionamento, já que aceita string como entrada, mas estamos passando um char)
            estado = transição[estado] /*linha*/ [simboloInt] /*coluna*/; //Atualiza o estado com base na transição
            posicao++; //Acessa toda a cadeia
        }

        imprimeCI(cadeia, estado, posicao);
        //Verifica o conjunto de caracteres que pode ser aceito
        boolean aceita = false;

        //Percorre todos os estados de aceitação e vê qual finaliza a execução
        for (int i: aceitacao){
            if(estado == i) aceita = true; //Se for igual, a cadeia é aceita
        }

            if (aceita) {
                System.out.println("Aceita");
            } else {
                System.out.println("Nao Aceita");
            }
    }

    //Função de configuração instantânea para realizar a leitura de cada simbolo da cadeia
    public static void imprimeCI(String cadeia, int estado, int posicao){
        //Imprime a cadeia que esta sendo lida de acordo com a mudança de estados
            System.out.print(cadeia.substring(0, posicao));
        //Diz qual o estado atual
            System.out.print("[q"+estado+"]");
        //Informa a cadeia que falta
            System.out.println(cadeia.substring(posicao));
    }
}




