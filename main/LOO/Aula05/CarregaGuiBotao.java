import javax.swing.JFrame;

public class CarregaGuiBotao{
    public static void main(String[] args){
        JFrame frame = new JFrame ("Uso de botões");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add (new GuiBotao());
        frame.setBounds(0,0,500,300);
        frame.setVisible (true);
    }
}
