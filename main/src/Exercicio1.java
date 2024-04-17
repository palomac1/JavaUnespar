import java.util.Scanner;

public class Exercicio1 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Digite os pontos do time líder do campeonato: ");
        int pontosLider = sc.nextInt();

        System.out.print("Digite os pontos do time lanterna: ");
        int pontosLanterna = sc.nextInt();

        int diferencaPontos = pontosLider - pontosLanterna;
        int vitoriasNecessarias = diferencaPontos / 3;

        System.out.println("O lanterna precisa de " + vitoriasNecessarias + " vitórias para alcançar o time líder do campeonato.");
    }
}