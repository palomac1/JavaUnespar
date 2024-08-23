package Física;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RelogioComTorque extends JFrame implements ActionListener {

    private JTextField raioField, correnteField, campoMagneticoField, espirasField, anguloInicialField;
    private JTextArea resultadoArea;
    private JButton calcularButton, resetarButton;
    private RelogioPanel relogioPanel;
    private int numeroEspiras; // Número de espiras para a visualização
    private double raioBobina; // Armazenar o raio da bobina para desenhar as espiras

    public RelogioComTorque() {

        // Configurações do JFrame e do painel de entrada
        setTitle("Relógio com Torque");
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        setLayout(new BorderLayout());

        // Painel de Entrada e campos de texto
        inputPanel.add(new JLabel("Digite o raio da bobina (r) em cm:"));
        raioField = new JTextField();
        inputPanel.add(raioField);

        inputPanel.add(new JLabel("Digite a corrente (I) em A:"));
        correnteField = new JTextField();
        inputPanel.add(correnteField);

        inputPanel.add(new JLabel("Digite o campo magnético (B) em mT:"));
        campoMagneticoField = new JTextField();
        inputPanel.add(campoMagneticoField);

        inputPanel.add(new JLabel("Digite o número de espiras (n):"));
        espirasField = new JTextField();
        inputPanel.add(espirasField);

        inputPanel.add(new JLabel("Digite o ângulo inicial (θ) em graus:"));
        anguloInicialField = new JTextField();
        inputPanel.add(anguloInicialField);

        calcularButton = new JButton("Calcular");
        calcularButton.addActionListener(this);
        inputPanel.add(calcularButton);

        resetarButton = new JButton("Resetar");
        resetarButton.addActionListener(this);
        inputPanel.add(resetarButton);

        // Área que mostra os resultados dos cálculos
        resultadoArea = new JTextArea(5, 20);
        resultadoArea.setEditable(false);
        JScrollPane resultadoScrollPane = new JScrollPane(resultadoArea);
        add(resultadoScrollPane, BorderLayout.SOUTH);

        // Painel que desenha o relógio e o ímã
        relogioPanel = new RelogioPanel();
        add(relogioPanel, BorderLayout.CENTER);

        add(inputPanel, BorderLayout.NORTH); // Adiciona o painel de entrada ao JFrame
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == calcularButton) {
            calcular();
        } else if (e.getSource() == resetarButton) {
            resetar();
        }
    }

    private void calcular() {
        try {
            raioBobina = Double.parseDouble(raioField.getText()) / 100; // Converter para metros
            double I = Double.parseDouble(correnteField.getText());
            double B = Double.parseDouble(campoMagneticoField.getText()) / 1000; // Converter para Tesla ( = 1 mT)
            numeroEspiras = Integer.parseInt(espirasField.getText());
            double θInicial = Double.parseDouble(anguloInicialField.getText());

            // Cálculo da área da espira da bobina 
            double A = Math.PI * raioBobina * raioBobina;

            // Cálculo do torque
            double torque = numeroEspiras * I * A * B * Math.sin(Math.toRadians(θInicial));

            // Cálculo do tempo até que o torque seja exercido, supondo que o ponteiro se move a uma taxa constante de 0,5 graus por minuto
            double taxaMovimento = 0.5; // graus por minuto
            double tempo = Math.abs(θInicial / taxaMovimento); // minutos

            // Cálculo do tempo em horas e minutos apos o torque ser exercido
            int horas = 13;
            int minutos = (int) Math.round(tempo);

            while (minutos >= 60) {
                minutos -= 60;
                horas += 1;
            }
            horas = horas % 12 == 0 ? 12 : horas % 12;

            // Resultados
            StringBuilder resultado = new StringBuilder();
            resultado.append(String.format("\nTorque exercido sobre a bobina: %.5f N·m%n", torque));
            resultado.append(String.format("\nO ponteiro das horas aponta na direção do torque às %02d:%02d%n", horas, minutos));

            resultadoArea.setText(resultado.toString());

            // Atualizar o painel do relógio com o novo tempo
            relogioPanel.setHoraMinuto(horas, minutos);
            relogioPanel.repaint();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Insira um valor válido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Limpar os campos de texto e a área de resultados
    private void resetar() {
        raioField.setText("");
        correnteField.setText("");
        campoMagneticoField.setText("");
        espirasField.setText("");
        anguloInicialField.setText("");
        resultadoArea.setText("");
        relogioPanel.setHoraMinuto(0, 0);
        relogioPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                RelogioComTorque frame = new RelogioComTorque();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(600, 600);
                frame.setVisible(true);
            }
        });
    }

    // Desenho do relógio e do ímã
    private class RelogioPanel extends JPanel {
        private int horas;
        private int minutos;

        // Inicializar o relógio com o tempo 12:00
        public void setHoraMinuto(int horas, int minutos) {
            this.horas = horas;
            this.minutos = minutos;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
        
            Graphics2D g2d = (Graphics2D) g.create(); // Criar uma cópia do Graphics2D para manipulação
        
            // Desenha o fundo do relógio
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            int raioRelogio = 100; // Raio do relógio em pixels
        
            g2d.setColor(Color.WHITE);
            g2d.fillOval(centerX - raioRelogio, centerY - raioRelogio, 2 * raioRelogio, 2 * raioRelogio);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(centerX - raioRelogio, centerY - raioRelogio, 2 * raioRelogio, 2 * raioRelogio);
        
            // Desenhar as espiras fora do relógio
            for (int i = 0; i < numeroEspiras; i++) {
                int espiraRaio = raioRelogio + (i + 1) * 15; // Espiras fora do relógio
                g2d.drawOval(centerX - espiraRaio, centerY - espiraRaio, 2 * espiraRaio, 2 * espiraRaio);
            }
        
            // Desenhar os números do relógio
            for (int i = 1; i <= 12; i++) {
                int angulo = 360 / 12 * i;
                double radianos = Math.toRadians(angulo);
                int x = (int) (centerX + (raioRelogio - 20) * Math.sin(radianos));
                int y = (int) (centerY - (raioRelogio - 20) * Math.cos(radianos));
                g2d.drawString(Integer.toString(i), x - 5, y + 5);
            }
        
            // Desenhar o ponteiro das horas
            double anguloHoras = 360 / 12 * (horas % 12) + 360 / 12 * (minutos / 60.0);
            double radHoras = Math.toRadians(anguloHoras);
            int xHoras = (int) (centerX + (raioRelogio - 50) * Math.sin(radHoras));
            int yHoras = (int) (centerY - (raioRelogio - 50) * Math.cos(radHoras));
            g2d.drawLine(centerX, centerY, xHoras, yHoras);
        
            // Desenhar o ponteiro dos minutos
            double anguloMinutos = 360 / 60 * minutos;
            double radMinutos = Math.toRadians(anguloMinutos);
            int xMinutos = (int) (centerX + (raioRelogio - 30) * Math.sin(radMinutos));
            int yMinutos = (int) (centerY - (raioRelogio - 30) * Math.cos(radMinutos));
            g2d.drawLine(centerX, centerY, xMinutos, yMinutos);
        
            // Desenhar o ímã representando o campo magnético com rotação 
            g2d.setColor(Color.RED);
            int imãX = (int) (centerX + (raioRelogio + 20) * Math.sin(radHoras)); // Usa o centerX + para inverter a direção do ímã, já que o eixo x cresce para a direita, assim o ímã aponta para o ponteiro das horas
            int imãY = (int) (centerY - (raioRelogio + 20) * Math.cos(radHoras)); // Usa o centerY - para inverter a direção do ímã ja que o eixo y cresce para baixo, assim o ímã aponta para o ponteiro das horas
            // Transladar e rotacionar para alinhar o ímã com o ponteiro das horas
            g2d.translate(imãX, imãY); // Gira o imã para a direção do ponteiro das horas, que é o torque
            g2d.rotate(radHoras); // Gira o imã para a diireção do torque, usando o ângulo do ponteiro das horas como referência
            
            // Desenhar o ímã como um retângulo rotacionado
            g2d.fillRect(-20, -5, 40, 10); 
        
            g2d.dispose(); // Liberar o contexto gráfico para economizar memória 
        }
        
    }
}
