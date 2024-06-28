package AOC;

import java.util.*;

public class Raid {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<Character> disco1 = new ArrayList<>();
        List<Character> disco2 = new ArrayList<>();
        List<Character> disco3 = new ArrayList<>();
        List<Character> paridade = new ArrayList<>();
        List<Character> discoReconstruido = new ArrayList<>();


        System.out.println("Digite a frase: ");
        String frase = sc.nextLine();

        // RAID 0
        System.out.println("\nRAID 0: ");
        char[] letras = frase.toCharArray();

        for (int i = 0; i < letras.length; i++) {
            if (i % 2 == 0) {
                disco1.add(letras[i]);
            } else {
                disco2.add(letras[i]);
            }
        }

        System.out.println("Disco 1: " + disco1);
        System.out.println("Disco 2: " + disco2);

        // RAID 1
        System.out.println("\nRAID 1: ");
        System.out.println("Disco 1: " + frase);
        System.out.println("Disco 2: " + frase);
        System.out.println(frase);

        // RAID 4
        disco1.clear();
        disco2.clear();
        paridade.clear();

        System.out.println("\nRAID 4: ");
        for (int i = 0; i < letras.length; i++) {
            if (i % 3 == 0) { // Calcula a paridade a cada 3 letras e armazena no disco 3
                disco1.add(letras[i]);
            } else if (i % 3 == 1) {
                disco2.add(letras[i]);
            } else {
                disco3.add(letras[i]);
            }
            if (i % 3 == 2 && i + 2 < letras.length) { // Calcula a paridade a cada 3 letras
                paridade.add((char) (letras[i - 2] ^ letras[i - 1] ^ letras[i]));
            }
        }

        System.out.println("Disco 1: " + disco1);
        System.out.println("Disco 2: " + disco2);
        System.out.println("Disco 3: " + disco3);
        System.out.println("Paridade: " + paridade);

        for (int i = 0; i < paridade.size(); i++) {
            char valorReconstruido = (char) (disco1.get(i) ^ disco2.get(i) ^ paridade.get(i));
            discoReconstruido.add(valorReconstruido);
        }

        System.out.println("Disco Reconstruído: " + discoReconstruido);

        sc.close();
    }
}
