package Swing;

import javax.swing.*;
import java.awt.event.*; //Classe de interfaces

public class FrameExample implements ActionListener{ //Implementa na classe antes de usar o programa
    JButton b;
    FrameExample(){
        JFrame f = new JFrame("Swing"); // Titulo do Frame 
        b = new JButton("Clique aqui"); // Cria o botão
        f.add(b); // Add o componente no frame
        f.setSize(200, 200); // Tam. da janela
        b.setBounds(50,50,100,30); // Especifica a posição e o tam.
        f.setLayout(null); 
        f.setVisible(true); //Deixa o frame visivel na tela
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Fecha a janela e termina a app
        b.addActionListener(this);
        /*b.addActionListener(new ActionListener() { //Implementa usando uma classe anônima
            public void actionPerformed (ActionEvent ae) {
                b.setText("Olá, clique aqui");
            }
        }); */
    }

    public void actionPerformed(ActionEvent e){
        b.setText("Olá, clique aqui");
    }
    public static void main(String[] args) {
       
        new FrameExample();
    }
}
