package LOO.Aula050607;

import javax.swing.JFrame;

public class CarregaGuiDialogoMensagem{
    public static void main(String[] args){
        //Novo frame com título da classe a ser testada
        JFrame frame = new JFrame ("Uso de dialogo de informação");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        // Nova instancia da Classe a ser testada
        frame.getContentPane().add (new GuiDialogoMensagem());
        frame.setBounds(0,0,500,300);
        frame.setVisible (true);
    }
}
