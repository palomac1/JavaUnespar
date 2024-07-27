import java.awt.*;
import javax.swing.*;

public class TelaInicial extends JFrame {
    private ImageIcon imageIcon1;
    private JLabel imageLabel;
    private JButton okButton;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                TelaInicial frame = new TelaInicial();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(500, 500); // Definir o tamanho da janela
                Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
                frame.setLocation((tela.width - frame.getSize().width) / 2,
                        (tela.height - frame.getSize().height) / 2);
                frame.setVisible(true);
            }
        });
    }

    public TelaInicial() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espaçamento entre os componentes

        String path1 = "FundoInicial.gif";
        imageIcon1 = new ImageIcon(getClass().getResource(path1));

        if (imageIcon1.getIconWidth() != -1) { // Verificar se a imagem foi carregada corretamente
            imageLabel = new JLabel(imageIcon1);
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            imageLabel.setVerticalAlignment(JLabel.CENTER);

            JPanel imagePanel = new JPanel();
            imagePanel.setLayout(new BorderLayout());
            imagePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Adiciona margens ao painel de imagem
            imagePanel.setBackground(Color.BLACK); // Define o fundo do painel de imagem como preto
            imagePanel.add(imageLabel, BorderLayout.CENTER);

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 1;
            gbc.gridheight = 1;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.anchor = GridBagConstraints.CENTER;
            add(imagePanel, gbc);
        } else {
            System.out.println("Imagem não encontrada: " + path1);
        }

     // Define o fundo da janela como preto
        getContentPane().setBackground(Color.BLACK);

        // Botão 'Iniciar'
        okButton = new JButton("INICIAR"); 
        okButton.setFont(new Font("Space Mono", Font.BOLD, 20)); // Define a fonte do texto do botão
        okButton.setPreferredSize(new Dimension(600, 200)); // Define tamanho maior do botão
        okButton.setBackground(new Color(128, 0, 128)); // Define a cor do botão para roxo
        okButton.setForeground(Color.WHITE); // Define a cor do texto do botão para branco
        okButton.setBorderPainted(false); // Remove a borda ao redor do texto
        okButton.setFocusPainted(false); // Remove a borda de foco ao redor do texto

        gbc.gridx = 0; // Posição do botão na horizontal
        gbc.gridy = 1; // Posição do botão na vertical (abaixo do GIF)
        gbc.gridwidth = 2; // Largura do botão
        gbc.gridheight = 1; // Altura do botão
        gbc.weightx = 1.0; // Peso do botão na horizontal
        gbc.weighty = 1.0; // Peso do botão na vertical
        gbc.insets = new Insets(5, 0, 0, 0); // Espaço acima do botão
        gbc.anchor = GridBagConstraints.CENTER;
        add(okButton, gbc);

        okButton.addActionListener(e -> System.exit(0)); // Fecha a aplicação quando o botão é clicado
    }
}
