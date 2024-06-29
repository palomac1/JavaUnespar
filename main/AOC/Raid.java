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

        StringBuilder sbDisco1 = new StringBuilder();
        for (Character ch : disco1) {
            sbDisco1.append(ch);
        }
        System.out.println("Disco 1: " + sbDisco1.toString());

        StringBuilder sbDisco2 = new StringBuilder();
        for (Character ch : disco2) {
            sbDisco2.append(ch);
        }
        System.out.println("Disco 2: " + sbDisco2.toString());

        // RAID 1
        System.out.println("\nRAID 1: ");
        System.out.println("Disco 1: " + frase);
        System.out.println("Disco 2: " + frase);

        // RAID 4
        disco1.clear();
        disco2.clear();
        disco3.clear();
        paridade.clear();

        System.out.println("\nRAID 4: ");
        for (int i = 0; i < letras.length; i++) {
            if (i % 3 == 0) { 
                disco1.add(letras[i]);
            } else if (i % 3 == 1) {
                disco2.add(letras[i]);
            } else {
                disco3.add(letras[i]);
                paridade.add((char) (letras[i - 2] ^ letras[i - 1] ^ letras[i]));
            }
        }

        if (letras.length % 3 != 0) {
            paridade.add((char) (letras[letras.length - 1] ^ letras[letras.length - 2]));
        }

        
        System.out.println("Disco 1: " + sbDisco1.toString());
        System.out.println("Disco 2: " + sbDisco2.toString());

        StringBuilder sbDisco3 = new StringBuilder();
        for (Character ch : disco3) {
            sbDisco3.append(ch);
        }
        System.out.println("Disco 3: " + sbDisco3.toString());

        StringBuilder sbParidade = new StringBuilder();
        for (Character ch : paridade) {
            sbParidade.append(ch);
        }
        System.out.println("Paridade: " + sbParidade.toString());


        for (int i = 0; i < paridade.size(); i++) {
            char valorReconstruido = (char) (disco1.get(i) ^ disco2.get(i) ^ paridade.get(i));
            discoReconstruido.add(valorReconstruido);
        }

        StringBuilder sbDiscoReconstruido = new StringBuilder();
        for (Character ch : discoReconstruido) {
            sbDiscoReconstruido.append(ch);
        }

        System.out.println("\nRECONSTRUÇÃO: \nDisco 1: " + sbDisco1.toString());
        System.out.println("Disco 2: " + sbDisco2.toString());
        System.out.println("Paridade: " + sbParidade.toString());
        System.out.println("Disco Reconstruído: " + sbDiscoReconstruido.toString());

        sc.close();
    }
}
