
public class Bomba {

    public void ligar(int tempo){

        for(int i =  tempo; i > 0; i--){

            System.out.println("Bomba ligada. Tempo restante: " + i + " segundos");
        }

        desligar();

    }

    public void desligar(){

        System.out.println("Bomba desligada");

    }
    public static void main(String[] args){
        
        Bomba bomba = new Bomba();
        bomba.ligar(10);

    }
}