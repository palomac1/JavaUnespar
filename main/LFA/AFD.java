package main.LFA;

import java.util.Scanner;

//E = {q0, q1}
//I = q0
//F = q1
//Sigma = {0, 1}
public class AFD {
    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        String cadeia;
        int posicao = 0, estado = 0;
        char simbolo;

        System.out.println("Informe uma cadeia: ");
        cadeia = leitor.nextLine();

        //Executa o while até que a cadeia seja totalmente lida
        while (posicao<cadeia.length()) {
            imprimeCI(cadeia, estado, posicao);

            //Comparação da tabela de transição - aqui ja e esepcifico pra linguagem do cara
            /*  0   1
            q0  q0  q1
            q1  q1  q0 */    
            simbolo = cadeia.charAt(posicao);

            if(estado == 0 && simbolo == '0'){//se o simbolo for zero, permanece em q0
                estado = 0;
            } else if (estado == 0 && simbolo == '1'){
                estado = 1;
            } else if (estado == 1 && simbolo == '0'){
                estado = 1;
            } else if (estado == 1 && simbolo == '1'){
                estado = 0;
            }
            posicao++; //Acessa toda a cadeia
        }

        imprimeCI(cadeia, estado, posicao);
            if (estado == 1) {
                System.out.println("Aceita");
            } else if (estado == 0){
                System.out.println("Nao Aceita");
            }
    }

    //Procedimento de configuração instantânea para realizar a leitura de cada simbolo da cadeia
    public static void imprimeCI(String cadeia, int estado, int posicao){
        //Imprime a cadeia que esta sendo lida de acordo com a mudança de estados
            System.out.print(cadeia.substring(0, posicao));
        //Diz qual o estado atual
            System.out.print("[q"+estado+"]");
        //Informa a cadeia que resta
            System.out.println(cadeia.substring(posicao));
    }
}



