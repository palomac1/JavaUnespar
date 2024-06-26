import java.awt.event.*;
import javax.swing.*;
public class GuiBotao extends JPanel{
    private JButton btMensagem, btTeimoso;

    public GuiBotao() {
        inicializarComponentes();
        definirEventos();
    }

    private void inicializarComponentes() {
        setLayout(null);
        String path = "sair.png";
        btMensagem = new JButton("Mensagem", (new ImageIcon(getClass().getResource(path))));
        btMensagem.setBounds(50, 20, 140, 38);
        btMensagem.setMnemonic(KeyEvent.VK_M);
        btMensagem.setToolTipText("Clique aqui para ver a mensagem");
        btTeimoso = new JButton("Teimoso");
        btTeimoso.setBounds(50, 70, 100, 25);
        add(btMensagem);
        add(btTeimoso);
    }

    private void definirEventos() {
        btMensagem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Botão Mensagem");
            }
        });

		btTeimoso.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) { 
                    JOptionPane.showMessageDialog(null, "Botão Teimoso!");
                }
        });

		btTeimoso.addMouseListener(new MouseListener() {
                public void mouseClicked(MouseEvent e) {}
                public void mousePressed(MouseEvent e) {}
                public void mouseReleased(MouseEvent e) {}
                public void mouseEntered(MouseEvent e) {
                    btTeimoso.setBounds(50, 120, 100, 25);
                }
                public void mouseExited(MouseEvent e) {
                    btTeimoso.setBounds(50, 70, 100, 25);
                }
        });
    }
}