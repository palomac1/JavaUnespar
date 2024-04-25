package LOO.Aula24;

import javax.swing.JOptionPane;

public class exc5 {

    public static void main(String[] args) {

        String frase = JOptionPane.showInputDialog("Digite uma frase:");

        String fraseInvertida = "";
        for (int i = frase.length() - 1; i >= 0; i--) {
            fraseInvertida += frase.charAt(i);
        }

        JOptionPane.showMessageDialog(null, "Frase invertida: " + fraseInvertida);
    }
}