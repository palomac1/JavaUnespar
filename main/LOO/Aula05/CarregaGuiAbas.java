import javax.swing.JFrame;

public class CarregaGuiAbas {

    public static void main(String[] args){

        JFrame frame = new JFrame ("Uso de texto");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add (new GuiAbas());
        frame.setBounds(0,0,500,300);

        frame.setVisible (true);
    }
}