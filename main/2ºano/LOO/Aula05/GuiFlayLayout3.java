import java.awt.*;
import javax.swing.*;

public class GuiFlayLayout3 extends JPanel {
    
    // Remove the declaration of the unused field
    // private JButton[] botoes = new JButton[6];
    public GuiFlayLayout3() {
        
        inicializarComponentes();

    }

    private void inicializarComponentes() {
        setLayout(new FlowLayout(1, 20, 40));
        for (int i = 1; i <= 6; i++) {
            add(new JButton("Botão " + i));
        }
    }
}