package LOO.Aula03;

import javax.swing.JOptionPane;

public class exc1 {
    public static void main(String[] args) {

        int[] notas = new int[5];

        for (int i = 0; i < 5; i++) {
                String notaStr = JOptionPane.showInputDialog("Digite as 5 notas:");
                notas[i] = Integer.parseInt(notaStr);
            }

        for (int i = 0; i < 5; i++) {
            for (int j = i + 1; j < 5; j++) {
                if (notas[i] < notas[j]) {
                    int temp = notas[i];
                    notas[i] = notas[j];
                    notas[j] = temp;
                }
            }
        }

        double media = 0;

        for (int nota : notas) {
            media += nota;
        }

        media /= 5;

        System.out.println("Notas em ordem decrescente:");

        for (int nota : notas) {
            System.out.println(nota);
        }

        System.out.println("Média aritmética: " + media);
    }
}
