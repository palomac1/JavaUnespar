package LOO.Aula050607;
import javax.swing.JFrame;

public class CarregaGuiCaixaOpcao{
    public static void main(String[] args){
        JFrame frame = new JFrame ("Uso de caixa de opção");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add (new GuiCaixaOpcao());
        frame.setBounds(0,0,500,300);
        frame.setVisible (true);
    }
}
