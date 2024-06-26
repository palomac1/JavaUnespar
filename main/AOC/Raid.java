package AOC;

import java.util.*;

public class Raid {
        public static void main(String[] args) {
    
            Scanner sc = new Scanner(System.in);
    
            // RAID 0
            List<Character> disco1 = new ArrayList<>();
            List<Character> disco2 = new ArrayList<>();
            List<Character> disco3 = new ArrayList<>();

            System.out.println("Digite a frase: ");
            String frase = sc.nextLine();
    
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

            //RAID 1
            System.out.println("\nRAID 1: ");
            System.out.println("Disco 1: " + frase);
            System.out.println("Disco 2: " + frase);
            System.out.println(frase);
            
            //RAID 4
            System.out.println("\nRAID 4: ");
                for (int i = 0; i < letras.length; i++) {
                    if (i % 3 == 0) {
                        disco1.add(letras[i]);
                    } else if (i % 3 != 0) {
                        if(i % 2 == 0){
                            disco2.add(letras[i]);
                        } else {
                            disco3.add(letras[i]);
                        }  
                    } 
                }

            System.out.println("Disco 1: " + disco1);
            System.out.println("Disco 2: " + disco2);
            System.out.println("Disco 3: " + disco3);

        }
}



// Recupera contando os binarios
// par de 1 = 0
// impar de 1 = 1
// par de 0 = 1
// impar de 0 = 0
// ^ passa para xor 