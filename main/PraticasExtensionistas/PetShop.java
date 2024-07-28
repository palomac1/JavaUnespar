import javax.swing.*;

public class PetShop {
    public static void main(String[] args) {
        while (true) {
            String[] options = {"Anfíbios", "Invertebrados", "Mamíferos", "Peixes", "Répteis", "Sair"};
            int choice = JOptionPane.showOptionDialog(null, "Escolha uma categoria de animais para começar:", "Jogo de Adivinhação",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0:
                    Anfibios.main(args);
                    break;
                case 1:
                    Invertebrados.main(args);
                    break;
                case 2:
                    Mamiferos.main(args);
                    break;
                case 3:
                    Peixe.peixe();
                    break;
                case 4:
                    Repteis.repteis();
                    break;
                case 5:
                    System.exit(0);
                default:
                    break;
            }
        }
    }
}
