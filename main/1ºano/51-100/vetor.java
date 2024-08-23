import java.util.Scanner;
public class vetor {
    public static void main (String [] args){
        Scanner s = new Scanner (System.in);

        int n[] = new int[4];

        for (int i = 0; i < 4; i++){
            System.out.println("Digite um numero: ");
            n[i] = s.nextInt();
        }
    }
    
}
